package github.qe7.detect.module.impl.combat;

import java.text.DecimalFormat;
import java.util.concurrent.CopyOnWriteArrayList;

import github.qe7.detect.event.listeners.EventMotion;
import github.qe7.detect.module.Category;
import github.qe7.detect.setting.impl.SettingBoolean;
import github.qe7.detect.setting.impl.SettingMode;
import github.qe7.detect.util.Timer;
import github.qe7.detect.util.chat.AddChatMessage;
import github.qe7.detect.util.player.Distance;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import org.lwjgl.input.Keyboard;

import github.qe7.detect.event.Event;
import github.qe7.detect.module.Module;
import github.qe7.detect.setting.impl.SettingNumber;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;

public class Killaura extends Module {

    public SettingBoolean ab = new SettingBoolean("AutoBlock", false);
    public SettingMode rots = new SettingMode("Rotations", "Lock", "None");
	public SettingNumber cps = new SettingNumber("Attacks", 12, "#.#", 1, 20);
	public SettingNumber reach = new SettingNumber("Reach", 3.2, "#.#", 2, 6);
    public SettingNumber search = new SettingNumber("Search", 6.0, "#.#", 3, 10);
    public SettingBoolean player = new SettingBoolean("Player", true);
    public SettingBoolean monster = new SettingBoolean("Monster", false);
    public SettingBoolean animal = new SettingBoolean("Animal", false);
	
	public Killaura() {
		super("Killaura", Keyboard.KEY_R, Category.COMBAT);
		addSettings(ab, rots, cps, reach, search, player, monster, animal);
	} 

	private CopyOnWriteArrayList<Entity> entities = new CopyOnWriteArrayList<Entity>();
    private Timer timer = new Timer();
    public static Entity currentTarget;
    public Entity lastTarget;

    public void onEvent(Event e) {
    	setSuffix("Single");
    	if (e instanceof EventMotion && mc.thePlayer.ticksExisted > 40) {
            EventMotion event = (EventMotion) e;

            double x = event.getX();
            double y = event.getY();
            double z = event.getZ();

            CopyOnWriteArrayList<Entity> ent = AntiBot.getEntities();
            Entity target = getMainEntity(Distance.distanceSort(ent));
            if (target != null) {
                float[] rots = rotations(target, event);
                if (target.getDistance(x, y, z) <= (search.getValue())) {
                    currentTarget = target;

                    switch(this.rots.getCurrentValue()) {
                        case "None" :
                            break;
                        case "Lock" :
                                mc.thePlayer.renderYawOffset = rots[0];
                                event.yaw = rots[0];
                                event.pitch = rots[1];
                            break;
                    }
                } else {
                    currentTarget = null;
                }
                if (target.getDistance(x, y, z) <= reach.getValue() && timer.hasReached(1000 / (cps.getValue() + (Math.random() * 5)))) {
                    switch(this.rots.getCurrentValue()) {
                        case "None" :
                            break;
                        case "Lock" :
                            event.yaw = rots[0];
                            event.pitch = rots[1];
                            break;
                    }
                    mc.thePlayer.swingItem();
                    mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
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
	
	public static float[] rotations(Entity e, EventMotion p) {
        double x = e.posX + (e.posX - e.lastTickPosX) - p.getX();
        double y = (e.posY + e.getEyeHeight()) - (p.getY() + mc.thePlayer.getEyeHeight()) - 0.2;
        double z = e.posZ + (e.posZ - e.lastTickPosZ) - p.getZ();
        double dist = Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2));

        float yaw = (float) Math.toDegrees(-Math.atan(x / z));
        float pitch = (float) -Math.toDegrees(Math.atan(y / dist));

        if (x < 0 && z < 0)
            yaw = 90 + (float) Math.toDegrees(Math.atan(z / x));
        else if (x > 0 && z < 0)
            yaw = -90 + (float) Math.toDegrees(Math.atan(z / x));

        yaw += Math.random() * 6 - Math.random();
        pitch += Math.random() * 4 - Math.random();

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
