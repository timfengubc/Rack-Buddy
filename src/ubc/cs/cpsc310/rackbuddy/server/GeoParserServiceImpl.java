package ubc.cs.cpsc310.rackbuddy.server;

import ubc.cs.cpsc310.rackbuddy.client.GeoParserService;
import ubc.cs.cpsc310.rackbuddy.client.MarkerLocation;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class GeoParserServiceImpl extends RemoteServiceServlet implements GeoParserService {
	
	private GeoParser myGeoParser;
	private static final String myAPIKey = "";
	
	public GeoParserServiceImpl() {
		this.myGeoParser = new GeoParser(myAPIKey);
	}
	
	@Override
	public MarkerLocation getLatLng(String address) {
		double lat = myGeoParser.getLatitude(address);
		double lng = myGeoParser.getLongitude(address);
		MarkerLocation result = new MarkerLocation(lat,lng);
		return result;
	}

}
