package github.qe7.detect.module.impl.player;

import github.qe7.detect.event.Event;
import github.qe7.detect.event.listeners.EventUpdate;
import github.qe7.detect.module.Category;
import github.qe7.detect.module.Module;
import github.qe7.detect.setting.impl.SettingNumber;
import github.qe7.detect.util.Timer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.Slot;
import net.minecraft.item.*;

import java.util.ArrayList;

public class InvManager extends Module {

    public SettingNumber moveDelay = new SettingNumber("Delay", 50, "#.", 5, 150);

    public Timer timer = new Timer();
    public int sec = 0;

    public InvManager() {
        super("InventoryManager", 0, Category.PLAYER);
    }

    ArrayList<Slot> sortedSwords;

    Slot lastSwordSlot = null;
    public void onEvent(Event event) {
        int swords = 0;
        if(event instanceof EventUpdate && mc.currentScreen instanceof GuiInventory) {
            for(Slot s : mc.thePlayer.inventoryContainer.inventorySlots) {
                if(s.getStack() != null && timer.hasReached(moveDelay.getValue())) {
                    Slot fuckYou = s;
                    if(s.getStack().getItem() instanceof ItemSword) {
                        //System.out.println(swordCompare(((ItemSword)s.getStack().getItem()),(ItemSword)lastSwordSlot));

                        if(lastSwordSlot != null && lastSwordSlot.getStack() != null
                                && s != lastSwordSlot && lastSwordSlot.getStack().getItem() instanceof ItemSword) {

                            if(swordCompare((ItemSword)s.getStack().getItem(), (ItemSword)lastSwordSlot.getStack().getItem(), s.getStack(),lastSwordSlot.getStack())) {
                                dropItem(s);

                                timer.reset();
                            }
                        }else {
                            moveItem(s.slotNumber, 0);
                            this.lastSwordSlot = s;
                        }
                    }
                    else
                    if(isBad(s.getStack()))
                        dropItem(s);
                }
            }
        }
    }

    public float swordMaterial(ItemSword sword, ItemStack stack) {
        float mat = 0;
        switch(sword.getToolMaterialName()) {

            case "WOOD":
                mat = 0;
                break;
            case "GOLD":
                mat = 1;
                break;
            case "STONE":
                mat = 1;
                break;
            case "IRON":
                mat = 2;
                break;
            case "EMERALD":
                mat = 3;
                break;
        }
        mat+= EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack) * 1.25f;
        return mat;
    }

    public boolean swordCompare(ItemSword s1, ItemSword s2, ItemStack st1, ItemStack st2) {
        return swordMaterial(s1, st1) <= swordMaterial(s2, st2);
    }

    public void moveItem(int slot, int nextSlot) {
        mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, nextSlot, 2, mc.thePlayer);
    }

    public void dropItem(Slot slot) {
        mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot.slotNumber, 1, 4, mc.thePlayer);
    }

    private boolean isBad(ItemStack stack) {
        Item i = stack.getItem();
        return !(i instanceof ItemBlock
                || i instanceof ItemSword || i instanceof ItemTool
                || i instanceof ItemFood || i instanceof ItemArmor);
    }

}
