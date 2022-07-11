package net.cybercake.fallback.commands;

import net.cybercake.cyberapi.spigot.chat.UChat;
import net.cybercake.cyberapi.spigot.chat.UTabComp;
import net.cybercake.cyberapi.spigot.server.commands.Command;
import net.cybercake.cyberapi.spigot.server.commands.CommandInformation;
import net.cybercake.fallback.Main;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class AttemptConnect extends Command {

    public AttemptConnect() {
        super(
                newCommand("attemptconnect")
                        .setUsage("&6/&aattemptconnect <server>")
                        .setDescription("Attempts to connect you to a certain server.")
                        .setCommodore(true)
        );
    }

    @Override
    public boolean perform(@net.cybercake.cyberapi.dependencies.annotations.jetbrains.NotNull CommandSender sender, @net.cybercake.cyberapi.dependencies.annotations.jetbrains.NotNull String command, CommandInformation information, String[] args) {
        if(!(sender instanceof Player player)) {
            sender.sendMessage(UChat.component("&cOnly in-game players can use this command!")); return true;
        }

        if(args.length < 1) {
            player.sendMessage(UChat.component("&6Attempting to connect you to default server &2(&a" + Main.getInstance().getConfiguration().getConnectTo() + "&2)&6..."));
            Main.getInstance().send(player, "$$configuration");
        }else{
            player.sendMessage(UChat.component("&6Attempting to connect you to &a" + args[0] + "&6..."));
            Main.getInstance().send(player, args[0]);
        }

        return true;
    }

    @Override
    public List<String> tab(@net.cybercake.cyberapi.dependencies.annotations.jetbrains.NotNull CommandSender sender, @net.cybercake.cyberapi.dependencies.annotations.jetbrains.NotNull String command, CommandInformation information, String[] args) {
        if(args.length == 1) return UTabComp.tabCompletionsSearch(args[0], List.of(Main.getInstance().getConfiguration().getConnectTo()));
        return UTabComp.emptyList;
    }

}
