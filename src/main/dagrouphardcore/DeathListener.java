
/**
* 
* Event class on player death
* 
* @author Lejara (Leonel Jara)
* 
*/

package main.dagrouphardcore;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.event.entity.PlayerDeathEvent;

import net.md_5.bungee.api.ChatColor;

public class DeathListener implements Listener{
    
	private LivesManager lives;
	private GroupHardcore main;
	
	public DeathListener(LivesManager l, GroupHardcore m) {
		lives = l;
		main = m;
	}
	
    @EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
    	if(!main.worldFailed && lives.active) {
    		
    		lives.loseALive(event.getEntity());
    		
    		if(!main.worldFailed) {
        		//event.setDeathMessage(ChatColor.DARK_RED + event.getEntity().getDisplayName() + " has Died! "+ ChatColor.WHITE + "Lost one life.");	
        		
    			for (Player p : Bukkit.getOnlinePlayers()) {
    				p.sendTitle(ChatColor.DARK_RED + event.getEntity().getDisplayName() + ChatColor.DARK_RED +" has Died!", "Lost one life." , 8, 80, 70);
    			}
    		}
    		        	
    		//Play lost live sound mix
    		for (Player p : Bukkit.getOnlinePlayers()) {
    			
    			p.playSound(p.getLocation(), Sound.AMBIENT_CAVE, 10, 6);
    			p.playSound(p.getLocation(), Sound.AMBIENT_CAVE, 8, 8);
    			p.playSound(p.getLocation(), Sound.AMBIENT_CAVE, 2, 6);
    			p.playSound(p.getLocation(), Sound.ENTITY_ZOMBIE_CONVERTED_TO_DROWNED, 9, 1);
    		}    		
    	}
	
	}
	
}
