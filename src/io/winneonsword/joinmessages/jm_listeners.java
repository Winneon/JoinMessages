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

public class jm_listeners implements Listener{
	
	private joinmessages plugin;
	
	public jm_listeners(joinmessages plugin){
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoin(final PlayerJoinEvent e){
		List<String> joinMessages = plugin.getConfig().getStringList("joinMessages");
		Random rand = new Random();
		int randomNumber = rand.nextInt(joinMessages.size());
		final String joinMessage = joinMessages.get(randomNumber);
		final String messageColour = plugin.getConfig().getString("messageColour");
		
		e.setJoinMessage(null);
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin,
				new Runnable(){ public void run(){
					Player p = e.getPlayer();
					Bukkit.broadcastMessage("§" + messageColour + joinMessage.replace("%p", "§" + messageColour + p.getDisplayName() + "§" + messageColour).replace(" ", " §" + messageColour));
				}}, 5);
		return;
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerQuit(final PlayerQuitEvent e){
		List<String> leaveMessages = plugin.getConfig().getStringList("leaveMessages");
		Random rand = new Random();
		int randomNumber = rand.nextInt(leaveMessages.size());
		final String leaveMessage = leaveMessages.get(randomNumber);
		final String messageColour = plugin.getConfig().getString("messageColour");
		
		e.setQuitMessage(null);
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin,
				new Runnable(){ public void run(){
					Player p = e.getPlayer();
					Bukkit.broadcastMessage("§" + messageColour + leaveMessage.replace("%p", "§" + messageColour + p.getDisplayName() + "§" + messageColour).replace(" ", " §" + messageColour));
				}}, 5);
		return;
	}
}
