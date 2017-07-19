package com.tcs.weather.dto;

/**
 * 
 * @author srawanthi.samala WeatherDataDto contains all setters and getters
 *         methods
 * 
 */
public class WeatherDataDto {

	// location|position|localtimeutc|conditions|temparature|position|humidity
	private String location;
	private String position;
	private String localtime;
	private String conditions;
	private String temparature;
	private String pressure;
	private String humidity;

	
	
	/**
	 * @return the localtime
	 */
	public String getLocaltime() {
		return localtime;
	}

	/**
	 * @param localtime the localtime to set
	 */
	public void setLocaltime(String localtime) {
		this.localtime = localtime;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * @return the conditions
	 */
	public String getConditions() {
		return conditions;
	}

	/**
	 * @param conditions
	 *            the conditions to set
	 */
	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	/**
	 * @return the temparature
	 */
	public String getTemparature() {
		return temparature;
	}

	/**
	 * @param temparature
	 *            the temparature to set
	 */
	public void setTemparature(String temparature) {
		this.temparature = temparature;
	}

	/**
	 * @return the pressure
	 */
	public String getPressure() {
		return pressure;
	}

	/**
	 * @param pressure
	 *            the pressure to set
	 */
	public void setPressure(String pressure) {
		this.pressure = pressure;
	}

	/**
	 * @return the humidity
	 */
	public String getHumidity() {
		return humidity;
	}

	/**
	 * @param humidity
	 *            the humidity to set
	 */
	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	/**
	 * return the value as
	 * 
	 * @return String position, conditions, temparature, pressure, humidity,
	 *         city, localtime, timeOffset
	 */
	@Override
	public String toString() {
		return "position: " + position + ", conditions: " + conditions
				+ ", temparature: " + temparature + ", pressure: " + pressure
				+ ", humidity: " + humidity;
	}

}
