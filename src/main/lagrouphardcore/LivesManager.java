/**
* 
* Managing Class for lives
* 
* @author Lejara (Leonel Jara)
* 
*/

package main.lagrouphardcore;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import net.md_5.bungee.api.ChatColor;

public class LivesManager {
		
	public int currentLives = 0;
	GroupHardcore main;
	
	public LivesManager(int currentNumLive, GroupHardcore m){
		currentLives = currentNumLive;
		main = m;
	}
	
	public void setLives(int l) {
		currentLives = l;	
		main.scoreTracker.updateScoreBoardOfLives();
		main.saveToConfig(main.currentWorld);
	}
	
	public void resetLives() {
		currentLives = main.defualtNumberOfLives;		
		main.scoreTracker.updateScoreBoardOfLives();		
		main.saveToConfig(main.currentWorld);
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
	
	
	
//	public void setScoreBoardOfLives(Player player) {
//        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
//        Objective obj = board.registerNewObjective("LivesObj", "life", "----");
//        obj.setDisplaySlot(DisplaySlot.SIDEBAR);        
//        Score livesScore = obj.getScore("Lives: ");
//        livesScore.setScore(currentLives);
//        player.setScoreboard(board);
//	}
		
//	
//	private void updateScoreBoardOfLives() {
//		for (Player p : Bukkit.getOnlinePlayers()) {
//			Scoreboard board = p.getScoreboard();
//			board.getObjective("LivesObj").getScore("Lives: ").setScore(currentLives);
//		}
//	}	
	
	private void lose() {
		Bukkit.broadcastMessage(ChatColor.RED + "Failed, All Lives Are Gone!");
		main.worldEnd();
	}		
	
}
