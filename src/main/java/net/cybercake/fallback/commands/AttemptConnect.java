package net.cybercake.fallback.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.cybercake.cyberapi.spigot.chat.TabCompleteType;
import net.cybercake.cyberapi.spigot.chat.UChat;
import net.cybercake.cyberapi.spigot.server.commands.CommandInformation;
import net.cybercake.cyberapi.spigot.server.commands.SpigotCommand;
import net.cybercake.fallback.Main;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AttemptConnect extends SpigotCommand {

    public AttemptConnect() {
        super(
                newCommand("attemptConnect")
                        .setUsage("&6/&aattemptConnect <server>")
                        .setDescription("Attempts to connect you to a certain server.")
                        .setPermission("fallbackserver.attemptconnect", UChat.chat("&cYou don't have permission to use this command!"))
                        .setTabCompleteType(TabCompleteType.SEARCH)
                        .setCommodore(
                                LiteralArgumentBuilder.literal("attemptConnect")
                                        .then(RequiredArgumentBuilder.argument("server", StringArgumentType.string()))
                                        .build()
                        )
        );
    }

    @Override
    public boolean perform(@NotNull CommandSender sender, @NotNull String command, CommandInformation information, String[] args) {
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
    public List<String> tab(@NotNull CommandSender sender, @NotNull String command, CommandInformation information, String[] args) {
        List<String> returned = new ArrayList<>();
        if(args.length == 1) {
            returned.add(Main.getInstance().getConfiguration().getConnectTo());
            returned.addAll(Main.getInstance().getConfiguration().getPlayersToServers().values().stream().map(Object::toString).toList());
        }

        if(String.join(" ", Arrays.copyOfRange(args, 0, args.length)).startsWith("\"")) { // add begin quote if it's added to suggestions
            returned = new ArrayList<>(returned.stream().map(mappedItem -> "\"" + mappedItem).toList());
            if(!String.join(" ", Arrays.copyOfRange(args, 0, args.length)).endsWith("\"")) { // add end quote if a begin quote exists
                returned = new ArrayList<>(returned.stream().map(mappedItem -> mappedItem + "\"").toList());
                returned.add(args[args.length-1] + "\"");
            }
        }

        return returned;
    }

}
