package ubc.cs.cpsc310.rackbuddy.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("jdoService")
public interface JDOService extends RemoteService {
	
	public void addBikeRackData(BikeRackData data) throws NotLoggedInException;
	
	public void removeBikeRackData(BikeRackData data) throws NotLoggedInException;
	
	public List<BikeRackData> getData() throws NotLoggedInException;
	
	public void updateBikeRackData(BikeRackData updatedData) throws NotLoggedInException;
	
}
