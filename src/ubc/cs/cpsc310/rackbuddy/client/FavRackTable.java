package ubc.cs.cpsc310.rackbuddy.client;

import java.util.List;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;

public class FavRackTable implements IsWidget {
	
	LoginInfo loginInfo;
	private JDOServiceAsync jdoService = GWT.create(JDOService.class);
	private AsyncDataProvider<BikeRackData> provider;
	
	public FavRackTable(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
	}

	@Override
	public Widget asWidget() {
				// Create a CellTable.
				final CellTable<BikeRackData> table = new CellTable<BikeRackData>();

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

				
				Column<BikeRackData, Boolean> checkBoxCol = new Column<BikeRackData, Boolean>(
						new CheckboxCell()) {
					@Override
					public Boolean getValue(BikeRackData object) {
						return object.isFave();
					}
				};
				
				checkBoxCol.setFieldUpdater(new FieldUpdater<BikeRackData,Boolean>(){

					@Override
					public void update(int index, BikeRackData object, Boolean value) {
						
						if(value == false){{
							object.setFave(false);
							loginInfo.setBikeRackID(object.getId());
							deleteFavBikeRack(loginInfo);
							
						}
					}

					}});

				table.addColumn(checkBoxCol, BikeRackTable.MARK_AS_FAVORITE);
				
				initProvider(loginInfo);
				
				provider.addDataDisplay(table);
			
				SimplePager pager = new SimplePager();
				pager.setDisplay(table);

				VerticalPanel vp = new VerticalPanel();
				vp.add(new Label("User's Favorite Bike Rack Location"));
				vp.add(table);
				vp.add(pager);
				return vp;
	}

	private void initProvider(final LoginInfo loginInfo) {
		provider = new AsyncDataProvider<BikeRackData>(){

			@Override
			protected void onRangeChanged(final HasData<BikeRackData> display) {
				
				jdoService.getListofFaves(loginInfo, new AsyncCallback<List<BikeRackData>>(){

					@Override
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
						
					}

					@Override
					public void onSuccess(List<BikeRackData> result) {
						int start = display.getVisibleRange().getStart();
				        int end = start + display.getVisibleRange().getLength();
				        end = end >= result.size() ? result.size() : end;
				        List<BikeRackData> sub = result.subList(start, end);
				        updateRowData(start, sub);
				        updateRowCount(result.size(), true);
					}
				});
				
			}
			
		};
	}
	
	private void deleteFavBikeRack(LoginInfo loginInfo) {
		if(jdoService == null){
			jdoService = GWT.create(JDOService.class);
		}
		
		jdoService.removeFavRack(loginInfo, new AsyncCallback<Void>(){

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error has occured: " +caught.getMessage());
			}

			@Override
			public void onSuccess(Void result) {
				Window.alert("successfully removed");
			}
			
		});
		
	}
}