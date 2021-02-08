package com.doom.commands.commands.Emoji;

import com.doom.commands.CommandContext;
import com.doom.commands.ICommand;
import com.vdurmont.emoji.EmojiManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.time.Instant;
import java.util.ArrayList;

public class EmojiCommand implements ICommand {
    @Override
    public void handle(CommandContext e) {
        if(e.getArgs().isEmpty()) e.getChannel().sendMessage(getHelp()).queue();

        else if("-m".equals(e.getArgs().get(0)))
        {
            try{
                if(!EmojiManager.isEmoji(e.getArgs().get(0))) {
                    ArrayList<Emote> emotesByName = (ArrayList<Emote>) e.getJDA().getEmotesByName(e.getArgs().get(1).substring(1, e.getArgs().size()-1), true);
                    e.getChannel().sendMessage("Name: **" + emotesByName.get(0).getName() + "**\n`" + emotesByName.get(0).getAsMention() + "`\n"
                            + emotesByName.get(0).getImageUrl()).queue();
                    return;
                }

                com.vdurmont.emoji.Emoji emo = EmojiManager.getByUnicode(e.getArgs().get(1));

                String emoji = emo.getUnicode() + " `" + emo.getUnicode() + "`";
                String description = emo.getDescription().substring(0, 1).toUpperCase() + emo.getDescription().substring(1);
                String html = "`" + emo.getHtmlDecimal() + "`\n`" + emo.getHtmlHexadecimal() + "`";
                String alias = "";

                for(String a : emo.getAliases()) {
                    alias += a.substring(0, 1).toUpperCase() + a.substring(1) + ", \n";
                }
                alias = alias.substring(0, alias.length() - 3);

                String tag = "";
                for(String t : emo.getTags()) {
                    tag += t.substring(0, 1).toUpperCase() + t.substring(1) + ", \n";
                }
                if("".equals(tag))
                    tag = "None";
                else
                    tag = tag.substring(0, tag.length() - 3);

                EmbedBuilder embedemo = new EmbedBuilder();
                embedemo.setColor(Color.green);
                embedemo.addField("Emoji", emoji, true);
                embedemo.addField("Description", description, false);
                embedemo.addField("Aliases", alias, true);
                embedemo.addField("Tags", tag, true);
                embedemo.addField("Html", html, true);
                embedemo.setFooter("Emoji Information", null);
                embedemo.setTimestamp(Instant.now());
                MessageEmbed meem = embedemo.build();

                e.getChannel().sendMessage(meem).queue();
                embedemo.clearFields();
            } catch (NullPointerException npe) {
                e.getChannel().sendMessage(Emoji.ERROR + " Please enter a valid alias for that emoji.").queue();
                return;
            } catch (IndexOutOfBoundsException ioobe) {
                e.getChannel().sendMessage(Emoji.ERROR + " Please enter a valid server emoji.").queue();
                return;
            }
        }

        else
        {
            String input = "";
            for(int i = 0; i < e.getArgs().size(); i++)
            {
                input += e.getArgs().get(i) + " ";
            }

            if(input.length() != 0)
            {
                if (e.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) {
                    e.getChannel().deleteMessageById(e.getMessage().getId()).queue();
                }

                String output = Emoji.stringToEmoji(input);
                e.getChannel().sendMessage(output).queue();
            }
            else
                getHelp();
        }
    }

    @Override
    public String getName() {
        return "emoji";
    }

    @Override
    public String getHelp() {
        return "This command is for emoji utilities.\n"
                + "Command Usage: `/emoji`\n"
                + "Parameter: `[Words and Letters] | -m [Emoji Name]`\n"
                + "[Words and Letters]: Let the bot say [Words and Letters] in emoji language for you.\n"
                + "-m [Emoji Name]: Get information about an emoji.";
    }
}
