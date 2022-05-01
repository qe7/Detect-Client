package github.qe7.detect.module.impl.combat;

import github.qe7.detect.event.Event;
import github.qe7.detect.event.listeners.EventPacket;
import github.qe7.detect.module.Category;
import github.qe7.detect.module.Module;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

public class Velocity extends Module {

    public Velocity() {
        super("Velocity", 0, Category.COMBAT);
    }

    public void onEvent(Event event) {
        setSuffix("Cancel");
        if (event instanceof EventPacket) {
            if (((EventPacket) event).getPacket() instanceof S12PacketEntityVelocity || ((EventPacket) event).getPacket() instanceof S27PacketExplosion) {
                event.setCancelled(true);
            }
        }
    }

}
