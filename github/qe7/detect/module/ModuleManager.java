package github.qe7.detect.module;

import github.qe7.detect.event.Event;
import github.qe7.detect.event.listeners.EventKey;
import github.qe7.detect.module.impl.combat.AntiBot;
import github.qe7.detect.module.impl.combat.Autoclicker;
import github.qe7.detect.module.impl.combat.Killaura;
import github.qe7.detect.module.impl.combat.Velocity;
import github.qe7.detect.module.impl.movement.*;
import github.qe7.detect.module.impl.player.AutoRespawn;
import github.qe7.detect.module.impl.player.FastPlace;
import github.qe7.detect.module.impl.player.InvWalk;
import github.qe7.detect.module.impl.visual.Clickgui;
import github.qe7.detect.module.impl.visual.Fullbright;
import github.qe7.detect.module.impl.visual.Hud;
import github.qe7.detect.module.impl.visual.TargetHud;

import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;

public class ModuleManager {

    public CopyOnWriteArrayList<Module> modules = new CopyOnWriteArrayList<>();

    public void init() {
        /* Combat */
        modules.add(new Autoclicker());
        modules.add(new AntiBot());
        modules.add(new Killaura());
        modules.add(new Velocity());

        /* Exploit */

        /* Movement */
        modules.add(new Fly());
        modules.add(new Scaffold());
        modules.add(new NoSlowDown());
        modules.add(new Speed());
        modules.add(new Sprint());

        /* Player */
        modules.add(new AutoRespawn());
        modules.add(new FastPlace());
        modules.add(new InvWalk());

        /* Visual */
        modules.add(new Clickgui());
        modules.add(new Fullbright());
        modules.add(new Hud());
        modules.add(new TargetHud());

        sort();
    }

    public void sort() {
        Collections.sort(modules, new Comparator<Module>() {
            public int compare(Module o1, Module o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
    }

    public CopyOnWriteArrayList<Module> getModules() {
        return modules;
    }

    public CopyOnWriteArrayList<Module> getModulesByCategory(Category c) {
        CopyOnWriteArrayList<Module> mods = new CopyOnWriteArrayList<>();
        for(Module mod : modules) {
            if(mod.getCategory().equals(c))
                mods.add(mod);
        }
        return mods;
    }

    public Module getModuleByName(String name) {
        for (Module module : modules)
            if(module.getName().equalsIgnoreCase(name))
                return module;
        return null;
    }

    public void onEvent(Event event) {
        if(event instanceof EventKey) {
            EventKey e = (EventKey)event;
            for (Module module : modules) {
                if(module.getKey() == e.getCode())
                    module.toggle();
            }
        }
        for(Module mod : modules) {
            if(mod.isToggled())
                mod.onEvent(event);
            mod.onSkipEvent(event);
        }
    }
}
