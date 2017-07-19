package com.tcs.weather.dto;

/**
 * CityPositionDto contains longitude latitude elevation country details
 * 
 * @author srawanthi.samala
 *
 */
public class CityPositionDto {

	private String city;
	private String country;
	private String latlongelevation;
	private String latlong;

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the latlongelevation
	 */
	public String getLatlongelevation() {
		return latlongelevation;
	}

	/**
	 * @param latlongelevation
	 *            the latlongelevation to set
	 */
	public void setLatlongelevation(String latlongelevation) {
		this.latlongelevation = latlongelevation;
	}

	/**
	 * @return the latlong
	 */
	public String getLatlong() {
		return latlong;
	}

	/**
	 * @param latlong
	 *            the latlong to set
	 */
	public void setLatlong(String latlong) {
		this.latlong = latlong;
	}

}
