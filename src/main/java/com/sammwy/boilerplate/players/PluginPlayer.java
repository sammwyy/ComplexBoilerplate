package com.sammwy.boilerplate.players;

import org.bukkit.entity.Player;

import com.sammwy.boilerplate.BukkitBoilerplate;

public class PluginPlayer extends PluginPlayerBase {
    public PluginPlayer(BukkitBoilerplate plugin, Player bukkitPlayer) {
        super(plugin, bukkitPlayer);
    }

    public void download() {
    }
}