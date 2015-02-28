package ubc.cs.cpsc310.rackbuddy.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class RackBuddy implements EntryPoint {

	private JDOServiceAsync jdoRPC = GWT.create(JDOService.class);
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		/**
		 * Testing code here
		 * Feel free to remove
		 */
		BikeRackData b1 = new BikeRackData("street num 1", "bia 1", "street name 1", "streetside 1", "skytrain stn 1", 1, "year installed 1");
		BikeRackData b2 = new BikeRackData("street num 2", "bia 2", "street name 2", "streetside 2", "skytrain stn 2", 2, "year installed 2");
		BikeRackData b3 = new BikeRackData("street num 3", "bia 3", "street name 3", "streetside 3", "skytrain stn 3", 3, "year installed 3");
		List<BikeRackData> racks = new ArrayList<BikeRackData>();;
		racks.add(b1);
		racks.add(b2);
		racks.add(b3);
		
		add(racks);
		
		jdoRPC.getAllData(new AsyncCallback<List<BikeRackData>>() {

			@Override
			public void onFailure(Throwable caught) {

			}

			@Override
			public void onSuccess(List<BikeRackData> result) {
				findById(result);
			}

		});
	}
	/**
	 * Function to test get method
	 * Feel free to remove it
	 */
	private void get() {
		jdoRPC.getAllData(new AsyncCallback<List<BikeRackData>>() {

			@Override
			public void onFailure(Throwable caught) {

			}

			@Override
			public void onSuccess(List<BikeRackData> result) {

			}

		});
	}
	
	/**
	 * Function to test add method
	 * Feel free to remove it
	 */
	private void add(List<BikeRackData> datas) {
		for (BikeRackData data : datas) {
			jdoRPC.addBikeRackData(data, new AsyncCallback<Void>() {

				@Override
				public void onFailure(Throwable caught) {

				}

				@Override
				public void onSuccess(Void result) {
					// Window.alert("successfully added");
				}

			});

		}
	}
	/**
	 * Function to test update method
	 * Feel free to remove it
	 */
	private void updateData(List<BikeRackData> result) {

		for (BikeRackData data : result) {

			BikeRackData temp = new BikeRackData(data);

			temp.setBia("modified bia");
			temp.setNumRacks(temp.getNumRacks() + 20);
			temp.setSkytrainStation(" modified skytrain station");
			temp.setStreetName(" modified street name");
			temp.setStreetNumber(" updated street number");
			temp.setStreetSide(" updated street side");
			temp.setYearInstalled(" modified year installed");

			jdoRPC.updateBikeRackData(data, temp, new AsyncCallback<Void>() {

				@Override
				public void onFailure(Throwable caught) {

				}

				@Override
				public void onSuccess(Void result) {
					Window.alert("updated");
				}

			});

		}

	}
	/**
	 * Function to test remove method
	 * Feel free to remove
	 */
	private void removeData(List<BikeRackData> toBeRemoved){
		
		for(BikeRackData data : toBeRemoved){
			
			jdoRPC.removeBikeRackData(data, new AsyncCallback<Void>(){

				@Override
				public void onFailure(Throwable caught) {
					
				}

				@Override
				public void onSuccess(Void result) {
					Window.alert("successfully removed");
				}
				
			});
		}
	}
	/**
	 * Function to test remove all method
	 * Feel free to remove
	 */
	private void removeAll(){
		jdoRPC.removeAll(new AsyncCallback<Void>(){

			@Override
			public void onFailure(Throwable caught) {
				
			}

			@Override
			public void onSuccess(Void result) {
				Window.alert("successfully removed all");
			}
			
		});
	}
	/**
	 * Function to test remove all method
	 * Feel free to remove
	 */
	private void findById(List<BikeRackData> datas){
		for(BikeRackData brd : datas){
			jdoRPC.findDataById(brd.getId(), new AsyncCallback<BikeRackData>(){

				@Override
				public void onFailure(Throwable caught) {
					
				}

				@Override
				public void onSuccess(BikeRackData result) {
					if(result == null){
						Window.alert("null was found");
					}else{
						Window.alert(result.toString());
					}
				}
				
			});
		}
	}
}
