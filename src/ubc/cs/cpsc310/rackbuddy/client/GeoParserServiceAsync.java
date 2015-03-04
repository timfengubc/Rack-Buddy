package ubc.cs.cpsc310.rackbuddy.client;

import org.json.JSONObject;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GeoParserServiceAsync {

public void getLatitude(String address,  AsyncCallback<Double> callback);
	
	public void getLongitude(String address, AsyncCallback<Double> callback);
	
	public void makeRequestURL(String address, AsyncCallback<String> callback);
	
	public void getGeoCodeJSON(String address, AsyncCallback<Void> callback);
	
	public void getLocationJSONObject(JSONObject jsonObject, AsyncCallback<JSONObject> callback);
	
}
