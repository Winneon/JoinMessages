package io.winneonsword.joinmessages;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class jm_command implements CommandExecutor{
	
	private joinmessages plugin;
	
	public jm_command(joinmessages plugin){
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("jm")){
			String introMessage = plugin.getConfig().getString("introMessage");
			Player player = (Player) sender;
			
			if (args.length == 0){
				// Name & version number.
				sender.sendMessage(introMessage + " §7Join Messages, v1.0.2-o");
				return true;
			}
			if (args[0].equalsIgnoreCase("?") || args[0].equalsIgnoreCase("help")){
				// The main help menu.
				sender.sendMessage(introMessage + " §7Join Messages Help Menu");
				sender.sendMessage("  §c/jm ? §7- Help Menu.");
				sender.sendMessage("  §c/jm reload §7- Reloads the config file. §8// §cStaff command only!");
				sender.sendMessage("§7Created by WS, v1.0.2-o");
				return true;
			}
			if (args[0].equalsIgnoreCase("reload")){
				if (player.hasPermission("joinmessages.reload")){
					plugin.reloadConfig();
					sender.sendMessage(introMessage + " §7Successfully reloaded the config file.");
					return true;
				}
			}
			sender.sendMessage(introMessage + " §cUnknown argument. §7Type §c/jm ? §7for command usage.");
			return true;
		}
		return false;
	}
}
