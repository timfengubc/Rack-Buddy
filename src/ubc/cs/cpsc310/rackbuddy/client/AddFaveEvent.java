package ubc.cs.cpsc310.rackbuddy.client;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.Window;

public class AddFaveEvent extends GwtEvent<AddFaveEventHandler> {
	
	public static Type<AddFaveEventHandler> TYPE = new Type<AddFaveEventHandler>();
	
	private LoginInfo loginInfo;
	
	public AddFaveEvent(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
		
		Window.alert("in constructor of addfaveevent");
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<AddFaveEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AddFaveEventHandler handler) {
		handler.onFaveAdded(this);
		
	}
	
	public LoginInfo getLoginInfo(){
		return this.loginInfo;
	}

}
