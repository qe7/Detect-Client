package github.qe7.detect.setting;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import github.qe7.detect.Detect;
import github.qe7.detect.module.Module;
import github.qe7.detect.setting.impl.SettingBoolean;
import github.qe7.detect.setting.impl.SettingMode;
import github.qe7.detect.setting.impl.SettingNumber;
import github.qe7.detect.util.Util;
import github.qe7.detect.util.client.DLogger;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SettingManager {

    private ArrayList<Setting> settings;

    public SettingManager(){
        this.settings = new ArrayList<Setting>();
    }

    public void loadConfig(Gson gson) {
        for(Setting s: this.settings) {
            File file = new File(Util.mc.mcDataDir + File.separator + "detect" + File.separator + "settings" + File.separator + s.getParentMod().getName() + File.separator + s.getName() + ".json");
            try (FileReader reader = new FileReader(file)) {
                Map<String, Object> map = gson.fromJson(reader, new TypeToken<Map<String, Object>>() {}.getType());
                if((boolean)map.get("isCheck")) {
                    s.setValue((boolean)map.get("value"));
                } else if((boolean)map.get("isSlider")) {
                    s.setValue((double)map.get("value"));
                } else if((boolean)map.get("isCombo")) {
                    s.setValue((String)map.get("value"));
                }
            } catch (JsonSyntaxException e) {
                DLogger.DEBUG("SettingsManager(loadConfig) : JsonSyntaxException");
            } catch (JsonIOException e) {
                DLogger.DEBUG("SettingsManager(loadConfig) : JsonIOException");
            } catch (FileNotFoundException e) {
                DLogger.DEBUG("SettingsManager(loadConfig) : FileNotFoundException");
            } catch (IOException e1) {
                DLogger.DEBUG("SettingsManager(loadConfig) : IOException");
            } catch (NullPointerException e) {
                DLogger.DEBUG("SettingsManager(loadConfig) : NullPointerException");
            }
        }
    }

    public void saveConfig (Gson gson) {
        for(Setting s: this.settings) {
            File file = new File(Util.mc.mcDataDir + File.separator + Detect.i.name + File.separator + "settings" + File.separator + s.getParentMod().getName() + File.separator + s.getName() + ".json");
            if(!file.exists()) {
                new File(Util.mc.mcDataDir + File.separator + Detect.i.name + File.separator + "settings" + File.separator + s.getParentMod().getName()).mkdirs();
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    DLogger.DEBUG("SettingsManager(saveConfig) : IOException");
                }
            }
            try (FileWriter writer = new FileWriter(file)) {
                Map<String, Object> map = new HashMap<>();
//                if (s instanceof SettingBoolean) {
//                    //s.setValue(map.get("value"));
//                    map.put("value", s.getValue());
//                }
//                if (s instanceof SettingNumber) {
//                    //s.setValue(map.get("value"));
//                    map.put("value", s.getValue());
//                }
//                if (s instanceof SettingMode) {
//                    //s.setValue(map.get("value"));
//                    map.put("value", ((SettingMode) s).getCurrentValue());
//                }
                map.put("isCheck", s.isCheck());
                map.put("isSlider", s.isSlider());
                map.put("isCombo", s.isCombo());
                if(s.isCombo()) {
                    map.put("value", s.getValue());
                } else if(s.isCheck()) {
                    map.put("value", s.getValue());
                } else if(s.isSlider()) {
                    map.put("value", s.getValue());
                }
                gson.toJson(map, writer);
            } catch (IOException e) {
                DLogger.DEBUG("SettingsManager(saveConfig) : IOException(2)");
            }
        }
    }
}
