package com.doom.commands.commands;

import com.doom.commands.CommandContext;
import com.doom.commands.ICommand;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.duncte123.botcommons.web.ContentType;
import me.duncte123.botcommons.web.WebParserUtils;
import me.duncte123.botcommons.web.WebUtils;
import net.dv8tion.jda.api.entities.TextChannel;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.util.function.Consumer;

public class HasteCommand implements ICommand {

    private static final String HASTE_SERVER = "https://hasteb.in.com/";

    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();

        if (ctx.getArgs().isEmpty()) {
            channel.sendMessage("Missing arguments").queue();
            return;
        }

        final String invoke = this.getName();
        final String contentRaw = ctx.getMessage().getContentRaw();
        final int index = contentRaw.indexOf(invoke) + invoke.length();
        final String body = contentRaw.substring(index).trim();

        this.createPaste(body, (text) -> channel.sendMessage(text).queue());
    }

    private void createPaste(String text, Consumer<String> callback) {
        Request request = new Request.Builder()
                .post(RequestBody.create(text.getBytes()))
                .addHeader("Content-Type", ContentType.TEXT_HTML.getType())
                .url(HASTE_SERVER + "documents")
                .build();

        WebUtils.ins.prepareRaw(request, (r) -> WebParserUtils.toJSONObject(r, new ObjectMapper())).async(
                (json) -> {
                    String key = json.get("key").asText();

                    callback.accept(HASTE_SERVER + key);
                },
                (e) -> callback.accept("Error: " + e.getMessage())
        );
    }

    @Override
    public String getName() {
        return "share";
    }

    @Override
    public String getHelp() {
        return "Makes a webpage that has your message to share with people\n" +
                "Usage: `/share <text>`";
    }
}
