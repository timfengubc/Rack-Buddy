package ubc.cs.cpsc310.rackbuddy.server;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import ubc.cs.cpsc310.rackbuddy.client.BikeRackData;
import ubc.cs.cpsc310.rackbuddy.client.GeoParserService;
import ubc.cs.cpsc310.rackbuddy.client.MarkerLocation;

public class GeoParserServiceImpl extends RemoteServiceServlet implements GeoParserService {
	
	private GeoParser myGeoParser;
	private static final String MY_API_KEY = "";
	private JDOServiceImpl jdoService;
	
	public GeoParserServiceImpl() {
		this.myGeoParser = new GeoParser(MY_API_KEY);
		jdoService = new JDOServiceImpl();
	}

	@Override
	public MarkerLocation getMarkerLocation(String address) {
		double lat = myGeoParser.getLatitude(address);
		double lng = myGeoParser.getLongitude(address);
		MarkerLocation result = new MarkerLocation(lat,lng);
		return result;
	}

	@Override
	public List<MarkerLocation> getAllMarkerLocation() {
		List<MarkerLocation> markers = new ArrayList<MarkerLocation>();
		List<BikeRackData> datas = jdoService.getAllData();
		
		for(BikeRackData brd : datas){
			String address = brd.getStreetNumber() + " " + brd.getStreetName() + ", Vancouver, BC";
			double lat = myGeoParser.getLatitude(address);
			double lng = myGeoParser.getLongitude(address);
			MarkerLocation marker = new MarkerLocation(lat,lng);
			markers.add(marker);
		}
		
		return markers;
	}
	
	
}
