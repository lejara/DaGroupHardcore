package main.lagrouphardcore;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import net.md_5.bungee.api.ChatColor;

public class ScorebroadTracker {

	LivesManager lives;
	DaysTracker daysTrack;
	
	public ScorebroadTracker(LivesManager l, DaysTracker d ) {
		lives = l;
		daysTrack = d;
	}
	
	
	public void setScoreBoardToPlayer(Player player) {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("LivesObj", "life", ChatColor.DARK_RED + "-------");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR); 
        
        obj.getScore(" ").setScore(4);
        obj.getScore(" Lives: " + ChatColor.GREEN +lives.currentLives + "    ").setScore(3);
        obj.getScore("  ").setScore(2);
        obj.getScore(" Days Left: " + ChatColor.GREEN +daysTrack.daysLeft + " ").setScore(1);   
        obj.getScore("   ").setScore(0);
        
        player.setScoreboard(board);
	}
	
	public void updateScoreBoardOfLives() {
		for (Player p : Bukkit.getOnlinePlayers()) {
//			Scoreboard board = p.getScoreboard();
//			board.getObjective("LivesObj").getScore("Lives: ").setScore(lives.currentLives);
			setScoreBoardToPlayer(p);
		}
	}	
	
	public void updateScoreBoardOfDaysLeft() {
		for (Player p : Bukkit.getOnlinePlayers()) {
//			Scoreboard board = p.getScoreboard();
//			board.getObjective("LivesObj").getScore("Days Left: ").setScore(daysTrack.daysLeft);
			setScoreBoardToPlayer(p);
		}
	}	
	
	public void clearScoreBoard() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());			
		}
	}
	
}
