package github.qe7.detect.module.impl.movement;

import github.qe7.detect.event.Event;
import github.qe7.detect.event.listeners.EventUpdate;
import github.qe7.detect.module.Category;
import github.qe7.detect.module.Module;
import github.qe7.detect.setting.impl.SettingBoolean;
import github.qe7.detect.setting.impl.SettingMode;
import github.qe7.detect.setting.impl.SettingNumber;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Step extends Module {

    private SettingMode mode;
    private SettingBoolean onePoint5;
    private SettingNumber timer;
    public Step() {
        super("Step", 0, Category.MOVEMENT);

        mode = new SettingMode("Mode", "Vanilla", "Motion");
        onePoint5 = new SettingBoolean("1.5", true);
        timer = new SettingNumber("Timer", 0.5d, ".##", 0.1d, 3d);

        addSettings(mode, onePoint5, timer);
    }

    public double calculateHeight() {

        double x = -Math.sin(mc.thePlayer.getDirection()) * 0.4;
        double z = Math.cos(mc.thePlayer.getDirection()) * 0.4;
        double height = 0;
        for (double i = 0.5; i <= 1.5 + 0.1; i += 0.1) {
            boolean step = mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer,
                    mc.thePlayer.getEntityBoundingBox().offset(x, i, z)).isEmpty();
            if (step) {
                height = i;
                break;
            }
        }
        return height;
    }
    public void onEvent(Event event) {
        setSuffix(mode.getCurrentValue());

        if (event instanceof EventUpdate) {
            switch (mode.getCurrentValue()) {
                case "Vanilla" : {
                    if (mc.thePlayer.isCollidedHorizontally) {
                        if (event.isPre()) {
                            mc.thePlayer.stepHeight = 1.0F;
                            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.42D, mc.thePlayer.posZ, mc.thePlayer.onGround));
                            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.753D, mc.thePlayer.posZ, mc.thePlayer.onGround));
                        }
                    }
                    break;
                }
                case "Motion" : {
                    mc.thePlayer.stepHeight = 0.5f;
                    if (mc.thePlayer.isCollidedHorizontally) {
                        if (calculateHeight() > 0.5 && calculateHeight() < 1.5) {

                            if (timer.getValue() == 0) {
                                mc.timer.timerSpeed = 1f;
                            } else {
                                mc.timer.timerSpeed = timer.getValue().floatValue();
                            }

                            mc.thePlayer.motionY = 0.3;
                        }
                        if (calculateHeight() > 1 && calculateHeight() < 1.75 && onePoint5.getValue()) {

                            if (timer.getValue() == 0) {
                                mc.timer.timerSpeed = 1f;
                            } else {
                                mc.timer.timerSpeed = timer.getValue().floatValue();
                            }

                            mc.timer.timerSpeed = timer.getValue().floatValue();
                            mc.thePlayer.motionY = 0.45;
                        }
                    } else {
                        mc.timer.timerSpeed = 1f;
                    }
                    break;
                }
            }
        }
    }
    public void onDisable() {
        mc.thePlayer.stepHeight = 0.5f;
    }

}
