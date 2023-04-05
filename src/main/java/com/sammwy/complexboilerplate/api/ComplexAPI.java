package com.sammwy.complexboilerplate.api;

import org.bukkit.entity.Player;

import com.sammwy.complexboilerplate.ComplexBoilerplate;
import com.sammwy.complexboilerplate.players.ComplexPlayer;

public class ComplexAPI {
    private static ComplexBoilerplate plugin;

    public ComplexAPI(ComplexBoilerplate plugin) {
        ComplexAPI.plugin = plugin;
    }

    public static ComplexPlayer getPlayer(Player player) {
        return plugin.getPlayerManager().getPlayer(player);
    }
}
