package com.doom.commands.commands;

import com.doom.commands.CommandContext;
import com.doom.commands.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class AvatarCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final Message message = ctx.getMessage();

        if (message.getMentionedMembers().isEmpty()) {
            final Member target = message.getMember();
            final String avatarUrl = target.getUser().getAvatarUrl();

            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle(target.getNickname());
            embedBuilder.setImage(avatarUrl);
            embedBuilder.setColor(Color.cyan);
            embedBuilder.setFooter("/help to get some help");

            channel.sendMessage(embedBuilder.build()).queue();
        } else {
            final Member target = message.getMentionedMembers().get(0);
            final String avatarUrl = target.getUser().getAvatarUrl();

            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle(target.getNickname());
            embedBuilder.setImage(avatarUrl);
            embedBuilder.setColor(Color.cyan);
            embedBuilder.setFooter("/help to get some help");

            channel.sendMessage(embedBuilder.build()).queue();
        }
    }

    @Override
    public String getName() {
        return "avatar";
    }

    @Override
    public String getHelp() {
        return "Shows the avatar of a specific user. \n" +
                "Usage: `/avatar <@user>`";
    }
}