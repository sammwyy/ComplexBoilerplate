package com.sammwy.complexboilerplate.players;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import com.sammwy.complexboilerplate.ComplexBoilerplate;

public class ComplexPlayerManager {
    private ComplexBoilerplate plugin;

    private Map<Player, ComplexPlayer> players;

    public ComplexPlayerManager(ComplexBoilerplate plugin) {
        this.plugin = plugin;
        this.players = new HashMap<>();
    }

    public ComplexPlayer addPlayer(Player bukkitPlayer) {
        ComplexPlayer player = new ComplexPlayer(this.plugin, bukkitPlayer);
        this.players.put(bukkitPlayer, player);
        return player;
    }

    public ComplexPlayer removePlayer(Player bukkitPlayer) {
        return this.players.remove(bukkitPlayer);
    }

    public ComplexPlayer getPlayer(Player bukkitPlayer) {
        return this.players.get(bukkitPlayer);
    }

    public ComplexPlayer getPlayer(String name) {
        Player bukkitPlayer = this.plugin.getServer().getPlayerExact(name);
        if (bukkitPlayer != null && bukkitPlayer.isOnline()) {
            return this.getPlayer(bukkitPlayer);
        } else {
            return null;
        }
    }

    public Collection<ComplexPlayer> getPlayers() {
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