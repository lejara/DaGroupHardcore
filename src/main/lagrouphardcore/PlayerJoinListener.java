/**
* 
* Event class on player joining the server
* On join, the player will either be kick if the world has failed the hardcore,
* Or the scoreboard of lives will be set
* 
* @author Lejara (Leonel Jara)
* 
*/
package main.lagrouphardcore;


import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

	
	private LivesManager lives;
	private ScorebroadTracker scoreTracker;
	
	public PlayerJoinListener(LivesManager l, ScorebroadTracker t) {
		lives = l;
		scoreTracker = t;
	}
	
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
    	if(!lives.worldFailed) {
    		scoreTracker.setScoreBoardToPlayer(event.getPlayer());
            event.setJoinMessage("Welcome to da hardcore, " + event.getPlayer().getName());
    	}
    	else {
    		event.getPlayer().kickPlayer("Hardcore on this world has failed");
    	}

    }	
	

	
	
}
