package ubc.cs.cpsc310.rackbuddy.server;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Transactional;


public class JDOServiceImpl extends RemoteServiceServlet implements JDOService{
	
	
	private static final Logger LOG = Logger.getLogger(JDOServiceImpl.class.getName());
	private static final PersistenceManagerFactory PMF = JDOHelper.getPersistenceManagerFactory("transactions-optional");
	
	public JDOServiceImpl() {
	}
	

	@Override
	public void addBikeRackData(BikeRackData data) {
		PersistenceManager pm = getPersistenceManager();
		try{
			pm.makePersistent(data);
		}finally{
			pm.close();
		}
	}

	@Override
	public void removeBikeRackData(BikeRackData data) {
		
		PersistenceManager pm = getPersistenceManager();
		List<BikeRackData> results = null;
		List<BikeRackData> detachedList = null;
		
		try{
			Query q = pm.newQuery(BikeRackData.class, "id == u");
			q.declareParameters("Long u");
			
			detachedList = new ArrayList<BikeRackData>();
			results = (List<BikeRackData>)q.execute(data.getId());

			for (BikeRackData brd : results) {
				detachedList.add(pm.detachCopy(brd));
			}
			
			if( (results.size() > 0) && (results.get(0).getId().equals(data.getId())) ){
				pm.deletePersistent(data);
			}
			
		}finally{
			pm.close();
		}
		
		
	}
	@Transactional
	@Override
	public List<BikeRackData> getData() {
		// http://stackoverflow.com/questions/3242217/how-do-you-make-query-results-available-after-closing-the-persistence-manager
		PersistenceManager pm = getPersistenceManager();
		List<BikeRackData> results = null;
		List<BikeRackData> detachedList = null;
		try {
			Query query = pm.newQuery(BikeRackData.class);

			results = (List<BikeRackData>) query.execute();

			detachedList = new ArrayList<BikeRackData>();

			for (BikeRackData data : results) {
				detachedList.add(pm.detachCopy(data));
			}

		} finally {
			pm.close();
		}

		return detachedList;
	}

	@Override
	public void updateBikeRackData(BikeRackData updatedData) {
		//TODO same impl as add.... might remove in the future
		PersistenceManager pm = getPersistenceManager();
		
		String streetNumber = updatedData.getStreetNumber();
		
		String streetName = updatedData.getStreetName();
		
		String streetSide = updatedData.getStreetSide();
		
		String skytrainStation = updatedData.getSkytrainStation();
		
		int numRacks = updatedData.getNumRacks();
		
		String yearInstalled = updatedData.getYearInstalled();
		
		try{
			updatedData = pm.getObjectById(BikeRackData.class, updatedData.getId());
			updatedData.setStreetName(streetName);
			updatedData.setStreetNumber(streetNumber);
			updatedData.setStreetSide(streetSide);
			updatedData.setSkytrainStation(skytrainStation);
			updatedData.setNumRacks(numRacks);
			updatedData.setYearInstalled(yearInstalled);
			pm.makePersistent(updatedData);
		}finally{
			pm.close();
		}
		
	}
	
	public PersistenceManager getPersistenceManager(){
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


	@Override
	public BikeRackData findByKey(Long key) {
		BikeRackData detachedCopy=null;
				BikeRackData	object=null;
		PersistenceManager pm= getPersistenceManager();
	    try{
	    	
	        object = pm.getObjectById(BikeRackData.class,key);
	        detachedCopy = pm.detachCopy(object);
	    }
	    finally {
	        pm.close(); // close here
	    }
	    return detachedCopy;
	}

}
