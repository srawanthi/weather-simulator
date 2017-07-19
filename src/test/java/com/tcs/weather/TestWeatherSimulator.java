package com.tcs.weather;


import org.junit.Assert;
import org.junit.Test;

import com.tcs.weather.dto.WeatherDataDto;

/**
 * 
 * @author srawanthi.samala
 *
 */
public class TestWeatherSimulator {
	
	/**
	 * testSetupProcess tests SetupProcess() method
	 * @throws Exception
	 */
	@Test
	public void testSetupProcess() throws Exception{
		String[] argsval = new String[4];

		argsval[0] = "Sydney";
		argsval[1] = "Australia";
		argsval[2] = "2017-01-14";
		argsval[3] = "src\\main\\resources\\World_Cities_Coordinators.txt";
		try{
		WeatherGameSimulator.validateArgs(argsval);
		}catch (Exception e){
			Assert.assertEquals(true, true);
		}
		String[] args = new String[5];

		args[0] = "Sydney";
		args[1] = "Australia";
		args[2] = "2017-01-14";
		args[3] = "src\\main\\resources\\World_Cities_Coordinators.txt";
		args[4] = "src\\main\\resources\\sydney_1year_hystoricaldata.txt";
		Assert.assertTrue(WeatherGameSimulator.setupProcess(args));
	}
	
	/**
	 * testsimulateWeatherData tests  simulateWeatherData() method
	 * @throws Exception
	 */
	@Test
	public void testsimulateWeatherData() throws Exception{
		String[] args = new String[5];

		args[0] = "Sydney";
		args[1] = "Australia";
		args[2] = "2017-01-14";
		args[3] = "src\\main\\resources\\World_Cities_Coordinators.txt";
		args[4] = "src\\main\\resources\\sydney_1year_hystoricaldata.txt";
		
		WeatherDataDto objWeatherDataDto = WeatherGameSimulator.simulateWeatherData(
				args[3],args[4],args[0],args[1],args[2]);


		Assert.assertEquals("temp " + objWeatherDataDto.getTemparature()
				+ "  humidity " + objWeatherDataDto.getHumidity()
				+ " pressure " + objWeatherDataDto.getPressure()
				+ " conditions " + objWeatherDataDto.getConditions()
				+ " city "+ objWeatherDataDto.getLocation()+
				" Locationtimeutc " +objWeatherDataDto.getLocaltime()+
				" position " + objWeatherDataDto.getPosition(),"temp 25.5  humidity 62.5 pressure 1004.0 conditions Rain city Sydney Locationtimeutc 2017-01-14 position -33.86785;#:151.2073212;#:1");
		
		args[0] = "Melbourne";
		args[1] = "Australia";
		args[2] = "2017-01-14";
		args[3] = "src\\main\\resources\\World_Cities_Coordinators.txt";
		args[4] = "src\\main\\resources\\sydney_1year_hystoricaldata.txt";
		
		objWeatherDataDto = WeatherGameSimulator.simulateWeatherData(
				args[3],args[4],args[0],args[1],args[2]);

		Assert.assertEquals("temp " + objWeatherDataDto.getTemparature()
				+ "  humidity " + objWeatherDataDto.getHumidity()
				+ " pressure " + objWeatherDataDto.getPressure()
				+ " conditions " + objWeatherDataDto.getConditions()
				+ " city "+ objWeatherDataDto.getLocation()+
				" Locationtimeutc " +objWeatherDataDto.getLocaltime()+
				" position " + objWeatherDataDto.getPosition(),"temp 25.5  humidity 62.5 pressure 1004.0 conditions Rain city Melbourne Locationtimeutc 2017-01-14 position -37.8139966;#:144.9633179;#:58");
		
		
		args[0] = "Melbourne";
		args[1] = "Australia";
		args[2] = "2017-01-14";
		args[3] = "src\\main\\resources\\World_Cities_Coordinators.txt";
		args[4] = "src\\main\\resources\\sydney_1year_hystoricaldata.txt";
		
		objWeatherDataDto = WeatherGameSimulator.simulateWeatherData(
				args[3],args[4],args[0],args[1],args[2]);
		
		Assert.assertEquals("temp " + objWeatherDataDto.getTemparature()
				+ "  humidity " + objWeatherDataDto.getHumidity()
				+ " pressure " + objWeatherDataDto.getPressure()
				+ " conditions " + objWeatherDataDto.getConditions()
				+ " city "+ objWeatherDataDto.getLocation()+
				" Locationtimeutc " +objWeatherDataDto.getLocaltime()+
				" position " + objWeatherDataDto.getPosition(),"temp 25.5  humidity 62.5 pressure 1004.0 conditions Rain city Melbourne Locationtimeutc 2017-01-14 position -37.8139966;#:144.9633179;#:58");
		
		args[0] = "Melbourne";
		args[1] = "Australia";
		args[2] = "2018-01-14";
		args[3] = "src\\main\\resources\\World_Cities_Coordinators.txt";
		args[4] = "src\\main\\resources\\sydney_1year_hystoricaldata.txt";
		
		objWeatherDataDto = WeatherGameSimulator.simulateWeatherData(
				args[3],args[4],args[0],args[1],args[2]);
	
		Assert.assertEquals("temp " + objWeatherDataDto.getTemparature()
				+ "  humidity " + objWeatherDataDto.getHumidity()
				+ " pressure " + objWeatherDataDto.getPressure()
				+ " conditions " + objWeatherDataDto.getConditions()
				+ " city "+ objWeatherDataDto.getLocation()+
				" Locationtimeutc " +objWeatherDataDto.getLocaltime()+
				" position " + objWeatherDataDto.getPosition(),"temp 25.5  humidity 62.5 pressure 1004.0 conditions Rain city Melbourne Locationtimeutc 2018-01-14 position -37.8139966;#:144.9633179;#:58");
		
		
		args[0] = "Melbourne";
		args[1] = "Australia";
		args[2] = "2016-09-14T10:51:10Z";
		args[3] = "src\\main\\resources\\World_Cities_Coordinators.txt";
		args[4] = "src\\main\\resources\\sydney_1year_hystoricaldata.txt";
		
		objWeatherDataDto = WeatherGameSimulator.simulateWeatherData(
				args[3],args[4],args[0],args[1],args[2]);
		
		Assert.assertEquals("temp " + objWeatherDataDto.getTemparature()
				+ "  humidity " + objWeatherDataDto.getHumidity()
				+ " pressure " + objWeatherDataDto.getPressure()
				+ " conditions " + objWeatherDataDto.getConditions()
				+ " city "+ objWeatherDataDto.getLocation()+
				" Locationtimeutc " +objWeatherDataDto.getLocaltime()+
				" position " + objWeatherDataDto.getPosition(),"temp 19.5  humidity 68.5 pressure 1011.5 conditions Rain city Melbourne Locationtimeutc 2016-09-14T10:51:10Z position -37.8139966;#:144.9633179;#:58");
		
		args[0] = "Melbourne";
		args[1] = "Australia";
		args[2] = "2015-09-14T10:51:10";
		args[3] = "src\\main\\resources\\World_Cities_Coordinators.txt";
		args[4] = "src\\main\\resources\\sydney_1year_hystoricaldata.txt";
		
		objWeatherDataDto = WeatherGameSimulator.simulateWeatherData(
				args[3],args[4],args[0],args[1],args[2]);
		
		Assert.assertEquals("temp " + objWeatherDataDto.getTemparature()
				+ "  humidity " + objWeatherDataDto.getHumidity()
				+ " pressure " + objWeatherDataDto.getPressure()
				+ " conditions " + objWeatherDataDto.getConditions()
				+ " city "+ objWeatherDataDto.getLocation()+
				" Locationtimeutc " +objWeatherDataDto.getLocaltime()+
				" position " + objWeatherDataDto.getPosition(),"temp 19.5  humidity 68.5 pressure 1011.5 conditions Rain city Melbourne Locationtimeutc 2015-09-14T10:51:10 position -37.8139966;#:144.9633179;#:58");
		
		args[0] = "Hyderabad";
		args[1] = "India";
		args[2] = "2015-09-14T10:51:10";
		args[3] = "src\\main\\resources\\World_Cities_Coordinators.txt";
		args[4] = "src\\main\\resources\\sydney_1year_hystoricaldata.txt";
		
		objWeatherDataDto = WeatherGameSimulator.simulateWeatherData(
				args[3],args[4],args[0],args[1],args[2]);
		
		Assert.assertEquals("temp " + objWeatherDataDto.getTemparature()
				+ "  humidity " + objWeatherDataDto.getHumidity()
				+ " pressure " + objWeatherDataDto.getPressure()
				+ " conditions " + objWeatherDataDto.getConditions()
				+ " city "+ objWeatherDataDto.getLocation()+
				" Locationtimeutc " +objWeatherDataDto.getLocaltime()+
				" position " + objWeatherDataDto.getPosition(),
				"temp 19.5  humidity 68.5 pressure 1011.5 conditions Rain city Hyderabad Locationtimeutc 2015-09-14T10:51:10 position 17.3752778;#:78.4744415;#:494");

	}

}
