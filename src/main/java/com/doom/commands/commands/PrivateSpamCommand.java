package com.doom.commands.commands;

import com.doom.commands.CommandContext;
import com.doom.commands.ICommand;
import com.doom.commands.commands.Emoji.Emoji;
import com.doom.commands.commands.admin.Prefix;
import net.dv8tion.jda.api.entities.User;
import java.util.HashMap;
import java.util.List;

public class PrivateSpamCommand implements ICommand {

    private HashMap<String, Integer> spamcount = new HashMap<>();
    private long lol;

    @Override
    public void handle(CommandContext e) {
        lol = e.getGuild().getIdLong();
        if(e.getArgs().size() == 1 && "-h".equals(e.getArgs().get(0))) {
            e.getChannel().sendMessage(getHelp()).queue();
            return;
        }

        //No NUMBER specified
        if(e.getArgs().isEmpty()) {
            e.getChannel().sendMessage("No user and value imputed.").queue();
        }
        //Target Specified
        else {

            List<User> mention = e.getMessage().getMentionedUsers();

            for(User u : mention) {
                if(u.getId().equals(e.getJDA().getSelfUser().getId()))
                    continue;

                /*long id = u.getIdLong();
                if(Long.getLong(Config.get("owner_id")) == id) {
                    e.getChannel().sendMessage(Emoji.EYES + " How dare you? Spamming my creator!?").queue();
                    u = e.getAuthor();
                }*/

                u.openPrivateChannel().queue(PrivateChannel ->
                        PrivateChannel.sendMessage("***Warning***\n**" + e.getMember().getEffectiveName() + "#" + e.getAuthor().getDiscriminator()
                                + "** from **" + e.getGuild().getName()
                                + "** has spammed you!\n").queue());

                final int amount = Integer.parseInt(e.getArgs().get(0));

                if (amount < 1) {
                    e.getChannel().sendMessage("Invalid amount imputed").queue();
                    return;
                }


                if (amount > 999) {
                    e.getChannel().sendMessage("Too much spam count imputed").queue();
                    return;
                }

                String word = e.getArgs().get(1);

                if (e.getArgs().size() > 2) {
                    word = word + " " + e.getArgs().get(2);
                }
                if (e.getArgs().size() > 3) {
                    word = word + " " + e.getArgs().get(3);
                }
                if (e.getArgs().size() > 4) {
                    word = word +  " " + e.getArgs().get(4);
                }
                if (e.getArgs().size() > 5) {
                    word = word +  " " + e.getArgs().get(5);
                }
                if (e.getArgs().size() > 6) {
                    word = word + " " +  e.getArgs().get(6);
                }
                if (e.getArgs().size() > 7) {
                    word = word + " " +  e.getArgs().get(7);
                }
                if (e.getArgs().size() > 8) {
                    word = word + " " +  e.getArgs().get(8);
                }

                int goal = 5;
                int x = 1;
                String message = "";

                while (x <= amount) {
                    message = message + "\n" + word;

                    if (x == goal) {

                        String finalMessage1 = message;
                        u.openPrivateChannel().queue(PrivateChannel ->
                                PrivateChannel.sendMessage(finalMessage1).queue());
                        goal += 5;
                        message = "";
                    }
                    x++;
                }

                if (!message.isEmpty()) {
                    String finalMessage = message;
                    u.openPrivateChannel().queue(PrivateChannel ->
                            PrivateChannel.sendMessage(finalMessage).queue());
                }
            }

            if(!mention.isEmpty()) {
                e.getChannel().sendMessage("Spammed spams to " + mention.size() + " user(s)!").queue();
                if(spamcount.containsKey(e.getAuthor().getId()))
                    spamcount.put(e.getAuthor().getId(), spamcount.get(e.getAuthor().getId())+1);
                else
                    spamcount.put(e.getAuthor().getId(), 0);
            }
            else {
                e.getChannel().sendMessage(Emoji.ERROR + " Invalid spam! So I spammed **YOU**!").queue();
                e.getAuthor().openPrivateChannel().queue(PrivateChannel ->
                        PrivateChannel.sendMessage("**Punishing Spam**\n").queue());

                int goal = 5;
                int x = 1;
                String message = "";
                String word = "You fat punishable fat kid!!!";

                while (x <= 100) {
                    message = message + "\n" + word;

                    if (x == goal) {

                        String finalMessage1 = message;
                        e.getAuthor().openPrivateChannel().queue(PrivateChannel ->
                                PrivateChannel.sendMessage(finalMessage1).queue());
                        goal += 5;
                        message = "";
                    }
                    x++;
                }
            }
        }
    }

    @Override
    public String getName() {
        return "pspam";
    }

    @Override
    public String getHelp() {
        return "Spams private messages to the users!\n"
                + "Command Usage: `" + Prefix.getDefaultPrefix(lol) + "spam`\n"
                + "Parameter: `-h | [Number] @Mention(s)`\n"
                + "[Number]: Spam an amount of spams.\n"
                + "@Mention(s): Spam spams to mentioned member(s) in dm.\n";
    }
}
