package net.cybercake.fallback.commands;

import net.cybercake.cyberapi.chat.UChat;
import net.cybercake.cyberapi.chat.UTabComp;
import net.cybercake.cyberapi.exceptions.BetterStackTraces;
import net.cybercake.cyberapi.instances.Spigot;
import net.cybercake.fallback.Main;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FallbackReload implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        try {
            long mss = System.currentTimeMillis();

            Main.getInstance().getConfiguration().reload();

            sender.sendMessage(UChat.component("&aSuccessfully &7reloaded the fallback configuration in &b" + (System.currentTimeMillis()-mss) + "&bms&7!"));
            Spigot.playSound(sender, Sound.ENTITY_PLAYER_LEVELUP, 1F, 1F);
        } catch (Exception exception) {
            sender.sendMessage(UChat.component("&cAn error occurred whilst reloading the fallback configuration! &4" + exception));
            BetterStackTraces.print(exception);
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return UTabComp.emptyList;
    }
}
