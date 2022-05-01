package github.qe7.detect.util.player;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;

public class Distance {
	
	public static CopyOnWriteArrayList<Entity> distanceSort(CopyOnWriteArrayList<Entity> distenceList) {
		distenceList.sort(Comparator.comparingDouble(e -> Minecraft.getMinecraft().thePlayer.getDistanceToEntity((e))));
		return distenceList;
	}

}
