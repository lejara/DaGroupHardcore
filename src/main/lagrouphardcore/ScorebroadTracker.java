package main.lagrouphardcore;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ScorebroadTracker {

	LivesManager lives;
	DaysTracker daysTrack;
	
	public ScorebroadTracker(LivesManager l, DaysTracker d ) {
		lives = l;
		daysTrack = d;
	}
	
	
	public void setScoreBoardToPlayer(Player player) {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("LivesObj", "life", "----");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR); 
        
        obj.getScore("Days Left: ").setScore(daysTrack.daysLeft);
        
        obj.getScore("Lives: ").setScore(lives.currentLives);
        
        player.setScoreboard(board);
	}
	
	public void updateScoreBoardOfLives() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			Scoreboard board = p.getScoreboard();
			board.getObjective("LivesObj").getScore("Lives: ").setScore(lives.currentLives);
		}
	}	
	
	public void updateScoreBoardOfDaysLeft() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			Scoreboard board = p.getScoreboard();
			board.getObjective("LivesObj").getScore("Days Left: ").setScore(daysTrack.daysLeft);
		}
	}	
	
	public void clearScoreBoard() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());			
		}
	}
	
}
