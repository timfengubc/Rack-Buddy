package ubc.cs.cpsc310.rackbuddy.client;

import com.google.gwt.core.client.EntryPoint;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
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
}
