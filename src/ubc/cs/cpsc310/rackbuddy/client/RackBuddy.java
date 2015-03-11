package ubc.cs.cpsc310.rackbuddy.client;




import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import ubc.cs.cpsc310.rackbuddy.client.JDOService;
import ubc.cs.cpsc310.rackbuddy.client.JDOServiceAsync;
import com.google.gwt.user.client.Window;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Icon;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.VerticalPanel;

public class RackBuddy implements EntryPoint {
	
	private static final String UNABLE_TO_DISPLAY_BIKE_RACK_LOCATION_ON_MAP = "Unable to display bike rack location on map...";
	private static final String SEARCH_RADIUS = "Search radius: ";
	private static final String ADDRESS_TO_SEARCH_FROM = "Address to search from:";
	private static final String _1_KM = "1 km";
	private static final String _500_M = "500 m";
	private static final String _100_M = "100 m";
	private static final String SEARCH = "Search...";
	public static final String INVALID_ADDRESS = "Please input a valid address.";
	protected static final String UNABLE_TO_DISPLAY_POI_ON_MAP = "Unable to display POI on map...";
	protected static final String DATA_LOADED = "Data loaded into database.";
	protected static final int ZOOM_LEVEL = 12;
	protected static final String POI_ICON = "http://maps.google.com/mapfiles/arrow.png";
	private LoginInfo loginInfo = null;
	private FlowPanel loginPanel = new FlowPanel();
	private Button loadData = new Button("Load Data");
	private Label loginLabel = new Label(
			"Please sign in to your Google Account to access the RackBuddy application.");
	private Anchor signInLink = new Anchor("Sign In");
	private String signOutLink = new String();
	private Button signOutButton = new Button("Sign Out");
	private MapWidget map;
	
	private VerticalPanel searchPanel;
	private Button searchButton;
	private TextBox address;
	private ListBox searchRadius;
	private GeoParserServiceAsync geoParserService = GWT.create(GeoParserService.class);
	private final JDOServiceAsync jdoService = GWT.create(JDOService.class);
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		// Check login status using login service.
		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(GWT.getHostPageBaseURL(),
				new AsyncCallback<LoginInfo>() {
					public void onFailure(Throwable error) {
					}

					public void onSuccess(LoginInfo result) {
						loginInfo = result;
						if (loginInfo.isLoggedIn()) {
							loadRackBuddy();
						} else {
							loadLogin();
						}
					}
				});
	}
	/**
	 * Assemble login panel
	 */
	private void loadLogin() {
		signInLink.setHref(loginInfo.getLoginUrl());
		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);
		RootPanel.get("rackMap").add(loginPanel);
	}

	private void loadRackBuddy() {
		
		Maps.loadMapsApi("", "2", false, new Runnable(){
			public void run() {
				buildUi();

			}
		});
		signOutLink = loginInfo.getLogoutUrl();
		
		

		// Associate the panels with the HTML host page.
		RootPanel.get("signOut").add(signOutButton);

		signOutButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.Location.assign(signOutLink);
			}
		});
		
		
		RootPanel.get("loadData").add(loadData);
		if (loginInfo.getAdmin() == false) {
			loadData.setVisible(false);
		} else {
			loadData.setVisible(true);
		}
		
		//listen on Load Rack Data Button
		  loadData.addClickHandler(new ClickHandler() {
		      public void onClick(ClickEvent event) {
		        loadRacks();
		      }
		    });
	}

	public void loadRacks() {
		 jdoService.loadRacks(new AsyncCallback<Void>() {
		    	public void onFailure(Throwable error) {
			    	 handleError(error);
			      }
		      public void onSuccess(Void ignore) {
		    		Window.alert(DATA_LOADED);
		      }
		    });
		  }
	
	private void buildUi() {
		// Open a map centered on Vancouver, BC, Canada
		LatLng vancouver = LatLng.newInstance(49.261226, -123.1139268);
		map = new MapWidget(vancouver, 2);
		map.setSize("60%", "100%");
		map.setZoomLevel(ZOOM_LEVEL);
		
		// Add bike rack markers
		displayAllMarkers();
		
		// Add some controls for the zoom level
		map.addControl(new LargeMapControl());

		LayoutPanel rackMapPanel = new LayoutPanel();
		rackMapPanel.setSize("100em", "40em");
		rackMapPanel.setStyleName("rackMap");
		rackMapPanel.add(map);

		RootPanel.get().add(rackMapPanel);

		initSearchPanel();
	}	
	
	// displaying all bike rack data from the data store as markers on the map.
	private void displayAllMarkers() {
		if (geoParserService == null) {
			geoParserService = GWT.create(GeoParserService.class);
		}

		geoParserService.getAllMarkerLocation(new AsyncCallback<List<MarkerLocation>>() {
			
					@Override
					public void onFailure(Throwable caught) {
						Window.alert(UNABLE_TO_DISPLAY_BIKE_RACK_LOCATION_ON_MAP);
					}

					@Override
					public void onSuccess(List<MarkerLocation> result) {
						map.clearOverlays();
						for (MarkerLocation markerLocation : result) {
							LatLng poi = LatLng.newInstance(
									markerLocation.getLat(),
									markerLocation.getLng());
							map.addOverlay(new Marker(poi));
						}
					}
				});		
	}
	/**
	 * Creates widgets to allow searching of bike racks from specified POI  
	 */
	private void initSearchPanel() {
		searchPanel = new VerticalPanel();

		searchButton = new Button(SEARCH);
		searchButton.addStyleName("marginTop");

		searchButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (isTextValid(address.getValue()) == true) {

					map.clearOverlays();

					displayRacksFromPOI(address.getValue().trim(), searchRadius
							.getValue(searchRadius.getSelectedIndex()));
				} else {
					Window.alert(INVALID_ADDRESS);
				}

			}
		});

		address = new TextBox();
		address.addStyleName("paddedLeft");
		address.setWidth("40em");
		address.setHeight("10px");

		searchRadius = new ListBox();
		searchRadius.setVisibleItemCount(1);
		searchRadius.addItem(_100_M);
		searchRadius.addItem(_500_M);
		searchRadius.addItem(_1_KM);

		HorizontalPanel h1 = new HorizontalPanel();
		h1.setStyleName("marginTop");

		HorizontalPanel h2 = new HorizontalPanel();
		h2.setStyleName("marginTop");

		h1.add(new Label(ADDRESS_TO_SEARCH_FROM));
		h1.add(address);

		h2.add(new Label(SEARCH_RADIUS));
		h2.add(searchRadius);

		searchPanel.add(h1);
		searchPanel.add(h2);
		searchPanel.add(searchButton);

		RootPanel.get().add(searchPanel);
	}
	/**
	 * Checks if specified text is valid
	 */
	private boolean isTextValid(String text) {

		if (text == null) {
			return false;
		}

		if (text.isEmpty()) {
			return false;
		}

		if (text.matches("\\s+")) {
			return false;
		}

		return true;
	}
	/**
	 * Displays all bike rack from a specified POI address with a given radius
	 */
	private void displayRacksFromPOI(String poiAddress,
			final String searchRadius) {
		map.clearOverlays();

		if (geoParserService == null) {
			geoParserService = GWT.create(GeoParserService.class);
		}

		geoParserService.getMarkerLocation(poiAddress,
				new AsyncCallback<MarkerLocation>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert(UNABLE_TO_DISPLAY_POI_ON_MAP);
					}

					@Override
					public void onSuccess(MarkerLocation result) {
						map.clearOverlays();

						LatLng poi = LatLng.newInstance(result.getLat(),
								result.getLng());

						Icon icon = Icon.newInstance(POI_ICON);
						MarkerOptions ops = MarkerOptions.newInstance(icon);
						Marker marker = new Marker(poi, ops);
						map.addOverlay(marker);

						map.setCenter(poi);

						map.setZoomLevel(ZOOM_LEVEL);

						displayBikeRacks(searchRadius);
					}

				});
	}
	
	private void displayBikeRacks(final String seachRadius) {
		if (geoParserService == null) {
			geoParserService = GWT.create(GeoParserService.class);
		}

		geoParserService
				.getAllMarkerLocation(new AsyncCallback<List<MarkerLocation>>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert(UNABLE_TO_DISPLAY_BIKE_RACK_LOCATION_ON_MAP);
					}

					@Override
					public void onSuccess(List<MarkerLocation> result) {

						double radius = getSearchRadius(seachRadius);

						LatLng center = map.getCenter();

						for (MarkerLocation markerLocation : result) {
							LatLng poi = LatLng.newInstance(
									markerLocation.getLat(),
									markerLocation.getLng());

							if (center.distanceFrom(poi) <= radius) {
								Marker marker = new Marker(poi);
								map.addOverlay(marker);
							}
						}
					}

				});

	}
	
	private double getSearchRadius(String searchRadius) {
		double meter = 0.0;

		if (searchRadius.equals(_100_M)) {
			meter = 100.0;
			return meter;
		}

		if (searchRadius.equals(_500_M)) {
			meter = 500.0;
			return meter;
		}

		if (searchRadius.equals(_1_KM)) {
			meter = 1000.0;
			return meter;
		}

		return meter;
	}
	
	private void handleError(Throwable error) {
	    Window.alert(error.getMessage());
	
	    }

}

//
