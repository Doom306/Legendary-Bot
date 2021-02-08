package com.doom.commands.commands.Info;

import com.doom.Listener;
import com.doom.commands.CommandContext;
import com.doom.commands.ICommand;
import com.doom.commands.commands.Utils.UtilString;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.time.Instant;
import java.util.List;
import java.util.Random;

public class InfoUserCommand implements ICommand {

    public final static String GUILD_ONLINE = "<:online:313956277808005120>";
    public final static String GUILD_IDLE = "<:away:313956277220802560>";
    public final static String GUILD_DND = "<:dnd:313956276893646850>";
    public final static String GUILD_OFFLINE = "<:offline:313956277237710868>";
    public final static String GUILD_STREAMING = "<:streaming:313956277132853248>";

    public final static String CHECK = "<:check:314349398811475968>";
    public final static String UNCHECK = "<:xmark:314349398824058880>";
    public final static String NOCHECK = "<:empty:314349398723264512>";

    private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);
    @Override
    public void handle(CommandContext ctx) {
        if(ctx.getArgs().isEmpty()) {
            embedUser(ctx.getAuthor(), ctx.getGuild().getMember(ctx.getSelfUser()), ctx.getEvent());
        } else {
            List<User> userMention = ctx.getMessage().getMentionedUsers();
            for(User user : userMention) {
                embedUser(user, ctx.getGuild().getMember(user), ctx.getEvent());
            }
        }
    }

    @Override
    public String getName() {
        return "user";
    }

    @Override
    public String getHelp() {
        return "Get info about the specified user\n" +
                "`/user [mentioned member]`";
    }


    public static String getStatusEmoji(OnlineStatus stat)
    {
        String status = "";
        switch (stat) {
            case ONLINE:
                status = GUILD_ONLINE;
                break;
            case IDLE:
                status = GUILD_IDLE;
                break;
            case DO_NOT_DISTURB:
                status = GUILD_DND;
                break;
            case INVISIBLE:
                status = GUILD_OFFLINE;
                break;
            case OFFLINE:
                status = GUILD_OFFLINE;
                break;
            default:
                status = GUILD_OFFLINE;
        }
        return status;
    }

    public static Color randomColor() {
        Random colorpicker = new Random();
        int red = colorpicker.nextInt(255) + 1;
        int green = colorpicker.nextInt(255) + 1;
        int blue = colorpicker.nextInt(255) + 1;
        return new Color(red, green, blue);
    }

    private void embedUser(User user, Member member, GuildMessageReceivedEvent e) {
        String name, id, dis, nickname, icon, status, statusEmoji, game, join, register;

        icon = user.getEffectiveAvatarUrl();

        /* Identity */
        name = user.getName();
        id = user.getId();
        dis = user.getDiscriminator();
        nickname = member == null || member.getNickname() == null ? "N/A" : member.getEffectiveName();

        /* Status */
        OnlineStatus stat = member == null ? null : member.getOnlineStatus();
        status = stat == null ? "N?A" : UtilString.VariableToString("_", stat.getKey());
        statusEmoji = stat == null ? "" : getStatusEmoji(stat);
        game = stat == null ? "N/A" : member.getActivities().isEmpty() ? "N/A" : member.getActivities().get(0).getName();

        /* Time */
        join = member == null ? "N?A" : UtilString.formatOffsetDateTime(member.getTimeJoined());
        register = UtilString.formatOffsetDateTime(user.getTimeCreated());

        EmbedBuilder embed = new EmbedBuilder()
                .setAuthor(name, null, icon)
                .setColor(randomColor()).setThumbnail(icon).setTimestamp(Instant.now())
                .setFooter("User Info", null);

        embed.addField("Identity", "ID `"+id+"`\n"+
                "Nickname `"+nickname+"` | Discrim `"+dis+"`", true);

        embed.addField("Status", "🎮 "+" `"+game+"`\n"
                +statusEmoji+" `"+status+"`\n", true);

        embed.addField("⌚ "+"Time", "Join `"+join+"`\n"+
                "Register `"+register+"`\n", true);

        e.getChannel().sendMessage(embed.build()).queue();
    }
}
