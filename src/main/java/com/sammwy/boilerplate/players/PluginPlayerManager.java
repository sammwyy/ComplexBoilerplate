package com.sammwy.boilerplate.players;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import com.sammwy.boilerplate.BukkitBoilerplate;

public class PluginPlayerManager {
    private BukkitBoilerplate plugin;

    private Map<Player, PluginPlayer> players;

    public PluginPlayerManager(BukkitBoilerplate plugin) {
        this.plugin = plugin;
        this.players = new HashMap<>();
    }

    public PluginPlayer addPlayer(Player bukkitPlayer) {
        PluginPlayer player = new PluginPlayer(this.plugin, bukkitPlayer);
        this.players.put(bukkitPlayer, player);
        return player;
    }

    public PluginPlayer removePlayer(Player bukkitPlayer) {
        return this.players.remove(bukkitPlayer);
    }

    public PluginPlayer getPlayer(Player bukkitPlayer) {
        return this.players.get(bukkitPlayer);
    }

    public PluginPlayer getPlayer(String name) {
        Player bukkitPlayer = this.plugin.getServer().getPlayerExact(name);
        if (bukkitPlayer != null && bukkitPlayer.isOnline()) {
            return this.getPlayer(bukkitPlayer);
        } else {
            return null;
        }
    }

    public Collection<PluginPlayer> getPlayers() {
        return this.players.values();
    }

    public void clear() {
        this.players.clear();
    }

    public void addAll() {
        for (Player bukkitPlayer : this.plugin.getServer().getOnlinePlayers()) {
            this.addPlayer(bukkitPlayer).download();
        }
    }
}