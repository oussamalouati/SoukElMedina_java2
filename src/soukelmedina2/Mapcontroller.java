/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soukelmedina2;
import utils.NavigatorData;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.InfoWindow;
import com.lynden.gmapsfx.javascript.object.InfoWindowOptions;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.lynden.gmapsfx.service.directions.DirectionStatus;
import com.lynden.gmapsfx.service.directions.DirectionsResult;
import com.lynden.gmapsfx.service.directions.DirectionsService;
import com.lynden.gmapsfx.service.directions.DirectionsServiceCallback;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author INETEL
 */
public class Mapcontroller implements Initializable , MapComponentInitializedListener, DirectionsServiceCallback {
 private GeocodingService geocodingService;
  private MarkerOptions markerOptions;
   private GoogleMap map;
    @FXML
    private GoogleMapView mapView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mapView.addMapInializedListener(this);
        ObservableList<String> competence = FXCollections.observableArrayList();
    }   

    @Override
    public void mapInitialized() {
   geocodingService = new GeocodingService();
        MapOptions options = new MapOptions();

        options.center(new LatLong(34.3055732, 11.255412))
                .zoomControl(true)
                .zoom(6)
                .overviewMapControl(false)
                .mapType(MapTypeIdEnum.ROADMAP);
        GoogleMap map = mapView.createMap(options);
       LatLong l = new LatLong(NavigatorData.getInstance().getLat(),NavigatorData.getInstance().getLong());
        
         Marker myMarker = null;
        markerOptions = new MarkerOptions();
        markerOptions.position(l)
                .title("My new Marker")
                .visible(true);

        myMarker = new Marker(markerOptions);
        InfoWindowOptions infoOptions = new InfoWindowOptions();
        infoOptions.content(NavigatorData.getInstance().getAdr())
                .position(l);

        InfoWindow window = new InfoWindow(infoOptions);
        window.open(map, myMarker);
        map.addMarker(myMarker);
        
    }
  private Marker AddMarker(LatLong l, String address) {

        Marker myMarker = null;
        markerOptions = new MarkerOptions();
        markerOptions.position(l)
                .title("My new Marker")
                .visible(true);

        myMarker = new Marker(markerOptions);
        InfoWindowOptions infoOptions = new InfoWindowOptions();
        infoOptions.content(address)
                .position(l);

        InfoWindow window = new InfoWindow(infoOptions);
        window.open(map, myMarker);
        map.addMarker(myMarker);
        return myMarker;

    }

    @Override
    public void directionsReceived(DirectionsResult dr, DirectionStatus ds) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    } 
}

