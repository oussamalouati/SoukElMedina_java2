/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soukelmedina2;

import com.teamdev.jxmaps.GeocoderCallback;
import com.teamdev.jxmaps.GeocoderRequest;
import com.teamdev.jxmaps.GeocoderResult;
import com.teamdev.jxmaps.GeocoderStatus;
import com.teamdev.jxmaps.ControlPosition;
import com.teamdev.jxmaps.InfoWindow;
import com.teamdev.jxmaps.LatLng;
import com.teamdev.jxmaps.LatLngBounds;
import com.teamdev.jxmaps.Map;
import com.teamdev.jxmaps.MapMouseEvent;
import com.teamdev.jxmaps.MapOptions;
import com.teamdev.jxmaps.MapReadyHandler;
import com.teamdev.jxmaps.MapStatus;
import com.teamdev.jxmaps.MapTypeControlOptions;
import com.teamdev.jxmaps.MapViewOptions;
import com.teamdev.jxmaps.Marker;
import com.teamdev.jxmaps.MouseEvent;
import com.teamdev.jxmaps.PlaceDetailsCallback;
import com.teamdev.jxmaps.PlaceDetailsRequest;
import com.teamdev.jxmaps.PlaceResult;
import com.teamdev.jxmaps.PlacesServiceStatus;
import com.teamdev.jxmaps.javafx.MapView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import static soukelmedina2.MagazinController.Updmag;
import static soukelmedina2.VendeurController.markerchange;

/**
 *
 * @author INETEL
 */
public class MapController extends MapView implements Initializable {

    public static double lat = 36.7570731;
    public static double lng = 10.1782658;
    static final MapViewOptions mapOptions;
    String adresseCMarker;

    static {
        // initializing a map view options
        mapOptions = new MapViewOptions();
        // enabling usage of places library
        mapOptions.importPlaces();
    }

    public MapController() {
        super(mapOptions);
    }

    public static double getLat() {
        return lat;
    }

    public static void setLat(double lat) {
        MapController.lat = lat;
    }

    public static double getLng() {
        return lng;
    }

    public static void setLng(double lng) {
        MapController.lng = lng;
    }
    //final MapView mapView = new MapView();
    @FXML
    private MapView mapView;

    public String getfullAdress(Map map, MouseEvent me) {

        return adresseCMarker;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        mapView.setOnMapReadyHandler(new MapReadyHandler() {

            public void onMapReady(MapStatus status) {
                // Check if the map is loaded correctly
                if (status == MapStatus.MAP_STATUS_OK) {
                    // Getting the associated map object
                    final Map map = mapView.getMap();
                    // Creating a map options object
                    MapOptions options = new MapOptions();
                    // Creating a map type control options object
                    MapTypeControlOptions controlOptions = new MapTypeControlOptions();
                    // Changing position of the map type control
                    controlOptions.setPosition(ControlPosition.TOP_RIGHT);
                    // Setting map type control options
                    options.setMapTypeControlOptions(controlOptions);
                    // Setting map options
                    map.setOptions(options);
                    // Setting the map center
                    map.setCenter(new LatLng(lat, lng));
                    // Setting initial zoom value

                    map.setZoom(10.0);
                    map.addEventListener("click", new MapMouseEvent() {
                        @Override
                        public void onEvent(MouseEvent me) {
                            final Marker marker = new Marker(map);
                            // Move marker to the position where user clicked
                            marker.setPosition(me.latLng());
                            lat = me.latLng().getLat();
                            lng = me.latLng().getLng();

                            //code ++ 
                            GeocoderRequest gr = new GeocoderRequest();
                            gr.setLocation(me.latLng());
                            getServices().getGeocoder().geocode(gr, new GeocoderCallback(map) {
                                @Override
                                public void onComplete(GeocoderResult[] results, GeocoderStatus status) {
                                    if ((status == GeocoderStatus.OK) && (results.length > 0)) {
                                        // Getting the first result
                                        GeocoderResult result = results[0];
                                        PlaceDetailsRequest request = new PlaceDetailsRequest();
                                        request.setPlaceId(result.getPlaceId());
                                        getServices().getPlacesService().getDetails(request, new PlaceDetailsCallback(map) {
                                            @Override
                                            public void onComplete(PlaceResult result, PlacesServiceStatus status) {
                                                if (status == PlacesServiceStatus.OK) {
                                                    adresseCMarker = result.getFormattedAddress();
                                                    MainController.vdrctrl.markeradress(adresseCMarker);
                                                    MagazinController.modifmagctrl.markeradress(adresseCMarker);
                                                }

                                            }
                                        });

                                    }
                                }

                            });

                            //code ++ -
                            // Adding event listener that intercepts clicking on marker
                            map.addEventListener("click", new MapMouseEvent() {
                                @Override
                                public void onEvent(MouseEvent mouseEvent) {
                                    // Removing marker from the map
                                    marker.remove();
                                }
                            });

                        }
                    });
                    if (Updmag) {
                        final Marker marker = new Marker(map);
                        marker.setPosition(new LatLng(lat, lng));
                        map.addEventListener("click", new MapMouseEvent() {
                            @Override
                            public void onEvent(MouseEvent mouseEvent) {
                                // Removing marker from the map
                                marker.remove();
                            }
                        });
                    }

                }
            }
        });
    }

}
