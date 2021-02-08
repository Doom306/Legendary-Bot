package com.doom.commands.commands.Games;

import com.doom.commands.CommandContext;
import com.doom.commands.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;

public class GameCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        if(ctx.getArgs().size() == 1 && "-h".equals(ctx.getArgs().get(0))) {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("Miscellaneous Module", null);
            embed.addField("game -help","", true);
            ctx.getChannel().sendMessage(embed.build()).queue();
            return;
        }

        if(ctx.getArgs().isEmpty())
        {
            ctx.getChannel().sendMessage("Choose a game below to get its help message:\n"
                    + "1: Rock Paper Scissors\n"
                    + "2: Guess Number\n"
                    + "3: Hang Man\n"
                    + "Hint: Use `/game [Number]` to choose.").queue();
        }

        else {
            if ("1".equals(ctx.getArgs().get(0))) {
                RPSCommand rps = new RPSCommand();
                rps.handle(ctx);
            } else if ("2".equals(ctx.getArgs().get(0))) {
                GuessNumberCommand gn = new GuessNumberCommand();
                gn.handle(ctx);
            } else if ("3".equals(ctx.getArgs().get(0))) {
                HangManCommand hm = new HangManCommand();
                hm.handle(ctx);
            }
        }
    }

    @Override
    public String getName() {
        return "game";
    }

    @Override
    public String getHelp() {
        return "Choose the game to get its help mwessage";
    }
}
