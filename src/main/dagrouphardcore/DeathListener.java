
/**
* 
* Event class on player death
* 
* @author Lejara (Leonel Jara)
* 
*/

package main.dagrouphardcore;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

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
    	}
	
	}
	
}
