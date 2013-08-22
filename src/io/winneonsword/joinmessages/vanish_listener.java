package io.winneonsword.joinmessages;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import org.kitteh.vanish.VanishPerms;
import org.kitteh.vanish.event.VanishStatusChangeEvent;

public class vanish_listener implements Listener {
	
	private joinmessages plugin;
	
	public vanish_listener(joinmessages plugin){
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onVanishEvent(VanishStatusChangeEvent e){
		Player p = e.getPlayer();
		
		if (e.isVanishing() == true){
			String introMessage = plugin.getConfig().getString("introMessage");
			
			plugin.getConfig().set("Users." + e.getPlayer().getName() + ".vanished", true);
			plugin.saveConfig();
			if (VanishPerms.joinWithoutAnnounce(p) || VanishPerms.joinVanished(p)){
				e.getPlayer().sendMessage(introMessage + " §7You are now omit from sending join and leave messages.");
				return;
			}
			e.getPlayer().sendMessage(introMessage + " §7You are now omit from sending leave messages.");
			return;
		}
		String introMessage = plugin.getConfig().getString("introMessage");
		plugin.getConfig().set("Users", null);
		plugin.saveConfig();
		if (VanishPerms.joinWithoutAnnounce(p) || VanishPerms.joinVanished(p)){
			e.getPlayer().sendMessage(introMessage + " §7You are no longer omit from sending join and leave messages.");
			return;
		}
		e.getPlayer().sendMessage(introMessage + " §7You are no longer omit from sending leave messages.");
	}
}
