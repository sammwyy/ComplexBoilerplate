package com.sammwy.boilerplate.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sammwy.boilerplate.BukkitBoilerplate;
import com.sammwy.boilerplate.errors.BadArgumentException;
import com.sammwy.boilerplate.errors.MaterialNotFoundException;
import com.sammwy.boilerplate.errors.PlayerOfflineException;
import com.sammwy.boilerplate.errors.SoundNotFoundException;
import com.sammwy.boilerplate.errors.WorldNotFoundException;
import com.sammwy.boilerplate.players.PluginPlayer;

public class CommandContext {
    private BukkitBoilerplate plugin;
    private CommandExecutor executor;
    private CommandArguments arguments;

    public CommandContext(BukkitBoilerplate plugin, CommandSender sender, Argument[] requiredArguments) {
        if (sender instanceof Player) {
            this.executor = plugin.getPlayerManager().getPlayer((Player) sender);
        } else {
            this.executor = new CommandExecutor(plugin, sender);
        }

        this.plugin = plugin;
        this.arguments = new CommandArguments(plugin, requiredArguments);
    }

    public void parseArguments(String[] args) throws BadArgumentException, PlayerOfflineException,
            WorldNotFoundException, MaterialNotFoundException, SoundNotFoundException {
        this.arguments.parse(args);
    }

    public BukkitBoilerplate getPlugin() {
        return this.plugin;
    }

    public CommandExecutor getExecutor() {
        return this.executor;
    }

    public PluginPlayer getPlayer() {
        return (PluginPlayer) this.executor;
    }

    public boolean isPlayer() {
        return this.executor.isPlayer();
    }

    public CommandArguments getArguments() {
        return this.arguments;
    }
}