package github.qe7.detect.module.impl.combat;

import github.qe7.detect.event.Event;
import github.qe7.detect.event.listeners.EventMotion;
import github.qe7.detect.module.Category;
import github.qe7.detect.module.Module;
import github.qe7.detect.setting.impl.SettingBoolean;
import github.qe7.detect.setting.impl.SettingNumber;
import github.qe7.detect.util.Timer;
import github.qe7.detect.util.player.Movement;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.sql.Time;

public class AutoPot extends Module {

    public SettingBoolean regen;
    public SettingNumber health, delay;
    public static boolean potting;
    public static int haltTicks;
    public Timer timer = new Timer();

    public AutoPot() {
        super("AutoPot", 0, Category.COMBAT);
        regen = new SettingBoolean("Regen", true);
        health = new SettingNumber("Health", 12, "#.", 2, 20);
        delay = new SettingNumber("Delay", 800, "##.", 10, 1000);
        addSettings(regen, health, delay);
    }

    public void onEnable() {}

    public void onDisable() {}

    public void onEvent(Event event) {

        setSuffix("");

        if (event instanceof EventMotion) {
            EventMotion e = (EventMotion) event;
            float j = health.getValue().floatValue();

            if (potting && haltTicks < 0) {
                potting = false;
            }

            if (Movement.isMoving()) {
                if (mc.thePlayer.getHealth() <= j && this.getPotionFromInv() != -1 && this.timer.hasReached(delay.getValue().longValue())) {
                    haltTicks = 6;
                    this.swap(this.getPotionFromInv(), 6);
                    potting = true;
                    this.timer.reset();
                }
            } else if (mc.thePlayer.getHealth() <= j && this.getPotionFromInv() != -1 && this.timer.hasReached(delay.getValue().longValue()) && haltTicks < 0 && mc.thePlayer.isCollidedVertically) {
                mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, 90.0f, true));
                this.swap(this.getPotionFromInv(), 6);
                mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(6));
                mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
                mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                haltTicks = 5;
                potting = true;
            }

            --haltTicks;

            if (e.isPost() && potting) {
                if (Movement.isMoving()) {
                    mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(6));
                    mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
                    mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                }
                this.timer.reset();
            }
        }
    }

    protected void swap(final int slot, final int hotbarNum) {
        mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, hotbarNum, 2, mc.thePlayer);
    }

    private int getPotionFromInv() {
        int pot = -1;
        for (int i = 0; i < 45; ++i) {
            if (Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getStack();
                final Item item = is.getItem();
                if (item instanceof ItemPotion) {
                    final ItemPotion potion = (ItemPotion)item;
                    if (potion.getEffects(is) != null) {
                        for (final Object o : potion.getEffects(is)) {
                            final PotionEffect effect = (PotionEffect)o;
                            if ((effect.getPotionID() == Potion.heal.id || (effect.getPotionID() == Potion.regeneration.id && regen.getValue() && !AutoPot.mc.thePlayer.isPotionActive(Potion.regeneration))) && ItemPotion.isSplash(is.getItemDamage())) {
                                pot = i;
                            }
                        }
                    }
                }
            }
        }
        return pot;
    }
}
