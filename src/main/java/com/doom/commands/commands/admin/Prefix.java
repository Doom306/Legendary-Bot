package com.doom.commands.commands.admin;

import com.doom.commands.commands.BadDesign;
import com.doom.database.DatabaseManager;

public class Prefix {
    public static String BETA_PREFIX = "/";

    public synchronized static String getDefaultPrefix(long guildId) {
        BETA_PREFIX = BadDesign.PREFIXES.computeIfAbsent(guildId, DatabaseManager.INSTANCE::getPrefix);

        return BETA_PREFIX;
    }
}