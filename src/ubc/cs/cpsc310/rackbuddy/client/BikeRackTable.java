package ubc.cs.cpsc310.rackbuddy.client;

import java.util.List;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;

public class BikeRackTable implements IsWidget {

	public static final String YEARS_INSTALLED = "Years Installed";

	public static final String NUM_RACKS = "# of Racks";

	public static final String BIA2 = "BIA";

	public static final String SKYTRAIN_STATION_NAME = "Skytrain Station Name";

	public static final String ST_SIDE = "St. Side";

	public static final String ST_NAME = "St. Name";

	public static final String ST_NUMBER = "St. Number";

	public static final String BIKE_RACK_LOCATIONS_IN_THE_CITY_OF_VANCOUVER = "Official Bike Rack Locations in the City of Vancouver";
	
	private JDOServiceAsync jdoService = GWT.create(JDOService.class);

	@Override
	public Widget asWidget() {
				// Create a CellTable.
				final CellTable<BikeRackData> table = new CellTable<BikeRackData>();
				
				table.setPageSize(10);

				// Add a text column to show the street num.
				TextColumn<BikeRackData> stNum = new TextColumn<BikeRackData>() {
					@Override
					public String getValue(BikeRackData object) {
						return object.getStreetNumber();
					}
				};
				table.addColumn(stNum, ST_NUMBER);

				// Add a text column to show the street name.
				TextColumn<BikeRackData> stName = new TextColumn<BikeRackData>() {
					@Override
					public String getValue(BikeRackData object) {
						return object.getStreetName();
					}
				};
				table.addColumn(stName, ST_NAME);
				
				TextColumn<BikeRackData> stSide = new TextColumn<BikeRackData>() {
					@Override
					public String getValue(BikeRackData object) {
						return object.getStreetName();
					}
				};
				table.addColumn(stSide, ST_SIDE);
				
				TextColumn<BikeRackData> skytrainStn = new TextColumn<BikeRackData>() {
					@Override
					public String getValue(BikeRackData object) {
						return object.getSkytrainStation();
					}
				};
				table.addColumn(skytrainStn, SKYTRAIN_STATION_NAME);
				
				TextColumn<BikeRackData> bia = new TextColumn<BikeRackData>() {
					@Override
					public String getValue(BikeRackData object) {
						return object.getBia();
					}
				};
				table.addColumn(bia, BIA2);
				
				TextColumn<BikeRackData> numRacks = new TextColumn<BikeRackData>() {
					@Override
					public String getValue(BikeRackData object) {
						return String.valueOf(object.getNumRacks());
					}
				};
				table.addColumn(numRacks, NUM_RACKS);
				
				TextColumn<BikeRackData> yearsInstalled = new TextColumn<BikeRackData>() {
					@Override
					public String getValue(BikeRackData object) {
						return object.getYearInstalled();
					}
				};
				table.addColumn(yearsInstalled, YEARS_INSTALLED);
				
				jdoService.getAllData(new AsyncCallback<List<BikeRackData>>(){

					@Override
					public void onFailure(Throwable caught) {
						
					}

					@Override
					public void onSuccess(final List<BikeRackData> result) {
						AsyncDataProvider<BikeRackData> provider = new AsyncDataProvider<BikeRackData>() {
						@Override
						protected void onRangeChanged(HasData<BikeRackData> display) {
							int start = display.getVisibleRange().getStart();
							int end = start + display.getVisibleRange().getLength();
							end = end >= result.size() ? result.size() : end;
							List<BikeRackData> sub = result.subList(start, end);
							updateRowData(start, sub);
						}
					};
					provider.addDataDisplay(table);
					provider.updateRowCount(result.size(), true);
					}
					
				});

				SimplePager pager = new SimplePager();
				pager.setDisplay(table);

				VerticalPanel vp = new VerticalPanel();
				vp.add(new Label(BIKE_RACK_LOCATIONS_IN_THE_CITY_OF_VANCOUVER));
				vp.add(table);
				vp.add(pager);
				return vp;
	}
}
