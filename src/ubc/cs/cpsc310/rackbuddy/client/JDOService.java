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
	
	public void updateBikeRackData(BikeRackData updatedData);
	
	public BikeRackData findByKey(Long key);
	
}
