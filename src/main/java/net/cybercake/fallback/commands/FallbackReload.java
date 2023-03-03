package net.cybercake.fallback.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.cybercake.cyberapi.spigot.basic.BetterStackTraces;
import net.cybercake.cyberapi.spigot.chat.TabCompleteType;
import net.cybercake.cyberapi.spigot.chat.UChat;
import net.cybercake.cyberapi.spigot.server.commands.CommandInformation;
import net.cybercake.fallback.Main;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FallbackReload extends net.cybercake.cyberapi.spigot.server.commands.SpigotCommand {

    public FallbackReload() {
        super(
                newCommand("fallbackReload")
                        .setUsage("&6/&afallbackReload")
                        .setDescription("Reloads the Fallback plugin's configuration.")
                        .setPermission("fallbackserver.reload", UChat.chat("&cYou don't have permission to use this command!"))
                        .setTabCompleteType(TabCompleteType.SEARCH)
                        .setCommodore(
                                LiteralArgumentBuilder.literal("fallbackReload")
                                        .build()
                        )
        );
    }

    @Override
    public boolean perform(@NotNull CommandSender sender, @NotNull String command, CommandInformation information, String[] args) {
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
    public List<String> tab(@NotNull CommandSender sender, @NotNull String command, CommandInformation information, String[] args) {
        return null;
    }
}
