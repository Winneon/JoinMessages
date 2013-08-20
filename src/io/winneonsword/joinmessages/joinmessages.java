package io.winneonsword.joinmessages;

import org.bukkit.plugin.java.JavaPlugin;

public final class joinmessages extends JavaPlugin {
	
	joinmessages plugin;
	
	public joinmessages(){
		
	}
	
	public final jm_listeners Listener = new jm_listeners(this);
	
	@Override
	public void onEnable(){
		getLogger().info("Join Messages has been enabled!");
		getServer().getPluginManager().registerEvents(this.Listener, this);
		getCommand("jm").setExecutor(new jm_command(this));
		saveDefaultConfig();
	}
	
	@Override
	public void onDisable(){
		getLogger().info("Join Messages has been disabled!");
	}
}
