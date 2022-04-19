package github.qe7.detect.util;

import net.minecraft.util.ChatComponentText;

public class ChatUtil extends Util {

    public void addChatMessagePrefix(String msg) {
        mc.thePlayer.addChatMessage(new ChatComponentText("\u00A77[\u00A75Detect\u00A77] " + msg));
    }

    public void addChatMessageNoPrefix(String msg) {
        mc.thePlayer.addChatMessage(new ChatComponentText("\u00A77" + msg));
    }

}
