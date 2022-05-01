package github.qe7.detect.event.listeners;

import github.qe7.detect.event.Event;
import github.qe7.detect.event.EventDirection;
import net.minecraft.network.Packet;

public class EventPacket extends Event {
	
	private Packet packet;
	private EventDirection direction;
	private boolean cancelled;
	
	public EventPacket(EventDirection direction, Packet packet) {
		this.packet = packet;
		this.direction = direction;
	}
	
	public Packet getPacket() {
		return packet;
	}

	@Override
	public EventDirection getDirection() {
		return direction;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	
}
