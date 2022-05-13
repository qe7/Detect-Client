package github.qe7.detect.command;

import github.qe7.detect.command.commands.CommandFriend;
import github.qe7.detect.command.commands.CommandHelp;
import github.qe7.detect.command.commands.CommandToggle;
import github.qe7.detect.command.commands.CommandVClip;
import github.qe7.detect.event.listeners.EventMessage;
import github.qe7.detect.util.chat.AddChatMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager {

    public CommandManager() {
        setup();
    }

    public List<Command> commands = new ArrayList<Command>();
    public String prefix = ".";

    public void setup() {
        commands.add(new CommandToggle());
        commands.add(new CommandHelp());
        commands.add(new CommandFriend());
        commands.add(new CommandVClip());
    }

    public void handleChat(EventMessage event) {
        String message = event.getMessage();

        if (!message.startsWith(prefix)) {
            return;
        }

        message = message.substring(prefix.length());

        boolean foundCommand = false;

        if (message.split(" ").length > 0) {
            String commandName = message.split(" ")[0];

            for (Command c : commands) {
                if (c.aliases.contains(commandName) || c.name.equalsIgnoreCase(commandName)) {
                    c.onCommand(Arrays.copyOfRange(message.split(" "), 1, message.split(" ").length), message);
                    event.setCancelled(true);
                    foundCommand = true;
                    break;
                }
            }
        }

        if (!foundCommand) {
            AddChatMessage.addChatMessage("No command found.");
        }

    }

}
