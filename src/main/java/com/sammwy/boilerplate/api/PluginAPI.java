package com.sammwy.boilerplate.api;

import org.bukkit.entity.Player;

import com.sammwy.boilerplate.BukkitBoilerplate;
import com.sammwy.boilerplate.players.PluginPlayer;

public class PluginAPI {
    private static BukkitBoilerplate plugin;

    public PluginAPI(BukkitBoilerplate plugin) {
        PluginAPI.plugin = plugin;
    }

    public static PluginPlayer getPlayer(Player player) {
        return plugin.getPlayerManager().getPlayer(player);
    }
}
