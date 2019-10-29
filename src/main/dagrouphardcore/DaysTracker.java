package main.dagrouphardcore;

import org.bukkit.Bukkit;

import net.md_5.bungee.api.ChatColor;

public class DaysTracker {

	public int daysLeft;
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
		
		taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(main, new Runnable() {
            @Override
            public void run() {
                
            	Long currentTime = main.currentWorld.getTime();
            	if((currentTime >= 0L && currentTime <= dayCheckPeriod) && !daychecked) {
            		dayPassed();
            		daychecked = true;
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
		else {
			Bukkit.broadcastMessage("A Day Has Passed...");
		}
		main.scoreTracker.updateScoreBoardOfDaysLeft();	
		main.saveToConfig(main.currentWorld);
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
		
		if(save) {
			main.saveToConfig(main.currentWorld);
		}		
		if(taskID != -1) {
			startDayChecker();
		}
	}
	
	public void deactivate() {
		active = false;
		Bukkit.getScheduler().cancelTask(taskID);
		taskID = -1;
	}
	
	private void lose() {
		Bukkit.broadcastMessage(ChatColor.RED + "Failed, No More Days!");
		main.worldEnd();
	}
	
}
