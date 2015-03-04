package ubc.cs.cpsc310.rackbuddy.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;

import com.google.gwt.user.client.ui.Anchor;

public class RackBuddy implements EntryPoint {

	private final JDOServiceAsync jdoRPC = GWT.create(JDOService.class);
	private VerticalPanel mainPanel = new VerticalPanel();
	private LoginInfo loginInfo = null;
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label(
			"Please sign in to your Google Account to access the RackBuddy application.");
	private Anchor signInLink = new Anchor("Sign In");
	private Anchor signOutLink = new Anchor("Sign Out");

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
		// Window.alert("loaded");
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
		/**
		 * To be implemented
		 */
		
		mainPanel.add(signOutLink);
		
		// Associate the Main panel with the HTML host page.
		RootPanel.get("rackMap").add(mainPanel);
	}
	/**
	 * Function to test get method Feel free to remove it
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
	 * Function to test add method Feel free to remove it
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
	 * Function to test update method Feel free to remove it
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
	 * Function to test remove method Feel free to remove
	 */
	private void removeData(List<BikeRackData> toBeRemoved) {

		for (BikeRackData data : toBeRemoved) {

			jdoRPC.removeBikeRackData(data, new AsyncCallback<Void>() {

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
	 * Function to test remove all method Feel free to remove
	 */
	private void removeAll() {
		jdoRPC.removeAll(new AsyncCallback<Void>() {

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
