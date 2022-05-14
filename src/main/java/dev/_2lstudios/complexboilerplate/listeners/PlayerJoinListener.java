package dev._2lstudios.complexboilerplate.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import dev._2lstudios.complexboilerplate.ComplexBoilerplate;
import dev._2lstudios.complexboilerplate.players.ComplexPlayer;

public class PlayerJoinListener implements Listener {
  private ComplexBoilerplate plugin;

  public PlayerJoinListener(ComplexBoilerplate plugin) {
    this.plugin = plugin;
  }
  
  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent e) {
    ComplexPlayer player = this.plugin.getPlayerManager().addPlayer(e.getPlayer());
    player.download();
  }
}
