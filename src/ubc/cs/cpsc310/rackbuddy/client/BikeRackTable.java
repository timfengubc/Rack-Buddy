package ubc.cs.cpsc310.rackbuddy.client;

import java.util.ArrayList;

import java.util.Comparator;
import java.util.List;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;

import com.google.gwt.user.cellview.client.ColumnSortEvent.AsyncHandler;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;

import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.cellview.client.TextColumn;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;

import com.google.gwt.user.client.ui.PopupPanel;

import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.MultiSelectionModel;


public class BikeRackTable  implements IsWidget {

	public static final int NUM_DATA_PER_PAGE = 10;

	public static final String YEARS_INSTALLED = "Years Installed";

	public static final String NUM_RACKS = "# of Racks";

	public static final String BIA2 = "BIA";

	public static final String SKYTRAIN_STATION_NAME = "Skytrain Station Name";

	public static final String ST_SIDE = "St. Side";

	public static final String ST_NAME = "St. Name";

	public static final String ST_NUMBER = "St. Number";

	public static final String BIKE_RACK_LOCATIONS_IN_THE_CITY_OF_VANCOUVER = "Official Bike Rack Locations in the City of Vancouver";
	
	public static final String FILTER_BY = "Filter by : ";
	
	public static final String INVAILD_INPUT = "Invalid input";
	
	public static final String INVAILD_ST_NAME = "Invalid St. Name";
	
	public static final String INVAILD_ST_NUM = "Invalid St. Number";
	
	private Button filterButton;
	private Button searchButton;
	private Button quitButton;
	private PopupPanel popUp;
	private TextBox textbox;
	private ListBox filterby;
	private List<BikeRackData> racks;
	private List<BikeRackData> tableList = new ArrayList<BikeRackData>();
	private List<BikeRackData> savedList = new ArrayList<BikeRackData>();
	private List<BikeRackData> stNameList = new ArrayList<BikeRackData>();
	private List<BikeRackData> stNumList = new ArrayList<BikeRackData>();
	private final CellTable<BikeRackData> table = new CellTable<BikeRackData>(BikeRackData.KEY_PROVIDER);
	private VerticalPanel vp = new VerticalPanel();
	
	TextColumn<BikeRackData> stNum ;
	TextColumn<BikeRackData> stName;

	@Override
	public Widget asWidget() {
				
					
				racks = new ArrayList<BikeRackData>();
				
				// Create a CellTable.
				
				 
				MultiSelectionModel<BikeRackData> multi_selectionModel = new MultiSelectionModel<BikeRackData>(BikeRackData.KEY_PROVIDER);
				
								
				table.setSelectionModel(multi_selectionModel);
				
				table.setPageSize(NUM_DATA_PER_PAGE);
				 
				
				  SimplePager pager = new SimplePager(TextLocation.CENTER,true,true);
					
					 pager.setPageSize(NUM_DATA_PER_PAGE);
					pager.setDisplay(table);
					
					

				// Add a text column to show the street num.
					stNum = new TextColumn<BikeRackData>() {
					@Override
					public String getValue(BikeRackData object) {
						return object.getStreetNumber();
					}
				};				
				table.addColumn(stNum, ST_NUMBER);
				stNum.setSortable(true);			
														
				// Add a text column to show the street name.
				stName = new TextColumn<BikeRackData>() {
					@Override
					public String getValue(BikeRackData object) {
						return object.getStreetName();
					}
				};				
				table.addColumn(stName, ST_NAME);
				stName.setSortable(true);						
									
								
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
				numRacks.setSortable(true);
				Column<BikeRackData, Boolean> checkBoxCol = new Column<BikeRackData, Boolean>(new CheckboxCell()) { 
			        @Override 
			        public Boolean getValue(BikeRackData object) { 
			        	return object.isFave();
			        } 
			    };
			    
			    table.addColumn(checkBoxCol, "Mark as favorite?");  
					 			  	    		    
			    
			    vp.add(new Label(BIKE_RACK_LOCATIONS_IN_THE_CITY_OF_VANCOUVER));
			    initFilter();
				vp.add(table);
				vp.add(pager);				
				
				return vp ;
	} 
	// updates table according to the markers on the map
	public void updateTable(final List<BikeRackData> list){		
			
		AsyncDataProvider<BikeRackData> provider = new AsyncDataProvider<BikeRackData>() {
			
			@Override
			protected void onRangeChanged(HasData<BikeRackData> display) {
			
				int start = display.getVisibleRange().getStart();
				int end = start + display.getVisibleRange().getLength();
				end = end >= list.size() ? list.size() : end;
				List<BikeRackData> sub = list.subList(start, end);
				updateRowData(start, sub);	
				
			}
		};
		provider.addDataDisplay(table);		
		provider.updateRowCount(list.size(), true);			
	    AsyncHandler columnSortHandler = new AsyncHandler(table);
	    table.addColumnSortHandler(columnSortHandler);
	  	    	    			
	}
	// saves list made from displayAllMarkers
	public void saveList(List<BikeRackData> list) {
		savedList = list;
		
	}
	// sortTable function
	public void sortTable(final List<BikeRackData> list) {
		ListHandler<BikeRackData> sortHandler = new ListHandler<BikeRackData>(list);
		
		sortHandler.setComparator(stNum, new Comparator<BikeRackData>() {
			
			@Override
			public int compare(BikeRackData o1, BikeRackData o2) {

				return Integer.valueOf(o1.getStreetNumber()).compareTo(Integer.valueOf(o2.getStreetNumber()));
				
			}
		});
		
		sortHandler.setComparator(stName, new Comparator<BikeRackData>() {
			
			@Override
			public int compare(BikeRackData o1, BikeRackData o2) {
				
				return o1.getStreetName().compareTo(o2.getStreetName());
			}
			
		});
		
		table.addColumnSortHandler(sortHandler);
	}

	// initializes filter function.
	public void initFilter() {
	
		VerticalPanel PopVp = new VerticalPanel();
		PopVp.setSize("40em", "10em");
		
		// add Pop up panel
		popUp = new PopupPanel();
		popUp.add(PopVp);
		
		filterby = new ListBox();
		filterby.setVisibleItemCount(1);
		filterby.addItem(ST_NAME);
		filterby.addItem(ST_NUMBER);
		
		textbox = new TextBox();
		textbox.addStyleName("paddedLeft");
		textbox.setWidth("40em");
		textbox.setHeight("10px");
		
		HorizontalPanel h1 = new HorizontalPanel();
		h1.setStyleName("marginTop");		
		HorizontalPanel h2 = new HorizontalPanel();
		h2.setStyleName("marginTop");
		HorizontalPanel h3 = new HorizontalPanel();
		h3.setStyleName("marginTop");
		HorizontalPanel h4 = new HorizontalPanel();
		h4.setStyleName("marginTop");
		
		h1.add(new Label(FILTER_BY));
		h1.add(filterby);		
		h2.add(textbox);		
		
		
		PopVp.add(h1);
		PopVp.add(h2);		
		// Quit button within the PopupPanel
		quitButton = new Button("Quit");
		quitButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				popUp.hide();
				
			}
			
		});
		//Search button within the PopupPanel 
		searchButton = new Button("Filter..");			
		searchButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (MapDisplay.isTextValid(textbox.getValue()) == true) {
					
					if (filterby.getValue(filterby.getSelectedIndex()) == ST_NAME) {
						try {
							int inputint1 = 0;
							inputint1 = Integer.parseInt(textbox.getValue());
							Window.alert(INVAILD_ST_NAME);
						} catch (Exception e) {
							matchStName(savedList, textbox.getValue());	
							sortTable(stNameList);
							updateTable(stNameList);	
							popUp.hide();
						}						
					}										
					if (filterby.getValue(filterby.getSelectedIndex()) == ST_NUMBER) {
						int inputint = 0;
						try {
							inputint = Integer.parseInt(textbox.getValue());
							matchStNum(savedList, Integer.valueOf(textbox.getValue()));								
							sortTable(stNumList);
							updateTable(stNumList);
							popUp.hide();
						} catch (Exception e) {
							Window.alert(INVAILD_ST_NUM);
						}															
					} 

				} else {
					Window.alert(INVAILD_INPUT);
				}

			}
			
		});
		h3.add(searchButton);
		h3.add(quitButton);
		PopVp.add(h3);
		//filter button on the main page
		filterButton = new Button(FILTER_BY);
		filterButton.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				popUp.center();
			}
			
		}); 		
		

		vp.add(filterButton);
	
	}
	
	public void matchStName(List<BikeRackData> list, String stName) {
		stNameList.clear();
		for (BikeRackData brd : list) {
			if (brd.getStreetName().toLowerCase().contains(stName)) {
				
				stNameList.add(brd);
			}
		}	
	
	}	
	public void matchStNum (List<BikeRackData> list, int stNum) {
		stNumList.clear();
	
		for (BikeRackData brd : list) {
			if (Integer.valueOf(brd.getStreetNumber()) == stNum) {				
				stNumList.add(brd);
			}
		}	
	
	}
	public List<BikeRackData> getList(){
		return this.racks;
	}
	

	public void replaceData(List<BikeRackData> dataToReplaceWith){
		this.racks.clear();
		this.racks.addAll(dataToReplaceWith);
	}
}
