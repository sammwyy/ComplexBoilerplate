package com.sammwy.complexboilerplate.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.sammwy.complexboilerplate.ComplexBoilerplate;
import com.sammwy.complexboilerplate.errors.BadArgumentException;
import com.sammwy.complexboilerplate.errors.MaterialNotFoundException;
import com.sammwy.complexboilerplate.errors.PlayerOfflineException;
import com.sammwy.complexboilerplate.errors.SoundNotFoundException;
import com.sammwy.complexboilerplate.errors.WorldNotFoundException;
import com.sammwy.complexboilerplate.utils.ArrayUtils;

public abstract class CommandListener implements CommandExecutor {
    protected Command command;
    protected ComplexBoilerplate plugin;

    private List<CommandListener> subCommands = new ArrayList<>();

    // For override
    protected void onExecuteByPlayer(CommandContext ctx) {
        ctx.getExecutor().sendI18nMessage("common.no-by-player");
    }

    protected void onExecuteByConsole(CommandContext ctx) {
        ctx.getExecutor().sendI18nMessage("common.no-by-console");
    }

    protected void onExecute(CommandContext ctx) {
        if (ctx.isPlayer()) {
            this.onExecuteByPlayer(ctx);
        } else {
            this.onExecuteByConsole(ctx);
        }
    }

    protected void onMissingPermission(CommandContext ctx) {
        ctx.getExecutor().sendI18nMessage("common.no-permission");
    }

    protected void onBadUsage(CommandContext ctx) {
        String key = command.usageKey();
        if (key == null || key.isEmpty()) {
            key = command.name() + ".usage";
        }

        ctx.getExecutor().sendI18nMessage(key);
    }

    protected void onPlayerOffline(CommandContext ctx, String playerName) {
        ctx.getExecutor().sendMessage(
                ctx.getExecutor().getI18nMessage("common.offline-player")
                        .replace("{player}", playerName));
    }

    protected void onBadArgument(CommandContext ctx, String value, String type) {
        ctx.getExecutor().sendMessage(
                ctx.getExecutor().getI18nMessage("common.arg-must-be-" + type)
                        .replace("{arg}", value));
    }

    protected void onWorldNotFound(CommandContext ctx, String worldName) {
        ctx.getExecutor().sendMessage(
                ctx.getExecutor().getI18nMessage("common.world-not-found")
                        .replace("{world}", worldName));
    }

    protected void onMaterialNotFound(CommandContext ctx, String materialName) {
        ctx.getExecutor().sendMessage(
                ctx.getExecutor().getI18nMessage("common.material-not-found")
                        .replace("{material}", materialName));
    }

    protected void onSoundNotFound(CommandContext ctx, String soundName) {
        ctx.getExecutor().sendMessage(
                ctx.getExecutor().getI18nMessage("common.sound-not-found")
                        .replace("{sound}", soundName));
    }

    protected void onException(CommandContext ctx, Exception e) {
        e.printStackTrace();
        ctx.getExecutor().sendI18nMessage("common.exception");
    }

    // Utils
    public Command getCommandInfo() {
        return this.command;
    }

    public void addSubcommand(CommandListener subCommand) {
        this.subCommands.add(subCommand);
    }

    public CommandListener getSubCommand(String name) {
        for (CommandListener subCommand : this.subCommands) {
            if (subCommand.getCommandInfo().name().equalsIgnoreCase(name)) {
                return subCommand;
            }
        }

        return null;
    }

    public void register(ComplexBoilerplate plugin, boolean isSubCommand) {
        this.plugin = plugin;
        this.command = this.getClass().getAnnotation(Command.class);

        if (!isSubCommand) {
            plugin.getCommand(this.command.name()).setExecutor(this);
        }

        for (CommandListener subCommand : this.subCommands) {
            subCommand.register(this.plugin, true);
        }
    }

    // Command logic
    public void execute(CommandSender sender, String[] args) {
        CommandContext ctx = new CommandContext(this.plugin, sender, command.arguments());

        // Check for permissions
        if (!command.permission().isEmpty() && !sender.hasPermission(command.permission())) {
            this.onMissingPermission(ctx);
            return;
        }

        // Check for subcommands
        if (args.length > 0) {
            String possibleSubCommand = args[0];
            CommandListener sc = this.getSubCommand(possibleSubCommand);
            if (sc != null) {
                sc.execute(sender, ArrayUtils.removeFirstElement(args));
                return;
            }
        }

        // Check for arguments count
        if (command.minArguments() > args.length) {
            this.onBadUsage(ctx);
            return;
        }

        // Check for arguments type
        try {
            ctx.parseArguments(args);
            // Execute
            this.onExecute(ctx);
        } catch (PlayerOfflineException e) {
            this.onPlayerOffline(ctx, e.getUsername());
        } catch (BadArgumentException e) {
            this.onBadArgument(ctx, e.getArg(), e.getWaiting());
        } catch (WorldNotFoundException e) {
            this.onWorldNotFound(ctx, e.getWorldName());
        } catch (MaterialNotFoundException e) {
            this.onMaterialNotFound(ctx, e.getMaterialName());
        } catch (SoundNotFoundException e) {
            this.onSoundNotFound(ctx, e.getSoundName());
        } catch (Exception e) {
            this.onException(ctx, e);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        this.execute(sender, args);
        return true;
    }
}