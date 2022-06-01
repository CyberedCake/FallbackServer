package net.cybercake.fallback.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.cybercake.fallback.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ChatEvent implements Listener {

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        if(!Main.getInstance().getConfiguration().disableChat()) return;

        event.setCancelled(true);
    }

}
