package ubc.cs.cpsc310.rackbuddy.client;

import org.json.JSONObject;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("GeoParserService")
public interface GeoParserService extends RemoteService{
	
	public MarkerLocation getLatLng(String address);
	
	
	
	
	
	
}
