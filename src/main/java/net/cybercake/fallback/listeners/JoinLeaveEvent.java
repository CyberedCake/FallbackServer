package net.cybercake.fallback.listeners;

import net.cybercake.fallback.Configuration;
import net.cybercake.fallback.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinLeaveEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Configuration configuration = Main.getInstance().getConfiguration();
        if(!configuration.disableJoinLeaveEvents()) return;

        event.joinMessage(Component.empty());
        event.getPlayer().teleport(configuration.getSpawn());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if(!Main.getInstance().getConfiguration().disableJoinLeaveEvents()) return;

        event.quitMessage(Component.empty());
    }

}
