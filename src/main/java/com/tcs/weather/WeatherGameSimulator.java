package com.tcs.weather;

import java.io.IOException;
import java.util.HashMap;

import com.tcs.weather.bo.WeatherDataPredictor;
import com.tcs.weather.bo.WeatherHistoryDataClassifier;
import com.tcs.weather.constants.WeatherConstants;
import com.tcs.weather.dto.CityPositionDto;
import com.tcs.weather.dto.WeatherDataDto;
import com.tcs.weather.dto.WeatherHistoryDataDto;

/**
 * WeatherPredictor is used to create toy weather simulation program it
 * generates weather data for the given input
 * 
 * @author srawanthi.samala
 *
 */
public class WeatherGameSimulator {

	private static String city;
	private static String country;
	private static String datewithtime;
	private static String worldcitiescoordinatorslistpath;
	private static String weatherhistoricalyearwisedataPath;

	/**
	 * This main method used to run the Weather predictor program and setup the
	 * process for generating Weather Data args : String[] accepts parameters
	 * like city, country, datewithtime, worldcitiescoordinatorslistpath,
	 * weatherhistoricalyearwisedataPath
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		setupProcess(args);
	}

	/**
	 * setupProcess is used to accept arguments as city, country, datewithtime,
	 * worldcitiescoordinatorslistpath, weatherhistoricalyearwisedataPath and
	 * generating Weather data as
	 * location|position|localtimeutc|conditions|temparature|position|humidity
	 * 
	 * @param args
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean setupProcess(String[] args) throws Exception {

		validateArgs(args);
		city = args[0];
		country = args[1];
		datewithtime = args[2];
		worldcitiescoordinatorslistpath = args[3];
		weatherhistoricalyearwisedataPath = args[4];

		WeatherDataDto objWeatherDataDto = simulateWeatherData(
				worldcitiescoordinatorslistpath,
				weatherhistoricalyearwisedataPath, city, country, datewithtime);

		System.out.println(
				 "city "+ objWeatherDataDto.getLocation()+" position " + objWeatherDataDto.getPosition().replace(WeatherConstants.COLUMN_SEPERATOR,",")+
				" Locationtimeutc " +objWeatherDataDto.getLocaltime()+"temparature " + objWeatherDataDto.getTemparature()
				+ "  humidity " + objWeatherDataDto.getHumidity()
				+ " pressure " + objWeatherDataDto.getPressure()
				+ " conditions " + objWeatherDataDto.getConditions()
				);
		return true;
	}

	/**
	 * validateArgs is used for validating arguments those are city, country,
	 * datewithtime, worldcitiescoordinatorslistpath,
	 * weatherhistoricalyearwisedataPath
	 * 
	 * @param args
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean validateArgs(String[] args) throws Exception {
		if (args.length < 5) {
			System.out.println("Weather Simulator Parameters");
			System.out.println("city [e.g. Sydney]");
			System.out.println("country   [e.g. Australia]");
			System.out
					.println("datewithtime YYYY-MM-DD HH:mm:ss    [e.g. 2017-07-20 16:02:12]");
			System.out
					.println("worldcitiescoordinatorslistpath   [ e.g. src/main/resources/longlatlookup-place-timezone.txt ]");
			System.out
					.println("weatherhistoricalyearwisedataPath       [ e.g. src/main/resources/2017-07-14-weatherdata.txt ]");

			throw new Exception("Need more information (5 parameters)");
		}
		return true;
	}

	/**
	 * simulateWeatherData is used to classify weather historical data and
	 * predict the weather for given city on the specified date accepts as
	 * parameters are worldcitiescoordinatorslistpath,
	 * weatherhistoricalyearwisedataPath , city, country, datewithtime
	 * 
	 * @param String
	 *            , String, String, String, String
	 * @return
	 */
	public static WeatherDataDto simulateWeatherData(
			String worldcitiescoordinatorslistpath,
			String weatherhistoricalyearwisedataPath, String city,
			String country, String datewithtime) throws IOException {

		WeatherHistoryDataClassifier objWeatherHistoryDataClassifier = new WeatherHistoryDataClassifier();

		// reads worldcitycoordinatorslist from the
		// worldcitiescoordinatorslistpath file and get the latitude and
		// longitude with respect to city
		HashMap<String, CityPositionDto> objCityInfoMap = objWeatherHistoryDataClassifier
				.classifyWorldCitiesInfoCoordinators(worldcitiescoordinatorslistpath);

		// reads weather historical year wise data from the
		// weatherhistoricalyearwisedataPath file and creates
		// weatherhistorylonglatmap
		HashMap<String, HashMap<String, HashMap<String, HashMap<String, WeatherHistoryDataDto>>>> weatherhistorylonglatmap = objWeatherHistoryDataClassifier
				.classifyWeatherHistoryInfo(weatherhistoricalyearwisedataPath);

		String latlong = objCityInfoMap.get(
				city + WeatherConstants.COLUMN_SEPERATOR + country)
				.getLatlong();
		String longitude = String.format("%.1f", Double.parseDouble(latlong
				.split(WeatherConstants.COLUMN_SEPERATOR)[1]));
		String latitude = String.format("%.1f", Double.parseDouble(latlong
				.split(WeatherConstants.COLUMN_SEPERATOR)[0]));

		String date = datewithtime
				.split(WeatherConstants.DATE_TIME_FIELD_SEPERATOR)[0];

		WeatherDataPredictor objWeatherDataPredictor = new WeatherDataPredictor();
		String keyLatitudeLongitude = objWeatherDataPredictor.predictlonglat(
				weatherhistorylonglatmap, longitude, latitude, latlong);
		WeatherHistoryDataDto objlookupWeatherHistoryDataDto = objWeatherDataPredictor
				.predictYearWiseWeatherData(date, weatherhistorylonglatmap,
						keyLatitudeLongitude);

		WeatherDataDto objWeatherDataDto = objWeatherDataPredictor
				.mapWeatherData(objlookupWeatherHistoryDataDto);

		objWeatherDataDto.setLocation(city);
		objWeatherDataDto.setLocaltime(datewithtime);
		objWeatherDataDto.setPosition(objCityInfoMap.get(
				city + WeatherConstants.COLUMN_SEPERATOR + country)
				.getLatlongelevation());

		// 24 hours time format night 7(12+7) to morning 7(7) low temparature 19
		// 20 21 22 23 24 1 2 3 4 5 6 7
		// 1 to 12 raise
		// 12 to 2 constant
		// 2 to 24 reduce min to max how much difference 2/12 +0.17

		return objWeatherDataDto;
	}

}
