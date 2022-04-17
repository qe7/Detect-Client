package github.qe7.detect.setting.impl;

import github.qe7.detect.setting.Setting;

public class SettingNumber extends Setting<Double> {
	
	private double min, max;
	private String increments;

	public SettingNumber(String name, double value, String increments, double min, double max) {
		super(name, value);
		this.increments = increments;
		this.min = min;
		this.max = max;
	}
	
	public String getIncrement() {
		return increments;
	}
	
	public double getMin() {
		return min;
	}
	
	public double getMax() {
		return max;
	}
	
	public void setMax(double max) {
		this.max = max;
	}
	
	public void setMin(double min) {
		this.min = min;
	}

}
