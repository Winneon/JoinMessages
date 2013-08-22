package io.winneonsword.joinmessages;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.Plugin;

public final class joinmessages extends JavaPlugin {
	
	joinmessages plugin;
	
	public joinmessages(){
		
	}
	
	public final jm_listeners Listener = new jm_listeners(this);
	public final vanish_listener vanishListener = new vanish_listener(this);
	
	@Override
	public void onEnable(){
		Plugin VanishNoPacket = getServer().getPluginManager().getPlugin("VanishNoPacket");
		
		if (VanishNoPacket != null){
			getLogger().info("VanishNoPacket has been found! Hooking with VanishNoPacket...");
			getServer().getPluginManager().registerEvents(this.vanishListener, this);
			getLogger().info("Hooked with VanishNoPacket successfully!");
		}
		getServer().getPluginManager().registerEvents(this.Listener, this);
		getCommand("jm").setExecutor(new jm_command(this));
		getLogger().info("Join Messages has been enabled!");
		saveDefaultConfig();
	}
	
	@Override
	public void onDisable(){
		getLogger().info("Join Messages has been disabled!");
	}
}
