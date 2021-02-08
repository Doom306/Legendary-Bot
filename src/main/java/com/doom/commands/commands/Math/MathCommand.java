package com.doom.commands.commands.Math;

import com.doom.Listener;
import com.doom.commands.CommandContext;
import com.doom.commands.ICommand;
import com.doom.commands.commands.Emoji.Emoji;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MathCommand implements ICommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);

    @Override
    public void handle(CommandContext e) {
        if(e.getArgs().isEmpty()) {
            e.getChannel().sendMessage(getHelp()).queue();
            return;
        }

        if(e.getArgs().size() >= 1)
        {
            String input = "";
            //Convert args(without math invoke) into a String
            for(String in : e.getArgs())
            {
                if(!in.equals("math") && !in.equals("calc") && !in.equals("m"))
                    input += in;
            }

            try {
                Expression ex = new ExpressionBuilder(input)
                        .build();
                double result = ex.evaluate();
                int integer = (int)result;

                if(result >= 2147483647 || result <= -2147483647)
                    e.getChannel().sendMessage(Emoji.ERROR +  " Calculation exceeds the range I am able to display!\n Range: `2147483647 ~ -2147483648`").queue();

                else if(result % 1 == 0)
                {
                    e.getChannel().sendMessage(Emoji.PRINT + Emoji.NUMBER + "  `" + input + "` is  `"
                            + integer + "`").queue();
                }
                else
                {
                    e.getChannel().sendMessage(Emoji.PRINT + Emoji.NUMBER + "  `" + input + "` is  `"
                            + result + "`").queue();
                }

            } catch (IllegalArgumentException iae) {
                e.getChannel().sendMessage(Emoji.ERROR + " " + iae.getLocalizedMessage().replace("'", "`") + ".").queue();
            }catch (ArithmeticException ae) {
                e.getChannel().sendMessage(Emoji.ERROR + " Do not divide a value by 0.").queue();
            } catch (RuntimeException rte) {
                e.getChannel().sendMessage(Emoji.ERROR + " Please enter a valid math operation.").queue();
                LOGGER.info(this.getClass().getName(), "Unvalid operation \"" + input + "\"");
            }
        }
    }

    @Override
    public String getName() {
        return "math";
    }

    @Override
    public String getHelp() {
        return "This command is for calculating math operations.\n"
                + "Command Usage: `/math`\n"
                + "Parameter: `[Math Operation]`\n"
                + "Supported values: `pi, π, e, φ`\n";
    }
}
