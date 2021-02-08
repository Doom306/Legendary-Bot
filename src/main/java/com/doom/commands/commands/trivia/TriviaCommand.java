package com.doom.commands.commands.trivia;

import com.doom.Listener;
import com.doom.commands.CommandContext;
import com.doom.commands.ICommand;
import com.doom.commands.commands.Utils.UtilNum;
import com.doom.database.FilePath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TriviaCommand implements ICommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);
    private static String storeUser;

    @Override
    public void handle(CommandContext ctx) {
        String respond = "", output = "";
        int totalline = 0;

        try {
            totalline = UtilNum.getLineCount(FilePath.EightBall);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        int magic = UtilNum.randomNum(0, totalline), line = 0;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(FilePath.EightBall));

            while((respond = reader.readLine()) != null) {
                line++;
                if(line >= magic)
                    break;
            }
            reader.close();

        } catch (IOException io) {
            LOGGER.info(this.getClass().getName(), io, "BufferedReader at getting response.");
        }

        String msg = ctx.getAuthor().getAsMention() + respond;
        ctx.getChannel().sendMessage(msg).queue();
        storeUser = ctx.getAuthor().getAsMention();

    }


    @Override
    public String getName() {
        return "trivia";
    }

    @Override
    public String getHelp() {
        return "Sends a trivia message!!!";
    }
}
