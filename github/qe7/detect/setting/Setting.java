package github.qe7.detect.setting;

import com.google.gson.Gson;
import github.qe7.detect.module.Module;

public class Setting<T> {

	protected String name, mode;
	private Module parent;
	private T value;
	private boolean hidden;

	public Setting(String name, String mode, T value) {
		this.name = name;
		this.value = value;
		this.mode = mode;
		this.hidden = false;
	}

	public Boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public String getName() {
		return name;
	}

	public Module getParentMod(){
		return parent;
	}
	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public boolean isCombo(){
		return this.mode.equalsIgnoreCase("Mode");
	}

	public boolean isCheck(){
		return this.mode.equalsIgnoreCase("Boolean");
	}

	public boolean isSlider(){
		return this.mode.equalsIgnoreCase("Number");
	}


}
