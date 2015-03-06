package ubc.cs.cpsc310.rackbuddy.client;



import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.maps.client.InfoWindowContent;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;

/*
 * Entry point classes define <code>onModuleLoad()</code>.
 * 
 * 
 */

 
public class RackBuddy implements EntryPoint {

	List<Double> coords = new ArrayList<Double>(); 
	private GeoParserServiceAsync GeoRPC;
	
  // GWT module entry point method.
  public void onModuleLoad() {
	  
	  	GeoRPC = GWT.create(GeoParserService.class);
	  	GeoRPC.getLatLng("55 Alexander St, Vancouver, BC", new AsyncCallback<MarkerLocation>(){

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(MarkerLocation result) {
				coords.add(result.getLat());
				coords.add(result.getLng());
				Maps.loadMapsApi("", "2", false, new Runnable() {
				      public void run() {
				        buildUi();
				      }
				    });
			}
	  		
	  	});
		
	
   /*
    * Asynchronously loads the Maps API.
    *
    * The first parameter should be a valid Maps API Key to deploy this
    * application on a public server, but a blank key will work for an
    * application served from localhost.
   */
 
  }

  private void buildUi() {
	LatLng vancouver = LatLng.newInstance(49.261226, -123.1139268);
	LatLng ponderosa = LatLng.newInstance(coords.get(0), coords.get(1));
   final MapWidget map = new MapWidget(vancouver, 2);
    map.setSize("60%", "100%");
    map.setZoomLevel(12);
    // Add some controls for the zoom level
    map.addControl(new LargeMapControl());
    // Add a marker
   // map.addOverlay(new Marker(vancouver));
    map.addOverlay(new Marker(ponderosa));
    map.addOverlay(new Marker(vancouver));

 
    // Add an info window to highlight a point of interest
    map.getInfoWindow().open(ponderosa,
        new InfoWindowContent("Bike racks"));
  
    
    final DockLayoutPanel dock = new DockLayoutPanel(Unit.PX);
    dock.addNorth(map, 500);

    // Add the map to the HTML host page
    RootLayoutPanel.get().add(dock);
  }
  
}