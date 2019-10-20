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
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import net.md_5.bungee.api.ChatColor;

public class LivesManager {
		
	public int currentLives = 0;
	public int lives = 3;
	public boolean worldFailed = false;
	GroupHardcore main;
	
	public LivesManager(int numberOfLives, boolean wf, GroupHardcore m){
		lives = numberOfLives;
		currentLives = lives;
		main = m;
		worldFailed = wf;
	}
	
	public LivesManager(int numberOfLives, boolean wf, int currentNumLive, GroupHardcore m){
		lives = numberOfLives;
		currentLives = currentNumLive;
		main = m;
		worldFailed = wf;
	}
	
	public void setLives(int l) {
		lives = l;
		resetLives();		
	}
	
	public void resetLives() {
		currentLives = lives;
		worldFailed = false;
		updateScoreBoardOfLives();			
		main.saveToConfig(main.currentWorld);
	}
	
	public void loseALive(Player player) {		
		currentLives--;
		if(currentLives < 0) {
			lose();
			currentLives = 0;
		}		
		updateScoreBoardOfLives();		
		main.saveToConfig(player);
	}
	
	
	
	public void setScoreBoardOfLives(Player player) {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("LivesObj", "life", " ");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);        
        Score livesScore = obj.getScore("Lives: ");
        livesScore.setScore(currentLives);
        player.setScoreboard(board);
	}
		
	
	private void updateScoreBoardOfLives() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			Scoreboard board = p.getScoreboard();
			board.getObjective("LivesObj").getScore("Lives: ").setScore(currentLives);
		}
	}	
	
	private void lose() {
		Bukkit.broadcastMessage(ChatColor.RED + "All Lives Are Gone!");
		worldFailed = true;						
		if(main.doWorldEndEvent) {
			main.worldEnd();
		}
		else {
			for (Player p : Bukkit.getOnlinePlayers()) {			
				p.kickPlayer("Hardcore Failed, no more lives");
			}
		}

	}		
	
}
