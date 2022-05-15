package github.qe7.detect;

import com.google.gson.Gson;
import github.qe7.detect.command.CommandManager;
import github.qe7.detect.friend.FriendManager;
import github.qe7.detect.module.ModuleManager;
import github.qe7.detect.setting.SettingManager;
import github.qe7.detect.util.client.DLogger;

public class Detect {

    public static Detect i = new Detect();
    public String
            name = "Detect",
            version = "1.0",
            prefix = "§7[§4D§7]§r",
            author = "Admin";
    public ModuleManager moduleManager = new ModuleManager();
    public SettingManager settingManager = new SettingManager();
    public FriendManager friendManager = new FriendManager();
    public CommandManager commandManager = new CommandManager();

    private Gson gson;

    public Gson getGson() {
        return this.gson;
    }

    public void init() {
        DLogger.DEBUG("Started initialization process.");

        moduleManager.init();

        DLogger.DEBUG("Initialized Modules.");

        this.gson = new Gson();

        this.moduleManager.loadConfig(gson);
        this.moduleManager.saveConfig(gson);
        this.settingManager.loadConfig(gson);
        this.settingManager.saveConfig(gson);

        Runtime.getRuntime().addShutdownHook(new Thread("Client shutdown thread") {
            public void run() {
                moduleManager.saveConfig(getGson());
                settingManager.saveConfig(getGson());
                DLogger.DEBUG("Saved configs.");
            }
        });

        DLogger.DEBUG("Initialized configs.");

        //commandManager.init();

        DLogger.DEBUG("Initialized commands.");

        /* Always bottom */
        DLogger.DEBUG("Finished injecting.");
    }

}
