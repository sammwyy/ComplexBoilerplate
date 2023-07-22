package com.sammwy.boilerplate.players;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.sammwy.boilerplate.BukkitBoilerplate;
import com.sammwy.boilerplate.commands.CommandExecutor;
import com.sammwy.libchat.LibChatBukkit;
import com.sammwy.libchat.chat.Component;
import com.sammwy.libi18n.Language;

import me.clip.placeholderapi.PlaceholderAPI;

public class PluginPlayerBase extends CommandExecutor {
    private Player bukkitPlayer;

    public PluginPlayerBase(BukkitBoilerplate plugin, Player bukkitPlayer) {
        super(plugin, bukkitPlayer);
        this.bukkitPlayer = bukkitPlayer;
    }

    @Override
    public String formatMessage(String message) {
        String output = super.formatMessage(message);

        if (this.getPlugin().hasPlugin("PlaceholderAPI")) {
            output = PlaceholderAPI.setPlaceholders(this.getBukkitPlayer(), output);
        }

        return output;
    }

    @Override
    public Language getLang() {
        return this.getPlugin().getLanguageManager().getLanguage(this.bukkitPlayer);
    }

    public Player getBukkitPlayer() {
        return this.bukkitPlayer;
    }

    public String getName() {
        return this.bukkitPlayer.getName();
    }

    public String getLowerName() {
        return this.getName().toLowerCase();
    }

    public boolean isOnline() {
        return this.bukkitPlayer != null && this.bukkitPlayer.isOnline();
    }

    public void sendRawMessage(String json) {
        LibChatBukkit.sendMessage(bukkitPlayer, json);
    }

    public void sendActionBar(String text) {
        LibChatBukkit.sendActionbar(bukkitPlayer, text);
    }

    public void sendMessage(Component component) {
        LibChatBukkit.sendMessage(bukkitPlayer, component);
    }

    public void sendToServer(String server) {
        try {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);
            out.writeUTF("Connect");
            out.writeUTF(server);
            this.getBukkitPlayer().sendPluginMessage(this.getPlugin(), "BungeeCord", b.toByteArray());
            b.close();
            out.close();
        } catch (Exception e) {
            this.getBukkitPlayer().sendMessage(ChatColor.RED + "Error when trying to connect to " + server);
        }
    }
}
