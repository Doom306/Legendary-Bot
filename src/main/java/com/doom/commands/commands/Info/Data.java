package com.doom.commands.commands.Info;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;

import java.util.HashMap;

public class Data {

    private static HashMap<Guild, String> message = new HashMap<>();
    private static HashMap<User, Guild> user = new HashMap<>();

    public Data() {
    }

    public static HashMap<Guild, String> getMessage() {
        return message;
    }

    public static HashMap<User, Guild> getUser() {
        return user;
    }

    public static void setMessage(HashMap<Guild, String> message) {
        Data.message = message;
    }

    public static void setUser(HashMap<User, Guild> user) {
        Data.user = user;
    }
}
