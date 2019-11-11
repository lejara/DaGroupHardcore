package main.dagrouphardcore;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;

import net.md_5.bungee.api.ChatColor;

/**
* Class Handling the World Ending Event
* When the player has no lives and the doWorldEndEvent is true, this world event will trigger 
* before the kick of all players
* 
* @author Lejara (Leonel Jara)
* 
*/

public class WorldEndEvent {
		
	GroupHardcore main;
	int floorExploRadius = 2;
	int randomRange = 10;
	Long playerKickWarningTime = 920L;
	Long playerKickTime = 1000L;
	
	WorldEndEvent(GroupHardcore m){
		main = m;
	}
	
	public void startEvent() {
    	for (Player p : Bukkit.getOnlinePlayers()) {
			p.sendTitle(ChatColor.DARK_RED.toString() + "WORLD WILL NOW END! >:(", " " , 8, 40, 20);
		}
    	World world = main.getServer().getWorlds().get(0);
    	
    	world.setTime(13000);
    	
    	endingPlaySounds(50L);
    	
    	//Spawn mobs 
    	spawnBaddies(80L);
    	//Spawn Primed TNT, and lava below the player in 5 secs
    	spawnExplo(100L);
    	//Spawn Primed TNT, and lava below the player in 15 secs
    	spawnExplo(300L);
    	//Spawn Primed TNT, and lava below the player in 25 secs
    	spawnExplo(500L);
    	//Spawn mobs 
    	spawnBaddies(520L);
    	//Spawn Primed TNT, and lava below the player in 35 secs
    	spawnExplo(700L);
    	//Spawn Primed TNT, and lava below the player in 45 secs
    	spawnExplo(900L);
    	//Kick All Player Warning 
    	playerDelayKickWarning();
    	//Kick All Players
    	kickDelayAllPlayers();
	}
	
	private void endingPlaySounds(Long interval) {

		
    	Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {    		
    		
		    public void run() {
	    		for (Player p : Bukkit.getOnlinePlayers()) {
	    			
	    			p.playSound(p.getLocation(), Sound.AMBIENT_UNDERWATER_EXIT, 3, 6);
	    			p.playSound(p.getLocation(), Sound.BLOCK_BELL_RESONATE, 4, 8);
	    			p.playSound(p.getLocation(), Sound.BLOCK_BELL_RESONATE, 4, 8);
	    			p.playSound(p.getLocation(), Sound.AMBIENT_CAVE, 5, 6);
	    			p.playSound(p.getLocation(), Sound.AMBIENT_CAVE, 5, 6);
	    			p.playSound(p.getLocation(), Sound.AMBIENT_CAVE, 5, 6);
	    		}    
	    		
	    		if(!Bukkit.getOnlinePlayers().isEmpty()) {
	    			if (interval <= 5) {
	    				endingPlaySounds(interval);
	    			}
	    			else {
	    				endingPlaySounds(interval - 1);
	    			}
	    			
	    		}	    		
		    }
		 }, interval);
	}
	
	private void spawnExplo(Long Delay) {

    	Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
		    public void run() {		    			    	
		    	for (Player p : Bukkit.getOnlinePlayers()) {
		    		
		    		Location pLoc = p.getLocation();		
		    		int x_block = pLoc.getBlockX();
		    		int y_block = pLoc.getBlockY() - 1; //under player's feet
		    		int z_block = pLoc.getBlockZ();
		    							
		    		
		    		for(int xCoord = x_block - floorExploRadius; xCoord < x_block + floorExploRadius; xCoord++) {
		                for(int zCoord = z_block - floorExploRadius; zCoord < z_block + floorExploRadius; zCoord++) {
		                	Location spawnLoc = new Location(main.currentWorld, xCoord, y_block, zCoord);
		                	
		                	spawnLoc.setX((int)(Math.random() * randomRange + spawnLoc.getX()));
		                	spawnLoc.setZ((int)(Math.random() * randomRange + spawnLoc.getZ()));
		                	main.currentWorld.spawn(spawnLoc, TNTPrimed.class).setFuseTicks(15);			            				            	
		                	Block block = spawnLoc.getBlock(); 	            				            	
		                	block.setType(Material.LAVA);
		                }
		    		}
		    	}		    			    	
		    	
		    }
		}, Delay);
	}
	
	private void spawnBaddies(Long Delay) {

    	Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
		    public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {					
					
					Location spawn = p.getLocation();
					spawn.setY(spawn.getBlockY() + 4);
					main.currentWorld.spawnEntity(spawn, EntityType.GHAST);
					main.currentWorld.spawnEntity(spawn, EntityType.GHAST);
					main.currentWorld.spawnEntity(spawn, EntityType.PHANTOM);
				}
		    	
		    }
		 }, Delay);
		
	}
	
	private void dropPlayerToEndWorld() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			Location dumpLoc = p.getLocation();
			dumpLoc.setY(-2d);
			p.teleport(dumpLoc);
			p.setBedSpawnLocation(dumpLoc, true);
		}
	}
	
	private void playerDelayKickWarning() {    	
    	Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
		    public void run() {
		    	Bukkit.broadcastMessage(ChatColor.RED + "Goodbye Losers.");	
		    	dropPlayerToEndWorld();
		    }
		 }, playerKickWarningTime);
	}
	
	private void kickDelayAllPlayers() {
		
    	Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
		    public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {			
					p.kickPlayer("Hardcore Failed!");
				}
		    	
		    }
		 }, playerKickTime);
	}
}
