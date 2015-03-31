package ubc.cs.cpsc310.rackbuddy.client;

import java.util.List;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

public class FavRackTable implements IsWidget {
	
	public static final String USER_S_FAVORITE_BIKE_RACK_LOCATION = "Favorite Locations";
	LoginInfo loginInfo;
	private JDOServiceAsync jdoService = GWT.create(JDOService.class);
	private ListDataProvider<BikeRackData> dataProvider;
	final CellTable<BikeRackData> table = new CellTable<BikeRackData>();
	public FavRackTable(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
	}

	@Override
	public Widget asWidget() {
				
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

				Column<BikeRackData, String> removeFave = new Column<BikeRackData, String>(new ButtonCell()) {

		            @Override
		            public String getValue(final BikeRackData object) {
		                return BikeRackTable.YES;
		            }
		        };
		        
		        removeFave.setFieldUpdater(new FieldUpdater<BikeRackData,String>(){

					@Override
					public void update(int index, BikeRackData object, String value) {
						object.setFave(false);
						loginInfo.setBikeRackID(object.getId());
						deleteFavBikeRack(loginInfo);
						
					}
		        	
		        });
		        
		        table.addColumn(removeFave, BikeRackTable.UNMARK_AS_FAVORITE);

		        dataProvider = new ListDataProvider<BikeRackData>();
		        dataProvider.addDataDisplay(table);
		        
				final SimplePager pager = new SimplePager();
				pager.setDisplay(table);
				
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
				
				AppUtils.EVENT_BUS.addHandler(AddFaveEvent.TYPE, new AddFaveEventHandler(){

					@Override
					public void onFaveAdded(AddFaveEvent event) {
						jdoService.findDataById(event.getLoginInfo().getBikeRackID(), new AsyncCallback<BikeRackData>(){

							@Override
							public void onFailure(Throwable caught) {
								Window.alert(caught.getMessage());
								
							}

							@Override
							public void onSuccess(BikeRackData result) {
								dataProvider.getList().add(result);
								dataProvider.flush();
								dataProvider.refresh();
								table.redraw();
								
							}
							
						});
						
					}
					
				});
				
				AppUtils.EVENT_BUS.addHandler(RemoveFaveEvent.TYPE, new RemoveFaveEventHandler(){

					@Override
					public void onFaveRemoved(RemoveFaveEvent event) {
						jdoService.findDataById(event.getLoginInfo().getBikeRackID(), new AsyncCallback<BikeRackData>(){

							@Override
							public void onFailure(Throwable caught) {
								Window.alert(caught.getMessage());
								
							}

							@Override
							public void onSuccess(BikeRackData result) {
								if(dataProvider.getList().contains(result)){
									dataProvider.getList().remove(result);
									dataProvider.flush();
									dataProvider.refresh();
									table.redraw();
								}
								
							}
							
						});
						
					}
					
				});

				VerticalPanel vp = new VerticalPanel();
				vp.add(table);
				vp.add(pager);
				return vp;
	}

	private void deleteFavBikeRack(final LoginInfo loginInfo) {
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
				
				AppUtils.EVENT_BUS.fireEvent(new RemoveFaveEvent(loginInfo));
			}
			
		});
		
	}
}