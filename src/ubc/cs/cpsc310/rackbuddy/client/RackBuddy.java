package ubc.cs.cpsc310.rackbuddy.client;



import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
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
	
	public static final String INVALID_ADDRESS = "Please input a valid address.";
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
		
		//TODO: to be implemented

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
	}

	private void buildUi() {
		LatLng vancouver = LatLng.newInstance(49.261226,-123.1139268);
		map = new MapWidget(vancouver, 2);		
		map.setSize("60%", "100%");
		map.setZoomLevel(12);
		map.addControl(new LargeMapControl());
		map.addOverlay(new Marker(vancouver));
	
		LayoutPanel rackMapPanel = new LayoutPanel();
		rackMapPanel.setSize("100em", "40em");
		rackMapPanel.setStyleName("rackMap");
		rackMapPanel.add(map);
		
		RootPanel.get().add(rackMapPanel);
		
		initSearchPanel();
	}

	private void initSearchPanel() {
		searchPanel = new VerticalPanel();
		
		searchButton = new Button("Search...");
		searchButton.addStyleName("marginTop");
		
		searchButton.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				if(isTextValid(address.getValue()) == true){
					//TODO retrieve results
				}else{
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
		searchRadius.addItem("100 m");
		searchRadius.addItem("500 m");
		searchRadius.addItem("1 km");
		
		HorizontalPanel h1 = new HorizontalPanel();
		h1.setStyleName("marginTop");
		
		HorizontalPanel h2 = new HorizontalPanel();
		h2.setStyleName("marginTop");
		
		h1.add(new Label("Address to search from:"));
		h1.add(address);
		
		h2.add(new Label("Search radius: "));
		h2.add(searchRadius);
		
		searchPanel.add(h1);
		searchPanel.add(h2);
		searchPanel.add(searchButton);
		
		RootPanel.get().add(searchPanel);
	}
	
	private boolean isTextValid(String text){
		
		if(text == null){
			return false;
		}
		
		if(text.isEmpty()){
			return false;
		}
		
		if(text.matches("\\s+")){
			return false;
		}
		
		return true;
	}
	
}
