package com.sammwy.boilerplate.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sammwy.boilerplate.BukkitBoilerplate;
import com.sammwy.libi18n.Language;

public class CommandExecutor {
    private BukkitBoilerplate plugin;
    private CommandSender sender;

    public CommandExecutor(BukkitBoilerplate plugin, CommandSender sender) {
        this.plugin = plugin;
        this.sender = sender;
    }

    public String formatMessage(String message) {
        return ChatColor.translateAlternateColorCodes('&', message)
                .replace("{plugin_version}", this.plugin.getDescription().getVersion());
    }

    public Language getLang() {
        return this.plugin.getLanguageManager().getDefaultLanguage();
    }

    public String getI18nMessage(String key) {
        Language lang = this.getLang();
        String message = lang.get(key);

        if (message == null) {
            return "<missing translation key \"" + key + "\"> in lang " + lang + ">";
        } else {
            return message;
        }
    }

    public void sendMessage(String message) {
        this.sender.sendMessage(this.formatMessage(message));
    }

    public void sendI18nMessage(String key) {
        this.sendMessage(this.getI18nMessage(key));
    }

    public boolean isPlayer() {
        return this.sender instanceof Player;
    }

    public boolean hasPermission(String permission) {
        if (this.isPlayer()) {
            return this.sender.hasPermission(permission);
        } else {
            return true;
        }
    }

    public BukkitBoilerplate getPlugin() {
        return this.plugin;
    }
}