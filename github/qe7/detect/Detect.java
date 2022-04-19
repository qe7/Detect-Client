package github.qe7.detect;

import github.qe7.detect.module.ModuleManager;

public class Detect {

    public static Detect i = new Detect();
    public String name = "Detect", version = "0.1";
    public ModuleManager moduleManager = new ModuleManager();

    public void init() {
        moduleManager.init();
    }

}
