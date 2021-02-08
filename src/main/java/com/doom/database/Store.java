package com.doom.database;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;

public class Store {
    private int stored = 0;
    private static List<Role> mentionedRoles;
    private static List<TextChannel> mentionedChannels;
    private static List<Message> messages;


    public int getStored() {
        return stored;
    }

    public List<Role> getMentionedRoles() {
        return mentionedRoles;
    }

    public List<TextChannel> getMentionedChannels() {
        return mentionedChannels;
    }

    public void setMentionedRoles(int count, List<Role> mentionedRoles) {
        this.mentionedRoles.set(count, mentionedRoles.get(0));
    }

    public void setMentionedChannels(int count, List<TextChannel> mentionedChannels) {
        this.mentionedChannels.set(count, mentionedChannels.get(0));
    }

    public List<Message> getTextChannels() {
        return messages;
    }

    public void setTextChannels(List<Message> messages) {
        this.messages = messages;
    }

    public void setStored(int stored) {
        this.stored = stored;
    }
}
