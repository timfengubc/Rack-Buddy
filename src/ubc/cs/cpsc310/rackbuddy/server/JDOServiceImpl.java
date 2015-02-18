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


public class JDOServiceImpl extends RemoteServiceServlet implements JDOService {
	
	private static final Logger LOG = Logger.getLogger(JDOServiceImpl.class.getName());
	private static final PersistenceManagerFactory PMF = JDOHelper.getPersistenceManagerFactory("transactions-optional");

	@Override
	public void addBikeRackData(BikeRackData data) throws NotLoggedInException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeBikeRackData(BikeRackData data)
			throws NotLoggedInException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<BikeRackData> getData() throws NotLoggedInException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateBikeRackData(BikeRackData updatedData)
			throws NotLoggedInException {
		// TODO Auto-generated method stub
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
