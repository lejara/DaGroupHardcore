package main.dagrouphardcore;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

public class DaysTracker {

	public Integer daysLeft;
	public boolean active;
	private int taskID;
	private GroupHardcore main;
	private Long dayCheckPeriod = 20L; //20 ticks = 1 second
	private boolean daychecked = false;
	
	public DaysTracker(GroupHardcore m, int days) {
		daysLeft = days;
		main = m;
		active = true;
		startDayChecker();
	}	
	
	public void startDayChecker() {
		active = true;
		taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(main, new Runnable() {
            @Override
            public void run() {
                
            	Long currentTime = main.currentWorld.getTime();
            	if((currentTime >= 0L && currentTime <= dayCheckPeriod) && !daychecked) {
            		if(!Bukkit.getOnlinePlayers().isEmpty()) {
                		dayPassed();
                		daychecked = true;
            		}
            	}
            	if (daychecked &&  currentTime > dayCheckPeriod) {
            		daychecked = false;
            	}
            
            }
        }, 0, dayCheckPeriod); 
	}	
	
	public void dayPassed() {		
		daysLeft--;
		if(daysLeft == 0) {
			lose();
		}

		if(active) {		
//			main.scoreTracker.updateScoreBoardOfDaysLeft(); //sendTitleWihoutScoreBoard() calls a redraw  to the score board, leaving this out for now.
			for (Player p : Bukkit.getOnlinePlayers()) {
				main.scoreTracker.sendTitleWihoutScoreBoard(p, ChatColor.GRAY + "A Day Has Passed...",
						" ", 8, 50, 70);
			}						
			main.saveToConfig(main.currentWorld);
		}
	}
	
	public void setDays(int day) {
		daysLeft = day;
		main.scoreTracker.updateScoreBoardOfDaysLeft();		
		main.saveToConfig(main.currentWorld);
	}
	
	public void reset() {
		reset(true);
	}
	
	public void reset(boolean save) {
		active = true;
		daysLeft = main.defualtNumberOfDays;
		main.scoreTracker.updateScoreBoardOfDaysLeft();		
		daychecked = false;
		if(save) {
			main.saveToConfig(main.currentWorld);
		}		
		
		Bukkit.getScheduler().cancelTask(taskID);
		startDayChecker();
		main.currentWorld.setTime(30L);
		
		
	}
	
	public void deactivate() {
		active = false;
		Bukkit.getScheduler().cancelTask(taskID);
	}
	
	private void lose() {
		main.worldEnd();
	}
	
}
