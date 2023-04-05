package com.sammwy.complexboilerplate;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.sammwy.complexboilerplate.api.ComplexAPI;
import com.sammwy.complexboilerplate.api.events.ComplexEvent;
import com.sammwy.complexboilerplate.commands.CommandListener;
import com.sammwy.complexboilerplate.commands.impl.HelloCommand;
import com.sammwy.complexboilerplate.config.ConfigManager;
import com.sammwy.complexboilerplate.config.Configuration;
import com.sammwy.complexboilerplate.listeners.PlayerJoinListener;
import com.sammwy.complexboilerplate.listeners.PlayerQuitListener;
import com.sammwy.complexboilerplate.players.ComplexPlayerManager;
import com.sammwy.libi18n.LanguageManager;

public class ComplexBoilerplate extends JavaPlugin {
    private ConfigManager configManager;
    private LanguageManager languageManager;
    private ComplexPlayerManager playerManager;

    private void addCommand(CommandListener command) {
        command.register(this, false);
    }

    private void addListener(Listener listener) {
        this.getServer().getPluginManager().registerEvents(listener, this);
    }

    public boolean callEvent(ComplexEvent event) {
        this.getServer().getPluginManager().callEvent(event);
        return !event.isCancelled();
    }

    @Override
    public void onEnable() {
        // Initialize API
        new ComplexAPI(this);

        // Instantiate managers.
        this.configManager = new ConfigManager(this);
        this.languageManager = LanguageManager.forBukkit(this, "lang");
        this.playerManager = new ComplexPlayerManager(this);

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

    public ComplexPlayerManager getPlayerManager() {
        return this.playerManager;
    }

    // Others getters
    public boolean hasPlugin(String pluginName) {
        Plugin plugin = this.getServer().getPluginManager().getPlugin(pluginName);
        return plugin != null && plugin.isEnabled();
    }
}