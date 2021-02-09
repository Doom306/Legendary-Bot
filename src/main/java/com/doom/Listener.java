package com.doom;

import com.doom.commands.commands.BadDesign;
import com.doom.database.DatabaseManager;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import me.duncte123.botcommons.BotCommons;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Listener extends ListenerAdapter {

    private static HashMap<Guild, String> message = new HashMap<>();
    private static HashMap<Guild, Boolean> activate = new HashMap<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);
    private final CommandManager manager;
    private String text = "Congratulations";

    public Listener(EventWaiter waiter) {
        manager = new CommandManager(waiter);
    }


    @Override
    public void onReady(@NotNull ReadyEvent event) {
        LOGGER.info("{} is ready", event.getJDA().getSelfUser().getAsTag());
        LOGGER.info("Legendary Bot");
    }

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        final TextChannel dontDoThis = event.getGuild().getDefaultChannel();

        final String useGuildSpecificSettingsInstead = String.format("Welcome %s to %s",
                event.getMember().getUser().getAsMention(), event.getGuild().getName());

        if (!event.getMember().hasPermission(Permission.MESSAGE_WRITE)) {
            return;
        }

        assert dontDoThis != null;
        dontDoThis.sendMessage(useGuildSpecificSettingsInstead).queue();

    }


    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        final String useGuildSpecificSettingsInstead = String.format("Thank you for adding %s to %s!!!'n" +
                        "To know about this bot feel free to type /help\n" +
                        "You can change the prefix by typing /setprefix [prefix]\n" +
                        "To know more about a command type /help [command name]",
                event.getJDA().getSelfUser().getAsMention(), event.getGuild().getName());

        Objects.requireNonNull(event.getGuild().getDefaultChannel()).sendMessage(useGuildSpecificSettingsInstead).queue();
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {

        User user = event.getAuthor();

        LOGGER.info(event.getMessage().getContentRaw());

        final long guildId = event.getGuild().getIdLong();
        String prefix = BadDesign.PREFIXES.computeIfAbsent(guildId, DatabaseManager.INSTANCE::getPrefix);

        if (event.getMessage().getContentRaw().equals(prefix + "enable") && event.getMember().hasPermission(Permission.BAN_MEMBERS)) {
            activate.put(event.getGuild(), true);
            return;
        }

        if (event.getMessage().getContentRaw().equals(prefix + "disable") && event.getMember().hasPermission(Permission.BAN_MEMBERS)) {
            activate.put(event.getGuild(), false);
            return;
        }

        if (activate.containsKey(event.getGuild()) && activate.get(event.getGuild()).equals(true)) {
            if (event.getMessage().getContentRaw().contains(text) && event.getMessage().getContentRaw().contains("You won the") && event.getAuthor().getId().equals("294882584201003009")) {
                List<User> mention = event.getMessage().getMentionedUsers();


                for (User u : mention) {
                    if (message.containsKey(event.getGuild())) {
                        u.openPrivateChannel().queue(PrivateChannel ->
                                PrivateChannel.sendMessage("***Congratulations***\n** " +
                                        "You won the giveaway"
                                        + "** in **" + event.getGuild().getName()
                                        + "\n** Message from the admins of " + event.getGuild().getName() +":\n" +
                                        message.get(event.getGuild()))
                                        .queue());
                    } else {
                        u.openPrivateChannel().queue(PrivateChannel ->
                                PrivateChannel.sendMessage("***Congratulations***\n**"
                                        + "You won the giveaway"
                                        + "** in **" + event.getGuild().getName()
                                        + "\n** Kindly go to " + event.getGuild().getName() + " to claim your reward(s)!!!\n")
                                        .queue());

                    }
                    return;
                }
            }
        }

        if (user.isBot() || event.isWebhookMessage()) {
            return;
        }


        if (event.getMessage().getContentRaw().startsWith(prefix + "setmessage") && event.getMember().hasPermission(Permission.BAN_MEMBERS)) {
            String wow = event.getMessage().getContentRaw().substring(10 + prefix.length());
            message.put(event.getGuild(), wow);
            event.getChannel().sendMessage("The message was edited to " + wow).queue();
            return;
        }


        String raw = event.getMessage().getContentRaw();

        if (raw.equalsIgnoreCase(prefix + "shutdown")
                && event.getAuthor().getId().equals(Config.get("owner_id"))) {
            LOGGER.info("The bot " + event.getAuthor().getAsMention() + " is shutting down.\n" +
                    "Nice code you did sir!!!");
            event.getChannel().sendMessage("Shutting down...").queue();
            event.getJDA().shutdown();
            BotCommons.shutdown(event.getJDA());

            return;
        } else if (raw.equalsIgnoreCase(prefix + "shutdown")
                && event.getAuthor().getId().equals(Config.get("owner_id_partner"))) {
            LOGGER.info("Shutting down...");
            event.getChannel().sendMessage("Shutting down...").queue();
            event.getJDA().shutdown();
            BotCommons.shutdown(event.getJDA());

            return;
        }

        if (raw.startsWith(prefix)) {
            manager.handle(event, prefix);

        }
    }
}
