package com.sammwy.boilerplate.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.sammwy.boilerplate.BukkitBoilerplate;
import com.sammwy.boilerplate.players.PluginPlayer;

public class PlayerJoinListener implements Listener {
  private BukkitBoilerplate plugin;

  public PlayerJoinListener(BukkitBoilerplate plugin) {
    this.plugin = plugin;
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent e) {
    PluginPlayer player = this.plugin.getPlayerManager().addPlayer(e.getPlayer());
    player.download();
  }
}
