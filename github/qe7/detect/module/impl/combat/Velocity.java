package github.qe7.detect.module.impl.combat;

import github.qe7.detect.event.impl.EventPacket;
import github.qe7.detect.module.Category;
import github.qe7.detect.setting.impl.SettingMode;
import net.minecraft.network.play.server.S27PacketExplosion;

import github.qe7.detect.event.Event;
import github.qe7.detect.module.Module;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

public class Velocity extends Module {

	public SettingMode mode;

	public Velocity() {
		super("Velocity", 0, Category.COMBAT);
		mode = new SettingMode("mode", "Cancel");
		addSettings(mode);
	}
	
	public void onEvent(Event e) {
		setSuffix(mode.getCurrentValue());
		if (e instanceof EventPacket) {
			switch (mode.getCurrentValue()) {
				case "Cancel" :
					if (((EventPacket) e).getPacket() instanceof S12PacketEntityVelocity) {
						e.setCancelled(true);
					}
					if (((EventPacket) e).getPacket() instanceof S27PacketExplosion) {
						e.setCancelled(true);
					}
					break;
			}
		}
	}
}
