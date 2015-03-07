package ubc.cs.cpsc310.rackbuddy.client;

import java.util.ArrayList;
import java.util.List;

import ubc.cs.cpsc310.rackbuddy.server.GeoParser;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.maps.client.InfoWindowContent;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Anchor;


public class RackBuddy implements EntryPoint {
	
	private VerticalPanel mainPanel = new VerticalPanel();
	private LoginInfo loginInfo = null;
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label(
			"Please sign in to your Google Account to access the RackBuddy application.");
	private Anchor signInLink = new Anchor("Sign In");
	private Anchor signOutLink = new Anchor("Sign Out");
	
	private GeoParserServiceAsync service = GWT.create(GeoParserService.class);
	List<Double> coords = new ArrayList<Double>();
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		// Check login status using login service.
//		LoginServiceAsync loginService = GWT.create(LoginService.class);
//		loginService.login(GWT.getHostPageBaseURL(),
//				new AsyncCallback<LoginInfo>() {
//					public void onFailure(Throwable error) {
//					}
//
//					public void onSuccess(LoginInfo result) {
//						loginInfo = result;
//						if (loginInfo.isLoggedIn()) {
//							loadRackBuddy();
//						} else {
//							loadLogin();
//						}
//					}
//				});
		// Window.alert("loaded");
		
		service.getMarkerLocation("6488 University Blvd, Vancouver, BC", new AsyncCallback<MarkerLocation>(){

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(final MarkerLocation result) {
				
				 coords.add(result.getLat());
				 coords.add(result.getLng());
				
				 Maps.loadMapsApi("", "2", false, new Runnable() {
				      public void run() {
				        buildUi();
				      }
				    });
			}
			
		});
		

	}
	
	private void loadLogin() {
		// Assemble login panel.
		signInLink.setHref(loginInfo.getLoginUrl());
		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);
		RootPanel.get("rackMap").add(loginPanel);

	}

	private void loadRackBuddy() {
		signOutLink.setHref(loginInfo.getLogoutUrl());

		
		mainPanel.add(signOutLink);
		

		
		// Associate the Main panel with the HTML host page.
		RootPanel.get("rackMap").add(mainPanel);
	}
	
	  private void buildUi() {
		    // Open a map centered on Cawker City, KS USA
		    //LatLng vancouver = LatLng.newInstance(49.261226,-123.1139268);
		    LatLng ponderosa = LatLng.newInstance(coords.get(0), coords.get(1));
		    
		    final MapWidget map = new MapWidget(ponderosa, 2);
		    map.setSize("60%", "100%");
		    map.setZoomLevel(12);
		    // Add some controls for the zoom level
		    map.addControl(new LargeMapControl());

		    // Add a marker
		    //map.addOverlay(new Marker(vancouver));
		   map.addOverlay(new Marker(ponderosa));
		    
		    // Add an info window to highlight a point of interest
		    map.getInfoWindow().open(ponderosa,
		        new InfoWindowContent("Ponderosa"));

		    final DockLayoutPanel dock = new DockLayoutPanel(Unit.PX);
		    dock.addNorth(map, 500);

		    // Add the map to the HTML host page
		    RootLayoutPanel.get().add(dock);
		  }
}
