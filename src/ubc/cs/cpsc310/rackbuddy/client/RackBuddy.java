package ubc.cs.cpsc310.rackbuddy.client;

import ubc.cs.cpsc310.rackbuddy.server.GeoParserServiceImpl;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.maps.client.InfoWindowContent;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;

/*
 * Entry point classes define <code>onModuleLoad()</code>.
 * 
 * 
 */

 
public class RackBuddy implements EntryPoint {

	double[] coords = null;
  // GWT module entry point method.
  public void onModuleLoad() {
	  GeoParserServiceAsync GeoParserService = GWT.create(GeoParserService.class);
	  GeoParserService.getLatitude("2150 Cambie", new AsyncCallback<Double>(){

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Double result) {
			coords[0] = result;
			
		}
		  
	  });
	  
   /*
    * Asynchronously loads the Maps API.
    *
    * The first parameter should be a valid Maps API Key to deploy this
    * application on a public server, but a blank key will work for an
    * application served from localhost.
   */
   Maps.loadMapsApi("", "2", false, new Runnable() {
      public void run() {
        buildUi();
      }
    });
  }

  private void buildUi() {
	LatLng vancouver = LatLng.newInstance(49.261226, -123.1139268);
    LatLng cambie = LatLng.newInstance(coords[0], coords[1]);
    
   final MapWidget map = new MapWidget(vancouver, 2);
    map.setSize("60%", "100%");
    map.setZoomLevel(12);
    // Add some controls for the zoom level
    map.addControl(new LargeMapControl());
    // Add a marker
    map.addOverlay(new Marker(vancouver));
    map.addOverlay(new Marker(cambie));
    
    // Add an info window to highlight a point of interest
    map.getInfoWindow().open(map.getCenter(),
        new InfoWindowContent("Bike Rack"));

    final DockLayoutPanel dock = new DockLayoutPanel(Unit.PX);
    dock.addNorth(map, 500);

    // Add the map to the HTML host page
    RootLayoutPanel.get().add(dock);
  }
}