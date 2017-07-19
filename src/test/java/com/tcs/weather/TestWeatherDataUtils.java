package com.tcs.weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;

import com.tcs.weather.bo.WeatherDataUtils;
import com.tcs.weather.constants.WeatherConstants;
import com.tcs.weather.dto.WeatherHistoryDataDto;

/**
 * 
 * @author srawanthi.samala
 * TestWeatherDataUtils contains all testcases related to WeatherDataUtils
 */
public class TestWeatherDataUtils {

	/**
	 * testGetFileContentinBuffer read weather data file content and generate bufferreader object
	 * and compares line with expected value
	 * @throws IOException
	 */
	@Test
	public void testGetFileContentinBuffer() throws IOException {
		WeatherDataUtils objWeatherDataUtils = new WeatherDataUtils();
		BufferedReader br = objWeatherDataUtils
				.getFileContentinBuffer("src/main/resources/sydney_1year_hystoricaldata.txt");

		Assert.assertEquals(br.readLine(),
				"-33.86|151.21|39|2016|7|1|15|10|6|76|59|38|1031|1029|1028|Sunny");
	}
	
	/**
	 * testparseLineToWeatherHistoryObject is testing parseLineToWeatherHistoryObject method
	 * @throws IOException
	 */
	@Test
	public void testparseLineToWeatherHistoryObject() throws IOException{
		WeatherDataUtils objWeatherDataUtils = new WeatherDataUtils();
		BufferedReader br = objWeatherDataUtils
				.getFileContentinBuffer("src/main/resources/sydney_1year_hystoricaldata.txt");
		String line = br.readLine();
		WeatherHistoryDataDto objWeatherHistoryDataDto =objWeatherDataUtils.parseLineToWeatherHistoryObject(line.split(WeatherConstants.ROW_SPLIT_SEPERATOR));

		Assert.assertEquals(objWeatherHistoryDataDto.getConditions(),"Sunny");
		Assert.assertEquals(objWeatherHistoryDataDto.getTempAvg(),"6");
		Assert.assertEquals(objWeatherHistoryDataDto.getTempMax(),"15");
		Assert.assertEquals(objWeatherHistoryDataDto.getTempMin(),"10");
		Assert.assertEquals(objWeatherHistoryDataDto.getHumAvg(),"59");
		Assert.assertEquals(objWeatherHistoryDataDto.getHumMax(),"76");
		Assert.assertEquals(objWeatherHistoryDataDto.getHumMin(),"38");
		Assert.assertEquals(objWeatherHistoryDataDto.getDay(),"1");
		Assert.assertEquals(objWeatherHistoryDataDto.getYear(),"2016");
		Assert.assertEquals(objWeatherHistoryDataDto.getMonth(),"7");
		Assert.assertEquals(objWeatherHistoryDataDto.getLatitude(),"-33.9");
		Assert.assertEquals(objWeatherHistoryDataDto.getLongitude(),"151.2");
		Assert.assertEquals(objWeatherHistoryDataDto.getElevation(),"39");
		Assert.assertEquals(objWeatherHistoryDataDto.getSeaPressureAvg(),"1029");
		Assert.assertEquals(objWeatherHistoryDataDto.getSeaPressureMax(),"1031");		
		Assert.assertEquals(objWeatherHistoryDataDto.getSeaPressureMin(),"1028");
		
	}
	
	/**
	 * @throws IOException 
	 * 
	 */
	@Test
	public void testclassifyHistoryYearWiseData() throws IOException{
		WeatherDataUtils objWeatherDataUtils = new WeatherDataUtils();
		BufferedReader br = objWeatherDataUtils
				.getFileContentinBuffer("src/main/resources/sydney_1year_hystoricaldata.txt");
		String line = br.readLine();
		HashMap<String, HashMap<String, HashMap<String, HashMap<String, WeatherHistoryDataDto>>>> weatherhistorylonglatmap = new 
				HashMap<String, HashMap<String, HashMap<String, HashMap<String, WeatherHistoryDataDto>>>> ();

		WeatherHistoryDataDto objWeatherHistoryDataDto = objWeatherDataUtils.parseLineToWeatherHistoryObject(line.split(WeatherConstants.ROW_SPLIT_SEPERATOR));
		weatherhistorylonglatmap = objWeatherDataUtils.classifyHistoryYearWiseData(weatherhistorylonglatmap, objWeatherHistoryDataDto);
		line = br.readLine();
		objWeatherHistoryDataDto = objWeatherDataUtils.parseLineToWeatherHistoryObject(line.split(WeatherConstants.ROW_SPLIT_SEPERATOR));
		weatherhistorylonglatmap = objWeatherDataUtils.classifyHistoryYearWiseData(weatherhistorylonglatmap, objWeatherHistoryDataDto);
		Assert.assertEquals(weatherhistorylonglatmap.size()+"",""+1);

		
	}
	

}
