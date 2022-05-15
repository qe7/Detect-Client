package github.qe7.detect.module.impl.combat;

import github.qe7.detect.Detect;
import github.qe7.detect.event.Event;
import github.qe7.detect.event.listeners.EventMotion;
import github.qe7.detect.event.listeners.EventPacket;
import github.qe7.detect.event.listeners.EventUpdate;
import github.qe7.detect.module.Category;
import github.qe7.detect.module.Module;
import github.qe7.detect.setting.impl.SettingMode;
import github.qe7.detect.setting.impl.SettingNumber;
import github.qe7.detect.util.Timer;
import github.qe7.detect.util.chat.AddChatMessage;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Criticals extends Module {

    public SettingMode mode;
    public SettingNumber delay;

    public Criticals() {
        super("Criticals", 0, Category.COMBAT);
        mode = new SettingMode("Mode", "Packet1", "Packet2", "Jump", "MiniJump");
        delay = new SettingNumber("Delay", 500, "##.", 400, 1000);
        addSettings(mode, delay);
    }

    public Timer timer = new Timer();

    public void onEvent(Event e) {
        if (e instanceof EventPacket) {
            EventPacket event = (EventPacket) e;

            setSuffix(mode.getCurrentValue());

            if (timer.hasReached(delay.getValue().longValue())) {
                if (event.getPacket() instanceof C02PacketUseEntity) {

                    if (!mc.thePlayer.onGround)
                        return;

                    switch (mode.getCurrentValue()) {
                        case "Packet1":
                            doCrits(0.05D, false);
                            doCrits(0.D, false);
                            doCrits(0.012511D, false);
                            doCrits(0.D, false);
                            break;
                        case "Packet2":
                            doCrits(0.069d, false);
                            doCrits(0.D, false);
                            break;
                        case "Jump":
                            if (mc.thePlayer.onGround)
                                mc.thePlayer.motionY = 0.42;
                            break;
                        case "MiniJump":
                            if (mc.thePlayer.onGround)
                                mc.thePlayer.motionY = 0.3425;
                            break;
                    }
                    timer.reset();
                }
            }
        }
    }

    static void doCrits(double offset, boolean ground) {
        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + offset, mc.thePlayer.posZ, ground));
    }
}
