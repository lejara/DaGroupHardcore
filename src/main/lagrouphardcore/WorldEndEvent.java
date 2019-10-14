package main.lagrouphardcore;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
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
	int randomRange = 6;
	Long playerKickWarningTime = 920L;
	Long playerKickTime = 1220L;
	
	WorldEndEvent(GroupHardcore m){
		main = m;
	}
	
	public void StartEvent() {
		Bukkit.broadcastMessage(ChatColor.RED + "World Will Now End.");
    	World world = main.getServer().getWorlds().get(0);
    	world.setTime(13000);
    	endingPlaySounds(20L);
    	//Spawn Primed TNT, and lava below the player in 5 secs
    	spawnExplo(100L, world);
    	//Spawn Primed TNT, and lava below the player in 15 secs
    	spawnExplo(300L, world);
    	//Spawn Primed TNT, and lava below the player in 25 secs
    	spawnExplo(500L, world);
    	//Spawn Primed TNT, and lava below the player in 35 secs
    	spawnExplo(700L, world);
    	//Spawn Primed TNT, and lava below the player in 45 secs
    	spawnExplo(900L, world);
    	
    	//Kick All Player Warning 
    	playerDelayKickWarning();
    	//Kick All Players
    	kickDelayAllPlayers();
	}
	
	private void endingPlaySounds(Long interval) {
		
    	Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
		    public void run() {
	    		for (Player p : Bukkit.getOnlinePlayers()) {
	    			
	    			p.playSound(p.getLocation(), Sound.AMBIENT_UNDERWATER_EXIT, 10, 6);
	    			p.playSound(p.getLocation(), Sound.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR, 8, 8);
	    			p.playSound(p.getLocation(), Sound.ENTITY_SKELETON_DEATH, 2, 6);
	    			p.playSound(p.getLocation(), Sound.ENTITY_SKELETON_DEATH, 9, 1);
	    			p.playSound(p.getLocation(), Sound.ENTITY_SKELETON_DEATH, 9, 1);
	    		}    
	    		if(!Bukkit.getOnlinePlayers().isEmpty()) {
	    			endingPlaySounds(interval);
	    		}	    		
		    }
		 }, interval);
	}
	
	private void spawnExplo(Long Delay, World world) {

    	Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
		    public void run() {		    			    	
		    	for (Player p : Bukkit.getOnlinePlayers()) {
		    		
		    		Location pLoc = p.getLocation();		
		    		int x_block = pLoc.getBlockX();
		    		int y_block = pLoc.getBlockY() - 1; //under player's feet
		    		int z_block = pLoc.getBlockZ();
		    							
		    		
		    		for(int xCoord = x_block - floorExploRadius; xCoord < x_block + floorExploRadius; xCoord++) {
		                for(int zCoord = z_block - floorExploRadius; zCoord < z_block + floorExploRadius; zCoord++) {
		                	Location spawnLoc = new Location(world, xCoord, y_block, zCoord);
		                	spawnLoc.setX((int)(Math.random() * randomRange + spawnLoc.getX()));
		                	spawnLoc.setZ((int)(Math.random() * randomRange + spawnLoc.getZ()));
		                	world.spawn(spawnLoc, TNTPrimed.class).setFuseTicks(15);			            				            	
		                	Block block = spawnLoc.getBlock(); 	            				            	
		                	block.setType(Material.LAVA);
		                }
		    		}
		    	}		    			    	
		    	
		    }
		}, Delay);
	}
	
	private void playerDelayKickWarning() {    	
    	Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
		    public void run() {
		    	Bukkit.broadcastMessage(ChatColor.RED + "Goodbye in " + 
		    			((int)(playerKickTime-playerKickWarningTime))/20
		    			+ "secs...");		    	
		    }
		 }, playerKickWarningTime);
	}
	
	private void kickDelayAllPlayers() {
		
    	Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
		    public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {			
					p.kickPlayer("Hardcore Failed, no more lives");
				}
		    	
		    }
		 }, playerKickTime);
	}
}
