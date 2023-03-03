package net.cybercake.fallback.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.cybercake.cyberapi.spigot.server.listeners.SpigotListener;
import net.cybercake.fallback.Main;
import org.bukkit.event.EventHandler;

public class ChatEvent implements SpigotListener {

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        if(!Main.getInstance().getConfiguration().disableChat()) return;

        event.setCancelled(true);
    }

}
