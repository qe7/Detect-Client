package github.qe7.detect.util.chat;

import github.qe7.detect.Detect;
import github.qe7.detect.util.Util;
import net.minecraft.util.ChatComponentText;

public class AddChatMessage {

    public static void addChatMessage(String msg) {
        Util.mc.thePlayer.addChatMessage(new ChatComponentText(Detect.i.prefix + " " + msg));
    }

}
