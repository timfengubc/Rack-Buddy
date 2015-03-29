package ubc.cs.cpsc310.rackbuddy.client;

import java.util.List;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;

public class CommentTable implements IsWidget{
	BikeRackData data;
	public static final int NUM_COM_PER_PAGE = 10;
	private JDOServiceAsync jdoService = GWT.create(JDOService.class);
	private ListDataProvider<Comment> dataProvider;
	private HorizontalPanel addPanel;
	private Button postButton;
	private TextBox textbox;
	LoginInfo loginInfo;
	
	public CommentTable(BikeRackData data) {
		this.data = data;
	}

	@Override
	public Widget asWidget() {
		final CellTable<Comment> table = new CellTable<Comment>();
		
		table.setPageSize(BikeRackTable.NUM_DATA_PER_PAGE);
		
		TextColumn<Comment> email = new TextColumn<Comment>() {

			@Override
			public String getValue(Comment object) {
				return object.getEmail();
			}
		};
		
		table.addColumn(email, "Email");

		TextColumn<Comment> message = new TextColumn<Comment>() {

			@Override
			public String getValue(Comment object) {
				return object.getMessage();
			}

		};
		
		table.addColumn(message, "Comment");
		
		Column<Comment, String> removeComment = new Column<Comment, String>(new ButtonCell()) {

            @Override
            public String getValue(final Comment object) {
                return "Delete";
            }
        };
        
        table.addColumn(removeComment, "Delete Comment");
        
		removeComment.setFieldUpdater(new FieldUpdater<Comment,String>(){

			@Override
			public void update(int index, Comment object, String value) {
				removeCommentByID(object.getCommentID());
			}
		});

		dataProvider = new ListDataProvider<Comment>();
        dataProvider.addDataDisplay(table);
        
		final SimplePager pager = new SimplePager();
		pager.setDisplay(table);
		
		jdoService.getRackComments(data, new AsyncCallback<List<Comment>>(){

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
			@Override
			public void onSuccess(List<Comment> result) {
				dataProvider.getList().clear();
				dataProvider.getList().addAll(result);
			    dataProvider.flush();
			    dataProvider.refresh();
			    table.redraw();	
			}

		});
		
		VerticalPanel vp = new VerticalPanel();
		initAddPanel();
		vp.add(table);
		vp.add(addPanel);
		return vp;

	}

	private void removeCommentByID(Long id) {
		if(jdoService == null){
			jdoService = GWT.create(JDOService.class);
		}
		
		jdoService.removeCommentByID(id, new AsyncCallback<Void>(){

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error has occured: " +caught.getMessage());
			}

			@Override
			public void onSuccess(Void result) {
				Window.alert("Successfully Deleted Message!");
			}
			
		});
		
	}
	
	private void initAddPanel() {
		addPanel = new HorizontalPanel();
		postButton = new Button("Post");
		postButton.setHeight("2em");
		postButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (isTextValid(textbox.getValue()) == true) {
					Comment newComment = new Comment();
					newComment.bikeRackID =  data.getId();
					newComment.email = loginInfo.getEmailAddress();
					newComment.message = textbox.getValue();
					addComment(newComment);
				} else {
				}

			}
		});
		
		textbox = new TextBox();
		textbox.setWidth("40em");
		textbox.setHeight("1em");
		
		addPanel.add(textbox);
		addPanel.add(postButton);
		
	}

		private void addComment(Comment comment) {
			jdoService.getListofFaves(loginInfo, new AsyncCallback<List<BikeRackData>>(){

				@Override
				public void onFailure(Throwable caught) {
					Window.alert(caught.getMessage());
				}

				@Override
				public void onSuccess(List<BikeRackData> result) {
					dataProvider.getList().clear();
					dataProvider.getList().addAll(result);
				    dataProvider.flush();
				    dataProvider.refresh();
				    table.redraw();	
				}
				
			});
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
