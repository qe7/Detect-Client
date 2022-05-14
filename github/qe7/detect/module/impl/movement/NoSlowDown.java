package github.qe7.detect.module.impl.movement;

import github.qe7.detect.event.Event;
import github.qe7.detect.event.listeners.EventMotion;
import github.qe7.detect.event.listeners.EventSlowDown;
import github.qe7.detect.module.Category;
import github.qe7.detect.module.Module;
import github.qe7.detect.setting.impl.SettingMode;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class NoSlowDown extends Module {

    public SettingMode mode;
    int slot = -1;

    public NoSlowDown() {
        super("NoSlowDown", 0, Category.MOVEMENT);
        mode = new SettingMode("Mode", "Cancel", "NCP", "Spoof");
        addSettings(mode);
    }

    public void onEvent(Event e) {
        setSuffix(mode.getCurrentValue());
        switch(mode.getCurrentValue()) {
            case "Cancel" :
                if (e instanceof EventSlowDown) {
                    e.setCancelled(true);
                }
                break;
            case "NCP" :
                if (e instanceof EventSlowDown) {
                    e.setCancelled(true);
                }
                if (e instanceof EventMotion) {
                    if (e.isPre() && mc.thePlayer.isBlocking()) {
                        mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                    }
                    if (e.isPost() && mc.thePlayer.isBlocking()) {
                        mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
                    }
                }
                break;
            case "Spoof" :
//                while (slot == mc.thePlayer.inventory.currentItem || slot == -1){
//                    slot = 2;
//                }
//                mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(slot));
                break;
        }
    }

}
