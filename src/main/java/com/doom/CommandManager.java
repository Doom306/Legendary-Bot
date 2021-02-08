package com.doom;

import com.doom.commands.CommandContext;
import com.doom.commands.ICommand;
import com.doom.commands.commands.*;
import com.doom.commands.commands.Emoji.EmojiCommand;
import com.doom.commands.commands.Fonts.AsciiCommand;
import com.doom.commands.commands.Games.*;
import com.doom.commands.commands.Info.InfoServerCommand;
import com.doom.commands.commands.Info.InfoUserCommand;
import com.doom.commands.commands.Info.ModsCommand;
import com.doom.commands.commands.Math.MathCommand;
import com.doom.commands.commands.trivia.TriviaCommand;
import com.doom.commands.commands.admin.SetPrefixCommand;
import com.doom.commands.commands.music.*;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class CommandManager {
    private final List<ICommand> commands = new ArrayList<>();

    public CommandManager(EventWaiter waiter) {
        addCommand(new HelpCommand(this));
        addCommand(new PingCommand());

        addCommand(new MemeCommand());
        addCommand(new JokeCommand());
        addCommand(new PruneCommand());
        addCommand(new AvatarCommand());
        addCommand(new SpamCommand());
        addCommand(new Clear());
        addCommand(new Hack());
        addCommand(new EightballCommand());

        addCommand(new AddToServer());

        addCommand(new JoinCommand());
        addCommand(new PlayCommand());
        addCommand(new StopCommand());
        addCommand(new SkipCommand());
        addCommand(new NowPlayingCommand());
        addCommand(new QueueCommand());
        addCommand(new RepeatCommand());
        addCommand(new LeaveCommand());

        addCommand(new HangManCommand());
        addCommand(new GuessNumberCommand());
        addCommand(new RPSCommand());
        addCommand(new GameCommand());
        addCommand(new AsciiCommand());

        addCommand(new KickCommand());
        addCommand(new BanCommand());
        addCommand(new MuteCommand());
        addCommand(new DeafenCommand());
        addCommand(new SoftBanCommand());

        addCommand(new SetPrefixCommand());
        addCommand(new AddRoleCommand());

        addCommand(new WebhookCommand());
        addCommand(new ServerCommand());
        addCommand(new ServerListCommand());

        addCommand(new PasteCommand());
        addCommand(new TriviaCommand());
        addCommand(new InfoUserCommand());
        addCommand(new InfoServerCommand());
        addCommand(new NumberCommand());
        addCommand(new SayCommand());
        addCommand(new MathCommand());
        addCommand(new EmojiCommand());
        addCommand(new ModsCommand());
        addCommand(new PrivateSpamCommand());
    }

    private void addCommand(ICommand cmd) {
        boolean nameFound = this.commands.stream().anyMatch((it) -> it.getName().equalsIgnoreCase(cmd.getName()));

        if (nameFound) {
            throw new IllegalArgumentException("A command with this name is already present, " + cmd.getName());
        }

        commands.add(cmd);
    }

    public List<ICommand> getCommands() { return commands; }

    @Nullable
    public ICommand getCommand(String search) {
        String searchLower = search.toLowerCase();

        for (ICommand cmd : this.commands) {
            if (cmd.getName().equals(searchLower) || cmd.getAliases().contains(searchLower)) {
                return cmd;
            }
        }

        return null;
    }


    void handle(GuildMessageReceivedEvent event, String prefix) {
        String[] split = event.getMessage().getContentRaw()
                .replaceFirst("(?i)" + Pattern.quote(prefix), "")
                .split("\\s+");

        String invoke = split[0].toLowerCase();
        ICommand cmd = this.getCommand(invoke);

        if (cmd != null) {
            event.getChannel().sendTyping().queue();
            List<String> args = Arrays.asList(split).subList(1, split.length);

            CommandContext ctx = new CommandContext(event, args);
            cmd.handle(ctx);
        }
    }
}
