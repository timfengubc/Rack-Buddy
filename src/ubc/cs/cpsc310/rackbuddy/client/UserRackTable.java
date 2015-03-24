package ubc.cs.cpsc310.rackbuddy.client;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;

public class UserRackTable implements IsWidget {
	
	LoginInfo loginInfo;
	
	public UserRackTable(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
	}

	/**
	 * A simple data type that represents a contact.
	 */
	private static class Contact {
		private final String address;
		private final Date birthday;
		private final String name;

		public Contact(String name, Date birthday, String address) {
			this.name = name;
			this.birthday = birthday;
			this.address = address;
		}
	}

	/**
	 * The list of data to display.
	 */
	@SuppressWarnings("deprecation")
	private static final List<Contact> CONTACTS = Arrays.asList(new Contact(
			"John", new Date(80, 4, 12), "123 Abc Avenue"), new Contact("Joe",
			new Date(85, 2, 22), "22 Lance Ln"), new Contact("Tom", new Date(
			85, 3, 22), "33 Lance Ln"), new Contact("Jack",
			new Date(85, 4, 22), "44 Lance Ln"), new Contact("Tim", new Date(
			85, 5, 22), "55 Lance Ln"), new Contact("Mike",
			new Date(85, 6, 22), "66 Lance Ln"), new Contact("George",
			new Date(46, 6, 6), "77 Lance Ln"));

	





	@Override
	public Widget asWidget() {
		// Create a CellTable.
				final CellTable<UserBikeRackData> table = new CellTable<UserBikeRackData>();
				// Display 3 rows in one page
				table.setPageSize(BikeRackTable.NUM_DATA_PER_PAGE);

				TextColumn<UserBikeRackData> stNum = new TextColumn<UserBikeRackData>() {
					@Override
					public String getValue(UserBikeRackData object) {
						return object.getStreetNumber();
					}
				};
				table.addColumn(stNum, BikeRackTable.ST_NUMBER);

				TextColumn<UserBikeRackData> stName = new TextColumn<UserBikeRackData>() {
					@Override
					public String getValue(UserBikeRackData object) {
						return object.getStreetName();
					}
				};
				table.addColumn(stName, BikeRackTable.ST_NAME);
				
				TextColumn<UserBikeRackData> stSide = new TextColumn<UserBikeRackData>() {
					@Override
					public String getValue(UserBikeRackData object) {
						return object.getStreetSide();
					}
				};
				table.addColumn(stSide, BikeRackTable.ST_SIDE);
				
				TextColumn<UserBikeRackData> skytrainName = new TextColumn<UserBikeRackData>() {
					@Override
					public String getValue(UserBikeRackData object) {
						return object.getSkytrainStation();
					}
				};
				table.addColumn(skytrainName, BikeRackTable.SKYTRAIN_STATION_NAME);
				
				TextColumn<UserBikeRackData> bia = new TextColumn<UserBikeRackData>() {
					@Override
					public String getValue(UserBikeRackData object) {
						return object.getBia();
					}
				};
				table.addColumn(bia, BikeRackTable.BIA2);
				
				TextColumn<UserBikeRackData> numRacks = new TextColumn<UserBikeRackData>() {
					@Override
					public String getValue(UserBikeRackData object) {
						return String.valueOf(object.getNumRacks());
					}
				};
				table.addColumn(numRacks, BikeRackTable.NUM_RACKS);
				
				TextColumn<UserBikeRackData> yearsInstalled = new TextColumn<UserBikeRackData>() {
					@Override
					public String getValue(UserBikeRackData object) {
						return object.getYearInstalled();
					}
				};
				table.addColumn(yearsInstalled, BikeRackTable.YEARS_INSTALLED);
				
				Column<UserBikeRackData, Boolean> checkBoxCol = new Column<UserBikeRackData, Boolean>(
						new CheckboxCell()) {
					@Override
					public Boolean getValue(UserBikeRackData object) {
						return object.isFave();
					}
				};
				
//				checkBoxCol.setFieldUpdater(new FieldUpdater<BikeRackData,Boolean>(){
//
//					@Override
//					public void update(int index, BikeRackData object, Boolean value) {
//						
//						if(value == true){
//							object.setFave(true);
//							loginInfo.setBikeRackID(object.getId());
//							addNewFavBikeRack(loginInfo);
//						}else{
//							object.setFave(false);
//							loginInfo.setBikeRackID(object.getId());
//							deleteFavBikeRack(loginInfo);
//						}
//					}
//
//				});

				table.addColumn(checkBoxCol, BikeRackTable.MARK_AS_FAVORITE);

				// Associate an async data provider to the table
				// XXX: Use AsyncCallback in the method onRangeChanged
				// to actaully get the data from the server side
//				AsyncDataProvider<UserBikeRackData> provider = new AsyncDataProvider<UserBikeRackData>() {
//					@Override
//					protected void onRangeChanged(HasData<UserBikeRackData> display) {
//						int start = display.getVisibleRange().getStart();
//						int end = start + display.getVisibleRange().getLength();
//						end = end >= CONTACTS.size() ? CONTACTS.size() : end;
//						List<UserBikeRackData> sub = CONTACTS.subList(start, end);
//						updateRowData(start, sub);
//					}
//				};
//				provider.addDataDisplay(table);
//				provider.updateRowCount(CONTACTS.size(), true);

				SimplePager pager = new SimplePager();
				pager.setDisplay(table);

				VerticalPanel vp = new VerticalPanel();
				vp.add(new Label("User's Bike Rack Location"));
				vp.add(table);
				vp.add(pager);
				return vp;
	}
}