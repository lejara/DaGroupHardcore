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


import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;



public class GroupHardcore extends JavaPlugin {
	
	boolean doWorldEndEvent = false;
	int defualtNumberOfLives = 3;		
	LivesManager livesManager;	
	GroupHCCommandHandler commandHandler;
	WorldEndEvent worldEndEvent;
	
    @Override
    public void onEnable() {
    	        	
    	LoadFromConfig();
    	    	
    	commandHandler = new GroupHCCommandHandler(livesManager, this);
    	worldEndEvent = new WorldEndEvent(this);
    	
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
    	this.getConfig().set("DoWorldEndEvent", doWorldEndEvent);
    	this.getConfig().set("Lives", livesManager.lives);
    	this.getConfig().set("CurrentLives", livesManager.currentLives);
    	this.getConfig().set("WorldID", world.getUID().hashCode());    	
    	this.getConfig().set("WorldFailed", livesManager.worldFailed);
    	this.saveConfig();
    	System.out.print("[LaGroupHardcore] Config and Data Saved");
    }
    
    public void LoadFromConfig() {
    	World currentWorld = getServer().getWorlds().get(0);
    	
    	int getWorldID = this.getConfig().getInt("WorldID");
    	
    	if(getWorldID != 0 && getWorldID == currentWorld.getUID().hashCode()) {
    		System.out.print("[LaGroupHardcore] Loaded Config and Data, World UID Hash: " + currentWorld.getUID().hashCode());
    		int gotNumberOfLives = this.getConfig().getInt("Lives");
    		int gotCurrentLives = this.getConfig().getInt("CurrentLives");
    		boolean gotWorldFailed = this.getConfig().getBoolean("WorldFailed");
    		doWorldEndEvent = this.getConfig().getBoolean("DoWorldEndEvent");
    		livesManager = new LivesManager(gotNumberOfLives, gotWorldFailed, gotCurrentLives, this);
    	}
    	else {    		
    		System.out.print("[LaGroupHardcore] Did not load Config and Data, will load defaults");
    		livesManager = new LivesManager(defualtNumberOfLives, false, this);
    		SaveToConfig(this.getServer().getWorlds().get(0));
    	}
    	
    	
    }        
           
    @Override
    public boolean onCommand(CommandSender sender,
                             Command command,
                             String label,
                             String[] args) {
    	
    	return commandHandler.onCommand(sender, command, label, args);
    }    
    
    public void WorldEnd() {
    	worldEndEvent.StartEvent();
    } 
}
