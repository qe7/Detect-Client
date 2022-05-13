package github.qe7.detect.module.impl.player;

import github.qe7.detect.event.Event;
import github.qe7.detect.event.listeners.EventMotion;
import github.qe7.detect.event.listeners.EventPacket;
import github.qe7.detect.module.Category;
import github.qe7.detect.module.Module;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public class NoRotate extends Module {

    public NoRotate() {
        super("NoRotate", 0, Category.PLAYER);
    }

    public void onEvent(Event event) {
        if (event instanceof EventPacket) {
            EventPacket e = (EventPacket) event;
            if (e.getPacket() instanceof S08PacketPlayerPosLook) {
                S08PacketPlayerPosLook.yaw = NoRotate.mc.thePlayer.rotationYaw;
                S08PacketPlayerPosLook.pitch = NoRotate.mc.thePlayer.rotationPitch;
            }
        }
    }

}
