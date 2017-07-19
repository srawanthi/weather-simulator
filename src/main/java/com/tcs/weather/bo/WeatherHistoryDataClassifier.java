package com.tcs.weather.bo;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

import com.tcs.weather.constants.WeatherConstants;
import com.tcs.weather.dto.CityPositionDto;
import com.tcs.weather.dto.WeatherHistoryDataDto;

/**
 * WeatherHistoryDataClassifier used to generate WorldCitiesCoordinatorsMap and
 * WeatherHistorylonglatmap WorldCitiesCoordinatorsMap contains all cities and
 * respective world longitude, latitude and elevation WeatherHistorylonglatmap
 * contains longitude latitude year wise data in HashMap
 * 
 * @author srawanthi.samala
 *
 */
public class WeatherHistoryDataClassifier {

	/**
	 * classifyWorldCitiesInfoCoordinators reads worldcitycoordinatorslist from
	 * the worldcitiescoordinatorslistpath file and map the latitude and
	 * longitude with respect to city into HashMap<String, CityPositionDto>
	 * 
	 * @param path
	 * @return HashMap<String, CityPositionDto>
	 * @throws IOException
	 */
	public HashMap<String, CityPositionDto> classifyWorldCitiesInfoCoordinators(
			String path) throws IOException {

		WeatherDataUtils objWeatherDataUtils = new WeatherDataUtils();
		BufferedReader cityInfoBuffer = objWeatherDataUtils
				.getFileContentinBuffer(path);
		String cityInfoLine = cityInfoBuffer.readLine();

		HashMap<String, CityPositionDto> objCityInfoMap = new HashMap<String, CityPositionDto>();
		while (cityInfoLine != null) {
			CityPositionDto objCityPositionDto = new CityPositionDto();
			// cityInfoLine contains columns are id country city latitude
			// longitude elevation
			String[] objCityArr = cityInfoLine
					.split(WeatherConstants.ROW_SPLIT_SEPERATOR);
			objCityPositionDto.setCountry(objCityArr[1]);
			objCityPositionDto.setCity(objCityArr[2]);

			objCityPositionDto.setLatlong(String.format("%.1f",
					Double.parseDouble(objCityArr[3]))
					+ WeatherConstants.COLUMN_SEPERATOR
					+ String.format("%.1f", Double.parseDouble(objCityArr[4])));
			objCityPositionDto.setLatlongelevation(objCityArr[3]
					+ WeatherConstants.COLUMN_SEPERATOR + objCityArr[4]
					+ WeatherConstants.COLUMN_SEPERATOR + objCityArr[5]);
			objCityInfoMap.put(
					objCityPositionDto.getCity()
							+ WeatherConstants.COLUMN_SEPERATOR
							+ objCityPositionDto.getCountry(),
					objCityPositionDto);
			cityInfoLine = cityInfoBuffer.readLine();
		}
		return objCityInfoMap;
	}

	/**
	 * reads weather historical year wise data from the
	 * weatherhistoricalyearwisedataPath file and creates
	 * weatherhistorylonglatmap and contains
	 * LATITUDE|LONGITUDE|ELEVATION|year|month
	 * |day|Tmax|Tmin|Tavg|Humidityhigh|HumidityAvg
	 * |HumidityLow|Pressurehigh|PressureAvg|PressureLow|Conditions
	 * 
	 * @param path
	 * @return HashMap<String, HashMap<String, HashMap<String, HashMap<String,
	 *         WeatherHistoryDataDto>>>>
	 * @throws IOException
	 */
	public HashMap<String, HashMap<String, HashMap<String, HashMap<String, WeatherHistoryDataDto>>>> classifyWeatherHistoryInfo(
			String path) throws IOException {
		WeatherDataUtils objWeatherDataUtils = new WeatherDataUtils();
		BufferedReader historydataBuffer = objWeatherDataUtils
				.getFileContentinBuffer(path);

		String historydataLine = historydataBuffer.readLine();

		HashMap<String, HashMap<String, HashMap<String, HashMap<String, WeatherHistoryDataDto>>>> weatherhistorylatlongmap = new HashMap<String, HashMap<String, HashMap<String, HashMap<String, WeatherHistoryDataDto>>>>();
		while (historydataLine != null) {
			String[] historydataArr = historydataLine
					.split(WeatherConstants.ROW_SPLIT_SEPERATOR);

			WeatherHistoryDataDto objWeatherHistoryDataDto = objWeatherDataUtils
					.parseLineToWeatherHistoryObject(historydataArr);
			weatherhistorylatlongmap = objWeatherDataUtils
					.classifyHistoryYearWiseData(weatherhistorylatlongmap,
							objWeatherHistoryDataDto);

			historydataLine = historydataBuffer.readLine();
		}
		return weatherhistorylatlongmap;
	}

}
