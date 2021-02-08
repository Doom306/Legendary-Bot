package com.doom.commands.commands;

import com.doom.commands.CommandContext;
import com.doom.commands.ICommand;

public class AddToServer implements ICommand {

    @Override
    public void handle(CommandContext ctx) {
        ctx.getChannel().sendMessage("Link to invite the bot:\n" +
                "[https://discord.com/oauth2/authorize?client_id=761072632958681099&scope=bot&permissions=819014710]").queue();
    }

    @Override
    public String getName() {
        return "invite";
    }

    @Override
    public String getHelp() {
        return "Sends a link to invite the bot to your server";
    }
}
