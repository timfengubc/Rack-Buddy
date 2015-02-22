package ubc.cs.cpsc310.rackbuddy.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("jdoService")
public interface JDOService extends RemoteService {
	
	//don't throw log in exception for now
	
	public void addBikeRackData(BikeRackData data);
	
	public void removeBikeRackData(BikeRackData data);
	
	public List<BikeRackData> getData();
	
	//try using 2 param:oldData, updatedData
	public void updateBikeRackData(BikeRackData oldData, BikeRackData updatedData);
	
	public  BikeRackData getBikeRackObject(BikeRackData data);
	
	public void removeAll();
	
}
