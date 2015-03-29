package ubc.cs.cpsc310.rackbuddy.client;

import javax.jdo.PersistenceManager;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

public class CommentTable implements IsWidget{
	Comment comment;
	LoginInfo logininfo;
	public static final int NUM_COM_PER_PAGE = 10;
	private JDOServiceAsync jdoService = GWT.create(JDOService.class);
	private ListDataProvider<Comment> dataProvider;

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
				removeCommentByID(object.getBikeRackID());
			}
		});
		
		VerticalPanel vp = new VerticalPanel();
		vp.add(table);
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
}
