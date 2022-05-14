package github.qe7.detect.module.impl.movement;

import github.qe7.detect.event.Event;
import github.qe7.detect.event.listeners.EventUpdate;
import github.qe7.detect.module.Category;
import github.qe7.detect.module.Module;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Step extends Module {

    public Step() {
        super("Step", 0, Category.MOVEMENT);
    }

    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
            if (mc.thePlayer.isCollidedHorizontally) {
                if (event.isPre()) {
                    mc.thePlayer.stepHeight = 1.0F;
                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.42D, mc.thePlayer.posZ, mc.thePlayer.onGround));
                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.753D, mc.thePlayer.posZ, mc.thePlayer.onGround));
                } else {
                }
            }
        }
    }
    public void onDisable() {
        mc.thePlayer.stepHeight = 0.5f;
    }

}
