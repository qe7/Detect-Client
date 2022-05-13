package github.qe7.detect.setting.impl;

import github.qe7.detect.setting.Setting;

public class SettingMode extends Setting<String[]> {
	
	private String currentValue;

	public SettingMode(String name, String...values) {
		super(name,"Mode" , values);
		currentValue = values[0];
	}
	
	public String getCurrentValue() {
		return currentValue;
	}
	
	public void setCurrentValue(String currentValue) {
		this.currentValue = currentValue;
	}
}
