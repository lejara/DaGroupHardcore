/**
* 
* Event class on player joining the server
* On join, the player will either be kick if the world has failed the hardcore,
* Or the scoreboard of lives will be set
* 
* @author Lejara (Leonel Jara)
* 
*/
package main.dagrouphardcore;


import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

	
	private GroupHardcore main;
	private ScorebroadTracker scoreTracker;
	
	public PlayerJoinListener(GroupHardcore m, ScorebroadTracker t) {
		main = m;
		scoreTracker = t;
	}
	
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
    	if(!main.worldFailed && main.active) {
    		scoreTracker.setScoreBoardToPlayer(event.getPlayer());
            event.setJoinMessage("Welcome to da hardcore, " + event.getPlayer().getName());
    	}
    	else if(main.worldFailed)  {
    		event.getPlayer().kickPlayer("Hardcore on this world has failed");
    	}

    }	
	

	
	
}
