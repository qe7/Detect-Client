package github.qe7.detect.module.impl.movement;

import github.qe7.detect.event.Event;
import github.qe7.detect.event.listeners.EventMotion;
import github.qe7.detect.module.Category;
import github.qe7.detect.module.Module;
import github.qe7.detect.setting.impl.SettingMode;
import github.qe7.detect.setting.impl.SettingNumber;
import github.qe7.detect.util.chat.AddChatMessage;
import github.qe7.detect.util.player.Movement;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.lwjgl.input.Keyboard;

public class Fly extends Module {

    public SettingMode mode;
    public SettingNumber flyspeed;


    public Fly() {
        super("Fly", 0, Category.MOVEMENT);
        mode = new SettingMode("Mode", "Vanilla", "VerusFly");
        flyspeed = new SettingNumber("Speed", 1, "#.##", 0.1, 10);
        addSettings(mode, flyspeed);
    }

    public void onEnable() {
        switch (mode.getCurrentValue()) {
            case "VerusFly":
                verusdamage();
                break;
        }
    }

    public void onEvent(Event e) {
        setSuffix(mode.getCurrentValue());
        if (e instanceof EventMotion) {
            switch (mode.getCurrentValue()) {
                case "Vanilla":
                    vanillaFly();
                    break;
                case "VerusFly":
                    verusfly();
                    break;
            }
        }
    }

    public void onDisable() {
        mc.timer.timerSpeed = 1f;
        mc.thePlayer.setSpeed(0.f);
    }

    public void vanillaFly() {
        mc.thePlayer.motionY = 0.f;
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            mc.thePlayer.motionY += flyspeed.getValue();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            mc.thePlayer.motionY -= flyspeed.getValue();
        }
        if (Movement.isMoving()) {
            mc.thePlayer.setSpeed(flyspeed.getValue());
        } else {
            mc.thePlayer.setSpeed(0.f);
            mc.timer.timerSpeed = 1f;
        }
    }

    public void verusfly() {
        mc.thePlayer.motionY = 0.f;
        mc.timer.timerSpeed = 0.2f;
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            mc.thePlayer.motionY += 2;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            mc.thePlayer.motionY -= 2;
        }
        if (Movement.isMoving()) {
            mc.thePlayer.setSpeed(8);
        } else {
            mc.thePlayer.setSpeed(0.f);
            mc.timer.timerSpeed = 1f;
        }
    }

    public void verusdamage() {
        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 3.33, mc.thePlayer.posZ, false));
        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
        mc.thePlayer.motionY = 0.5;
    }

    public void damage() {
        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 3.5f, mc.thePlayer.posZ, true));
        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
        AddChatMessage.addChatMessage("Damaged player");
    }
}
