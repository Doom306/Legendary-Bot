package com.doom.commands.commands;

import com.doom.commands.CommandContext;
import com.doom.commands.ICommand;
import com.doom.commands.commands.Emoji.Emoji;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;

import java.awt.*;

public class SayCommand implements ICommand {
    @Override
    public void handle(CommandContext e) {
        if(e.getArgs().isEmpty()) {
            e.getChannel().sendMessage(getHelp()).queue();
            return;
        }

        if("embed".equals(e.getArgs().get(0)))
        {
            String[] tokens = e.getMessage().getContentRaw().split(" ");
            String content = "";

            for(int i = 0; i < tokens.length; i++) {
                content += i==0 || i==1 ? "" : tokens[i]+" "; //Ignore first two tokens: =say and embed
            }

            e.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE);

            EmbedBuilder embedmsg = new EmbedBuilder();
            embedmsg.setColor(Color.red);
            embedmsg.setAuthor("Said", null, e.getJDA().getSelfUser().getAvatarUrl());
            embedmsg.setDescription(content);
            embedmsg.setFooter("Requested by " + e.getAuthor().getName(), null);
            e.getChannel().sendMessage(embedmsg.build()).queue();
        }

        else
        {
            String[] tokens = e.getMessage().getContentRaw().split(" ");
            String content = "";

            for(int i = 0; i < tokens.length; i++) {
                content += i == 0 ? "" : tokens[i] + " "; //Ignore first token: =say
            }
            e.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE);

            e.getChannel().sendMessage(Emoji.Unicode.zero_width + content).queue();
        }
    }

    @Override
    public String getName() {
        return "say";
    }

    @Override
    public String getHelp() {
        return "This command is for letting a bot say something for you.\n"
                + "Command Usage: `/say`\n"
                + "Parameter: `-h | [Content] | embed [Content]| null`\n"
                + "[Content]: The sentence you want AIBot to say in normal message form.\n"
                + "embed [Content]: The sentence you want AIBot to say in embed message form.\n"
                + "Support @mention(s): @everyone, @here, and @user.";
    }
}
