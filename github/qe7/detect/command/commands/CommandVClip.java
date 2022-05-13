package github.qe7.detect.command.commands;

import github.qe7.detect.command.Command;
import github.qe7.detect.util.Util;
import github.qe7.detect.util.chat.AddChatMessage;

public class CommandVClip extends Command {

    public CommandVClip() {
        super("Vclip", "Clips u you a chad", "vclip <int>", "v");
    }

    @Override
    public void onCommand(String[] args, String command) {
        if (args.length > 0) {
            AddChatMessage.addChatMessage("Vclipped " + Float.parseFloat(args[0]) + " Blocks");
            Util.mc.thePlayer.setPosition(Util.mc.thePlayer.posX, Util.mc.thePlayer.posY + Float.parseFloat(args[0]), Util.mc.thePlayer.posZ);
        } else {
            AddChatMessage.addChatMessage(".vclip <amount>");
        }
    }
}
