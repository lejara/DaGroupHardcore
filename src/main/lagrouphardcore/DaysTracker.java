package main.lagrouphardcore;

import org.bukkit.Bukkit;

import net.md_5.bungee.api.ChatColor;

public class DaysTracker {

	public int daysLeft;
	private GroupHardcore main;
	private Long dayCheckPeriod = 20L; //20 ticks = 1 second
	
	public DaysTracker(GroupHardcore m, int days) {
		daysLeft = days;
		main = m;
		startDayChecker();
	}
	
	public void startDayChecker() {
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(main, new Runnable() {
            @Override
            public void run() {
                
            	Long currentTime = main.currentWorld.getTime();
            	if(currentTime >= 0L && currentTime <= dayCheckPeriod) {
            		dayPassed();
            	}
            	
            	Bukkit.broadcastMessage("Time : " +  currentTime);
            
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
	
	public void resetDays() {
		daysLeft = main.defualtNumberOfDays;
		main.scoreTracker.updateScoreBoardOfDaysLeft();		
		main.saveToConfig(main.currentWorld);
	}
	
	private void lose() {
		Bukkit.broadcastMessage(ChatColor.RED + "Failed, No More Days!");
		main.worldEnd();
	}
	
}
