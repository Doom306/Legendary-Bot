package com.doom.commands.commands;

import com.doom.Listener;
import com.doom.commands.CommandContext;
import com.doom.commands.ICommand;
import com.doom.commands.commands.Emoji.Emoji;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SoftBanCommand implements ICommand {
    private final int delDays = 7;
    private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);

    @Override
    public void handle(CommandContext e) {
        if(e.getArgs().isEmpty()) {
            e.getChannel().sendMessage(getHelp()).queue();
            return;
        }

        if(e.getArgs().size() == 0) {
            e.getChannel().sendMessage(Emoji.ERROR + " You need to mention 1 or more members to soft ban!").queue();
        }

        else {
            Guild guild = e.getGuild();
            Member selfMember = guild.getSelfMember();

            //Check if the bot have permission to kick.
            if (!selfMember.hasPermission(Permission.BAN_MEMBERS)) {
                e.getChannel().sendMessage(Emoji.ERROR + " I need to have **Ban Members** Permission to soft ban members.").queue();
                return;
            } else if (!e.getMember().hasPermission(Permission.BAN_MEMBERS)) {
                e.getChannel().sendMessage(Emoji.ERROR + " You need to have **Ban Members** Permission to soft ban members.").queue();
                return;
            }

            List<User> mentionedUsers = e.getMessage().getMentionedUsers();
            LOGGER.info(this.getClass().getName(), "Called to soft ban " + mentionedUsers.size() + " users.");

            for (User user : mentionedUsers) {
                Member member = guild.getMember(user);

                guild.ban(member, delDays).queue(
                        success -> {
                            guild.unban(user).queue();
                            e.getChannel().sendMessage(Emoji.SUCCESS + " Successfully Soft Banned `" + user.getName() + "`").queue();
                        },
                        error -> {
                            e.getChannel().sendMessage(Emoji.ERROR + " An error occurred!\n```" + error.getMessage() + "```").queue();
                        }
                );
            }
        }
    }

    @Override
    public String getName() {
        return "softban";
    }

    @Override
    public String getHelp() {
        return "Soft ban will ban and immediately unban a member to kick and clean up the member's message.\n"
                + "Command Usage: `/softban`\n"
                + "Parameter: `@Member(s)`";
    }
}
