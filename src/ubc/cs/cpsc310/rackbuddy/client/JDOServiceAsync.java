package ubc.cs.cpsc310.rackbuddy.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface JDOServiceAsync {

	void addBikeRackData(BikeRackData data, AsyncCallback<Void> callback);

	void removeBikeRackData(BikeRackData data, AsyncCallback<Void> callback);

	void getData(AsyncCallback<List<BikeRackData>> callback);

	void updateBikeRackData(BikeRackData updatedData,
			AsyncCallback<Void> callback);
}
