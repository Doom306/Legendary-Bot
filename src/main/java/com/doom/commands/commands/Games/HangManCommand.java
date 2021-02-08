package com.doom.commands.commands.Games;

import com.doom.Listener;
import com.doom.commands.CommandContext;
import com.doom.commands.ICommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HangManCommand implements ICommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);
    HangMan game;
    @Override
    public void handle(CommandContext ctx) {
        if(ctx.getArgs().size() == 0)
        {
            ctx.getChannel().sendMessage(getHelp()).queue();
        }

        else if(ctx.getArgs().size() > 0 && "start".equals(ctx.getArgs().get(0)))
        {
            LOGGER.info(this.getClass().getName(), "HangMan Started.");
            game = new HangMan(ctx.getEvent());
        }

        else if(ctx.getArgs().size() > 0 && "end".equals(ctx.getArgs().get(0)))
        {
            if(ctx.getAuthor() == HangMan.starter)
                game.endGame();
            else
                ctx.getChannel().sendMessage("🛑 Only the game starter can end the game.").queue();
        }

        else
        {
            try {
                game.sendInput(ctx.getArgs(), ctx.getEvent());
            } catch(NullPointerException en) {
                ctx.getChannel().sendMessage("🛑 Game haven't started yet!").queue();
                LOGGER.info(en + this.getClass().getName(), "Game haven't started.");
            }
        }
    }

    @Override
    public String getName() {
        return "hm";
    }

    @Override
    public String getHelp() {
        return "Play Hangman with anyone\n" +
                "Usage: `/hm`" +
                "Options: `-h | start | [letter] | end | null";
    }
}
