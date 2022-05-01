package github.qe7.detect.module.impl.combat;

import java.util.concurrent.CopyOnWriteArrayList;

import github.qe7.detect.event.Event;
import github.qe7.detect.event.listeners.EventUpdate;
import github.qe7.detect.module.Category;
import github.qe7.detect.module.Module;
import io.netty.handler.timeout.TimeoutException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class AntiBot extends Module {

	public AntiBot() {
		super("AntiBot", 0, Category.COMBAT);
	}
	private static CopyOnWriteArrayList<Entity> entities = new CopyOnWriteArrayList<Entity>();
	
	public void onEvent(Event event) {
		if(event instanceof EventUpdate) {
			CopyOnWriteArrayList<Entity> entList = new CopyOnWriteArrayList<Entity>();
			for(Entity ent : mc.theWorld.loadedEntityList) {
					/* AntiBot (Hypixel) Removes Invisibles */
					if(!ent.isInvisible() && ent != mc.thePlayer && ent instanceof EntityPlayer)
						entList.add(ent);
			}
			entities = entList;
		}
	}
	
	public void onSkipEvent(Event event) {
		if(!this.isToggled()) {
			if(event instanceof EventUpdate) {
				CopyOnWriteArrayList<Entity> entList = new CopyOnWriteArrayList<Entity>();
				for(Entity ent : mc.theWorld.loadedEntityList) {
					if(ent != mc.thePlayer)
						entList.add(ent);
				}
				entities = entList;
			}
		}
	}
	
	private void writeAllPlayers() throws TimeoutException {
		
	}

	public static CopyOnWriteArrayList<Entity> getEntities() {
		return entities;
	}
	
}
