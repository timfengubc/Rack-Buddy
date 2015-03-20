package ubc.cs.cpsc310.rackbuddy.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import ubc.cs.cpsc310.rackbuddy.client.JDOService;
import ubc.cs.cpsc310.rackbuddy.client.JDOServiceAsync;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Anchor;

public class RackBuddy implements EntryPoint {

	private static final String ALL_DATA_SUCCESSFULLY_REMOVED_FROM_DATASTORE = "All data successfully removed from datastore.";
	protected static final String DATA_LOADED = "Data loaded into database.";
	private LoginInfo loginInfo = null;
	private FlowPanel loginPanel = new FlowPanel();
	private Button loadData = new Button("Load Data");
	private Button removeData = new Button("Remove all Data");
	private Label loginLabel = new Label(
			"Please sign in to your Google Account to access the RackBuddy application.");
	private Anchor signInLink = new Anchor("Sign In");
	private String signOutLink = new String();
	private Button signOutButton = new Button("Sign Out");
	private JDOServiceAsync jdoService = GWT.create(JDOService.class);
	
	private MapDisplay mapDisplay;
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

		mapDisplay = new MapDisplay();
		
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
		RootPanel.get("loadData").add(removeData);
		if (loginInfo.getAdmin() == false) {
			loadData.setVisible(false);
			removeData.setVisible(false);
		} else {
			loadData.setVisible(true);
			removeData.setVisible(true);
		}
		
		//listen on Load Rack Data Button
		  loadData.addClickHandler(new ClickHandler() {
		      public void onClick(ClickEvent event) {
		        loadRacks();
		      }
		    });
		  
		  removeData.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				removeAllData();
			}
			  
		  });
	}

	private void loadRacks() {
		 jdoService.loadRacks(new AsyncCallback<Void>() {
		    	public void onFailure(Throwable error) {
			    	 handleError(error);
			      }
		      public void onSuccess(Void ignore) {
		    		Window.alert(DATA_LOADED);

		    		mapDisplay.displayAllMarkers();
		      }
		    });
	}
	
	private void removeAllData(){
		jdoService.removeAll(new AsyncCallback<Void>(){

			@Override
			public void onFailure(Throwable caught) {
				handleError(caught);
			}

			@Override
			public void onSuccess(Void result) {
				Window.alert(ALL_DATA_SUCCESSFULLY_REMOVED_FROM_DATASTORE);
			}
			
		});
	}
	
	
		
	private void handleError(Throwable error) {
	    Window.alert(error.getMessage());
	
	    }

}

