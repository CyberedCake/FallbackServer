package net.cybercake.fallback;

import net.cybercake.cyberapi.spigot.chat.Log;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

public class Configuration {

    public Configuration() { reload(); }

    private String connectTo;
    private int connectionInterval;
    private Location spawn;
    private boolean joinLeaveEvents;
    private boolean chat;
    private boolean movement;
    private boolean playerVisibility;

    public void reload() {
        Main.getInstance().saveDefaultConfig();
        Main.getInstance().reloadConfig();

        FileConfiguration config = Main.getInstance().getMainConfig().values();

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
    }

    public String getConnectTo() { return connectTo; }
    public int getConnectionInterval() { return connectionInterval; }

    public Location getSpawn() { return spawn; }

    public boolean disableJoinLeaveEvents() { return joinLeaveEvents; }
    public boolean disableChat() { return chat; }
    public boolean disableMovement() { return movement; }
    public boolean disableVisibility() { return playerVisibility; }

}
