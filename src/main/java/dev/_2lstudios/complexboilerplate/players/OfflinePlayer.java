package dev._2lstudios.complexboilerplate.players;

import dev._2lstudios.complexboilerplate.ComplexBoilerplate;

public class OfflinePlayer extends ComplexPlayer {
    private String username;

    public OfflinePlayer(ComplexBoilerplate plugin, String username) {
        super(plugin, null);
        this.username = username.toLowerCase();
    }

    @Override
    public String getLowerName() {
        return this.username;
    }
}