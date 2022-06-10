package net.cybercake.fallback.commands;

import net.cybercake.cyberapi.chat.UChat;
import net.cybercake.cyberapi.chat.UTabComp;
import net.cybercake.fallback.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AttemptConnect implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player player)) {
            sender.sendMessage(UChat.component("&cOnly in-game players can use this command!")); return true;
        }

        if(args.length < 1) {
            player.sendMessage(UChat.component("&7Attempting to connect you to default server &8(&b" + Main.getInstance().getConfiguration().getConnectTo() + "&8)&7..."));
            Main.getInstance().send(player, "$$configuration");
        }else{
            player.sendMessage(UChat.component("&7Attempting to connect you to &b" + args[0] + "&7..."));
            Main.getInstance().send(player, args[0]);
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 1) return UTabComp.tabCompletionsSearch(args[0], List.of(Main.getInstance().getConfiguration().getConnectTo()));
        return UTabComp.emptyList;
    }
}
