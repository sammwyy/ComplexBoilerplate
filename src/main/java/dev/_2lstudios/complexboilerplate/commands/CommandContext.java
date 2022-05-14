package dev._2lstudios.complexboilerplate.commands;


import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev._2lstudios.complexboilerplate.ComplexBoilerplate;
import dev._2lstudios.complexboilerplate.errors.BadArgumentException;
import dev._2lstudios.complexboilerplate.errors.MaterialNotFoundException;
import dev._2lstudios.complexboilerplate.errors.PlayerOfflineException;
import dev._2lstudios.complexboilerplate.errors.SoundNotFoundException;
import dev._2lstudios.complexboilerplate.errors.WorldNotFoundException;
import dev._2lstudios.complexboilerplate.players.ComplexPlayer;

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

    public void parseArguments(String[] args) throws BadArgumentException, PlayerOfflineException, WorldNotFoundException, MaterialNotFoundException, SoundNotFoundException {
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