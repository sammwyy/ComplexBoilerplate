package com.sammwy.complexboilerplate.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sammwy.complexboilerplate.ComplexBoilerplate;
import com.sammwy.complexboilerplate.errors.BadArgumentException;
import com.sammwy.complexboilerplate.errors.MaterialNotFoundException;
import com.sammwy.complexboilerplate.errors.PlayerOfflineException;
import com.sammwy.complexboilerplate.errors.SoundNotFoundException;
import com.sammwy.complexboilerplate.errors.WorldNotFoundException;
import com.sammwy.complexboilerplate.players.ComplexPlayer;

public class CommandContext {
    private ComplexBoilerplate plugin;
    private CommandExecutor executor;
    private CommandArguments arguments;

    public CommandContext(ComplexBoilerplate plugin, CommandSender sender, Argument[] requiredArguments) {
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

    public ComplexBoilerplate getPlugin() {
        return this.plugin;
    }

    public CommandExecutor getExecutor() {
        return this.executor;
    }

    public ComplexPlayer getPlayer() {
        return (ComplexPlayer) this.executor;
    }

    public boolean isPlayer() {
        return this.executor.isPlayer();
    }

    public CommandArguments getArguments() {
        return this.arguments;
    }
}