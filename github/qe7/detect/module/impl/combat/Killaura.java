package github.qe7.detect.module.impl.combat;

import java.util.concurrent.CopyOnWriteArrayList;

import github.qe7.detect.Detect;
import github.qe7.detect.event.listeners.EventMotion;
import github.qe7.detect.friend.Friend;
import github.qe7.detect.friend.FriendManager;
import github.qe7.detect.module.Category;
import github.qe7.detect.setting.impl.SettingBoolean;
import github.qe7.detect.setting.impl.SettingMode;
import github.qe7.detect.util.Timer;
import github.qe7.detect.util.player.Distance;
import github.qe7.detect.util.player.Rotation;
import net.minecraft.item.ItemSword;

import github.qe7.detect.event.Event;
import github.qe7.detect.module.Module;
import github.qe7.detect.setting.impl.SettingNumber;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.input.Mouse;

public class Killaura extends Module {

    public static SettingMode mode = new SettingMode("Mode", "Single", "Switch");
    public static SettingBoolean ab = new SettingBoolean("AutoBlock", true);
    public static SettingMode abMode = new SettingMode("Mode", "NCP", "Legit", "Fake");
    public SettingBoolean rot = new SettingBoolean("Rotations", true);
    public SettingMode rotMode = new SettingMode("Mode", "Static", "Jitter");
    public static SettingNumber jitter = new SettingNumber("Jitter", 4, "#.", 2, 10);
    public SettingNumber fov = new SettingNumber("Fov", 180, "#.", 1, 180);
	public SettingNumber cps = new SettingNumber("Attacks", 12, "#.#", 1, 20);
	public SettingNumber reach = new SettingNumber("Reach", 3.0, "#.#", 3, 6);
    public SettingNumber search = new SettingNumber("Search", 4.0, "#.#", 3, 10);
    public SettingNumber block = new SettingNumber("Block", 4.0, "#.#", 3, 10);
    public SettingBoolean player = new SettingBoolean("Player", true);
    public SettingBoolean monster = new SettingBoolean("Monster", false);
    public SettingBoolean animal = new SettingBoolean("Animal", false);
	
	public Killaura() {
		super("Killaura", 0, Category.COMBAT);
		addSettings(mode, ab, abMode, rot, rotMode, jitter, fov, cps, reach, search, block, player, monster, animal);
	} 

	private CopyOnWriteArrayList<Entity> entities = new CopyOnWriteArrayList<Entity>();
    private Timer timer = new Timer();
    private Timer update = new Timer();
    public static Entity currentTarget;
    public Entity lastTarget;
    public static boolean hasTarget;

    public void onDisable() {
        hasTarget = false;
        mc.gameSettings.keyBindUse.pressed = Mouse.isButtonDown(1);
    }

    public void onEvent(Event e) {

        setSuffix(mode.getCurrentValue());

    	if (e instanceof EventMotion && mc.thePlayer.ticksExisted > 40) {
            EventMotion event = (EventMotion) e;

            double x = event.getX();
            double y = event.getY();
            double z = event.getZ();

            CopyOnWriteArrayList<Entity> ent = AntiBot.getEntities();
            Entity target = getMainEntity(Distance.distanceSort(ent));



            if (target != null) {

                if (Detect.i.friendManager.isFriend(target.getName()))
                    return;

                float[] jit = jitterRotations(target, event);
                float[] sta = staticRotations(target, event);

                if (this.isInFOV((EntityLivingBase) target) && target.getDistance(x, y, z) <= (search.getValue())) {
                    hasTarget = true;
                    currentTarget = target;

                    if (rot.getValue()) {
                        switch (this.rotMode.getCurrentValue()) {
                            case "Jitter":
                                mc.thePlayer.renderYawOffset = jit[0];
                                event.yaw = jit[0];
                                event.pitch = jit[1];
                                break;
                            case "Static":
                                mc.thePlayer.renderYawOffset = sta[0];
                                event.yaw = sta[0];
                                event.pitch = sta[1];
                                break;
                        }
                    }

                    if (ab.getValue() && target.getDistance(x, y, z) <= block.getValue()) {
                        switch (this.abMode.getCurrentValue()) {
                            case "NCP":
                                if (Killaura.mc.thePlayer.inventory.getCurrentItem() != null && Killaura.mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword) {
                                    Killaura.mc.thePlayer.setItemInUse(Killaura.mc.thePlayer.inventory.getCurrentItem(), 71999);
                                }
                                break;
                            case "Legit" :
                                if (Killaura.mc.thePlayer.inventory.getCurrentItem() != null && Killaura.mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword) {
                                    mc.gameSettings.keyBindUse.pressed = true;
                                }
                                break;
                        }
                    }
                } else {
                    currentTarget = null;
                    hasTarget = false;
                    if (ab.getValue()) {
                        switch (this.abMode.getCurrentValue()) {
                            case "Legit" :
                                mc.gameSettings.keyBindUse.pressed = Mouse.isButtonDown(1);
                                break;
                        }
                    }
                }
                if (this.isInFOV((EntityLivingBase) target) && target.getDistance(x, y, z) <= reach.getValue() && timer.hasReached(1000 / (cps.getValue() + (Math.random() * 5)))) {
                    if (rot.getValue()) {
                        switch (this.rotMode.getCurrentValue()) {
                            case "Jitter":
                                event.yaw = jit[0];
                                event.pitch = jit[1];
                                break;
                            case "Static":
                                event.yaw = sta[0];
                                event.pitch = sta[1];
                                break;
                        }
                    }

                    mc.thePlayer.swingItem();
//                    mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
                    mc.playerController.attackEntity(mc.thePlayer, target);
                    timer.reset();
                }
            } else {
                currentTarget = null;
            }
    	}
    }

	public Entity getMainEntity(CopyOnWriteArrayList<Entity> entities) {
        if (entities.size() > 0)
            for (Entity ent : entities) {
                if (ent != mc.thePlayer && ent instanceof EntityLivingBase && ent.isEntityAlive()) {
                    if (ent instanceof EntityPlayer && this.player.getValue()) {
                        return ent;
                    }
                    
                    if (ent instanceof EntityMob && this.monster.getValue()) {
                        return ent;
                    }
                    
                    if (ent instanceof EntityAnimal && this.animal.getValue()) {
                        return ent;
                    }
                }
            }
        return null;
    }

    private boolean isInFOV(final EntityLivingBase entity) {
        final int j = fov.getValue().intValue();
        return Math.abs(Rotation.getYawChange(entity.posX, entity.posZ)) <= j && Math.abs(Rotation.getPitchChange(entity, entity.posY)) <= j;
    }

	public static float[] jitterRotations(Entity e, EventMotion p) {
        double x = e.posX + (e.posX - e.lastTickPosX) - p.getX();
        double y = (e.posY + e.getEyeHeight()) - (p.getY() + mc.thePlayer.getEyeHeight()) - 0.8;
        double z = e.posZ + (e.posZ - e.lastTickPosZ) - p.getZ();
        double dist = Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2));

        float yaw = (float) Math.toDegrees(-Math.atan(x / z));
        float pitch = (float) -Math.toDegrees(Math.atan(y / dist));

        if (x < 0 && z < 0) {
            yaw = 90 + (float) Math.toDegrees(Math.atan(z / x));
        } else if (x > 0 && z < 0) {
            yaw = -90 + (float) Math.toDegrees(Math.atan(z / x));
        }

        yaw += Math.random() * jitter.getValue() - Math.random();
        pitch += Math.random() * jitter.getValue() - Math.random();

        if (pitch > 90)
            pitch = 90;
        if (pitch < -90)
            pitch = -90;
        if (yaw > 180)
            yaw = 180;
        if (yaw < -180)
            yaw = -180;

        return new float[]{yaw, pitch};
    }

    public static float[] staticRotations(Entity e, EventMotion p) {
        double x = e.posX + (e.posX - e.lastTickPosX) - p.getX();
        double y = (e.posY + e.getEyeHeight()) - (p.getY() + mc.thePlayer.getEyeHeight()) - 0.8;
        double z = e.posZ + (e.posZ - e.lastTickPosZ) - p.getZ();
        double dist = Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2));

        float yaw = (float) Math.toDegrees(-Math.atan(x / z));
        float pitch = (float) -Math.toDegrees(Math.atan(y / dist));

        if (x < 0 && z < 0)
            yaw = 90 + (float) Math.toDegrees(Math.atan(z / x));
        else if (x > 0 && z < 0)
            yaw = -90 + (float) Math.toDegrees(Math.atan(z / x));

        if (x == mc.thePlayer.posX && y == mc.thePlayer.posY) {
            pitch = 89f;
        }

        if (pitch > 90)
            pitch = 90;
        if (pitch < -90)
            pitch = -90;
        if (yaw > 180)
            yaw = 180;
        if (yaw < -180)
            yaw = -180;

        return new float[]{yaw, pitch};
    }
}
