package github.qe7.detect;

import github.qe7.detect.module.ModuleManager;

public class Detect {

    public static Detect i = new Detect();
    public String
            name = "Detect",
            exhi = "Exhibition",
            moon = "Moon",
            version = "1.0",
            prefix = "§7[§4D§7]§r";
    public ModuleManager moduleManager = new ModuleManager();

    public void init() {
        moduleManager.init();
    }

}
