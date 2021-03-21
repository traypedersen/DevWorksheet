package com.tylerpedersen.model;

public class Racecar {
	
	private String make;
	private String driver;
	private double speed;
	
	public Racecar() {
		
	}
	
	public Racecar(String make, String driver, double speed) {
		super();
		this.make = make;
		this.driver = driver;
		this.speed = speed;
	}
	
	public String getMake() {
		return make;
	}
	
	public void setMake(String make) {
		this.make = make;
	}
	
	public String getDriver() {
		return driver;
	}
	
	public void setDriver(String driver) {
		this.driver = driver;
	}
	
	public double getSpeed() {
		return speed;
	}
	
	public void setSpeed(double speed) {
		this.speed = speed;
	}

	@Override
	public String toString() {
		return "make=" + make + ", driver=" + driver + ", speed=" + speed;
	}
	
}
