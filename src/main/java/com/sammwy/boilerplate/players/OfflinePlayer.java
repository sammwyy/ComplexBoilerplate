package com.sammwy.boilerplate.players;

import org.bukkit.entity.Player;

import com.sammwy.boilerplate.BukkitBoilerplate;

public class OfflinePlayer extends PluginPlayer {
    private String username;

    public OfflinePlayer(BukkitBoilerplate plugin, Player bukkitPlayer, String username) {
        super(plugin, bukkitPlayer);
        this.username = username.toLowerCase();
    }

    @Override
    public String getLowerName() {
        return this.username;
    }
}