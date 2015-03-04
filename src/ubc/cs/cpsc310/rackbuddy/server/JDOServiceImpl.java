package ubc.cs.cpsc310.rackbuddy.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
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

import javax.jdo.JDOFatalInternalException;
import javax.jdo.JDOHelper;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.annotations.Transactional;

import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;


public class JDOServiceImpl extends RemoteServiceServlet implements JDOService{
		
	private static final Logger LOG = Logger.getLogger(JDOServiceImpl.class.getName());
	private static final PersistenceManagerFactory PMF = JDOHelper.getPersistenceManagerFactory("transactions-optional");
	
	public JDOServiceImpl() {
	}
	
	
	@Override
	/**
	 * Adds the BikeRackData object into the datastore
	 */
	public void addBikeRackData(BikeRackData data) {
		PersistenceManager pm = getPersistenceManager();
		try{
			pm.makePersistent(data);
		}finally{
			pm.close();
		}
	}
	/**
	 * Can't remove null data
	 * http://stackoverflow.com/questions/8868449/how-to-check-the-text-is-null-or-not-using-jdo
	 * 
	 * Removes the specified BikeRackData object from the datastore
	 */
	@Override
	public void removeBikeRackData(BikeRackData data) {

		PersistenceManager pm = getPersistenceManager();

		try {

			BikeRackData result = getBikeRackObject(data);

			List<BikeRackData> datas = getAllData();

			for (BikeRackData brd : datas) {
				if (brd.equals(result)) {
					pm.deletePersistent(result);
				}
			}

		} finally {
			pm.close();
		}
	}
	
	/**
	 * Retreives all the BikeRackData objects currently in the datasore.
	 */
	@Transactional
	@Override
	public List<BikeRackData> getAllData() {
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
	
	/**
	 * Removes all the BikeRackData objects currently in the datastore.
	 */
	@Override
	public void removeAll() {
		PersistenceManager pm = getPersistenceManager();
		try{
			List<BikeRackData> removedDatas = getAllData();
			pm.deletePersistentAll(removedDatas);
			
		}finally{
			pm.close();
		}
	}
	
	/**
	 * Updates the oldData with the updatedData
	 * 
	 * oldData: the original BikeRackData object
	 * updatedData: the updated BikeRackData object
	 */
	@Override
	public void updateBikeRackData(BikeRackData oldData, BikeRackData updatedData) {
		
		PersistenceManager pm = getPersistenceManager();
		
		String streetNumber = updatedData.getStreetNumber();	
		String streetName = updatedData.getStreetName();
		String streetSide = updatedData.getStreetSide();
		String skytrainStation = updatedData.getSkytrainStation();
		String bia = updatedData.getBia();
		int numRacks = updatedData.getNumRacks();
		String yearInstalled = updatedData.getYearInstalled();
		
		try{
			
			BikeRackData result = getBikeRackObject(oldData);
			
			List<BikeRackData> datas = getAllData();
			
			for(BikeRackData brd : datas){
				if(brd.equals(result)){
					oldData = pm.getObjectById(BikeRackData.class, brd.getId());
					oldData.setStreetName(streetName);
					oldData.setBia(bia);
					oldData.setStreetNumber(streetNumber);
					oldData.setStreetSide(streetSide);
					oldData.setSkytrainStation(skytrainStation);
					oldData.setNumRacks(numRacks);
					oldData.setYearInstalled(yearInstalled);
					
					pm.makePersistent(oldData);
				}
			}
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

	/**
	 * Retrieves the specified BikeRackData object from the datastore.
	 * 
	 * Returns null if unable to find it.
	 */
	@Override
	public BikeRackData getBikeRackObject(BikeRackData data) {

		PersistenceManager pm= getPersistenceManager();
		List<BikeRackData> results = null;

		List<BikeRackData> detachedList = null;
	    try{
	    	Query q = pm.newQuery(BikeRackData.class,
	    			"streetNumber == '"+data.getStreetNumber()+"'"+
	    			" && streetName == '"+data.getStreetName()+"'"+
	    			" && streetSide == '"+data.getStreetSide()+"'"+	
	    			 " && skytrainStation == '"+data.getSkytrainStation()+"'"+
                    " && numRacks == "+ data.getNumRacks() +
                    " && yearInstalled == '"+ data.getYearInstalled() + "'" 
                    		 );
	    	
	    	results = (List<BikeRackData>) q.execute();
	    	
	    	detachedList = new ArrayList<BikeRackData>();

			for (BikeRackData brd : results) {
				detachedList.add(pm.detachCopy(brd));
			}
	       
	    }
	    finally {
	        pm.close(); 
	    }
	    
	    if(!detachedList.isEmpty() && detachedList !=null){
	    	return detachedList.get(0);	
	    }
	    
	    return null;
	}
	
	/**
	 * Returns BikeRackData object from datastore with the associated id
	 */
	public BikeRackData findDataById(Long id) {
		BikeRackData detachedCopy=null, object=null;
		PersistenceManager pm= getPersistenceManager();
	    try{
	        object = pm.getObjectById(BikeRackData.class,id);
	        detachedCopy = pm.detachCopy(object);
	    }catch (JDOObjectNotFoundException e) {
	        return null; 
	    }
	    catch(JDOFatalInternalException e){
	    	return null;
	    }
	    finally {
	        pm.close(); 
	    }
	    return detachedCopy;
	}

	/**
	 * Removes BikeRackData object from datastore with the associated id
	 */
	@Override
	public void removeDataById(Long id) {
		
		BikeRackData result = findDataById(id);
		
		if(result != null){
			PersistenceManager pm= getPersistenceManager();
			try{
				pm.deletePersistent(result);
			}finally{
				pm.close();
			}
		}
		
	}

	/**
	 * Updates the BikeRackData object from the datastore with the associated id
	 */
	@Override
	public void updateDataById(BikeRackData data) {
		
		String streetNumber = data.getStreetNumber();	
		String streetName = data.getStreetName();
		String streetSide = data.getStreetSide();
		String skytrainStation = data.getSkytrainStation();
		String bia = data.getBia();
		int numRacks = data.getNumRacks();
		String yearInstalled = data.getYearInstalled();
		
		BikeRackData result = findDataById(data.getId());
		
		if(result != null){
			PersistenceManager pm= getPersistenceManager();
			
			try{
				
				result.setStreetNumber(streetNumber);
				result.setStreetName(streetName);
				result.setStreetSide(streetSide);
				result.setSkytrainStation(skytrainStation);
				result.setBia(bia);
				result.setNumRacks(numRacks);
				result.setYearInstalled(yearInstalled);
				
				pm.makePersistent(result);
			}finally{
				pm.close();
			}
		}
		
	}

	

}
