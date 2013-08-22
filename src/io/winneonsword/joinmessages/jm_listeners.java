package io.winneonsword.joinmessages;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import org.kitteh.vanish.VanishPerms;
import org.kitteh.vanish.event.VanishStatusChangeEvent;

public class jm_listeners implements Listener{
	
	private joinmessages plugin;
	
	public jm_listeners(joinmessages plugin){
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
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoin(final PlayerJoinEvent e){
		final Player p = e.getPlayer();
		List<String> joinMessages = plugin.getConfig().getStringList("joinMessages");
		Random rand = new Random();
		int randomNumber = rand.nextInt(joinMessages.size());
		final String joinMessage = joinMessages.get(randomNumber);
		final String messageColour = plugin.getConfig().getString("messageColour");
		final String nameColour = plugin.getConfig().getString("nameColour");
		
		e.setJoinMessage(null);
		if (VanishPerms.joinWithoutAnnounce(p) || VanishPerms.joinVanished(p)){
			return;
		}
		plugin.getConfig().set("Users", null);
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin,
				new Runnable(){ public void run(){
					Bukkit.broadcastMessage("§" + messageColour + joinMessage.replace("%p", "§" + nameColour + p.getDisplayName() + "§" + messageColour).replace(" ", " §" + messageColour));
				}}, 5);
		return;
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerQuit(final PlayerQuitEvent e){
		final Player p = e.getPlayer();
		List<String> leaveMessages = plugin.getConfig().getStringList("leaveMessages");
		Random rand = new Random();
		int randomNumber = rand.nextInt(leaveMessages.size());
		final String leaveMessage = leaveMessages.get(randomNumber);
		final String messageColour = plugin.getConfig().getString("messageColour");
		final String nameColour = plugin.getConfig().getString("nameColour");
		boolean isVanished = plugin.getConfig().getBoolean("Users." + p.getName() + ".vanished");
		
		e.setQuitMessage(null);
		if (isVanished == true){
			return;
		}
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin,
				new Runnable(){ public void run(){
					Bukkit.broadcastMessage("§" + messageColour + leaveMessage.replace("%p", "§" + nameColour + p.getDisplayName() + "§" + messageColour).replace(" ", " §" + messageColour));
				}}, 5);
		return;
	}
}
