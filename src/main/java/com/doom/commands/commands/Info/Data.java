package com.doom.commands.commands.Info;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;

import java.util.HashMap;

public class Data {

    public static HashMap<Guild, String> message = new HashMap<>();
    public static HashMap<User, Guild> user = new HashMap<>();
}
