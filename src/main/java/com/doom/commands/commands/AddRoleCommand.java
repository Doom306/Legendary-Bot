package com.doom.commands.commands;

import com.doom.commands.CommandContext;
import com.doom.commands.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

public class AddRoleCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final Message message = ctx.getMessage();
        final Member member = ctx.getMember();

        if (message.getMentionedMembers().isEmpty() || message.getMentionedRoles().isEmpty()) {
            channel.sendMessage("Missing arguments").queue();
            return;
        }

        final Member target = message.getMentionedMembers().get(0);
        final Role mentionedRoles = message.getMentionedRoles().get(0);

        if (!member.canInteract(target) || !member.hasPermission(Permission.MANAGE_ROLES)) {
            channel.sendMessage("You are missing permissions to add roles to this member").queue();
            return;
        }

        ctx.getGuild()
                .addRoleToMember(target,mentionedRoles)
                .queue(
                        (__) -> channel.sendMessage("Roles was added successfully").queue(),
                        (error) -> channel.sendMessageFormat("Could not add roles to %s", error.getMessage()).queue()
                );
    }

    @Override
    public String getName() {
        return "role";
    }

    @Override
    public String getHelp() {
        return "Add a role to a member in the server. \n" +
                "Usage: `/role <@user> <@role>`";
    }
}

