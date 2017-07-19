package com.tcs.weather.bo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.tcs.weather.constants.WeatherConstants;
import com.tcs.weather.dto.WeatherDataDto;
import com.tcs.weather.dto.WeatherHistoryDataDto;

/**
 * WeatherDataPredictor predicts weather data by using two techniques 1.
 * Longitude and Latitude Adjustments 2. Year wise Predictor here slightly using
 * sliding window algorithm
 * 
 * @author srawanthi.samala
 *
 */
public class WeatherDataPredictor {

	/**
	 * predictYearWiseWeatherData is used to predict weather data
	 * 
	 * @param date
	 * @param weatherhistorylonglatmap
	 * @param keyLatitudeLongitude
	 * @return WeatherHistoryDataDto
	 */
	public WeatherHistoryDataDto predictYearWiseWeatherData(
			String date,
			HashMap<String, HashMap<String, HashMap<String, HashMap<String, WeatherHistoryDataDto>>>> weatherhistorylonglatmap,
			String keyLatitudeLongitude) {
		WeatherHistoryDataDto objlookupWeatherHistoryDataDto = null;
		Integer currentyear = Integer.parseInt(date
				.split(WeatherConstants.DATE_FIELD_SEPERATOR)[0]);
		Integer currentmonth = Integer.parseInt(date
				.split(WeatherConstants.DATE_FIELD_SEPERATOR)[1]);
		Integer currentday = Integer.parseInt(date
				.split(WeatherConstants.DATE_FIELD_SEPERATOR)[2]);

		// weatherhistorylonglatmap contains keyLongitudeLatitude
		if (weatherhistorylonglatmap.containsKey(keyLatitudeLongitude)) {
			HashMap<String, HashMap<String, HashMap<String, WeatherHistoryDataDto>>> lookupyearmap = weatherhistorylonglatmap
					.get(keyLatitudeLongitude);
			// checking for history data in current year
			objlookupWeatherHistoryDataDto = lookupinHistoryYearData(
					lookupyearmap, currentyear, currentmonth, currentday);
			if (objlookupWeatherHistoryDataDto == null) {
				objlookupWeatherHistoryDataDto = lookupinFutureYearData(
						lookupyearmap, currentyear+1, currentmonth, currentday);
			}
		}
		return objlookupWeatherHistoryDataDto;
	}

	/**
	 * mapWeatherData emit measurements in WeatherDataDto object
	 * @param objlookupWeatherHistoryDataDto
	 * @return WeatherDataDto
	 */
	public WeatherDataDto mapWeatherData(
			WeatherHistoryDataDto objlookupWeatherHistoryDataDto) {
		String temp = "";
		String humidity = "";
		String pressure = "";
		String conditions = "";
		WeatherDataDto objWeatherDataDto = new WeatherDataDto();
		if (objlookupWeatherHistoryDataDto != null) {
			double meantemp = ((Integer.parseInt(objlookupWeatherHistoryDataDto
					.getTempMax()) + Integer
					.parseInt(objlookupWeatherHistoryDataDto.getTempMin())) / 2);
			temp = ""
					+ (meantemp + Double
							.parseDouble(objlookupWeatherHistoryDataDto
									.getTempAvg())) / 2;

			double meanhumidity = ((Integer
					.parseInt(objlookupWeatherHistoryDataDto.getHumMax()) + Integer
					.parseInt(objlookupWeatherHistoryDataDto.getHumMin())) / 2);
			humidity = ""
					+ (meanhumidity + Double
							.parseDouble(objlookupWeatherHistoryDataDto
									.getHumAvg())) / 2;

			double meanpressure = ((Integer
					.parseInt(objlookupWeatherHistoryDataDto.getSeaPressureMax()) + Integer
					.parseInt(objlookupWeatherHistoryDataDto.getSeaPressureMin())) / 2);
			pressure = ""
					+ (meanpressure + Double
							.parseDouble(objlookupWeatherHistoryDataDto
									.getSeaPressureAvg())) / 2;

			conditions = objlookupWeatherHistoryDataDto.getConditions();
		}
		objWeatherDataDto.setTemparature(temp);
		objWeatherDataDto.setHumidity(humidity);
		objWeatherDataDto.setPressure(pressure);
		objWeatherDataDto.setConditions(conditions);
		return objWeatherDataDto;
	}

	/**
	 * predictlonglat method used to match nearest longitude and latitude
	 * @param weatherhistorylonglatmap
	 * @param longitude
	 * @param latitude
	 * @param latlong
	 * @return String
	 */
	public String predictlonglat(
			HashMap<String, HashMap<String, HashMap<String, HashMap<String, WeatherHistoryDataDto>>>> weatherhistorylonglatmap,
			String longitude, String latitude, String latlong) {

		String keylongtitude = "";
		String keylatitude = "";
		boolean historyexists = false;
		Set<String> objset = weatherhistorylonglatmap.keySet();
		if (weatherhistorylonglatmap.containsKey(latitude
				+ WeatherConstants.COLUMN_SEPERATOR + longitude)) {
			keylongtitude = longitude;
			keylatitude = latitude;
			historyexists = true;
		}
		if (!historyexists) {
			latitude = (Double.parseDouble(latlong
					.split(WeatherConstants.COLUMN_SEPERATOR)[0]) + "")
					.split("\\.")[0];
			longitude = (Double.parseDouble(latlong
					.split(WeatherConstants.COLUMN_SEPERATOR)[1]) + "")
					.split("\\.")[0];

			Iterator<String> it = objset.iterator();
			int setsize = objset.size();
			int itcount = 0;
			while (it.hasNext()) {
				itcount++;
				String strlatlong = it.next();
				String objlatfromSet = strlatlong.split(WeatherConstants.COLUMN_SEPERATOR)[0];
				String objlongfromSet = strlatlong.split(WeatherConstants.COLUMN_SEPERATOR)[1];
				boolean longflag = false;
				boolean latflag = false;
				if (objlongfromSet.startsWith(longitude)) {
					longflag = true;
				} else {
					for (int i = 0; i < longitude.length(); i++) {
						if (longitude.length() >= 2)
							longitude = longitude.substring(0,
									longitude.length() - 1);
						if (objlongfromSet.startsWith(longitude)) {
							longflag = true;
							break;
						}
					}
				}
				if (objlatfromSet.startsWith(latitude)) {
					latflag = true;
				} else {
					for (int i = 0; i < latitude.length(); i++) {
						if (longitude.length() >= 2)
							latitude = latitude.substring(0,
									latitude.length() - 1);
						if (objlatfromSet.startsWith(latitude)) {
							latflag = true;
							break;
						}
					}
				}

				if (longflag && latflag) {
					keylongtitude = objlongfromSet;
					keylatitude = objlatfromSet;
					historyexists = true;
				} else if (longflag) {
					keylongtitude = objlongfromSet;
					keylatitude = objlatfromSet;
					historyexists = true;
				} else if (latflag) {
					keylongtitude = objlongfromSet;
					keylatitude = objlatfromSet;
					historyexists = true;
				} else if (itcount == setsize) {
					keylongtitude = objlongfromSet;
					keylatitude = objlatfromSet;
					historyexists = true;
				}
				if (historyexists)
					break;
			}

		}
		return keylatitude + WeatherConstants.COLUMN_SEPERATOR + keylongtitude;
	}

	/**
	 * lookupinHistoryYearData lookups the data as per year wise
	 * @param lookupyearmap
	 * @param currentyear
	 * @param currentmonth
	 * @param currentday
	 * @return WeatherHistoryDataDto
	 */
	public WeatherHistoryDataDto lookupinHistoryYearData(
			HashMap<String, HashMap<String, HashMap<String, WeatherHistoryDataDto>>> lookupyearmap,
			Integer currentyear, Integer currentmonth, Integer currentday) {
		WeatherHistoryDataDto objlookupWeatherHistoryDataDto = null;
		if (lookupyearmap.containsKey(String.valueOf(currentyear))) {
			HashMap<String, HashMap<String, WeatherHistoryDataDto>> lookupmonthmap = lookupyearmap
					.get(String.valueOf(currentyear));
			if (lookupmonthmap.containsKey(String.valueOf(currentmonth))) {
				HashMap<String, WeatherHistoryDataDto> lookupdaymap = lookupmonthmap
						.get(String.valueOf(currentmonth));

				if (lookupdaymap.containsKey(String.valueOf(currentday))) {
					objlookupWeatherHistoryDataDto = lookupdaymap.get(String
							.valueOf(currentday));
				} else if (lookupdaymap.containsKey(String
						.valueOf(currentday - 1))) {
					objlookupWeatherHistoryDataDto = lookupdaymap.get(String
							.valueOf(currentday - 1));
				}
			}
		}
		if (objlookupWeatherHistoryDataDto == null) {
			if (currentyear >= 2000)
				objlookupWeatherHistoryDataDto = lookupinHistoryYearData(
						lookupyearmap, currentyear - 1, currentmonth,
						currentday);
		}
		return objlookupWeatherHistoryDataDto;
	}
	
	/**
	 * lookupinFutureYearData lookups the data as per year wise
	 * @param lookupyearmap
	 * @param currentyear
	 * @param currentmonth
	 * @param currentday
	 * @return WeatherHistoryDataDto
	 */
	public WeatherHistoryDataDto lookupinFutureYearData(
			HashMap<String, HashMap<String, HashMap<String, WeatherHistoryDataDto>>> lookupyearmap,
			Integer currentyear, Integer currentmonth, Integer currentday) {
		WeatherHistoryDataDto objlookupWeatherHistoryDataDto = null;
		if (lookupyearmap.containsKey(String.valueOf(currentyear))) {
			HashMap<String, HashMap<String, WeatherHistoryDataDto>> lookupmonthmap = lookupyearmap
					.get(String.valueOf(currentyear));
			if (lookupmonthmap.containsKey(String.valueOf(currentmonth))) {
				HashMap<String, WeatherHistoryDataDto> lookupdaymap = lookupmonthmap
						.get(String.valueOf(currentmonth));

				if (lookupdaymap.containsKey(String.valueOf(currentday))) {
					objlookupWeatherHistoryDataDto = lookupdaymap.get(String
							.valueOf(currentday));
				} else if (lookupdaymap.containsKey(String
						.valueOf(currentday - 1))) {
					objlookupWeatherHistoryDataDto = lookupdaymap.get(String
							.valueOf(currentday - 1));
				}
			}
		}
		if (objlookupWeatherHistoryDataDto == null) {
			if (2025 >= currentyear)
				objlookupWeatherHistoryDataDto = lookupinHistoryYearData(
						lookupyearmap, currentyear + 1, currentmonth,
						currentday);
		}
		return objlookupWeatherHistoryDataDto;
	}

}
