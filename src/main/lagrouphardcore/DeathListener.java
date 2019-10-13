
/**
* 
* Event class on player death
* 
* @author Lejara (Leonel Jara)
* 
*/

package main.lagrouphardcore;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener{
    
	private LivesManager lives;
	
	public DeathListener(LivesManager l) {
		lives = l;
	}
	
    @EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
    	lives.LoseALive(event.getEntity());
		//Play lost live sound mix
		for (Player p : Bukkit.getOnlinePlayers()) {
			
			p.playSound(p.getLocation(), Sound.AMBIENT_CAVE, 10, 6);
			p.playSound(p.getLocation(), Sound.AMBIENT_CAVE, 8, 8);
			p.playSound(p.getLocation(), Sound.AMBIENT_CAVE, 2, 6);
			p.playSound(p.getLocation(), Sound.ENTITY_ZOMBIE_CONVERTED_TO_DROWNED, 9, 1);
		}
		event.setDeathMessage(event.getEntity().getDisplayName() + " has Died, lost one life");		
	}
	
}
