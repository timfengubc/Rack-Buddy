package ubc.cs.cpsc310.rackbuddy.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GeoParserServiceAsync {

	void getMarkerLocation(String address,
			AsyncCallback<MarkerLocation> callback);

}
