package com.tcs.weather.bo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import com.tcs.weather.constants.WeatherConstants;
import com.tcs.weather.dto.WeatherHistoryDataDto;

/**
 * 
 * @author srawanthi.samala WeatherDataUtils contains all methods related to
 *         File Content reading and convert into required object
 */
public class WeatherDataUtils {

	/**
	 * getFileContentinBuffer read weather data file content and generate
	 * bufferreader object
	 * 
	 * @param path
	 * @return BufferedReader
	 * @throws FileNotFoundException
	 */
	public BufferedReader getFileContentinBuffer(String path)
			throws FileNotFoundException {
		File initialFile = new File(path);
		InputStream in = new FileInputStream(initialFile);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		return br;
	}

	/**
	 * parseLineToWeatherHistoryObject read the the line from file converts into
	 * WeatherHistoryDataDto
	 * 
	 * @param historydataArr
	 * @return WeatherHistoryDataDto
	 */
	public WeatherHistoryDataDto parseLineToWeatherHistoryObject(
			String[] historydataArr) {
		WeatherHistoryDataDto historyData = new WeatherHistoryDataDto();
		
		historyData.setLatitude(String.format("%.1f",
				Double.parseDouble(historydataArr[0])));
		historyData.setLongitude(String.format("%.1f",
				Double.parseDouble(historydataArr[1])));
		historyData.setElevation(historydataArr[2]);
		historyData.setYear(historydataArr[3]);
		historyData.setMonth(historydataArr[4]);
		historyData.setDay(historydataArr[5]);

		historyData.setTempMax(historydataArr[6]);
		historyData.setTempMin(historydataArr[7]);
		historyData.setTempAvg(historydataArr[8]);

		historyData.setHumMax(historydataArr[9]);
		historyData.setHumAvg(historydataArr[10]);

		historyData.setHumMin(historydataArr[11]);
		historyData.setSeaPressureMax(historydataArr[12]);
		historyData.setSeaPressureAvg(historydataArr[13]);
		historyData.setSeaPressureMin(historydataArr[14]);
		historyData.setConditions(historydataArr[15]);

		return historyData;
	}

	/**
	 * classifyHistoryYearWiseData creates map as HashMap<latitudeLongitude,
	 * HashMap<Year, HashMap<Month, HashMap<Day, WeatherHistoryDataDto>>>>
	 * 
	 * @param weatherhistorylatlongmap
	 * @param historyData
	 * @return HashMap<String, HashMap<String, HashMap<String, HashMap<String,
	 *         WeatherHistoryDataDto>>>>
	 */
	public HashMap<String, HashMap<String, HashMap<String, HashMap<String, WeatherHistoryDataDto>>>> classifyHistoryYearWiseData(
			HashMap<String, HashMap<String, HashMap<String, HashMap<String, WeatherHistoryDataDto>>>> weatherhistorylatlongmap,
			WeatherHistoryDataDto historyData) {
		// weatherhistorylonglatmap if longitude and latitude exist
		if (weatherhistorylatlongmap
				.containsKey(historyData.getLatitude()
						+ WeatherConstants.COLUMN_SEPERATOR
						+ historyData.getLongitude())) {// key contains longitude
														// and latitude
			HashMap<String, HashMap<String, HashMap<String, WeatherHistoryDataDto>>> insertionyearmap = weatherhistorylatlongmap
					.get(historyData.getLatitude()
							+ WeatherConstants.COLUMN_SEPERATOR
							+ historyData.getLongitude());
			if (insertionyearmap.containsKey(historyData.getYear())) {// key
																		// contains
																		// year
				HashMap<String, HashMap<String, WeatherHistoryDataDto>> insertionmonthmap = insertionyearmap
						.get(historyData.getYear());
				if (insertionmonthmap.containsKey(historyData.getMonth())) {// key
																			// contains
																			// month
					HashMap<String, WeatherHistoryDataDto> insertiondaymap = insertionmonthmap
							.get(historyData.getMonth());
					insertiondaymap.put(historyData.getDay(), historyData);
				} else { // key doesn't contains month then insert month
					HashMap<String, WeatherHistoryDataDto> insertiondaymap = new HashMap<String, WeatherHistoryDataDto>();
					insertiondaymap.put(historyData.getDay(), historyData);
					insertionmonthmap.put(historyData.getMonth(),
							insertiondaymap);
				}
			} else { // key doesn't contain year then insert year
				HashMap<String, WeatherHistoryDataDto> insertiondaymap = new HashMap<String, WeatherHistoryDataDto>();
				insertiondaymap.put(historyData.getDay(), historyData);
				HashMap<String, HashMap<String, WeatherHistoryDataDto>> insertionmonthmap = new HashMap<String, HashMap<String, WeatherHistoryDataDto>>();
				insertionmonthmap.put(historyData.getMonth(), insertiondaymap);
				insertionyearmap.put(historyData.getYear(), insertionmonthmap);
			}
		} else { // for brand new history weather data
			HashMap<String, WeatherHistoryDataDto> insertiondaymap = new HashMap<String, WeatherHistoryDataDto>();
			insertiondaymap.put(historyData.getDay(), historyData);
			HashMap<String, HashMap<String, WeatherHistoryDataDto>> insertionmonthmap = new HashMap<String, HashMap<String, WeatherHistoryDataDto>>();
			insertionmonthmap.put(historyData.getMonth(), insertiondaymap);
			HashMap<String, HashMap<String, HashMap<String, WeatherHistoryDataDto>>> insertionyearmap = new HashMap<String, HashMap<String, HashMap<String, WeatherHistoryDataDto>>>();
			insertionyearmap.put(historyData.getYear(), insertionmonthmap);
			weatherhistorylatlongmap.put(
					historyData.getLatitude()
							+ WeatherConstants.COLUMN_SEPERATOR
							+ historyData.getLongitude(), insertionyearmap);
		}
		return weatherhistorylatlongmap;
	}
}
