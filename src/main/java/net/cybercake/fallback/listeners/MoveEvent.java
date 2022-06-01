package net.cybercake.fallback.listeners;

import net.cybercake.fallback.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveEvent implements Listener {

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event) {
        if(!Main.getInstance().getConfiguration().disableMovement()) return;

        event.setCancelled(true);
    }

}
