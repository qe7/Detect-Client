package github.qe7.detect.command.commands;

import github.qe7.detect.Detect;
import github.qe7.detect.command.Command;
import github.qe7.detect.module.Module;
import github.qe7.detect.util.chat.AddChatMessage;

public class CommandToggle extends Command {

    public CommandToggle() {
        super("Toggle", "Toggles a module.", "toggle <name>","t");
    }

    @Override
    public void onCommand(String[] args, String commmand) {
        String moduleName = args[0];

        for (Module module : Detect.i.moduleManager.modules) {
            if (module.name.equalsIgnoreCase(moduleName)) {
                module.toggle();
                break;
            }
        }
    }
}
