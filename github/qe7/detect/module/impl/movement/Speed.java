package github.qe7.detect.module.impl.movement;

import github.qe7.detect.event.Event;
import github.qe7.detect.event.listeners.EventMotion;
import github.qe7.detect.module.Category;
import github.qe7.detect.module.Module;
import github.qe7.detect.setting.impl.SettingMode;
import github.qe7.detect.setting.impl.SettingNumber;
import github.qe7.detect.util.Timer;
import github.qe7.detect.util.player.Movement;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Speed extends Module {

    public SettingMode mode;
    public SettingNumber speed;
    public Timer timer = new Timer();
    public int count = 0;
    public double moveSpeed;
    public int state;

    public Speed() {
        super("Speed", 0, Category.MOVEMENT);
        mode = new SettingMode("Mode", "Vanilla", "NCPHop", "NCPYPort", "FakeJump", "Verus");
        speed = new SettingNumber("Speed", 0.4, "#.##", 0.1, 1);
        addSettings(mode, speed);
    }

    public void onEnable() {
        mc.thePlayer.speedInAir = 0.02f;
        Speed.mc.timer.timerSpeed = 1.0f;
        count = 0;
    }

    public void onDisable() {
        mc.thePlayer.speedInAir = 0.02f;
        Speed.mc.timer.timerSpeed = 1.0f;
        count = 0;
    }

    public void onEvent(Event e) {

        setSuffix(mode.getCurrentValue());

        if (e instanceof EventMotion) {
            switch (mode.getCurrentValue()) {
                case "Vanilla":
                    if (Movement.isMoving()) {
                        if (mc.thePlayer.onGround && count == 0) {
                            mc.thePlayer.jump();
                            count++;
                        }

                        if (!mc.thePlayer.onGround) {
                            count = 0;
                        }

                        mc.thePlayer.setSpeed(speed.getValue());
                    }
                    break;
                case "NCPHop":
                    if (Movement.isMoving()) {
                        if (mc.thePlayer.onGround) {
                            mc.thePlayer.motionY = 0.411d;
                            mc.thePlayer.setSpeed(0.4);
                        }
                        mc.thePlayer.setSpeed(0.26);
                    } else {
                        mc.thePlayer.setSpeed(0f);
                    }
                    break;
                case "NCPYPort":
                    if (Movement.isMoving()) {
                        if (mc.thePlayer.onGround) {
                            mc.thePlayer.motionY = 0.4d;
                            mc.thePlayer.setSpeed(0.66425f);
                        } else {
                            mc.thePlayer.motionY -= 1;
                        }
                    }
                    break;
                case "FakeJump" :
                    if (Movement.isMoving()) {
                        if (mc.thePlayer.onGround) {
                            mc.thePlayer.motionY = 0.00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001d;
                            mc.thePlayer.setSpeed(0.199999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999d);
                            count++;
                        }
                    } else {
                        mc.thePlayer.setSpeed(0f);
                    }
                    break;
                case "Verus":
                    mc.thePlayer.setSprinting(true);
                    if (mc.thePlayer.onGround && Movement.isMoving()) {
//                        mc.thePlayer.motionY = 0.4d;
                        mc.thePlayer.jump();
                        mc.thePlayer.setSpeed(0.4);
                    } else if (!mc.thePlayer.onGround && Movement.isMoving()) {
                        mc.thePlayer.setSpeed(0.36);
                        mc.thePlayer.speedInAir = 0.032f;
                    } else {
                        mc.thePlayer.speedInAir = 0.02f;
                        mc.thePlayer.setSpeed(0);
                    }
                    break;
            }
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
