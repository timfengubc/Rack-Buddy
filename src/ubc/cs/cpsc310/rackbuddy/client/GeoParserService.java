package ubc.cs.cpsc310.rackbuddy.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("GeoParserService")
public interface GeoParserService extends RemoteService {
	
	public MarkerLocation getMarkerLocation(String address);
	
	public List<MarkerLocation> getAllMarkerLocation();

}
