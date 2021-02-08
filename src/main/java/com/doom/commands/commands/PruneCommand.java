package com.doom.commands.commands;

import com.doom.commands.CommandContext;
import com.doom.commands.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class PruneCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        TextChannel tc = ctx.getChannel();

        if (!ctx.getMember().hasPermission(Permission.KICK_MEMBERS)) {
            ctx.getChannel().sendMessage("Insufficient rights. You have don't have Kick Permission rights.\n" +
                    "Kindly use `/clear` instead.").queue();
            return;
        }
        if (ctx.getArgs().isEmpty()) {

            deleteMemberMessage(tc, ctx.getJDA().getSelfUser());

        } else if ("chat".equals(ctx.getArgs().get(0))) {
            String msg = "";
            for (int i = 0; i < 100; i++) {
                msg += "\n\n";
            }
            ctx.getChannel().sendMessage("Hold on..." + msg).queue();
        } else {
            if (!ctx.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) {
                tc.sendMessage("🛑 I do not have the `Manage Message` and `Message History` Permission!").queue();
                return;
            } else if (!ctx.getMember().hasPermission(ctx.getChannel(), Permission.MESSAGE_MANAGE)) {
                tc.sendMessage( "🛑 You need to have the `Manage Message` and `Message History` Permission!").queue();
                return;
            }

            try {
                if ("bot".equals(ctx.getArgs().get(0))) {
                    deleteBotMessage(tc);
                } else {
                    List<User> users = ctx.getMessage().getMentionedUsers();
                    deleteMemberMessage(tc, users.toArray(new User[users.size()]));

                    List<Role> roles = ctx.getMessage().getMentionedRoles();
                    deleteRoleMessage(tc, roles.toArray(new Role[roles.size()]));
                }
            } catch (IllegalArgumentException lae) {
                tc.sendMessage("🛑 Looks like there is no message to delete...").queue();
                return;
            }
            ctx.getMessage().delete().queueAfter(5, TimeUnit.SECONDS);
        }
    }

    private void deleteMemberMessage(TextChannel tc, User... users)
    {
        List<Message> messages = new ArrayList<>();
        for (User user : users) {
            List<Message> newMsg = tc.getIterableHistory()
                    .cache(false)
                    .stream()
                    .limit(1000)
                    .filter(message -> message.getAuthor().getId().equals(user.getId()))
                    .filter(message -> ChronoUnit.WEEKS.between(message.getTimeCreated(), ZonedDateTime.now()) < 2)
                    .collect(Collectors.toList());
            messages.addAll(newMsg);
        }
        messages = messages.size() > 100 ? messages.subList(0, 100) : messages;
        int size = messages.size();

        tc.deleteMessages(messages).queue(
                success -> tc.sendMessage("✔ Cleaned up " + size + " message(s) from " + users.length + " member(s).").queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS))
        );
    }

    private void deleteRoleMessage(TextChannel tc, Role... roles)
    {
        List<Message> messages;
        messages = new ArrayList<>();
        for (Role role : roles) {
            List<Message> newMsg = tc.getIterableHistory()
                    .cache(false)
                    .stream()
                    .limit(1000)
                    .filter(message -> !message.getMember().getRoles().isEmpty() && !message.getMember().getRoles().contains(role))
                    .filter(message -> ChronoUnit.WEEKS.between(message.getTimeCreated(), ZonedDateTime.now()) < 2)
                    .collect(Collectors.toList());
            messages.addAll(newMsg);
        }

        messages = messages.size() > 100 ? messages.subList(0, 100) : messages;
        int size = messages.size();
        tc.deleteMessages(messages).queue(
                success -> tc.sendMessage("✔ Cleaned up " + size + " message(s) from " + roles.length + " role(s).").queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS))
        );
    }

    private void deleteBotMessage(TextChannel tc)
    {
        List<Message> messages = tc.getIterableHistory()
                .cache(false)
                .stream()
                .limit(1000)
                .filter(message -> message.getAuthor().isBot())
                .filter(message -> ChronoUnit.WEEKS.between(message.getTimeCreated(), ZonedDateTime.now())<2)
                .collect(Collectors.toList());
        messages = messages.size() > 100 ? messages.subList(0, 100) : messages;
        int size = messages.size();

        tc.deleteMessages(messages).queue(
                success -> tc.sendMessage("✔ Cleaned up "+size+" message(s) from bots.").queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS))
        );
    }

    @Override
    public String getName() {
        return "delete";
    }

    @Override
    public String getHelp() {
        return "Deletes messages depending on what you imputed\n" +
                "Usage: `/delete`\n" +
                "Parameters: `bot` | `@Member(s)` | `@Role(s)` | `chat`";
    }
}
