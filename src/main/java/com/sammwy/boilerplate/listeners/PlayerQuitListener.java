package com.sammwy.boilerplate.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.sammwy.boilerplate.BukkitBoilerplate;

public class PlayerQuitListener implements Listener {
  private BukkitBoilerplate plugin;

  public PlayerQuitListener(BukkitBoilerplate plugin) {
    this.plugin = plugin;
  }

  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent e) {
    this.plugin.getPlayerManager().removePlayer(e.getPlayer());
  }
}
