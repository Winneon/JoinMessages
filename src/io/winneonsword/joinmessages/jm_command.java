package io.winneonsword.joinmessages;

import java.util.List;

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
			String messageColour = plugin.getConfig().getString("messageColour");
			String nameColour = plugin.getConfig().getString("nameColour");
			Player player = (Player) sender;
			
			if (args.length == 0){
				// Name & version number.
				sender.sendMessage(introMessage + " §7Join Messages, v1.2.1-o");
				return true;
			}
			if (args[0].equalsIgnoreCase("?") || args[0].equalsIgnoreCase("help")){
				// The main help menu.
				sender.sendMessage(introMessage + " §7Join Messages Help Menu");
				sender.sendMessage("  §c/jm ? §7- Help Menu.");
				sender.sendMessage("  §c/jm list §7- List Message Help Menu.");
				sender.sendMessage("  §c/jm add §7- Add Message Help Menu. §8// §cStaff command only!");
				sender.sendMessage("  §c/jm remove §7- Remove Message Help Menu. §8// §cStaff command");
				sender.sendMessage("      §conly!");
				sender.sendMessage("  §c/jm reload §7- Reloads the config file. §8// §cStaff command only!");
				sender.sendMessage("§7Created by WS, v1.2.1-o");
				return true;
			}
			if (args[0].equalsIgnoreCase("list") && args.length == 1){
				// The List Message Help Menu.
				sender.sendMessage(introMessage + " §7List Message Help Menu");
				sender.sendMessage("  §c/jm list join §7- List the join messages and their corresponding");
				sender.sendMessage("      §7ID.");
				sender.sendMessage("  §c/jm list leave §7- List the leave messages and their");
				sender.sendMessage("      §7corresponding ID.");
				return true;
			}
			if (args[0].equalsIgnoreCase("list") && args[1].equalsIgnoreCase("join")){
				// The /jm list join command.
				List<String> joinMessageList = plugin.getConfig().getStringList("joinMessages");
				
				sender.sendMessage(introMessage + " §7Join Messages:");
				for (int i = 1; i < joinMessageList.size() + 1; i++){
					String message = joinMessageList.get(i - 1);
					
					sender.sendMessage("  §7[§c" + i + "§7] §" + messageColour + message.replace("%p", "§" + nameColour + player.getDisplayName() + "§" + messageColour).replace(" ", " §" + messageColour));
				}
				return true;
			}
			if (args[0].equalsIgnoreCase("list") && args[1].equalsIgnoreCase("leave")){
				// The /jm list leave command.
				List<String> leaveMessageList = plugin.getConfig().getStringList("leaveMessages");
				
				sender.sendMessage(introMessage + " §7Leave Messages:");
				for (int i = 1; i < leaveMessageList.size() + 1; i++){
					String message = leaveMessageList.get(i - 1);
					
					sender.sendMessage("  §7[§c" + i + "§7] §" + messageColour + message.replace("%p", "§" + nameColour + player.getDisplayName() + "§" + messageColour).replace(" ", " §" + messageColour));
				}
				return true;
			}
			if (args[0].equalsIgnoreCase("add") && args.length == 1){
				// The Add Message Help Menu.
				sender.sendMessage(introMessage + " §7Add Message Help Menu §8// §cStaff commands only!");
				sender.sendMessage("  §c/jm add join <message> §7- Add a join message to the config.");
				sender.sendMessage("  §c/jm add leave <message> §7- Add a leave message to the config.");
				return true;
			}
			if (args[0].equalsIgnoreCase("add") && args[1].equalsIgnoreCase("join")){
				// The /jm add join <message> command.
				if (player.hasPermission("joinmessages.add") || player.hasPermission("joinmessages.add.join")){
					if (args.length == 2){
						sender.sendMessage(introMessage + " §7Usage: §c/jm add join <message>");
						return true;
					}
					StringBuilder initial = new StringBuilder();
					
					for (int i = 2; i < args.length; i++){
						initial.append(' ').append(args[i]);
					}
					String message = initial.toString().replaceFirst(" ", "");
					
					if (!(message.contains("%p"))){
						sender.sendMessage(introMessage + " §cYou have not defined a playername! §7Be sure to include §c%p §7in your message to define the playername.");
						return true;
					}
					List<String> joinMessageList = plugin.getConfig().getStringList("joinMessages");
					int joinMessageID = joinMessageList.size() + 1;
					
					joinMessageList.add(message);
					plugin.getConfig().set("joinMessages", joinMessageList);
					plugin.saveConfig();
					sender.sendMessage(introMessage + " §7Successfully added a join message. The message ID is §c" + joinMessageID + "§7.");
					sender.sendMessage(introMessage + " §7Here is what it will look like:");
					sender.sendMessage("§" + messageColour + message.replace("%p", "§" + nameColour + player.getDisplayName() + "§" + messageColour).replace(" ", " §" + messageColour));
					return true;
				}
				return true;
			}
			if (args[0].equalsIgnoreCase("add") && args[1].equalsIgnoreCase("leave")){
				// The /jm add leave <message> command.
				if (player.hasPermission("joinmessages.add") || player.hasPermission("joinmessages.add.leave")){
					if (args.length == 2){
						sender.sendMessage(introMessage + " §7Usage: §c/jm add leave <message>");
						return true;
					}
					StringBuilder initial = new StringBuilder();
					
					for (int i = 2; i < args.length; i++){
						initial.append(' ').append(args[i]);
					}
					String message = initial.toString().replaceFirst(" ", "");
					
					if (!(message.contains("%p"))){
						sender.sendMessage(introMessage + " §cYou have not defined a playername! §7Be sure to include §c%p §7in your message to define the playername.");
						return true;
					}
					List<String> leaveMessageList = plugin.getConfig().getStringList("leaveMessages");
					int leaveMessageID = leaveMessageList.size() + 1;
					
					leaveMessageList.add(message);
					plugin.getConfig().set("leaveMessages", leaveMessageList);
					plugin.saveConfig();
					sender.sendMessage(introMessage + " §7Successfully added a leave message. The message ID is §c" + leaveMessageID + "§7.");
					sender.sendMessage(introMessage + " §7Here is what it will look like:");
					sender.sendMessage("§" + messageColour + message.replace("%p", "§" + nameColour + player.getDisplayName() + "§" + messageColour).replace(" ", " §" + messageColour));
					return true;
				}
				return true;
			}
			if (args[0].equalsIgnoreCase("remove") && args.length == 1){
				// The Remove Message Help Menu.
				sender.sendMessage(introMessage + " §7Remove Message Help Menu §8// §cStaff commands only!");
				sender.sendMessage("  §c/jm remove join <ID> §7- Remove a join message from the config.");
				sender.sendMessage("  §c/jm remove leave <ID> §7- Remove a leave message from the");
				sender.sendMessage("      §7config.");
				sender.sendMessage("§7To view message IDs, type §c/jm list§7.");
				return true;
			}
			if (args[0].equalsIgnoreCase("remove") && args[1].equalsIgnoreCase("join")){
				// The /jm remove join <message> command.
				if (player.hasPermission("joinmessages.remove") || player.hasPermission("joinmessages.remove.join")){
					if (args.length == 2){
						sender.sendMessage(introMessage + " §7Usage: §c/jm remove join <ID>§7. To view message IDs, type §c/jm list§7.");
						return true;
					}
					List<String> joinMessageList = plugin.getConfig().getStringList("joinMessages");
					try {
						Integer.parseInt(args[2]);
					} catch (NumberFormatException e){
						sender.sendMessage(introMessage + " §cThe ID you entered is not a number!");
						return true;
					}
					if (joinMessageList.size() < Integer.parseInt(args[2])){
						sender.sendMessage(introMessage + " §cThere is no join message with the ID specified!");
						return true;
					}
					if (joinMessageList.size() == 1){
						sender.sendMessage(introMessage + " §cThere is only 1 message left! Add another before you remove this message.");
						return true;
					}
					int joinMessageID = Integer.parseInt(args[2]);
					String message = joinMessageList.get(joinMessageID - 1);
					
					joinMessageList.remove(message);
					plugin.getConfig().set("joinMessages", joinMessageList);
					plugin.saveConfig();
					sender.sendMessage(introMessage + " §7Successfully removed the join message which goes by the ID of §c" + joinMessageID + "§7.");
					sender.sendMessage(introMessage + " §7Here is the message you removed:");
					sender.sendMessage("§" + messageColour + message.replace("%p", "§" + nameColour + player.getDisplayName() + "§" + messageColour).replace(" ", " §" + messageColour));
					return true;
				}
				return true;
			}
			if (args[0].equalsIgnoreCase("remove") && args[1].equalsIgnoreCase("leave")){
				// The /jm remove join <message> command.
				if (player.hasPermission("joinmessages.remove") || player.hasPermission("joinmessages.remove.leave")){
					if (args.length == 2){
						sender.sendMessage(introMessage + " §7Usage: §c/jm remove leave <ID>§7. To view message IDs, type §c/jm list§7.");
						return true;
					}
					List<String> leaveMessageList = plugin.getConfig().getStringList("leaveMessages");
					try {
						Integer.parseInt(args[2]);
					} catch (NumberFormatException e){
						sender.sendMessage(introMessage + " §cThe ID you entered is not a number!");
						return true;
					}
					if (leaveMessageList.size() < Integer.parseInt(args[2])){
						sender.sendMessage(introMessage + " §cThere is no leave message with the ID specified!");
						return true;
					}
					if (leaveMessageList.size() == 1){
						sender.sendMessage(introMessage + " §cThere is only 1 message left! Add another before you remove this message.");
						return true;
					}
					int leaveMessageID = Integer.parseInt(args[2]);
					String message = leaveMessageList.get(leaveMessageID - 1);
					
					leaveMessageList.remove(message);
					plugin.getConfig().set("leaveMessages", leaveMessageList);
					plugin.saveConfig();
					sender.sendMessage(introMessage + " §7Successfully removed the leave message which goes by the ID of §c" + leaveMessageID + "§7.");
					sender.sendMessage(introMessage + " §7Here is the message you removed:");
					sender.sendMessage("§" + messageColour + message.replace("%p", "§" + nameColour + player.getDisplayName() + "§" + messageColour).replace(" ", " §" + messageColour));
					return true;
				}
				return true;
			}
			if (args[0].equalsIgnoreCase("reload")){
				// The reload command.
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
