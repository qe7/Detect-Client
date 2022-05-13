package github.qe7.detect.command.commands;

import github.qe7.detect.Detect;
import github.qe7.detect.command.Command;
import github.qe7.detect.friend.Friend;
import github.qe7.detect.util.chat.AddChatMessage;

import java.text.MessageFormat;

public class CommandFriend extends Command {

    public CommandFriend() {
        super("Friend", "Adds friends", "friend add <name>", "f");
    }

    @Override
    public void onCommand(String[] args, String command) {
        if (args.length > 1) {
            switch (args[0].toLowerCase()) {
                case "add": {
                    if (args.length == 3) {
                        Detect.i.friendManager.addFriend(new Friend(args[1], args[2]));
                        AddChatMessage.addChatMessage(MessageFormat.format("Added friend {0} as {1}", args[1], args[2]));
                    } else {
                        Detect.i.friendManager.addFriend(new Friend(args[1], args[1]));
                        AddChatMessage.addChatMessage(MessageFormat.format("Added friend {0}", args[1]));
                    }
                    break;
                }
                case "del": case "delete": case "remove": {
                    Detect.i.friendManager.removeFriend(args[1]);
                    AddChatMessage.addChatMessage(MessageFormat.format("Removed friend {0}", args[1]));
                    break;
                }
            }
        } else if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "clear": {
                    Detect.i.friendManager.clearFriends();
                    AddChatMessage.addChatMessage(MessageFormat.format("You now have {0} friends", Detect.i.friendManager.getFriends().size()));
                    break;
                }
                case "list": {
                    Detect.i.friendManager.getFriends().forEach(friend -> AddChatMessage.addChatMessage(MessageFormat.format(" - {0} \2477({1})", friend.getName(), friend.getDisplayName())));
                    break;
                }
            }
        }
    }
}
