package main.lagrouphardcore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EnderDragonChangePhaseEvent;
import org.bukkit.entity.EnderDragon.Phase;;

public class EnderDragonEventListener implements Listener {

	GroupHardcore main;
	
	public EnderDragonEventListener(GroupHardcore m) {
		main = m;
	}
	
	@EventHandler
	public void onDragonPhaseChangeEvent(EnderDragonChangePhaseEvent event) {
		
		if(event.getNewPhase() ==  Phase.DYING) {
			main.worldWin();
		}
	}
	
}
