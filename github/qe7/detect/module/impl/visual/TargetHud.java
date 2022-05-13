package github.qe7.detect.module.impl.visual;

import github.qe7.detect.Detect;
import github.qe7.detect.event.Event;
import github.qe7.detect.event.listeners.EventRender2D;
import github.qe7.detect.module.Category;
import github.qe7.detect.module.Module;
import github.qe7.detect.module.impl.combat.Killaura;
import github.qe7.detect.setting.impl.SettingMode;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

public class TargetHud extends Module {

    public SettingMode mode;

    public TargetHud() {
        super("TargetHud", 0, Category.VISUAL);
        mode = new SettingMode("Mode", "Astolfy");
        addSettings(mode);
    }

    public void onEvent(Event event) {

        setSuffix(mode.getCurrentValue());

        if (event instanceof EventRender2D) {
            if (Detect.i.moduleManager.getModuleByName("Killaura").isToggled()) {
                renderTargetHud((EntityLivingBase) Killaura.currentTarget);
            }
        }
    }

    private void renderTargetHud(EntityLivingBase currentTarget) {
        if (currentTarget instanceof EntityPlayer) {
            Gui.drawRect(0, 100 + 0, 150, 100 + 50, 0x80000000);
            if (mc.getNetHandler() != null && currentTarget.getUniqueID() != null) {
                NetworkPlayerInfo i = mc.getNetHandler().getPlayerInfo(currentTarget.getUniqueID());
                if (i != null) {
                    mc.getTextureManager().bindTexture(i.getLocationSkin());
                    GlStateManager.color(1, 1, 1);
                    GL11.glEnable(GL11.GL_BLEND);
                    Gui.drawModalRectWithCustomSizedTexture(5.0, 100 + 5.0, (float) (50f/1.25), (float) (50f/1.25), (int) (50/1.25), (int) (50/1.25), (float) (400/1.25), (float) (400 / 1.25));
                    GL11.glDisable(GL11.GL_BLEND);
                }
            }
            if(currentTarget.getName().length() >= 2) {
                mc.fontRendererObj.drawString(currentTarget.getName().substring(0, 1), 50, 100 +10, -1);
                mc.fontRendererObj.drawString(currentTarget.getName().substring(1), 50 + mc.fontRendererObj.getStringWidth(currentTarget.getName().substring(0, 1)), 100 +10, -1);
            }
            mc.fontRendererObj.drawString(""+Math.floor(currentTarget.getHealth()) /2 + "\2477HP", 50, 100 +25, -1);
        }
    }
}
