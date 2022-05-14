package dev._2lstudios.complexboilerplate.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import dev._2lstudios.complexboilerplate.ComplexBoilerplate;

public class PlayerQuitListener implements Listener {
  private ComplexBoilerplate plugin;

  public PlayerQuitListener(ComplexBoilerplate plugin) {
    this.plugin = plugin;
  }
  
  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent e) {
    this.plugin.getPlayerManager().removePlayer(e.getPlayer());
  }
}
