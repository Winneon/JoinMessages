package com.github.winneonsword.JM;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
//import net.milkbowl.vault.permission.Permission;

import org.kitteh.vanish.VanishPerms;
import static com.github.winneonsword.CMAPI.API.ChatAPI.*;

public class ListenersJM implements Listener{
	
	private MainJM plugin;
	//private static Permission perm;
	
	public ListenersJM(MainJM plugin){
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerJoin(final PlayerJoinEvent e){
		final Player p = e.getPlayer();
		List<String> joinMessages = plugin.ConfigJM.getStringList("joinMessages");
		Random rand = new Random();
		int randomNumber = rand.nextInt(joinMessages.size());
		final String joinMessage = joinMessages.get(randomNumber);
		final String messageColour = plugin.ConfigJM.getString("messageColour");
		final String nameColour = plugin.ConfigJM.getString("nameColour");
		Plugin VanishNoPacket = plugin.getServer().getPluginManager().getPlugin("VanishNoPacket");
		//final Plugin Vault = plugin.getServer().getPluginManager().getPlugin("Vault");
		
		e.setJoinMessage(null);
		if (VanishNoPacket != null){
			if (VanishPerms.joinWithoutAnnounce(p) || VanishPerms.joinVanished(p)){
				return;
			}
		}
		plugin.ConfigJM.set("Users", null);
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin,
				new Runnable(){ public void run(){
					//String group = "";
					//if (Vault != null){
					//	group = perm.getPrimaryGroup(e.getPlayer());
					//}
					Bukkit.broadcastMessage(rA("&" + messageColour + joinMessage.replace("%p", "&" + nameColour + p.getDisplayName() + "&" + messageColour)));
				}}, 5);
		return;
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerQuit(final PlayerQuitEvent e){
		final Player p = e.getPlayer();
		List<String> leaveMessages = plugin.ConfigJM.getStringList("leaveMessages");
		Random rand = new Random();
		int randomNumber = rand.nextInt(leaveMessages.size());
		final String leaveMessage = leaveMessages.get(randomNumber);
		final String messageColour = plugin.ConfigJM.getString("messageColour");
		final String nameColour = plugin.ConfigJM.getString("nameColour");
		boolean isVanished = plugin.ConfigJM.getBoolean("Users." + p.getName() + ".vanished");
		//final Plugin Vault = plugin.getServer().getPluginManager().getPlugin("Vault");
		
		e.setQuitMessage(null);
		if (isVanished == true){
			return;
		}
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin,
				new Runnable(){ public void run(){
					//String group = "";
					//if (Vault != null){
					//	group = perm.getPrimaryGroup(e.getPlayer());
					//}
					Bukkit.broadcastMessage(rA("&" + messageColour + leaveMessage.replace("%p", "&" + nameColour + p.getDisplayName() + "&" + messageColour)));
				}}, 5);
		return;
	}
}
