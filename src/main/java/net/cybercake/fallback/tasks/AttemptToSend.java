package net.cybercake.fallback.tasks;

import net.cybercake.fallback.Main;
import org.bukkit.Bukkit;

public class AttemptToSend implements Runnable {

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> Main.getInstance().send(player, "$$configuration"));
    }
}
