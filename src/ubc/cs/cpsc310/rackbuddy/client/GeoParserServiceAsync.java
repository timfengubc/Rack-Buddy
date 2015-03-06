package ubc.cs.cpsc310.rackbuddy.client;


import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GeoParserServiceAsync {

	public void getLatLng(String address, AsyncCallback<MarkerLocation> asyncCallback);
	
	
	
		
}
