package main.dagrouphardcore;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import net.md_5.bungee.api.ChatColor;

public class ScorebroadTracker {

	LivesManager lives;
	DaysTracker daysTrack;
	GroupHardcore main;
	
	public ScorebroadTracker(LivesManager l, DaysTracker d, GroupHardcore m) {
		lives = l;
		daysTrack = d;
		main = m;
	}
	
	
	public void setScoreBoardToPlayer(Player player) {
		if(main.active) {					
			
	        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
	        
	        Objective obj = board.registerNewObjective("LivesObj", "life", 
	        		ChatColor.DARK_RED.toString() 
	        		+ ChatColor.BOLD.toString() 
	        		+ "DaGroupHardcore");
	        
	        obj.setDisplaySlot(DisplaySlot.SIDEBAR); 
	        
	        if(lives.currentLives == 0) {
	        	obj.getScore(" Lives: " + ChatColor.YELLOW + lives.currentLives).setScore(1); 
	        }
	        else {
	        	obj.getScore(" Lives: " + ChatColor.GREEN + lives.currentLives).setScore(1); 
	        }
	        

	        if(daysTrack.daysLeft == 1) {
	        	obj.getScore(" Days Left: " + ChatColor.YELLOW + daysTrack.daysLeft ).setScore(0);
	        }
	        else {
	        	obj.getScore(" Days Left: " + ChatColor.GREEN + daysTrack.daysLeft ).setScore(0);
	        }
	        
	        player.setScoreboard(board);			
		}

	}
	
	public void updateScoreBoardOfLives() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			setScoreBoardToPlayer(p);
		}
	}	
	
	public void updateScoreBoardOfDaysLeft() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			setScoreBoardToPlayer(p);
		}
	}	
	
	public void clearScoreBoard() {
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
		}
	}
	
	public void clearScoreBoard(Player player) {
		player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
	}
	
	public void sendTitleWihoutScoreBoard(Player player, String title, String subTitle, int fadeIn, int stay, int fadeOut) {
		clearScoreBoard(player);
		
		player.sendTitle(title, subTitle, fadeIn, stay, fadeOut);
		
    	Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
		    public void run() {
		    	setScoreBoardToPlayer(player);
		    }
		 }, (fadeIn + stay + fadeOut));
	}
	
}
