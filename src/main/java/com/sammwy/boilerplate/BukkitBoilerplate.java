package com.sammwy.boilerplate;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.sammwy.boilerplate.api.PluginAPI;
import com.sammwy.boilerplate.api.events.PluginEvent;
import com.sammwy.boilerplate.commands.CommandListener;
import com.sammwy.boilerplate.commands.impl.HelloCommand;
import com.sammwy.boilerplate.config.ConfigManager;
import com.sammwy.boilerplate.config.Configuration;
import com.sammwy.boilerplate.listeners.PlayerJoinListener;
import com.sammwy.boilerplate.listeners.PlayerQuitListener;
import com.sammwy.boilerplate.players.PluginPlayerManager;
import com.sammwy.libi18n.LanguageManager;

public class BukkitBoilerplate extends JavaPlugin {
    private ConfigManager configManager;
    private LanguageManager languageManager;
    private PluginPlayerManager playerManager;

    private void addCommand(CommandListener command) {
        command.register(this, false);
    }

    private void addListener(Listener listener) {
        this.getServer().getPluginManager().registerEvents(listener, this);
    }

    public boolean callEvent(PluginEvent event) {
        this.getServer().getPluginManager().callEvent(event);
        return !event.isCancelled();
    }

    @Override
    public void onEnable() {
        // Initialize API
        new PluginAPI(this);

        // Instantiate managers.
        this.configManager = new ConfigManager(this);
        this.languageManager = LanguageManager.forBukkit(this, "lang");
        this.playerManager = new PluginPlayerManager(this);

        // Load translation.
        this.languageManager.extractIfEmpty("lang/");
        this.languageManager.withDefault(getConfig().getString("settings.default-lang"));

        // Load data.
        this.languageManager.tryLoadLanguages();
        this.playerManager.addAll();

        // Register listeners.
        this.addListener(new PlayerJoinListener(this));
        this.addListener(new PlayerQuitListener(this));

        // Register commands.
        this.addCommand(new HelloCommand());
    }

    // Configuration getters
    public Configuration getConfig() {
        return this.configManager.getConfig("config.yml");
    }

    // Managers getters
    public LanguageManager getLanguageManager() {
        return this.languageManager;
    }

    public PluginPlayerManager getPlayerManager() {
        return this.playerManager;
    }

    // Others getters
    public boolean hasPlugin(String pluginName) {
        Plugin plugin = this.getServer().getPluginManager().getPlugin(pluginName);
        return plugin != null && plugin.isEnabled();
    }
}