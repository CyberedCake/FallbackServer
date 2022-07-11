package net.cybercake.fallback.commands;

import net.cybercake.cyberapi.spigot.basic.BetterStackTraces;
import net.cybercake.cyberapi.spigot.chat.UChat;
import net.cybercake.cyberapi.spigot.server.commands.CommandInformation;
import net.cybercake.fallback.Main;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;

import java.util.List;

public class FallbackReload extends net.cybercake.cyberapi.spigot.server.commands.Command {

    public FallbackReload() {
        super(
                newCommand("fallbackreload")
                        .setUsage("&6/&afallbackreload")
                        .setDescription("Reloads the " + Main.getInstance().getPluginName() + " plugin's configuration.")
                        .setCommodore(true)
        );
    }

    @Override
    public boolean perform(@net.cybercake.cyberapi.dependencies.annotations.jetbrains.NotNull CommandSender sender, @net.cybercake.cyberapi.dependencies.annotations.jetbrains.NotNull String command, CommandInformation information, String[] args) {
        try {
            long mss = System.currentTimeMillis();

            Main.getInstance().getConfiguration().reload();

            sender.sendMessage(UChat.component("&6Successfully reloaded the fallback configuration in &a" + (System.currentTimeMillis()-mss) + "ms&6!"));
            Main.getInstance().playSound(sender, Sound.ENTITY_PLAYER_LEVELUP, 1F, 1F);
        } catch (Exception exception) {
            sender.sendMessage(UChat.component("&cAn error occurred whilst reloading the fallback configuration! &4" + exception));
            BetterStackTraces.print(exception);
        }

        return true;
    }

    @Override
    public List<String> tab(@net.cybercake.cyberapi.dependencies.annotations.jetbrains.NotNull CommandSender sender, @net.cybercake.cyberapi.dependencies.annotations.jetbrains.NotNull String command, CommandInformation information, String[] args) {
        return null;
    }
}
