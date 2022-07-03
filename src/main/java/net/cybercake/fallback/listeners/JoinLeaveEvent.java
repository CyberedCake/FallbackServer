package net.cybercake.fallback.listeners;

import net.cybercake.fallback.Configuration;
import net.cybercake.fallback.Main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinLeaveEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Configuration configuration = Main.getInstance().getConfiguration();

        Player player = event.getPlayer();
        if(configuration.disableVisibility()) Bukkit.getOnlinePlayers().forEach(player1 -> player.hidePlayer(Main.getInstance(), player1));

        if(!configuration.disableJoinLeaveEvents()) return;

        event.joinMessage(null);

        player.setGameMode(GameMode.SPECTATOR);
        player.teleport(configuration.getSpawn());

        Main.getInstance().send(player, configuration.getConnectTo());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if(!Main.getInstance().getConfiguration().disableJoinLeaveEvents()) return;

        event.quitMessage(null);
    }

}
