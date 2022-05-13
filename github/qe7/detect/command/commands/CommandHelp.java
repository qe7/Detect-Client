package github.qe7.detect.command.commands;

import github.qe7.detect.Detect;
import github.qe7.detect.command.Command;
import github.qe7.detect.util.chat.AddChatMessage;

public class CommandHelp extends Command {

    public CommandHelp() {
        super("Help", "List of commands", "help", "h");
    }

    @Override
    public void onCommand(String[] args, String command) {
        AddChatMessage.addChatMessage("List of commands:");
        for (Command c : Detect.i.commandManager.commands) {
            AddChatMessage.addChatMessage(String.format("- %s, %s.", c.name, c.description));
        }
    }
}
