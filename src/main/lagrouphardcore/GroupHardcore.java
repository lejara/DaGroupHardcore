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
	
	boolean doWorldEndEvent = false;
	int defualtNumberOfLives = 3;	
	int defualtNumberOfDays = 35;
	Long worldEndStartDelay = 180L;
	
	World currentWorld;
	
	LivesManager livesManager;	
	ScorebroadTracker scoreTracker;
	DaysTracker days;
	GroupHCCommandHandler commandHandler;
	WorldEndEvent worldEndEvent;
	
    @Override
    public void onEnable() {
    	        	
    	loadFromConfig();
    	    	
    	commandHandler = new GroupHCCommandHandler(livesManager, this);
    	worldEndEvent = new WorldEndEvent(this);
    	scoreTracker = new ScorebroadTracker(livesManager, days);
    	    	
    	getServer().getPluginManager().registerEvents(new DeathListener(livesManager), this);
    	getServer().getPluginManager().registerEvents(new PlayerJoinListener(livesManager, scoreTracker), this);
    	getServer().getPluginManager().registerEvents(new EnderDragonEventListener(this), this);
    	    	
    }
    @Override
    public void onDisable() {
    }
    
    public void saveToConfig(Player player) {
    	saveToConfig(player.getWorld());
    }
    
    public void saveToConfig(World world) {    	
    	
    	this.getConfig().set("DoWorldEndEvent", doWorldEndEvent);
    	this.getConfig().set("Lives", livesManager.lives);
    	this.getConfig().set("CurrentLives", livesManager.currentLives);
    	this.getConfig().set("WorldID", world.getUID().hashCode());    	
    	this.getConfig().set("WorldFailed", livesManager.worldFailed);
    	this.getConfig().set("DaysLeft", days.daysLeft);
    	
    	this.saveConfig();
    	
    	System.out.print("[LaGroupHardcore] Config and Data Saved");
    }
    
    public void loadFromConfig() {
    	currentWorld = getServer().getWorlds().get(0);
    	
    	int getWorldID = this.getConfig().getInt("WorldID");
    	
    	if(getWorldID != 0 && getWorldID == currentWorld.getUID().hashCode()) {
    		
    		System.out.print("[LaGroupHardcore] Loaded Config and Data, World UID Hash: " + currentWorld.getUID().hashCode());
    		
    		int gotNumberOfLives = this.getConfig().getInt("Lives");
    		int gotCurrentLives = this.getConfig().getInt("CurrentLives");
    		boolean gotWorldFailed = this.getConfig().getBoolean("WorldFailed");
    		int daysLeft = this.getConfig().getInt("DaysLeft");
    		
    		doWorldEndEvent = this.getConfig().getBoolean("DoWorldEndEvent");    		
    		livesManager = new LivesManager(gotNumberOfLives, gotWorldFailed, gotCurrentLives, this);
    		days = new DaysTracker(daysLeft);
    	}
    	else {    		
    		System.out.print("[LaGroupHardcore] Did not load Config and Data, will load defaults");
    		livesManager = new LivesManager(defualtNumberOfLives, false, this);
    		days = new DaysTracker(defualtNumberOfDays);
    		saveToConfig(this.getServer().getWorlds().get(0));
    	}
    	
    	
    }        
           
    @Override
    public boolean onCommand(CommandSender sender,
                             Command command,
                             String label,
                             String[] args) {
    	
    	return commandHandler.onCommand(sender, command, label, args);
    }    
    
    public void worldEnd() {
    	
    	Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
		    public void run() {
		    	worldEndEvent.startEvent();
		    }
		 }, worldEndStartDelay);
    	
    	
    } 
    
    public void worldWin() {
    	System.out.print("World has been Won");
    }
}
