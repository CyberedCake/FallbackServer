package net.cybercake.fallback.tasks;

import net.cybercake.fallback.Main;
import org.bukkit.Bukkit;

public class AttemptToSend implements Runnable {

    @Override
    public void run() {
        boolean disableVisibility = Main.getInstance().getConfiguration().disableVisibility();
        Bukkit.getOnlinePlayers().forEach(player -> {
            if(disableVisibility) Bukkit.getOnlinePlayers().forEach(player1 -> player.hidePlayer(Main.getInstance(), player1));

            Main.getInstance().send(player, "$$configuration");
        });
    }
}
