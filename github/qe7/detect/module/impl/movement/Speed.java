package github.qe7.detect.module.impl.movement;

import github.qe7.detect.event.Event;
import github.qe7.detect.event.listeners.EventMotion;
import github.qe7.detect.module.Category;
import github.qe7.detect.module.Module;
import github.qe7.detect.setting.impl.SettingMode;
import github.qe7.detect.setting.impl.SettingNumber;
import github.qe7.detect.util.Mathutil;
import github.qe7.detect.util.Timer;
import github.qe7.detect.util.player.Movement;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.potion.Potion;
import net.minecraft.util.ChatComponentText;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Speed extends Module {

    public SettingMode mode;
    public SettingNumber speed;
    public Timer timer = new Timer();
    private Timer timer2 = new Timer();
    public int count = 0;
    public double moveSpeed;
    public int state;
    private boolean reachedDelay = false;
    private double lastDist;
    private int cooldownHops;
    private double groundPos = 0;
    private float bhopspeed;
    private Mathutil mathutil = new Mathutil();

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
        mc.thePlayer.stepHeight = 0.6f;
        mc.thePlayer.jumpMovementFactor = 0.02f;
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

                case "NCPHop": {
                    if (e instanceof EventMotion) {
                        if (Movement.isMoving()) {
                            /*if(!reachedDelay) {
                                if (timer.hasReached(100)) {
                                    reachedDelay = true;
                                    timer.reset();
                                }
                            }else{
                                if (!timer.hasReached(30)) {
                                    Movement.setFriction(2);
                                }else{
                                    reachedDelay = false;
                                    timer.reset();
                                }
                            }*/
                            if(mc.thePlayer.onGround){
                                groundPos = mc.thePlayer.posY;
                                mc.thePlayer.jump();
                                bhopspeed = mathutil.getRandomFloatBetween(0.3f, 0.37f);
                                Movement.setFriction(1.2f);
                            }else{
                                bhopspeed = 0.28f + mathutil.getRandomFloatBetween(-0.04f, 0.02f);
                                mc.thePlayer.jumpMovementFactor = 0.025f;

                                if(mc.thePlayer.fallDistance == 0){
                                    bhopspeed = mathutil.getRandomFloatBetween(0.27f, 0.3f);
                                    mc.thePlayer.jumpMovementFactor = mathutil.getRandomFloatBetween(0.023f, 0.026f);
                                }

                                if(mc.thePlayer.fallDistance > 0.3){
                                    mc.thePlayer.motionY -= 0.04f;
                                    bhopspeed = 0.27f + mathutil.getRandomFloatBetween(-0.02f, 0.02f);
                                }
                                if(mc.thePlayer.fallDistance > 0.7){
                                    mc.thePlayer.motionY -= 0.08f;
                                    bhopspeed = 0.23f;
                                }
                            }
                            mc.thePlayer.setSpeed(bhopspeed);
                        }
                    }
                    break;
                }

                case "NCPYPort":
                    if (e instanceof EventMotion) {
                        if (Movement.isMoving()) {
                            if (mc.thePlayer.onGround) {
                                mc.thePlayer.jump();
                                mc.thePlayer.setSpeed(0.664232f);
                            }else{
                             mc.thePlayer.motionY -= 1;
                            }
                        }
                    }
                    break;

                case "FakeJump":
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
