package ubc.cs.cpsc310.rackbuddy.client;

import org.json.JSONObject;

import com.google.gwt.user.client.rpc.RemoteService;

public interface GeoParserService extends RemoteService{
	
	public double getLatitude(String address);
	
	public double getLongitude(String address);
	
	public String makeRequestURL(String address);
	
	public void getGeoCodeJSON(String address);
	
	public JSONObject getLocationJSONObject(JSONObject jsonObject);
// asdf
}
