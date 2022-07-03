package net.cybercake.fallback;

import com.mojang.brigadier.tree.LiteralCommandNode;
import me.lucko.commodore.CommodoreProvider;
import me.lucko.commodore.file.CommodoreFileReader;
import net.cybercake.cyberapi.chat.Log;
import org.bukkit.command.PluginCommand;

import java.io.IOException;

public class Commodore {
    public static void register(PluginCommand pluginCommand, String fileName) throws IOException {
        try {
            LiteralCommandNode<?> commandProvided = CommodoreFileReader.INSTANCE.parse(Main.getInstance().getResource("commodore/" + fileName + ".commodore"));
            CommodoreProvider.getCommodore(Main.getInstance()).register(pluginCommand, commandProvided);
        }catch (Exception ignored) {
            Log.error("Commodore file for command \"" + fileName + "\" not found");
        }
    }
}