package main.lagrouphardcore;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class GroupHCCommandHandler {
	
	private LivesManager lives;
	
	public GroupHCCommandHandler(LivesManager livesMana) {
		lives = livesMana;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (command.getName().equalsIgnoreCase("setlives")) {
			return setLives(sender, args);
        }
		else if(command.getName().equalsIgnoreCase("resetlives")) {
			return resetLives(sender);
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
					lives.SetLives(num);
					sender.sendMessage("Lives Set to " + args[0]);
					return true;
				}										
	            
			}catch(NumberFormatException e) {
				sender.sendMessage("Error arg1 must be a whole number");
			}
		}
		else {
			sender.sendMessage("Error number of args");
		}
		return false;		
	}	
	
	public boolean resetLives(CommandSender sender) {
		sender.sendMessage("Lives has been reset");
		lives.ResetLives();
		return true;
	}
	
}
