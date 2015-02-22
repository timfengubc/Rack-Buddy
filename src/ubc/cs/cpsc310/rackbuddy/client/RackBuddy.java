package ubc.cs.cpsc310.rackbuddy.client;

import java.util.ArrayList;
import java.util.List;

import ubc.cs.cpsc310.rackbuddy.shared.FieldVerifier;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class RackBuddy implements EntryPoint {

	private JDOServiceAsync jdoRPC = GWT.create(JDOService.class);
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		List<BikeRackData> datas = new ArrayList<BikeRackData>();
		
		final BikeRackData newData = new BikeRackData("somestreetNum", "some bia", "some street name", " some street side", "some skytrain stn",
												100, "year installed somewhere...");
		
		
		BikeRackData newData2 = new BikeRackData("somestreetNum2", "some bia 2", "some street name2", " some street side2", "some skytrain stn2",
				900, "year installed somewhere...2");
		
		BikeRackData newData3 = new BikeRackData("somestreetNum3", "some bia 3", "some street name3", " some street side3", "some skytrain stn3",
				900, "year installed somewhere...3");
		
		BikeRackData nullData = new BikeRackData(null,null, null, null, null,
				3, null);
		
//		datas.add(newData);
//		datas.add(newData2);
//		datas.add(newData3);
		datas.add(nullData);

		//add(datas);

//		BikeRackData dne = new BikeRackData();

//		datas.add(new BikeRackData());
//		removeData(datas);
		
		//removeAll();
		
		
		updateData(datas);
		
		}

	private void get() {
		jdoRPC.getAllData(new AsyncCallback<List<BikeRackData>>(){

			@Override
			public void onFailure(Throwable caught) {
				
			}

			@Override
			public void onSuccess(List<BikeRackData> result) {
				
				//Window.alert(Integer.toString(result.size()));
				
//				removeData(result);
				//updateData(result);
				
			}
			
		});
	}

	private void add(List<BikeRackData> datas) {
		for(BikeRackData data : datas){
			jdoRPC.addBikeRackData(data, new AsyncCallback<Void>(){

				@Override
				public void onFailure(Throwable caught) {
					
				}

				@Override
				public void onSuccess(Void result) {
					//Window.alert("successfully added");
				}
				
			});
			
		}
	}
	
	private void updateData(List<BikeRackData> result){
			
		for(BikeRackData data : result){
			
		BikeRackData temp = new BikeRackData(data);
			
		temp.setBia( "modified bia");
		temp.setNumRacks(temp.getNumRacks() + 20);
		
		temp.setSkytrainStation( " modified skytrain station");
		
		temp.setStreetName( " modified street name");
		
		temp.setStreetNumber( " updated street number");
		
		temp.setStreetSide(" updated street side");
		
		temp.setYearInstalled( " modified year installed");

		jdoRPC.updateBikeRackData(data,temp, new AsyncCallback<Void>(){

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
}
