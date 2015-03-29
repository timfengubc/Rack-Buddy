package ubc.cs.cpsc310.rackbuddy.client;

import java.util.ArrayList;
import java.util.List;

import ubc.cs.cpsc310.rackbuddy.shared.AlreadyFavoritedException;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.MultiSelectionModel;

public class BikeRackTable implements IsWidget {

	private static final String BIKE_RACK_NOT_MARKED_AS_FAVORITE_THIS_BIKE_RACK_HAS_ALREADY_BEEN_MARKED_AS_FAVORITE = "Bike rack not marked as favorite: This bike rack has already been marked as favorite.";
	public static final String MARK_AS_FAVORITE = "Mark as favorite?";
	public static final String UNMARK_AS_FAVORITE = "Unmark as favorite?";
	public static final String YES = "Yes";

	public static final int NUM_DATA_PER_PAGE = 10;

	public static final String YEARS_INSTALLED = "Years Installed";

	public static final String NUM_RACKS = "# of Racks";

	public static final String BIA2 = "BIA";

	public static final String SKYTRAIN_STATION_NAME = "Skytrain Station Name";

	public static final String ST_SIDE = "St. Side";

	public static final String ST_NAME = "St. Name";

	public static final String ST_NUMBER = "St. Number";

	public static final String BIKE_RACK_LOCATIONS_IN_THE_CITY_OF_VANCOUVER = "Official Locations";
	
	private JDOServiceAsync jdoService = GWT.create(JDOService.class);
	
	private LoginInfo loginInfo;
	
	private List<BikeRackData> racks;
	public BikeRackTable(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
	}

	@Override
	public Widget asWidget() {
				
		racks = new ArrayList<BikeRackData>();

		// Create a CellTable.
		final CellTable<BikeRackData> table = new CellTable<BikeRackData>(
				BikeRackData.KEY_PROVIDER);

		MultiSelectionModel<BikeRackData> multiSelect = new MultiSelectionModel<BikeRackData>(
				BikeRackData.KEY_PROVIDER);

		table.setSelectionModel(multiSelect);

		table.setPageSize(NUM_DATA_PER_PAGE);

		SimplePager pager = new SimplePager(TextLocation.CENTER, true, true);

		pager.setPageSize(NUM_DATA_PER_PAGE);
		pager.setDisplay(table);

		TextColumn<BikeRackData> stNum = new TextColumn<BikeRackData>() {
			@Override
			public String getValue(BikeRackData object) {
				return object.getStreetNumber();
			}
		};
		table.addColumn(stNum, ST_NUMBER);

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
				return object.getStreetSide();
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
		
		Column<BikeRackData, String> addFave = new Column<BikeRackData, String>(new ButtonCell()) {

            @Override
            public String getValue(final BikeRackData object) {
                return YES;
            }
        };
        
        addFave.setFieldUpdater(new FieldUpdater<BikeRackData,String>(){

			@Override
			public void update(int index, BikeRackData object, String value) {
				object.setFave(true);
				loginInfo.setBikeRackID(object.getId());
				addNewFavBikeRack(loginInfo);
				
			}
        	
        });
        
        table.addColumn(addFave, MARK_AS_FAVORITE);
		
		jdoService.getAllData(new AsyncCallback<List<BikeRackData>>() {

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

		VerticalPanel vp = new VerticalPanel();
		vp.add(table);
		vp.add(pager);

		return vp;
	}
	
	private void addNewFavBikeRack(final LoginInfo loginInfo) {
		if(jdoService == null){
			jdoService = GWT.create(JDOService.class);
		}
		
		jdoService.addNewFavRack(loginInfo, new AsyncCallback<Void>(){

			@Override
			public void onFailure(Throwable caught) {
				
				if(caught instanceof AlreadyFavoritedException){
					Window.alert(BIKE_RACK_NOT_MARKED_AS_FAVORITE_THIS_BIKE_RACK_HAS_ALREADY_BEEN_MARKED_AS_FAVORITE);
				}
				else{
					Window.alert("Error has occured: " +caught.getMessage());
				}
				
			}

			@Override
			public void onSuccess(Void result) {		
				AppUtils.EVENT_BUS.fireEvent(new AddFaveEvent(loginInfo));
			}
			
		});
	}
	
	public List<BikeRackData> getList(){
		return this.racks;
	}
	
	public void replaceData(List<BikeRackData> dataToReplaceWith){
		this.racks.clear();
		this.racks.addAll(dataToReplaceWith);
	}
}
