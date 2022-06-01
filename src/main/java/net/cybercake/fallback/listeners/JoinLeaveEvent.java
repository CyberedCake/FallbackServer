package net.cybercake.fallback.listeners;

import net.cybercake.fallback.Configuration;
import net.cybercake.fallback.Main;
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
        if(!configuration.disableJoinLeaveEvents()) return;

        event.joinMessage(null);

        Player player = event.getPlayer();
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
