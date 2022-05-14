package dev._2lstudios.complexboilerplate;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import dev._2lstudios.complexboilerplate.api.ComplexAPI;
import dev._2lstudios.complexboilerplate.api.events.ComplexEvent;
import dev._2lstudios.complexboilerplate.commands.CommandListener;
import dev._2lstudios.complexboilerplate.commands.impl.HelloCommand;
import dev._2lstudios.complexboilerplate.config.ConfigManager;
import dev._2lstudios.complexboilerplate.config.Configuration;
import dev._2lstudios.complexboilerplate.i18n.LanguageManager;
import dev._2lstudios.complexboilerplate.listeners.PlayerJoinListener;
import dev._2lstudios.complexboilerplate.listeners.PlayerQuitListener;
import dev._2lstudios.complexboilerplate.players.ComplexPlayerManager;

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
    public void onEnable () {
        // Initialize API
        new ComplexAPI(this);

        // Instantiate managers.
        this.configManager = new ConfigManager(this);
        this.languageManager = new LanguageManager(this);
        this.playerManager = new ComplexPlayerManager(this);

        // Load data.
        this.languageManager.loadLanguagesSafe();
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