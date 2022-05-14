package dev._2lstudios.complexboilerplate.api;

import org.bukkit.entity.Player;

import dev._2lstudios.complexboilerplate.ComplexBoilerplate;
import dev._2lstudios.complexboilerplate.players.ComplexPlayer;

public class ComplexAPI {
    private static ComplexBoilerplate plugin;
    
    public ComplexAPI(ComplexBoilerplate plugin) {
        ComplexAPI.plugin = plugin;
    }

    public static ComplexPlayer getPlayer(Player player) {
        return plugin.getPlayerManager().getPlayer(player);
    }
}
