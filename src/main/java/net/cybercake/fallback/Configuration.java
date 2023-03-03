package net.cybercake.fallback;

import net.cybercake.cyberapi.spigot.chat.Log;
import net.cybercake.cyberapi.spigot.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class Configuration {

    public Configuration() { reload(); }

    private Config cyberApiConfig;
    private FileConfiguration config;

    private String connectTo;
    private int connectionInterval;
    private Location spawn;
    private boolean joinLeaveEvents;
    private boolean chat;
    private boolean movement;
    private boolean playerVisibility;
    private Map<String, Object> playersToServers;

    public void reload() {
        Main.getInstance().saveDefaultConfig();
        Main.getInstance().reloadConfig();

        Config cyberApiConfig = Main.getInstance().getMainConfig();
        FileConfiguration config = cyberApiConfig.values();
        if(config == null)
            throw new NullPointerException("The configuration (" + FileConfiguration.class.getCanonicalName() + ") cannot be null");
        this.config = config;
        this.cyberApiConfig = cyberApiConfig;

        this.connectTo = config.getString("connectTo");
        this.connectionInterval = config.getInt("connectionInterval");

        World world = Bukkit.getWorld(config.getString("spawnAt.world"));
        if(world != null) {
            this.spawn = new Location(world, config.getDouble("spawnAt.x"), config.getDouble("spawnAt.y"), config.getDouble("spawnAt.z"), config.getInt("spawnAt.yaw"), config.getInt("spawnAt.pitch"));
        }else{
            Log.warn("You have put an invalid world in your configuration file! Set to default values for now...");
            this.spawn = new Location(Main.getInstance().getServer().getWorlds().get(0), 0.0, 100.0, 0.0, 0, 0);
        }

        this.joinLeaveEvents = config.getBoolean("disable.joinLeaveMessages");
        this.chat = config.getBoolean("disable.chat");
        this.movement = config.getBoolean("disable.movement");
        this.playerVisibility = config.getBoolean("disable.playerVisibility");

        reloadPlayerServerMap();
    }

    public String getConnectTo() { return connectTo; }
    public int getConnectionInterval() { return connectionInterval; }

    public Location getSpawn() { return spawn; }

    public Map<String, Object> getPlayersToServers() { return playersToServers; }

    public boolean disableJoinLeaveEvents() { return joinLeaveEvents; }
    public boolean disableChat() { return chat; }
    public boolean disableMovement() { return movement; }
    public boolean disableVisibility() { return playerVisibility; }

    public Config getCyberApiConfig() { return this.cyberApiConfig; }
    public FileConfiguration getConfig() { return this.config; }


    public void setServer(String player, String value) {
        try {
            String path;
            if(player.equalsIgnoreCase("all")) path = "connectTo";
            else path = "specificPlayers." + player;
            this.config.set(path, value);
            this.cyberApiConfig.save();

            reloadPlayerServerMap();
        } catch (Exception exception) {
            throw new IllegalStateException("Failed to set settings for " + player + " to " + value + ": " + exception.toString(), exception);
        }
    }

    private void reloadPlayerServerMap() {
        ConfigurationSection section = config.getConfigurationSection("specificPlayers");
        if(section == null) {
            this.config.set("specificPlayers.CyberedCake", "different-server");
            section = config.getConfigurationSection("specificPlayers");
            try { cyberApiConfig.save(); } catch (Exception ignored) { } // don't care, it just didn't work
            if(section == null)
                throw new IllegalStateException("Configuration section specificPlayers could not be set");
        }
        this.playersToServers = new HashMap<>(section.getValues(false));
    }

}
