package com.doom.commands.commands;

import com.doom.CommandManager;
import com.doom.commands.CommandContext;
import com.doom.commands.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.util.List;

public class HelpCommand implements ICommand {

    private final CommandManager manager;

    public HelpCommand(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(CommandContext ctx) {
        List<String> args = ctx.getArgs();
        TextChannel channel = ctx.getChannel();

        String group = "Games\n" +
                "type `/help games` to find out what are the commands\n" +
                "\n" +
                "Music\n" +
                "type `/help music` to find out what are the commands\n" +
                "\n" +
                "Fun\n" +
                "type `/help fun` to find out what are the commands\n" +
                "\n" +
                "Moderation\n" +
                "type `/help mod` to find out what are the commands";
        String group1 = "Information\n" +
                "type `/help info` to find out what are the commands\n" +
                "\n" +
                "Bot\n" +
                "type `/help bot` to find out what are the commands\n" +
                "\n" +
                "Future\n" +
                "type `/help future` to find out what are the future commands";

        if (args.isEmpty()) {
            EmbedBuilder embedBuilder = new EmbedBuilder();

            embedBuilder.setTitle("Help!!!");
            embedBuilder.setColor(Color.cyan);
            embedBuilder.addField(group,"", false);
            embedBuilder.addField(group1,"", false);
            embedBuilder.setFooter("\nType `/help [group name]` to see their commands");


            channel.sendMessage(embedBuilder.build()).queue();
            return;
        }

        if (args.get(0).equals("music")) {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("Music Commands");
            embedBuilder.setColor(Color.yellow);
            embedBuilder.addField("1.) Join Command","`/join`", false);
            embedBuilder.addField("2.) Play Command","`/play`", false);
            embedBuilder.addField("3.) Stop Command","`/stop`", false);
            embedBuilder.addField("4.) Queue Command","`/queue`", false);
            embedBuilder.addField("5.) Now Playing Command","`/playinginfo`", false);
            embedBuilder.addField("6.) Repeat Command","`/repeat`", false);
            embedBuilder.addField("7.) Skip Command","`/skip`", false);
            embedBuilder.addField("8.) Leave Command","`/leave`", false);
            embedBuilder.setFooter("\nType `/help [command name]` to see what they do");

            channel.sendMessage(embedBuilder.build()).queue();
            return;
        }

        if (args.get(0).equals("fun")) {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            EmbedBuilder embedBuilder1 = new EmbedBuilder();
            embedBuilder.setTitle("Fun Commands");
            embedBuilder1.setTitle("Fun Commands");
            embedBuilder.setColor(Color.green);
            embedBuilder1.setColor(Color.green);
            embedBuilder.addField("1.) Spam Command","`/spam`", false);
            embedBuilder.addField("2.) Meme Command","`/meme`", false);
            embedBuilder.addField("3.) Joke Command","`/joke`", false);
            embedBuilder.addField("4.) Font Command","`/font`", false);
            embedBuilder.addField("5.) Code Command","`/code`", false);
            embedBuilder.addField("6.) Avatar Command","`/avatar`", false);
            embedBuilder1.addField("7.) Hack Command","`/hack`", false);
            embedBuilder1.addField("8.) Say Command", "`/say`", false);
            embedBuilder1.addField("9.) Math Command", "`/math`", false);
            embedBuilder1.addField("10.) Emoji Command","`/emoji`", false);
            embedBuilder1.addField("11.) Private Spam Command","`/pspam`", false);

            embedBuilder1.setFooter("\nType `/help [command name]` to see what they do");

            channel.sendMessage(embedBuilder.build()).queue();
            channel.sendMessage(embedBuilder1.build()).queue();
            return;
        }
        if (args.get(0).equals("games")) {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("Games Commands");
            embedBuilder.setColor(Color.ORANGE);
            embedBuilder.addField("1.) Game info Command*","`/game`", false);
            embedBuilder.addField("2.) Hang man Command*\n","`/hm`", false);
            embedBuilder.addField("3.) Guess the number Command*","`/gn`", false);
            embedBuilder.addField("4.) Rock Paper Scissors Command*","`/rps`", false);
            embedBuilder.addField("5.) Number Command", "`/number`", false);
            embedBuilder.setFooter("\nType `/help [command name]` to see what they do");

            channel.sendMessage(embedBuilder.build()).queue();
            return;
        }
        if (args.get(0).equals("info")) {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("Information Commands");
            embedBuilder.setColor(Color.red);
            embedBuilder.addField("1.) User Information Command","`/user`", false);
            embedBuilder.addField("2.) Server Information Command","`/serverinfo`", false);
            embedBuilder.addField("3.) Mod Information Command ","`/mods`", false);
            embedBuilder.setFooter("\nType `/help [command name]` to see what they do");

            channel.sendMessage(embedBuilder.build()).queue();
            return;
        }
        if (args.get(0).equals("bot")) {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("About the Bot Commands");
            embedBuilder.setColor(Color.blue);
            embedBuilder.addField("1.) Server List Command","`/serverlist`", false);
            embedBuilder.addField("2.) Server Count Command","`/server`", false);
            embedBuilder.setFooter("\nType `/help [command name]` to see what they do");

            channel.sendMessage(embedBuilder.build()).queue();
            return;
        }

        if (args.get(0).equals("mod")) {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            EmbedBuilder embedBuilder1 = new EmbedBuilder();
            embedBuilder.setTitle("Moderation Commands");
            embedBuilder1.setTitle("Moderation Commands");
            embedBuilder.setColor(Color.red);
            embedBuilder1.setColor(Color.red);
            embedBuilder.addField("1.) Kick Command", "`/kick`", false);
            embedBuilder.addField("2.) Ban Command", "`/ban`", false);
            embedBuilder.addField("3.) Mute Command", "`/mute`", false);
            embedBuilder.addField("4.) Deafen Command", "`/deafen`", false);
            embedBuilder.addField("5.) Clear Command", "`/clear`", false);
            embedBuilder.addField("6.) Delete Command", "`/delete`", false);
            embedBuilder1.addField("7.) Set Prefix Command", "`/setprefix`", false);
            embedBuilder1.addField("8.) Add Role Command", "`/role`", false);
            embedBuilder1.addField("9.) Enable Giveaway Win Message", "`/enable`", false);
            embedBuilder1.addField("10.) Disable Giveaway Win Message", "`/disable`", false);
            embedBuilder1.addField("11.) Set Giveaway Win Message", "`/setmessage`", false);
            embedBuilder1.setFooter("\nType `/help [command name]` to see what they do");

            channel.sendMessage(embedBuilder.build()).queue();
            channel.sendMessage(embedBuilder1.build()).queue();
            return;
        }

        if (args.get(0).equals("future")) {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("Future Commands");
            embedBuilder.setColor(Color.red);
            embedBuilder.addField("1.) Trivia Command", "`/trivia`", false);
            embedBuilder.setFooter("\nType `/help [command name]` to see what they do");

            channel.sendMessage(embedBuilder.build()).queue();
            return;
        }

        EmbedBuilder embedBuilder = new EmbedBuilder();

        String search = args.get(0);
        ICommand command = manager.getCommand(search);

        if (command == null) {
            channel.sendMessage("Nothing found for " + search).queue();
            return;
        }

        embedBuilder.setTitle("Help!!!");
        embedBuilder.setColor(Color.cyan);
        embedBuilder.addField(command.getHelp(), "", false);

        channel.sendMessage(embedBuilder.build()).queue();
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getHelp() {
        return "Shows the list with commands in the bot\n" +
                "Usage: `/help [command]`";
    }

    @Override
    public List<String> getAliases() {
        return List.of("commands", "cmds", "commandlist");
    }
}
