package com.sammwy.complexboilerplate.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.sammwy.complexboilerplate.ComplexBoilerplate;
import com.sammwy.complexboilerplate.errors.BadArgumentException;
import com.sammwy.complexboilerplate.errors.MaterialNotFoundException;
import com.sammwy.complexboilerplate.errors.PlayerOfflineException;
import com.sammwy.complexboilerplate.errors.SoundNotFoundException;
import com.sammwy.complexboilerplate.errors.WorldNotFoundException;
import com.sammwy.complexboilerplate.players.ComplexPlayer;
import com.sammwy.complexboilerplate.players.OfflinePlayer;
import com.sammwy.complexboilerplate.utils.BukkitUtils;

public class CommandArguments {
    private ComplexBoilerplate plugin;
    private Argument[] requiredArguments;
    private boolean isParsingLargeString = false;

    private List<Object> arguments = new ArrayList<>();

    public CommandArguments(ComplexBoilerplate plugin, Argument[] requiredArguments) {
        this.plugin = plugin;
        this.requiredArguments = requiredArguments;
    }

    public boolean hasIndex(int index) {
        return index < arguments.size() && index >= 0;
    }

    public String getString(int index) {
        if (!this.hasIndex(index))
            return null;
        return (String) this.arguments.get(index);
    }

    public int getInt(int index) {
        if (!this.hasIndex(index))
            return 0;
        return (int) this.arguments.get(index);
    }

    public boolean getBoolean(int index) {
        if (!this.hasIndex(index))
            return false;
        return (boolean) this.arguments.get(index);
    }

    public ComplexPlayer getPlayer(int index) {
        if (!this.hasIndex(index))
            return null;
        return (ComplexPlayer) this.arguments.get(index);
    }

    public Material getMaterial(int index) {
        if (!this.hasIndex(index))
            return null;
        return (Material) this.arguments.get(index);
    }

    public Sound getSound(int index) {
        if (!this.hasIndex(index))
            return null;
        return (Sound) this.arguments.get(index);
    }

    public World getWorld(int index) {
        if (!this.hasIndex(index))
            return null;
        return (World) this.arguments.get(index);
    }

    public void parse(String[] args) throws BadArgumentException, PlayerOfflineException, WorldNotFoundException,
            MaterialNotFoundException, SoundNotFoundException {
        int i = 0;

        for (String arg : args) {
            if (this.requiredArguments.length <= i) {
                if (isParsingLargeString) {
                    int index = this.arguments.size() - 1;
                    String str = (String) this.arguments.get(index);
                    str += " " + arg;
                    this.arguments.set(index, str);
                } else {
                    this.arguments.add(arg);
                }
                continue;
            }

            Argument type = this.requiredArguments[i];
            Object value = null;

            if (type == Argument.LARGE_STRING) {
                isParsingLargeString = true;
                value = arg;
            }

            else if (type == Argument.STRING) {
                value = arg;
            }

            else if (type == Argument.BOOL) {
                if (arg.equalsIgnoreCase("true")) {
                    value = true;
                } else if (arg.equalsIgnoreCase("false")) {
                    value = false;
                }
                throw new BadArgumentException(arg, "boolean");
            }

            else if (type == Argument.INT) {
                try {
                    value = Integer.parseInt(arg);
                } catch (Exception _exception) {
                    throw new BadArgumentException(arg, "number");
                }
            }

            else if (type == Argument.PLAYER || type == Argument.OFFLINE_PLAYER) {
                Player player = Bukkit.getServer().getPlayerExact(arg);
                if (player != null && player.isOnline()) {
                    value = this.plugin.getPlayerManager().getPlayer(player);
                } else {
                    if (type == Argument.OFFLINE_PLAYER) {
                        value = new OfflinePlayer(this.plugin, player, arg);
                    } else {
                        throw new PlayerOfflineException(arg);
                    }
                }
            }

            else if (type == Argument.WORLD) {
                World world = Bukkit.getServer().getWorld(arg);
                if (world != null) {
                    value = world;
                } else {
                    throw new WorldNotFoundException(arg);
                }
            }

            else if (type == Argument.MATERIAL) {
                value = BukkitUtils.getMaterial(arg);
                if (value == null) {
                    throw new MaterialNotFoundException(arg);
                }
            }

            else if (type == Argument.SOUND) {
                value = BukkitUtils.getSound(arg);
                if (value == null) {
                    throw new SoundNotFoundException(arg);
                }
            }

            i++;
            this.arguments.add(value);
        }
    }
}
