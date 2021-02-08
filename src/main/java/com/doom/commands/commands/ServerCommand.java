package com.doom.commands.commands;

import com.doom.commands.CommandContext;
import com.doom.commands.ICommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;

import java.util.List;

public class ServerCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        JDA jda = ctx.getJDA();
        List<Guild> list = jda.getGuilds();
        ctx.getChannel().sendMessage("This bot is in " + (list.size() + 1) + " servers").queue();
    }

    @Override
    public String getName() {
        return "server";
    }

    @Override
    public String getHelp() {
        return "Usage: `/server`\n" +
                "Returns the count of servers the bot is in";
    }
}
