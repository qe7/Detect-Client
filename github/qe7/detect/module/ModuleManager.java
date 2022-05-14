package github.qe7.detect.module;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import github.qe7.detect.Detect;
import github.qe7.detect.event.Event;
import github.qe7.detect.event.listeners.EventKey;

import java.io.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;


/* Category imports */
import github.qe7.detect.event.listeners.EventMessage;
import github.qe7.detect.module.impl.combat.*;
import github.qe7.detect.module.impl.exploit.Timer;
import github.qe7.detect.module.impl.misc.ToggleMessages;
import github.qe7.detect.module.impl.movement.*;
import github.qe7.detect.module.impl.player.*;
import github.qe7.detect.module.impl.visual.*;
import github.qe7.detect.util.Util;
import github.qe7.detect.util.client.DLogger;

public class ModuleManager {

    public CopyOnWriteArrayList<Module> modules = new CopyOnWriteArrayList<>();

    public void init() {
        /* Combat */
        modules.add(new AutoClicker());
        modules.add(new AutoPot());
        modules.add(new AntiBot());
        modules.add(new Killaura());
        modules.add(new Velocity());
        modules.add(new Criticals());

        /* Exploit */
        modules.add(new Timer());

        /* Misc */
        modules.add(new ToggleMessages());

        /* Movement */
        modules.add(new Fly());
        modules.add(new Scaffold());
        modules.add(new NoSlowDown());
        modules.add(new Speed());
        modules.add(new Sprint());
        modules.add(new Step());

        /* Player */
        modules.add(new AutoRespawn());
        modules.add(new FastPlace());
        modules.add(new InvWalk());
        modules.add(new NoRotate());
        modules.add(new NoFall());
        modules.add(new SpeedMine());
        modules.add(new KeepSprint());
        modules.add(new ChestStealer());
        modules.add(new InvManager());

        /* Visual */
        modules.add(new Animations());
        modules.add(new Clickgui());
        modules.add(new Fullbright());
        modules.add(new Hud());
        modules.add(new TargetHud());
        modules.add(new ESP());
        modules.add(new ChatExtras());


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
        if (event instanceof EventMessage) {
            Detect.i.commandManager.handleChat((EventMessage) event);
        }
        if (event instanceof EventKey) {
            EventKey e = (EventKey)event;
            for (Module module : modules) {
                if(module.getBind() == e.getCode())
                    module.toggle();
            }
        }
        for (Module mod : modules) {
            if(mod.isToggled())
                mod.onEvent(event);
            mod.onSkipEvent(event);
        }
    }

    public void loadConfig(Gson gson) {
        for(Module m: this.modules) {
            File file = new File(Util.mc.mcDataDir + File.separator + Detect.i.name + File.separator + "modules" + File.separator + m.getName() + ".json");
            try (FileReader reader = new FileReader(file)) {
                Map<String, Object> map = gson.fromJson(reader, new TypeToken<Map<String, Object>>() {}.getType());
                m.setBind((int)Math.round((double)map.get("bind")));
                m.setToggled((boolean)map.get("toggled"));
            } catch (JsonSyntaxException e) {
                DLogger.DEBUG("ModuleManager(loadConfig) : JsonSyntaxException");
            } catch (JsonIOException e) {
                DLogger.DEBUG("ModuleManager(loadConfig) : JsonIOException");
            } catch (FileNotFoundException e) {
                DLogger.DEBUG("ModuleManager(loadConfig) : FileNotFoundException");
            } catch (IOException e) {
                DLogger.DEBUG("ModuleManager(loadConfig) : IOException");
            } catch (NullPointerException e) {
                DLogger.DEBUG("ModuleManager(loadConfig) : NullPointerException");
            }
        }
    }

    public void saveConfig(Gson gson) {
        for(Module m: this.modules) {
            File file = new File(Util.mc.mcDataDir + File.separator + Detect.i.name + File.separator + "modules" + File.separator + m.getName() + ".json");
            if(!file.exists()) {
                new File(Util.mc.mcDataDir + File.separator + Detect.i.name + File.separator + "modules").mkdirs();
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    DLogger.DEBUG("ModuleManager : 1");
                }
            }
            try (FileWriter writer = new FileWriter(file)) {
                Map<String, Object> map = new HashMap<>();
                map.put("name", m.getName());
                map.put("bind", m.getBind());
                map.put("toggled", m.isToggled());
                gson.toJson(map, writer);
            } catch (IOException e) {
                DLogger.DEBUG("ModuleManager : 2");
            }
        }
    }
}
