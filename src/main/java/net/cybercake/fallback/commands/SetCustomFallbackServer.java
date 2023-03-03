package net.cybercake.fallback.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.cybercake.cyberapi.spigot.basic.BetterStackTraces;
import net.cybercake.cyberapi.spigot.chat.TabCompleteType;
import net.cybercake.cyberapi.spigot.chat.UChat;
import net.cybercake.cyberapi.spigot.server.commands.CommandInformation;
import net.cybercake.cyberapi.spigot.server.commands.SpigotCommand;
import net.cybercake.fallback.Main;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SetCustomFallbackServer extends SpigotCommand {

    public SetCustomFallbackServer() {
        super(
                newCommand("setCustomFallbackServer")
                        .setDescription("Set's the server for yourself or everyone on the server.")
                        .setUsage("&6/&esetcustomfallbackserver <myself|all|player> <server...>")
                        .setPermission("fallbackserver.setcustom", UChat.chat("&cYou don't have permission to use this command!"))
                        .setTabCompleteType(TabCompleteType.SEARCH)
                        .setCommodore(
                                LiteralArgumentBuilder.literal("setCustomFallbackServer")
                                        .then(
                                                LiteralArgumentBuilder.literal("myself")
                                                        .then(
                                                                RequiredArgumentBuilder.argument("server", StringArgumentType.string())
                                                        )
                                        )
                                        .then(
                                                LiteralArgumentBuilder.literal("all")
                                                        .then(
                                                                RequiredArgumentBuilder.argument("server", StringArgumentType.string())
                                                        )
                                        )
                                        .then(
                                                RequiredArgumentBuilder.argument("player", StringArgumentType.word())
                                                        .then(
                                                                RequiredArgumentBuilder.argument("server", StringArgumentType.string())
                                                        )
                                        )

                                        .build()
                        )
        );
    }

    @Override
    public boolean perform(@NotNull CommandSender sender, @NotNull String command, CommandInformation information, String[] args) {
        if(args.length < 2) {
            sender.sendMessage(UChat.component("&cInvalid usage! " + information.getUsage())); return true;
        }

        try {
            switch(args[0]) {
                case "myself", "all" -> {
                    Main.getInstance().getConfiguration().setServer((args[0].equalsIgnoreCase("myself") ? sender.getName() : "all"), args[1]);
                    sender.sendMessage(UChat.component("&6You set &a" + (args[0].equalsIgnoreCase("myself") ? "your&6" : "everyone&6's") + " &6fallback server to &b" + args[1] + "&6!"));
                    Main.getInstance().playSound(sender, Sound.ENTITY_PLAYER_LEVELUP, 1F, 2F);
                }
                default -> {
                    Main.getInstance().getConfiguration().setServer(args[0], args[1]);
                    sender.sendMessage(UChat.component("&6You set &a" + args[0]+ "&6's fallback server to &b" + args[1] + "&6!"));
                    Main.getInstance().playSound(sender, Sound.ENTITY_PLAYER_LEVELUP, 1F, 2F);
                }
            }
        } catch (Exception exception) {
            sender.sendMessage(UChat.component("&cAn error occurred whilst processing your request! &8" + exception));
            BetterStackTraces.print(exception);
        }

        return true;
    }

    @Override
    public List<String> tab(@NotNull CommandSender sender, @NotNull String command, CommandInformation information, String[] args) {
        List<String> returned = new ArrayList<>();
        if(args.length == 1) {
            returned.addAll(List.of("myself", "all"));
            returned.addAll(Main.getInstance().getConfiguration().getPlayersToServers().keySet().stream().filter(player -> !sender.getName().equalsIgnoreCase(player)).toList());
        }
        if(args.length == 2) {
            returned.add(Main.getInstance().getConfiguration().getConnectTo());
            returned.addAll(Main.getInstance().getConfiguration().getPlayersToServers().values().stream().map(Object::toString).toList());
        }

        if(String.join(" ", Arrays.copyOfRange(args, 1, args.length)).startsWith("\"")) { // add begin quote if it's added to suggestions
            returned = new ArrayList<>(returned.stream().map(mappedItem -> "\"" + mappedItem).toList());
            if(!String.join(" ", Arrays.copyOfRange(args, 1, args.length)).endsWith("\"")) { // add end quote if a begin quote exists
                returned = new ArrayList<>(returned.stream().map(mappedItem -> mappedItem + "\"").toList());
                returned.add(args[args.length-1] + "\"");
            }
        }

        return returned;
    }
}
