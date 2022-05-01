package github.qe7.detect.module.impl.movement;

import github.qe7.detect.Detect;
import github.qe7.detect.event.listeners.EventMotion;
import github.qe7.detect.module.Category;
import github.qe7.detect.util.Timer;
import github.qe7.detect.util.chat.AddChatMessage;
import github.qe7.detect.util.player.Movement;
import org.lwjgl.input.Keyboard;

import github.qe7.detect.event.Event;
import github.qe7.detect.module.Module;
import github.qe7.detect.setting.impl.SettingMode;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Fly extends Module {

	public SettingMode mode;
	
	public Fly() {
		super("Fly", 0, Category.MOVEMENT);
		mode = new SettingMode("Mode", "Vanilla", "VerusDMG", "Smooth");
		addSettings(mode);
	}

	public Timer timer = new Timer();

	public void onEnable() {
		timer.reset();
		switch(mode.getCurrentValue()) {
		case "VerusDMG" :
				damage();
			break;
		}
	}
	
	public void onEvent(Event e) {
		if (e instanceof EventMotion) {
			switch(mode.getCurrentValue()) {
			case "Vanilla" :
				vanillaFly();
				break;
			case "VerusDMG" :
				verusDMG();
				break;
			case "Smooth" :
				smoothFly();
				break;
			}
		}
	}
	
	public void onDisable() {
		mc.timer.timerSpeed = 1f;
		mc.thePlayer.setSpeed(0.f);
	}
	
	public void vanillaFly() {
		setSuffix("Vanilla");
		mc.thePlayer.motionY = 0.f;
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			mc.thePlayer.motionY += 0.4f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			mc.thePlayer.motionY -= 0.4f;
		}
		if (Movement.isMoving()) {
			mc.thePlayer.setSpeed(2f);
		} else {
			mc.thePlayer.setSpeed(0.f);
			mc.timer.timerSpeed = 1f;
		}
	}
	
	public void verusDMG() {
		setSuffix("VerusDMG");
		mc.timer.timerSpeed = 0.2f;
		//if (mc.thePlayer.hurtTime > 2f) {
			phase();
			mc.thePlayer.motionY = 0.0005f;
			if (Movement.isMoving()) {
				mc.thePlayer.setSpeed(8f);
				mc.timer.timerSpeed = 0.2f;
			} else {
				mc.thePlayer.setSpeed(0.f);
				mc.timer.timerSpeed = 1f;
			}
		//} else {
			//mc.thePlayer.setSpeed(0f);
		//}
	}
	
	public void smoothFly() {
		setSuffix("Smooth");
		mc.thePlayer.motionY = 0.f;
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			mc.thePlayer.motionY += 0.4f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			mc.thePlayer.motionY -= 0.4f;
		}
		if (Movement.isMoving()) {
			mc.thePlayer.setSpeed(0.8f);
		}
	}
	
	public void damage() {
        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 3.5f, mc.thePlayer.posZ, true));
        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
		AddChatMessage.addChatMessage("Damaged player");
	}

	public void jump() {
		mc.thePlayer.jump();
	}

	public void phase() {

		if (timer.hasReached(100L))
			mc.thePlayer.noClip = false;
		else if (!timer.hasReached(20L))
			mc.thePlayer.noClip = true;
			mc.thePlayer.motionY = 0.4f;
	}
}
