package main.lagrouphardcore;


import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

	
	private LivesManager lives;
	
	public PlayerJoinListener(LivesManager l) {
		lives = l;
	}
	
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
    	if(!lives.worldFailed) {
        	lives.setScoreBoardOfLives(event.getPlayer());
            event.setJoinMessage("Welcome to da hardcore, " + event.getPlayer().getName());
    	}
    	else {
    		event.getPlayer().kickPlayer("Hardcore on this world has failed");
    	}

    }	
	

	
	
}
