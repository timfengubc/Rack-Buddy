package ubc.cs.cpsc310.rackbuddy.client;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.DateCell;
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

public class BikeRackTable implements IsWidget {

	private static final String BIKE_RACK_LOCATIONS_IN_THE_CITY_OF_VANCOUVER = "Official Bike Rack Locations in the City of Vancouver";



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
				final CellTable<BikeRackData> table = new CellTable<BikeRackData>();
				
				table.setPageSize(20);

				// Add a text column to show the street num.
				TextColumn<BikeRackData> stNum = new TextColumn<BikeRackData>() {
					@Override
					public String getValue(BikeRackData object) {
						return object.getStreetNumber();
					}
				};
				table.addColumn(stNum, "St. Number");

				// Add a date column to show the birthday.
//				DateCell dateCell = new DateCell();
//				Column<Contact, Date> dateColumn = new Column<Contact, Date>(dateCell) {
//					@Override
//					public Date getValue(Contact object) {
//						return object.birthday;
//					}
//				};
//				table.addColumn(dateColumn, "Birthday");

				// Add a text column to show the street name.
				TextColumn<BikeRackData> stName = new TextColumn<BikeRackData>() {
					@Override
					public String getValue(BikeRackData object) {
						return object.getStreetName();
					}
				};
				table.addColumn(stName, "St. Name");
				
				TextColumn<BikeRackData> stSide = new TextColumn<BikeRackData>() {
					@Override
					public String getValue(BikeRackData object) {
						return object.getStreetName();
					}
				};
				table.addColumn(stSide, "St. Side");
				
				TextColumn<BikeRackData> skytrainStn = new TextColumn<BikeRackData>() {
					@Override
					public String getValue(BikeRackData object) {
						return object.getSkytrainStation();
					}
				};
				table.addColumn(skytrainStn, "Skytrain Station Name");
				
				TextColumn<BikeRackData> bia = new TextColumn<BikeRackData>() {
					@Override
					public String getValue(BikeRackData object) {
						return object.getBia();
					}
				};
				table.addColumn(bia, "BIA");
				
				TextColumn<BikeRackData> numRacks = new TextColumn<BikeRackData>() {
					@Override
					public String getValue(BikeRackData object) {
						return String.valueOf(object.getNumRacks());
					}
				};
				table.addColumn(numRacks, "# of Racks");
				
				TextColumn<BikeRackData> yearsInstalled = new TextColumn<BikeRackData>() {
					@Override
					public String getValue(BikeRackData object) {
						return object.getYearInstalled();
					}
				};
				table.addColumn(yearsInstalled, "Years Installed");

				// Associate an async data provider to the table
				// XXX: Use AsyncCallback in the method onRangeChanged
				// to actaully get the data from the server side
//				AsyncDataProvider<Contact> provider = new AsyncDataProvider<Contact>() {
//					@Override
//					protected void onRangeChanged(HasData<Contact> display) {
//						int start = display.getVisibleRange().getStart();
//						int end = start + display.getVisibleRange().getLength();
//						end = end >= CONTACTS.size() ? CONTACTS.size() : end;
//						List<Contact> sub = CONTACTS.subList(start, end);
//						updateRowData(start, sub);
//					}
//				};
//				provider.addDataDisplay(table);
//				provider.updateRowCount(CONTACTS.size(), true);

				SimplePager pager = new SimplePager();
				pager.setDisplay(table);

				VerticalPanel vp = new VerticalPanel();
				vp.add(new Label(BIKE_RACK_LOCATIONS_IN_THE_CITY_OF_VANCOUVER));
				vp.add(table);
				vp.add(pager);
				return vp;
	}
}
