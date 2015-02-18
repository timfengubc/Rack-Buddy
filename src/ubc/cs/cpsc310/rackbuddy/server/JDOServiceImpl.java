package ubc.cs.cpsc310.rackbuddy.server;

import java.util.List;

import ubc.cs.cpsc310.rackbuddy.client.BikeRackData;
import ubc.cs.cpsc310.rackbuddy.client.JDOService;
import ubc.cs.cpsc310.rackbuddy.client.NotLoggedInException;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.util.logging.Logger;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;


public class JDOServiceImpl extends RemoteServiceServlet implements JDOService {
	
	private static final Logger LOG = Logger.getLogger(JDOServiceImpl.class.getName());
	private static final PersistenceManagerFactory PMF = JDOHelper.getPersistenceManagerFactory("transactions-optional");
	private static final String SQL = "javax.jdo.query.SQL";

	@Override
	public void addBikeRackData(BikeRackData data) throws NotLoggedInException {
		PersistenceManager pm = getPersistenceManager();
		
		try{
			pm.makePersistent(data);
		}finally{
			pm.close();
		}
	}

	@Override
	public void removeBikeRackData(BikeRackData data)
			throws NotLoggedInException {
		
		PersistenceManager pm = getPersistenceManager();
		
		
		
		
	}

	@Override
	public List<BikeRackData> getData() throws NotLoggedInException {
		PersistenceManager pm = getPersistenceManager();
		
		Query query = pm.newQuery(SQL,"SELECT * FROM " + BikeRackData.class.getName());
		query.setClass(BikeRackData.class);
		List<BikeRackData> results = (List<BikeRackData>)query.execute();
		
		//TODO this impl might not work
		return results;
	}

	@Override
	public void updateBikeRackData(BikeRackData updatedData)
			throws NotLoggedInException {
		//TODO same impl as add.... might remove in the future
		PersistenceManager pm = getPersistenceManager();
		
		try{
			pm.makePersistent(updatedData);
		}finally{
			pm.close();
		}
	}
	
	private PersistenceManager getPersistenceManager(){
		return PMF.getPersistenceManager();
	}
	
	private User getUser(){
		UserService userService = UserServiceFactory.getUserService();
		return userService.getCurrentUser();
	}
	
	private void checkLoggedIn() throws NotLoggedInException{
		if(getUser() == null ){
			throw new NotLoggedInException("Not logged in");
		}
	}

}
