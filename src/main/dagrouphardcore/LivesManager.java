/**
* 
* Managing Class for lives
* 
* @author Lejara (Leonel Jara)
* 
*/

package main.dagrouphardcore;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class LivesManager {
	
	public boolean active;
	public int currentLives = 0;
	GroupHardcore main;
	
	public LivesManager(int currentNumLive, GroupHardcore m){
		currentLives = currentNumLive;
		main = m;
		active = true;
	}
	
	public void setLives(int l) {
		currentLives = l;	
		main.scoreTracker.updateScoreBoardOfLives();
		main.saveToConfig(main.currentWorld);
	}
	
	public void reset() {
		reset(true);
	}
	
	public void reset(boolean save) {
		active = true;
		currentLives = main.defualtNumberOfLives;		
		main.scoreTracker.updateScoreBoardOfLives();	
		
		if(save) {
			main.saveToConfig(main.currentWorld);
		}
		
	}
	
	public void loseALive(Player player) {		
		currentLives--;
		if(currentLives < 0) {
			lose();
		}		
		if(active) {
			//main.scoreTracker.updateScoreBoardOfLives(); //sendTitleWihoutScoreBoard() calls an redraw to the score board, leaving this out for now
			for (Player p : Bukkit.getOnlinePlayers()) {
				main.scoreTracker.sendTitleWihoutScoreBoard(p, 
						ChatColor.GRAY + player.getDisplayName() + ChatColor.GRAY +" has Died!", ChatColor.GRAY + "Lost one life." , 
						8, 50, 70);
				
				//Play lost live sound mix
    			p.playSound(p.getLocation(), Sound.AMBIENT_CAVE, 10, 6);
    			p.playSound(p.getLocation(), Sound.AMBIENT_CAVE, 8, 8);
    			p.playSound(p.getLocation(), Sound.AMBIENT_CAVE, 2, 6);
    			p.playSound(p.getLocation(), Sound.ENTITY_ZOMBIE_CONVERTED_TO_DROWNED, 9, 1);
    			
			}								
			main.saveToConfig(player);
		}

	}
	
	public void deactivate() {
		active = false;
	}
	
	private void lose() {
		main.worldEnd();
	}		
	
}
