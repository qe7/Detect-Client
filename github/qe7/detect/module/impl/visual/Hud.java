package github.qe7.detect.module.impl.visual;

import github.qe7.detect.Detect;
import github.qe7.detect.event.Event;
import github.qe7.detect.event.listeners.EventRender2D;
import github.qe7.detect.module.Category;
import github.qe7.detect.module.Module;
import github.qe7.detect.setting.impl.SettingBoolean;
import github.qe7.detect.setting.impl.SettingMode;
import github.qe7.detect.setting.impl.SettingNumber;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.multiplayer.ServerAddress;

import javax.print.attribute.SetOfIntegerSyntax;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;

public class Hud extends Module {

    public SettingBoolean bps, fps, cords, build, potionhud, watermark, arraylist, welcome, server;
    public SettingMode arraylistMode;
    public static SettingMode arraylistBrackets;
    public static SettingNumber r;
    public static SettingNumber g;
    public static SettingNumber b;

    public Hud() {
        super("Hud", 0, Category.VISUAL);
        setToggled(true);
        arraylistBrackets = new SettingMode("Suffix", "None", "[...]", "(...)", "{...}", "<...>", "|...");
        arraylistMode = new SettingMode("Array", "Static", "Rainbow", "Wave");
        potionhud = new SettingBoolean("Potion", true);
        build = new SettingBoolean("Build", true);
        cords = new SettingBoolean("Coords", true);
        fps = new SettingBoolean("fps", true);
        bps = new SettingBoolean("bps", true);
        server = new SettingBoolean("Server IP", true);
        watermark = new SettingBoolean("Watermark", true);
        welcome = new SettingBoolean("Welcomer", true);
        arraylist = new SettingBoolean("Arraylist", true);
        r = new SettingNumber("r", 50, "#.", 0, 255);
        g = new SettingNumber("g", 100, "#.", 0, 255);
        b = new SettingNumber("b", 255, "#.", 0, 255);
        addSettings(watermark, welcome, arraylist, arraylistMode, potionhud, build, cords, fps, bps, r, g, b);
    }

    public static class sortDefaultFont implements Comparator<Module> {
        @Override
        public int compare(Module arg0, Module arg1) {
            if (Minecraft.getMinecraft().fontRendererObj.getStringWidth(arg0.getName() + (arg0.getSuffix().length() > 1 ? (" [" + arg0.getSuffix()) : "]")) > Minecraft.getMinecraft().fontRendererObj.getStringWidth(arg1.getName() + (arg1.getSuffix().length() > 1 ? (" [" + arg1.getSuffix()) : "]"))) {
                return -1;
            } else if (Minecraft.getMinecraft().fontRendererObj.getStringWidth(arg0.getName() + (arg0.getSuffix().length() > 1 ? (" [" + arg0.getSuffix()) : "]")) < Minecraft.getMinecraft().fontRendererObj.getStringWidth(arg1.getName() + (arg1.getSuffix().length() > 1 ? (" [" + arg1.getSuffix()) : "]")))
                return 1;
            return 0;
        }
    }

    public static Color getColor() {
        if (Cape.getCape() == "mouseware" && Detect.i.moduleManager.getModuleByName("Cape").isToggled()) {
            return new Color(0xfc99ff);
        }
        else {
            return new Color(r.getValue().intValue(), g.getValue().intValue(), b.getValue().intValue(), 255);
        }
    }

    public void onEvent(Event event) {
        if (event instanceof EventRender2D) {
            CopyOnWriteArrayList<Module> modules = Detect.i.moduleManager.getModules();
            String timeStamp = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
            EventRender2D e = (EventRender2D) event;
            FontRenderer font = mc.fontRendererObj;
            Collections.sort(modules, new sortDefaultFont());

            if (watermark.getValue()) {
                if (Cape.getCape() == "mouseware" && Detect.i.moduleManager.getModuleByName("Cape").isToggled()) {
                    font.drawStringWithShadow("M§fouseware §7[§f" + timeStamp + "§7]", 4, 4, getColor().getRGB());
                }
                else if (Cape.getCape() == "exhicape" && Detect.i.moduleManager.getModuleByName("Cape").isToggled()){
                    font.drawStringWithShadow("E§fxhibition §7[§f" + timeStamp + "§7]", 4, 4, getColor().getRGB());
                }
                else {
                    font.drawStringWithShadow("D§fefect §7[§f" + timeStamp + "§7]", 4, 4, getColor().getRGB());
                }
            }

            if (welcome.getValue()) {
                font.drawStringWithShadow("User - §f" + Detect.i.author, e.getWidth() -2 - (font.getStringWidth("User - " + Detect.i.author)), e.getHeight() - 10, getColor().getRGB());
            }

            int j = cords.getValue() ? 2 : 1;
            int i = cords.getValue() ? fps.getValue() ? 3 : 2 : fps.getValue() ? 2 : 1;

            if (bps.getValue()) {
                String blockspersecond = String.format("%.2f", Math.hypot(mc.thePlayer.posX - mc.thePlayer.prevPosX, mc.thePlayer.posZ - mc.thePlayer.prevPosZ) * mc.timer.timerSpeed * 20.0D);
                font.drawStringWithShadow("Bps§7: §f" + blockspersecond, 4, ((EventRender2D) e).getHeight() - 4 - (font.FONT_HEIGHT * i), getColor().getRGB());
            }

            if (fps.getValue()) {
                font.drawStringWithShadow("Fps§7: §f" + Minecraft.getDebugFPS(), 4, ((EventRender2D) e).getHeight() - 4 - (font.FONT_HEIGHT * j), getColor().getRGB());
            }

            String ip = "";

            if (mc.isSingleplayer()) {
                ip = "Singleplayer";
            } else {
                ip = mc.getCurrentServerData().serverIP;
            }

            if (server.getValue()) {
                font.drawStringWithShadow("ServerIP - §f" + ip, e.getWidth() -2 - (font.getStringWidth("ServerIP - " + ip)), e.getHeight() - 20, getColor().getRGB());
            }

            if (cords.getValue()) {
                font.drawStringWithShadow("Xyz§7: §f" + new DecimalFormat("#.#").format(mc.thePlayer.posX) + ", " + new DecimalFormat("#.#").format(mc.thePlayer.posY) + ", " + new DecimalFormat("#.#").format(mc.thePlayer.posZ), 4, ((EventRender2D) e).getHeight() - 4 - font.FONT_HEIGHT, getColor().getRGB());
            }

            int count = 0;
            if (arraylist.getValue()) {
                for (Module module : modules) {
                    if (module.isToggled() && module.drawn.getValue()) {

                        String text = module.getName() + (module.getSuffix().length() > 0 ? " §f" + module.getSuffix() + "" : "");

                        double x = ((EventRender2D) e).getWidth() - font.getStringWidth(text) - 8;
                        double y = 4 + ((font.FONT_HEIGHT + 1) * count);

                        font.drawStringWithShadow(text, x + 2, y + 1, getColor().getRGB());
                        count++;
                    }
                }
            }
        }
    }
}
