package github.qe7.detect.module;

import github.qe7.detect.event.Event;
import github.qe7.detect.event.impl.EventKey;

import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;

/* Module imports */
import github.qe7.detect.module.impl.client.*;
import github.qe7.detect.module.impl.combat.AutoClicker;
import github.qe7.detect.module.impl.combat.Velocity;
import github.qe7.detect.module.impl.movement.*;
import github.qe7.detect.module.impl.player.NoFall;
import github.qe7.detect.module.impl.visuals.Fullbright;

public class ModuleManager {

    public CopyOnWriteArrayList<Module> modules = new CopyOnWriteArrayList<>();

    public void init() {
        /* client */
        modules.add(new Arraylist());
        modules.add(new Watermark());
        modules.add(new Clickgui());

        /* combat */
        modules.add(new AutoClicker());
        modules.add(new Velocity());

        /* exploit */

        /* movement */
        modules.add(new Sprint());
        modules.add(new NoSlowDown());

        /* player */
        modules.add(new NoFall());

        /* visuals */
        modules.add(new Fullbright());

        sortModules();
    }

    public void sortModules() {
        Collections.sort(modules, new Comparator<Module>() {
            public int compare(final Module object1, final Module object2) {
                return object1.getName().compareTo(object2.getName());
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
