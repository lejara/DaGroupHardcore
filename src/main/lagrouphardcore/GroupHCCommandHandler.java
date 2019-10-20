/**
* 
* Aggrogate class for all commands in this plugin
* 
* @author Lejara (Leonel Jara)
* 
*/

package main.lagrouphardcore;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class GroupHCCommandHandler {
	
	private LivesManager lives;
	private GroupHardcore main;
	
	public GroupHCCommandHandler(LivesManager livesMana, GroupHardcore m) {
		lives = livesMana;
		main = m;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (command.getName().equalsIgnoreCase("setlives")) {
			return setLives(sender, args);
        }
		else if(command.getName().equalsIgnoreCase("resetlives")) {
			return resetLives(sender);
		}
		else if(command.getName().equalsIgnoreCase("setworldend")) {
			return setWorldEndEvent(sender, args);
		}
		else if (command.getName().equalsIgnoreCase("resetplayerspawn")) {
			return resetPlayersSpawn(sender);
		}
		
        return false;
		
	}
	
	public boolean setLives(CommandSender sender, String[] args) {
		if(args.length == 1) {				
			try {
				int num = Integer.parseInt(args[0]);
				if (num < 0) {
					sender.sendMessage("Error number must be grater or equal to 0");
				}
				else {
					lives.setLives(num);
					sender.sendMessage("Lives Set to " + args[0]);
					return true;
				}										
	            
			}catch(NumberFormatException e) {
				sender.sendMessage("Error arg1 must be a whole number");
			}
		}
		else {
			sender.sendMessage("Error number of args, must be one");
		}
		return false;		
	}	
	
	public boolean resetLives(CommandSender sender) {
		sender.sendMessage("Lives has been reset");
		lives.resetLives();
		return true;
	}
	
	public boolean setWorldEndEvent(CommandSender sender, String[] args) {
		if(args.length == 1) {				
			try {
				int num = Integer.parseInt(args[0]);
				if(num == 0) {
					Bukkit.broadcastMessage(ChatColor.DARK_GREEN   + "World End is Now Set to True in this world. Good Choice :)");
					main.doWorldEndEvent = true;
					main.saveToConfig(main.currentWorld);
					return true;
				}
				else if(num == 1) {
					Bukkit.broadcastMessage(ChatColor.YELLOW + "World End is Now Set to False in this world. Coward!");
					main.doWorldEndEvent = false;
					main.saveToConfig(main.currentWorld);
					return true;
				}
				else {
					sender.sendMessage("Error arg must be 0 or 1");
				}
																		          
			}catch(NumberFormatException e) {
				sender.sendMessage("Error arg must be 0 or 1");
			}
		}else {
			sender.sendMessage("Error number of args, must be one");
		}
		return false;		
	}
	
	public boolean resetPlayersSpawn(CommandSender sender) {		
		for(Player p : Bukkit.getOnlinePlayers()) {
			p.setBedSpawnLocation(main.currentWorld.getSpawnLocation());
		}
		sender.sendMessage("All Online players Respawn reseted");
		return true;
		
	}
	
	
}
