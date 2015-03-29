package ubc.cs.cpsc310.rackbuddy.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CommentDialogBox extends DialogBox {
	private HorizontalPanel addPanel;
	private Button postButton;
	private TextBox message;
	public CommentDialogBox() {
        // Set the dialog box's caption.
        setText("Comments");

        // Enable animation.
        setAnimationEnabled(true);

        // Enable glass background.
        setGlassEnabled(true);

        // DialogBox is a SimplePanel, so you have to set its widget 
        // property to whatever you want its contents to be.
        Button ok = new Button("Close");
        ok.addClickHandler(new ClickHandler() {
           public void onClick(ClickEvent event) {
              CommentDialogBox.this.hide();
           }
        });

        VerticalPanel panel = new VerticalPanel();
        initAddPanel();
        CommentTable comments = new CommentTable();
        panel.add(ok);
        panel.add(comments);
        panel.add(addPanel);
        setWidget(panel);
     }

	private void initAddPanel() {
		addPanel = new HorizontalPanel();
		postButton = new Button("Post");
		postButton.setHeight("2em");
		postButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (isTextValid(message.getValue()) == true) {
					
				} else {
				}

			}
		});
		
		message = new TextBox();
		message.setWidth("40em");
		message.setHeight("1em");
		
		addPanel.add(message);
		addPanel.add(postButton);
		
	}

		public static boolean isTextValid(String text) {

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
	}
