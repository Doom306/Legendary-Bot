package com.doom.commands.commands;

import com.doom.commands.CommandContext;
import com.doom.commands.ICommand;
import com.doom.database.Store;
import com.doom.database.Wow;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class EventWaiterCommand implements ICommand {

    private final String EMOTE = "😋";
    private final EventWaiter waiter;

    public EventWaiterCommand(EventWaiter waiter) {
        this.waiter = waiter;
    }

    @Override
    public void handle(CommandContext ctx) {
        Wow wow = new Wow();
        Store store = wow.store;
        int stored = store.getStored();

        final TextChannel channel = ctx.getChannel();

        if (ctx.getMessage().getMentionedChannels().isEmpty()) {
            channel.sendMessage("`No channels mentioned`").queue();
            return;
        }

        store.setMentionedChannels(stored++, ctx.getMessage().getMentionedChannels());
        store.setMentionedRoles(stored++, ctx.getMessage().getMentionedRoles());
        store.setStored(stored++);

        final List<TextChannel> mentionedChannels = store.getMentionedChannels();
        final List<Role> mentionedRoles = store.getMentionedRoles();

        int finalStored = stored;
        mentionedChannels.get(stored).sendMessage("React with ")
                .append(EMOTE)
                .append(" to get the role of ")
                .append(mentionedRoles.get(stored).getAsMention())
                .append(".")
                .queue((message) -> {
                    message.addReaction(EMOTE).queue();

                    this.waiter.waitForEvent(
                            GuildMessageReactionAddEvent.class,
                            (e) -> e.getMessageIdLong() == message.getIdLong() && !e.getUser().isBot(),
                            (e) -> {
                                e.getGuild().addRoleToMember(e.getMember(), mentionedRoles.get(finalStored)).queue();
                            },
                             1460L, TimeUnit.DAYS,
                            () -> mentionedChannels.get(finalStored).sendMessage("You waited too long").queue()
                    );
                });

        channel.sendMessage("Created successfully (The message will expire within 4 years)").queue();
    }

    @Override
    public String getName() {
        return "roles";
    }

    @Override
    public String getHelp() {
        return "Gives a role to the user if he reacts to it\n" +
                "Usage: `/roles [Mention the role] [Mention the channel]`";
    }
}
