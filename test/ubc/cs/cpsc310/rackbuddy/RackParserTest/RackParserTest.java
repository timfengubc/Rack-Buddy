package ubc.cs.cpsc310.rackbuddy.RackParserTest;

import java.io.File;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;

import org.junit.Test;


import ubc.cs.cpsc310.rackbuddy.client.BikeRackData;

import com.opencsv.CSVReader;

import static org.junit.Assert.*;

	public class RackParserTest {
	//test1, test inputstream opening url
		
		@Test 
		public void testone() {
		InputStream input;
		//open inputstream from url
			try {
				input = new URL("ftp://webftp.vancouver.ca/opendata/bike_rack/BikeRackData.csv").openStream();
				assertNotNull("File null", input);

				Reader reader = new InputStreamReader(input, "UTF-8");
				assertNotNull("File null", reader);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	//test2, test csv reader, skip 1 line
		@Test
		public void testtwo() {
		
		
		File file = new File("test/ubc/cs/cpsc310/rackbuddy/RackParserTest/sample.csv");
		Reader reader;
		
		ArrayList<BikeRackData> racks = new ArrayList<BikeRackData>(); 
		ArrayList<BikeRackData> testrack = new ArrayList<BikeRackData>();
		testrack.add(new BikeRackData("134", "GT", "Abbott St", "East", "", 1, "Prior 2010"));
		System.out.println(testrack);
		try {
			reader = new FileReader(file);
			CSVReader csvReader = new CSVReader(reader, ',', '"', 1);
			String[] row = null;
			
			while((row = csvReader.readNext()) != null) {
				   BikeRackData rack = new BikeRackData();
				   rack.setStreetNumber(row[0]);
				   rack.setStreetName(row[1]);
				   rack.setStreetSide(row[2]);
				   rack.setSkytrainStation(row[3]);
				   rack.setBia(row[4]);
				  // 
				   rack.setNumRacks( Integer.parseInt(row[5]));
				   rack.setYearInstalled(row[6]);
				   racks.add(rack);
				   //System.out.println(rack);
				   
//				   System.out.println(row[0]);
//				   System.out.println(row[1]);
//				   System.out.println(row[2]);
//				   System.out.println(row[3]);
//				   System.out.println(row[4]);
//				   System.out.println(row[5]);
//				   System.out.println(row[6]);
				  
				}
			
				assertEquals(racks, testrack);
				csvReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		
	//test3, test csv reader, 2 entries
		@Test
		public void testthree() {
		
			File file = new File("test/ubc/cs/cpsc310/rackbuddy/RackParserTest/sample2.csv");
		Reader reader;
		
		ArrayList<BikeRackData> racks = new ArrayList<BikeRackData>(); 
		ArrayList<BikeRackData> testrack = new ArrayList<BikeRackData>();
		testrack.add(new BikeRackData("134", "GT", "Abbott St", "East", "", 1, "Prior 2010"));
		testrack.add(new BikeRackData("134", "GT", "Abbott St", "East", "", 1, "2012"));
		
		try {
			reader = new FileReader(file);
			CSVReader csvReader = new CSVReader(reader, ',', '"', 1);
			String[] row = null;
			
			while((row = csvReader.readNext()) != null) {
				   BikeRackData rack = new BikeRackData();
				   rack.setStreetNumber(row[0]);
				   rack.setStreetName(row[1]);
				   rack.setStreetSide(row[2]);
				   rack.setSkytrainStation(row[3]);
				   rack.setBia(row[4]);
				  // 
				   rack.setNumRacks( Integer.parseInt(row[5]));
				   rack.setYearInstalled(row[6]);
				   racks.add(rack);
				  
				}
			
				assertEquals(racks, testrack);
				csvReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		
	//test4, test csv reader, 0 entries
		@Test
		public void testfour() {
		
			File file = new File("test/ubc/cs/cpsc310/rackbuddy/RackParserTest/sample3.csv");
		Reader reader;
		
		ArrayList<BikeRackData> racks = new ArrayList<BikeRackData>(); 
		ArrayList<BikeRackData> testrack = new ArrayList<BikeRackData>();

		try {
			reader = new FileReader(file);
			CSVReader csvReader = new CSVReader(reader, ',', '"', 1);
			String[] row = null;
			
			while((row = csvReader.readNext()) != null) {
				   BikeRackData rack = new BikeRackData();
				   rack.setStreetNumber(row[0]);
				   rack.setStreetName(row[1]);
				   rack.setStreetSide(row[2]);
				   rack.setSkytrainStation(row[3]);
				   rack.setBia(row[4]);
				  // 
				   rack.setNumRacks( Integer.parseInt(row[5]));
				   rack.setYearInstalled(row[6]);
				   racks.add(rack);
				  
				}
			
				assertEquals(racks, testrack);
				csvReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}



