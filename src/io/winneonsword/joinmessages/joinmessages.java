package io.winneonsword.joinmessages;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.Plugin;

public final class joinmessages extends JavaPlugin {
	
	joinmessages plugin;
	
	public joinmessages(){
		
	}
	
	public final jm_listeners Listener = new jm_listeners(this);
	public final vanish_listener vanishListener = new vanish_listener(this);
	
	@SuppressWarnings(value = "unused")
	private String latestVersion = null;
	
	@SuppressWarnings("unused")
	private boolean versionDiff = false;
	
	private final class updateCheck implements Runnable{
		private joinmessages plugin;
		
		public updateCheck(joinmessages plugin){
			this.plugin = plugin;
		}
		
		
		
		@Override
		public void run(){
			
			String pluginVersion = plugin.getConfig().getString("version");
			
			try {
				// Credit to mbax for this version checker script. :)
				final URLConnection connection = new URL("http://dl.dropboxusercontent.com/u/62828086/version").openConnection();
				connection.setConnectTimeout(8000);
				connection.setReadTimeout(15000);
				connection.setRequestProperty("User-agent", "Join Messages");
				final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String version;
				if ((version = bufferedReader.readLine()) != null){
					latestVersion = version;
					if (!(pluginVersion.equals(version))){
						getLogger().info("Found a newer version of Join Messages available: " + version);
						getLogger().info("To download the newest version, go to: http://dev.bukkit.org/bukkit-plugins/join-messages/");
						versionDiff = true;
					}
					return;
				}
				bufferedReader.close();
				connection.getInputStream().close();
			} catch (final Exception e){
				
			}
			getLogger().severe("Could not check if plugin was up to date!");
		}
	}
	
	@Override
	public void onEnable(){
		
		getServer().getScheduler().runTaskTimerAsynchronously(this, new updateCheck(this), 40, 432000);
		
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
