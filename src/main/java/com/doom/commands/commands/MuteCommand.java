package com.doom.commands.commands;

import com.doom.commands.CommandContext;
import com.doom.commands.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class MuteCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final Message message = ctx.getMessage();
        final Member member = ctx.getMember();

        if (message.getMentionedMembers().isEmpty()) {
            channel.sendMessage("Missing mentioned member").queue();
            return;
        }

        final Member target = message.getMentionedMembers().get(0);

        if (!member.canInteract(target) || !member.hasPermission(Permission.VOICE_MUTE_OTHERS)) {
            channel.sendMessage("You are missing permissions to mute this member").queue();
            return;
        }

        final Member selfMember = ctx.getSelfMember();

        if (!selfMember.canInteract(target) || !selfMember.hasPermission(Permission.VOICE_MUTE_OTHERS)) {
            channel.sendMessage("I am missing permissions to mute that member").queue();
            return;
        }


        ctx.getGuild()
                .mute(target, true)
                .queue(
                        (__) -> channel.sendMessage("Mute was successful").queue(),
                        (error) -> channel.sendMessageFormat("Could not mute %s", error.getMessage()).queue()
                );
    }

    @Override
    public String getName() {
        return "mute";
    }

    @Override
    public String getHelp() {
        return "Voice mute a member in the server. \n" +
                "Usage: `/mute <@user>`";
    }
}
