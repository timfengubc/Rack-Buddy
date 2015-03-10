package ubc.cs.cpsc310.rackbuddy.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface JDOServiceAsync {

	void addBikeRackData(BikeRackData data, AsyncCallback<Void> callback);

	void removeBikeRackData(BikeRackData data, AsyncCallback<Void> callback);

	void getAllData(AsyncCallback<List<BikeRackData>> callback);

	void updateBikeRackData(BikeRackData oldData, BikeRackData updatedData,
			AsyncCallback<Void> callback);

	void getBikeRackObject(BikeRackData data, AsyncCallback<BikeRackData> callback);

	void removeAll(AsyncCallback<Void> callback);

	void findDataById(Long id, AsyncCallback<BikeRackData> callback);

	void removeDataById(Long id, AsyncCallback<Void> callback);

	void updateDataById(BikeRackData data, AsyncCallback<Void> callback);

	void loadRacks(AsyncCallback<Void> callback);
	
	void addBikeRackData(ArrayList<BikeRackData> racks, AsyncCallback<Void> callback);
}
