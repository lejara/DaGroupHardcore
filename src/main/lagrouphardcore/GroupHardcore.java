/*
 * 
 * https://www.justdave.net/dave/2015/05/04/how-to-write-a-minecraftbukkit-plugin-for-spigot-1-8/
 * https://hub.spigotmc.org/javadocs/spigot/overview-summary.html
 * https://www.spigotmc.org/wiki/spigot-plugin-development/
 * https://bukkit.gamepedia.com/Configuration_API_Reference
 * 
 */

/**
* Main Plugin class for this plugin
* Primary use is to setup core classes/listeners, load and save config data
* 
* @author Lejara (Leonel Jara)
* 
*/

package main.lagrouphardcore;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public class GroupHardcore extends JavaPlugin {
	
	boolean doWorldEndEvent = true; // do false by defualt
	int defualtNumberOfLives = 3;		
	int currentWorldUUID;
	LivesManager livesManager;	
	GroupHCCommandHandler commandHandler;
	
    @Override
    public void onEnable() {
    	        	
    	LoadFromConfig();
    	    	
    	commandHandler = new GroupHCCommandHandler(livesManager);
    	
    	getServer().getPluginManager().registerEvents(new DeathListener(livesManager), this);
    	getServer().getPluginManager().registerEvents(new PlayerJoinListener(livesManager), this);
    	    	
    }
    @Override
    public void onDisable() {
    }
    
    public void SaveToConfig(Player player) {
    	SaveToConfig(player.getWorld());
    }
    
    public void SaveToConfig(World world) {
    	System.out.print("Saved Lives");
    	this.getConfig().set("DoWorldEndEvent", doWorldEndEvent);
    	this.getConfig().set("Lives", livesManager.lives);
    	this.getConfig().set("CurrentLives", livesManager.currentLives);
    	this.getConfig().set("World", world.getUID().variant());
    	this.getConfig().set("WorldFailed", livesManager.worldFailed);
    	this.saveConfig();
    }
    
    public void LoadFromConfig() {
    	//TODO: mix in the seed and uuid to know the unquie world
    	currentWorldUUID = getServer().getWorlds().get(0).getUID().variant();
    	
    	int getWorldUUID = this.getConfig().getInt("World");
    	
    	if(getWorldUUID != 0 && getWorldUUID == currentWorldUUID) {
    		System.out.print("Loaded Lives, World UUID: " + currentWorldUUID);
    		int gotNumberOfLives = this.getConfig().getInt("Lives");
    		int gotCurrentLives = this.getConfig().getInt("CurrentLives");
    		boolean gotWorldFailed = this.getConfig().getBoolean("WorldFailed");
    		doWorldEndEvent = this.getConfig().getBoolean("DoWorldEndEvent");
    		livesManager = new LivesManager(gotNumberOfLives, gotWorldFailed, gotCurrentLives, this);
    	}
    	else {    		
    		System.out.print("Did not load Lives, loading defaults");
    		livesManager = new LivesManager(defualtNumberOfLives, false, this);
    	}
    	
    	SaveToConfig(this.getServer().getWorlds().get(0));
    }        
           
    @Override
    public boolean onCommand(CommandSender sender,
                             Command command,
                             String label,
                             String[] args) {
    	
    	return commandHandler.onCommand(sender, command, label, args);
    }    
    
    public void WorldEnd() {
    	Bukkit.broadcastMessage("World Will Now End");
    	
    	Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
		    public void run() {
		    	Bukkit.broadcastMessage("has it been 10 secs?");
		    }
		}, 200L);
    }
    
}
