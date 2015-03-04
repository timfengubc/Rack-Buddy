package ubc.cs.cpsc310.rackbuddy.client;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class RackBuddy implements EntryPoint {
	
	JDOServiceAsync service = GWT.create(JDOService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
	}

}
