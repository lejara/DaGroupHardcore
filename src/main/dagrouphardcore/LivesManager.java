/**
* 
* Managing Class for lives
* 
* @author Lejara (Leonel Jara)
* 
*/

package main.dagrouphardcore;

import org.bukkit.Bukkit;
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
			currentLives = 0;
		}		
		main.scoreTracker.updateScoreBoardOfLives();		
		main.saveToConfig(player);
	}
	
	public void deactivate() {
		active = false;
	}
	
	private void lose() {
		Bukkit.broadcastMessage(ChatColor.RED + "Failed, All Lives Are Gone!");
		main.worldEnd();
	}		
	
}
