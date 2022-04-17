package github.qe7.detect.setting;

public class Setting<T> {

	protected String name;
	private T value;
	private boolean hidden;

	public Setting(String name, T value) {
		this.name = name;
		this.value = value;
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

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}
	
}
