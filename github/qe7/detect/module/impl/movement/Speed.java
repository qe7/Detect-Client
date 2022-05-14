package github.qe7.detect.module.impl.movement;

import github.qe7.detect.event.Event;
import github.qe7.detect.event.listeners.EventMessage;
import github.qe7.detect.event.listeners.EventMotion;
import github.qe7.detect.event.listeners.EventUpdate;
import github.qe7.detect.module.Category;
import github.qe7.detect.module.Module;
import github.qe7.detect.setting.impl.SettingMode;
import github.qe7.detect.setting.impl.SettingNumber;
import github.qe7.detect.util.Timer;
import github.qe7.detect.util.player.Movement;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.MovementInput;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Speed extends Module {

    public SettingMode mode;
    public SettingNumber speed;
    public Timer timer = new Timer();
    public double moveSpeed;
    public int state;
    private double lastDist;
    private int cooldownHops;

    public Speed() {
        super("Speed", 0, Category.MOVEMENT);
        mode = new SettingMode("Mode", "Vanilla", "NCPHop", "NCPYPort", "GhostlyStrafe");
        speed = new SettingNumber("Speed", 0.4, "#.##", 0.1, 1);
        addSettings(mode, speed);
    }

    public void onEnable() {

    }

    public void onDisable() {
        Speed.mc.timer.timerSpeed = 1.0f;
        mc.thePlayer.speedInAir = 0.02f;
        mc.thePlayer.stepHeight = 0.6f;
    }

    public void onEvent(Event e) {

        setSuffix(mode.getCurrentValue());

        switch(mode.getCurrentValue()) {
        case "Vanilla" :
            if (e instanceof EventMotion) {
                if (Movement.isMoving()) {
                    if (mc.thePlayer.onGround) {
                        mc.thePlayer.jump();
                    }
                    mc.thePlayer.setSpeed(speed.getValue());
                }
            }
            break;
        case "NCPHop" :
            if (e instanceof EventMotion) {
                if (Movement.isMovingForward()) {
                    if (mc.thePlayer.onGround) {
                        mc.thePlayer.jump();
                        mc.thePlayer.setSpeed(0.4);
                    }
                } else {
                    mc.thePlayer.setSpeed(0f);
                }
            }
            break;
        case "NCPYPort" :
            if (e instanceof EventMotion) {
                if (Movement.isMovingForward()) {
                    if (mc.thePlayer.onGround) {
                        mc.thePlayer.jump();
                        mc.thePlayer.setSpeed(0.649f);
                    } else {
                        mc.thePlayer.motionY -= 1;
                    }
                }
            }
            break;
        case "GhostlyStrafe" :
            if (e instanceof EventMotion) {
                if (Movement.isMoving()) {
                    if (mc.thePlayer.onGround) {
                        mc.thePlayer.jump();
                        mc.thePlayer.setSpeed(0.4);
                    } else {
                        mc.thePlayer.setSpeed(getBaseMoveSpeed());
                    }
                } else {
                    mc.thePlayer.setSpeed(0f);
                }
            }
            break;
        }
    }

    public static double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;
        if (Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.moveSpeed)) {
            final int amplifier = Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        return baseSpeed;
    }

    public double round(double value, int places) {
        if (places < 0)
            throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
