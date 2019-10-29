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

package main.dagrouphardcore;


import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;



public class GroupHardcore extends JavaPlugin {
	
	boolean worldFailed = false;
	boolean doWorldEndEvent = false;
	boolean active = true;
	int defualtNumberOfLives = 4;	
	int defualtNumberOfDays = 12;
	Long worldEndStartDelay = 80L;
	
	World currentWorld;
	
	LivesManager livesManager;	
	ScorebroadTracker scoreTracker;
	DaysTracker days;
	GroupHCCommandHandler commandHandler;
	WorldEndEvent worldEndEvent;
	
    @Override
    public void onEnable() {
    	        	
    	loadFromConfig();
    	currentWorld.setDifficulty(Difficulty.HARD);
    	worldEndEvent = new WorldEndEvent(this);
    	scoreTracker = new ScorebroadTracker(livesManager, days);
    	commandHandler = new GroupHCCommandHandler(livesManager, days, this);
    	    	
    	getServer().getPluginManager().registerEvents(new DeathListener(livesManager, this), this);
    	getServer().getPluginManager().registerEvents(new PlayerJoinListener(this, scoreTracker), this);
    	getServer().getPluginManager().registerEvents(new EnderDragonEventListener(this), this);
    	
    	if(!active) {
    		deactivate();
    	}
    }
    @Override
    public void onDisable() {
    }
    
    public void saveToConfig(Player player) {
    	saveToConfig(player.getWorld());
    }
    
    public void saveToConfig(World world) {    	
    	
    	this.getConfig().set("WorldID", world.getUID().hashCode()); 
    	this.getConfig().set("DoWorldEndEvent", doWorldEndEvent);
    	this.getConfig().set("CurrentLives", livesManager.currentLives);    	   	
    	this.getConfig().set("WorldFailed", worldFailed);
    	this.getConfig().set("DaysLeft", days.daysLeft);
    	this.getConfig().set("HardcoreActive", active);
    	
    	this.saveConfig();
    	
    	System.out.print("[DaGroupHardcore] Config and Data Saved");
    }
    
    public void loadFromConfig() {
    	currentWorld = getServer().getWorlds().get(0);
    	
    	int getWorldID = this.getConfig().getInt("WorldID");
    	
    	if(getWorldID != 0 && getWorldID == currentWorld.getUID().hashCode()) {
    		
    		System.out.print("[DaGroupHardcore] Loaded Config and Data, World UID Hash: " + currentWorld.getUID().hashCode());
    		
    		int gotCurrentLives = this.getConfig().getInt("CurrentLives");
    		int daysLeft = this.getConfig().getInt("DaysLeft");
    		
    		worldFailed = this.getConfig().getBoolean("WorldFailed");
    		doWorldEndEvent = this.getConfig().getBoolean("DoWorldEndEvent");    		
    		livesManager = new LivesManager(gotCurrentLives, this);
    		days = new DaysTracker(this, daysLeft);
    		active = this.getConfig().getBoolean("HardcoreActive");
    		
    	}
    	else {    		
    		System.out.print("[DaGroupHardcore] Did not load Config and Data, will load defaults");
    		livesManager = new LivesManager(defualtNumberOfLives, this);
    		days = new DaysTracker(this, defualtNumberOfDays);
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
    	worldFailed = true;
    	    	
		if(doWorldEndEvent) {
	    	Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			    public void run() {
			    	worldEndEvent.startEvent();
			    }
			 }, worldEndStartDelay);
	    	
		}
		else {			
			Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			    public void run() {
					for (Player p : Bukkit.getOnlinePlayers()) {			
						p.kickPlayer("Hardcore Failed!");
					}
			    }
			 }, worldEndStartDelay);
			
		}    	    
    } 
    
    public void worldWin() {
    	deactivate();
    	Bukkit.broadcastMessage(ChatColor.GREEN + "You Have Won DaGroupHardcore! XD");    	
    }
    
    public void deactivate() {
    	System.out.print("[DaGroupHardcore] Deactivating DaGroupHardcore");
    	active = false;
    	scoreTracker.clearScoreBoard();
    	days.deactivate();
    	livesManager.deactivate();
    	saveToConfig(currentWorld);
    }
    
    public void reset() {
    	System.out.print("[DaGroupHardcore] Plugin Data Reseted");
    	worldFailed = false;
    	active = true;    	
    	for (Player p : Bukkit.getOnlinePlayers()) {
    		scoreTracker.setScoreBoardToPlayer(p);
    	}    	    	
    	livesManager.reset(false);
    	days.reset(false);
    	saveToConfig(currentWorld);
    }
}