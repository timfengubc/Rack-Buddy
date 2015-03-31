package ubc.cs.cpsc310.rackbuddy.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.view.client.ListDataProvider;

public abstract class Table implements IsWidget {

	public static final String USER_S_FAVORITE_BIKE_RACK_LOCATION = "Favorite Locations";
	protected LoginInfo loginInfo;
	protected JDOServiceAsync jdoService = GWT.create(JDOService.class);
	protected final CellTable<BikeRackData> table = new CellTable<BikeRackData>();
	protected SimplePager pager;
	protected ListDataProvider<BikeRackData> dataProvider;

	public Table(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
		initTable();
        
	}

	protected void initTable() {
		
		dataProvider = new ListDataProvider<BikeRackData>();
		
		table.setPageSize(BikeRackTable.NUM_DATA_PER_PAGE);

		TextColumn<BikeRackData> stNum = new TextColumn<BikeRackData>() {
			@Override
			public String getValue(BikeRackData object) {
				return object.getStreetNumber();
			}
		};
		table.addColumn(stNum, BikeRackTable.ST_NUMBER);
		
		TextColumn<BikeRackData> stName = new TextColumn<BikeRackData>() {
			@Override
			public String getValue(BikeRackData object) {
				return object.getStreetName();
			}
		};
		table.addColumn(stName, BikeRackTable.ST_NAME);
		
		TextColumn<BikeRackData> stSide = new TextColumn<BikeRackData>() {
			@Override
			public String getValue(BikeRackData object) {
				return object.getStreetSide();
			}
		};
		table.addColumn(stSide, BikeRackTable.ST_SIDE);
		
		TextColumn<BikeRackData> skytrainName = new TextColumn<BikeRackData>() {
			@Override
			public String getValue(BikeRackData object) {
				return object.getSkytrainStation();
			}
		};
		table.addColumn(skytrainName, BikeRackTable.SKYTRAIN_STATION_NAME);
		
		TextColumn<BikeRackData> bia = new TextColumn<BikeRackData>() {
			@Override
			public String getValue(BikeRackData object) {
				return object.getBia();
			}
		};
		table.addColumn(bia, BikeRackTable.BIA2);
		
		TextColumn<BikeRackData> numRacks = new TextColumn<BikeRackData>() {
			@Override
			public String getValue(BikeRackData object) {
				return String.valueOf(object.getNumRacks());
			}
		};
		table.addColumn(numRacks, BikeRackTable.NUM_RACKS);
		
		TextColumn<BikeRackData> yearsInstalled = new TextColumn<BikeRackData>() {
			@Override
			public String getValue(BikeRackData object) {
				return object.getYearInstalled();
			}
		};
		table.addColumn(yearsInstalled, BikeRackTable.YEARS_INSTALLED);
		
		pager = new SimplePager(TextLocation.CENTER, true, true);
		pager.setDisplay(table);
		
		dataProvider.addDataDisplay(table);
		
	}

}