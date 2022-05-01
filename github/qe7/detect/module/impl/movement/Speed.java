package github.qe7.detect.module.impl.movement;

import github.qe7.detect.event.Event;
import github.qe7.detect.event.listeners.EventMotion;
import github.qe7.detect.event.listeners.EventSlowDown;
import github.qe7.detect.module.Category;
import github.qe7.detect.module.Module;
import github.qe7.detect.setting.impl.SettingMode;
import github.qe7.detect.setting.impl.SettingNumber;
import github.qe7.detect.util.player.Movement;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

import java.util.List;

public class Speed extends Module {

    public SettingMode mode;
    public SettingNumber speed;

    public Speed() {
        super("Speed", 0, Category.MOVEMENT);
        mode = new SettingMode("Mode", "Vanilla", "Ncp", "Test");
        speed = new SettingNumber("Speed", 0.4, "#.##", 0.1, 1);
        addSettings(mode, speed);
    }

    public static int stage;
    private double moveSpeed;
    private double lastDist;

    public void onEnable() {
        if (Speed.mc.thePlayer != null) {
            this.moveSpeed = getBaseMoveSpeed();
            Speed.stage = 2;
        }
    }

    public void onDisable() {
        Speed.mc.timer.timerSpeed = 1.0f;
    }

    public void onEvent(Event e) {
        if (e instanceof EventMotion) {
            final EventMotion em = (EventMotion) e;
            setSuffix(mode.getCurrentValue());
            switch(mode.getCurrentValue()) {
                case "Vanilla" :
                    if (Movement.isMoving()) {
                        if (mc.thePlayer.onGround) {
                            mc.thePlayer.jump();
                        }
                        mc.thePlayer.setSpeed(speed.getValue());
                    }
                    break;
                case "Ncp" :
                    if (Movement.isMovingForward()) {
                        if (mc.thePlayer.onGround)
                            mc.thePlayer.jump();

                        if (mc.thePlayer.onGround)
                            mc.thePlayer.setSpeed(speed.getValue());
                    }
                    break;
                case "Test" :
                    if ((mc.thePlayer.onGround || stage == 3)) {
                        if ((!mc.thePlayer.isCollidedHorizontally && mc.thePlayer.moveForward != 0.0f) || mc.thePlayer.moveStrafing != 0.0f) {
                            if (stage == 2) {
                                this.moveSpeed *= 2.149;
                                stage = 3;
                            }
                            else if (stage == 3) {
                                stage = 2;
                                final double difference = 0.66 * (this.lastDist - getBaseMoveSpeed());
                                this.moveSpeed = this.lastDist - difference;
                            }
                            else {
                                final List collidingList = mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.boundingBox.offset(0.0, mc.thePlayer.motionY, 0.0));
                                if (collidingList.size() > 0 || mc.thePlayer.isCollidedVertically) {
                                    stage = 1;
                                }
                            }
                        }
                        else {
                            mc.timer.timerSpeed = 1.0f;
                        }
                        this.moveSpeed = Math.max(this.moveSpeed, getBaseMoveSpeed());
                        double forward = mc.thePlayer.movementInput.moveForward;
                        double strafe = mc.thePlayer.movementInput.moveStrafe;
                        float yaw = mc.thePlayer.rotationYaw;
                        if (forward == 0.0 && strafe == 0.0) {
                            mc.thePlayer.setSpeed(0.0);
                        } else {
                            double sin = Math.sin(Math.toRadians(yaw + 90.0f));
                            double cos = Math.cos(Math.toRadians(yaw + 90.0f));
                            Speed.mc.thePlayer.motionX = forward * this.moveSpeed * cos + strafe * this.moveSpeed * sin;
                            Speed.mc.thePlayer.motionZ = forward * this.moveSpeed * sin - strafe * this.moveSpeed * cos;
                            mc.thePlayer.jump();
                        }
                    }
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

}
