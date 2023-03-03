package net.cybercake.fallback;

import net.cybercake.cyberapi.common.builders.settings.Settings;
import net.cybercake.cyberapi.spigot.CyberAPI;
import net.cybercake.cyberapi.spigot.chat.Log;
import net.cybercake.cyberapi.spigot.chat.UChat;
import net.cybercake.fallback.tasks.AttemptToSend;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public final class Main extends CyberAPI {

    private static Main instance;

    private Configuration configuration;

    @Override
    public void onEnable() {
        instance = this;
        startCyberAPI(Settings.builder()
                .name("Fallback")
                .prefix("Fallback")
                .checkForUpdates(false)
                .muteStartMessage(true)
                .showPrefixInLogs(true)
                .mainPackage("net.cybercake.fallback")
                .build()
        );
        long mss = System.currentTimeMillis();

        configuration = new Configuration();
        Log.info("Loaded the configuration!");

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        Log.info("Registered outgoing plugin channel for BungeeCord!");

        registerRunnable(new AttemptToSend(), getConfiguration().getConnectionInterval());
        Log.info("Started all tasks!");

        Log.info("&e&l-".repeat(Math.max(0, 60)));
        Log.info("&aSuccessfully enabled!");
        Log.info(" &c\u250D &fPlugin &6" + this.getName());
        Log.info(" &c\u251C &fVersion &6" + this.getDescription().getVersion());
        Log.info(" &c\u251C &fCreated By &6" + String.join(", ", this.getDescription().getAuthors()));
        Log.info(" &c\u251C &fDescription &6" + (this.getDescription().getDescription().length() > 60 ? this.getDescription().getDescription().substring(0, 60) + "&8..." : this.getDescription().getDescription()));
        Log.info(" &c\u2515 &fEnabled In &6" + (System.currentTimeMillis()-mss) + "ms");
        Log.info(" ");
        Log.info("&7Occassionally check for updates at &b" + this.getDescription().getWebsite() + "&r&7!");
        Log.info("&e&l-".repeat(Math.max(0, 60)));
    }

    @Override
    public void onDisable() {
        long mss = System.currentTimeMillis();

        Log.info(ChatColor.RED + "Successfully disabled " + this.getName() + " [v" + this.getDescription().getVersion() + "] in " + (System.currentTimeMillis()-mss) + "ms!");
    }

    public static Main getInstance() { return instance; }

    public @NotNull Configuration getConfiguration() { return configuration; }

    public void send(Player player, String server) {
        player.sendMessage(UChat.getClearedChat());
        if(server.equalsIgnoreCase("$$configuration")) {
            server = getConfiguration().getConnectTo();
            String customServer = String.valueOf(Main.getInstance().getConfiguration().getPlayersToServers().get(player.getName()));
            if(customServer != null)
                server = customServer;
        }
        player.sendMessage(UChat.component("&6Sending you to &a" + server + "&6!"));
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(byteArrayOutputStream);

            out.writeUTF("Connect");
            out.writeUTF(server);

            player.sendPluginMessage(Main.getInstance(), "BungeeCord", byteArrayOutputStream.toByteArray());

            byteArrayOutputStream.close();
            out.close();
        } catch (Exception exception) {
            player.sendMessage(UChat.component("&e" + server + " &c(exception) -> &8" + exception));
            Log.error("&cAn error occurred whilst sending " + player.getName() + " to " + server + ": " + ChatColor.DARK_GRAY + exception);
        }
    }
}
