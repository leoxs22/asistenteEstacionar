package com.bahackaton.asistenteparaestacionar;

import android.app.Activity;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;


public class MapsActivity extends Activity implements OnMapReadyCallback {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private List<Marcador> marcadores = new ArrayList<Marcador>();

   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        mMap.setMyLocationEnabled(true);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);



    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            MapFragment mapFragment = (MapFragment) getFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            // Check if we were successful in obtaining the map.
            if (mMap != null) {

            }
        }
    }
    public void onMapReady(GoogleMap map){
        mMap=map;
        mMap.setMyLocationEnabled(true);
        setUpMap();

    }
    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));

        Button btnMarcador = (Button) findViewById(R.id.markerButton);
        btnMarcador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refrescarMarcadores();
            }
        });
        Button btnEstacionarDer = (Button) findViewById(R.id.btnEstacionarDer);
        btnEstacionarDer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refrescarMarcadores();
                calcularEstacionado(Vereda.getIzquierda());
            }
        });
        Button btnEstacionarIzq = (Button) findViewById(R.id.btnEstacionarIzq);
        btnEstacionarIzq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refrescarMarcadores();
                calcularEstacionado(Vereda.getDerecha());
            }
        });


    }

    private void calcularEstacionado(int vereda) {
        boolean puede = true;
        double lat= mMap.getMyLocation().getLatitude();
        double lon = mMap.getMyLocation().getLongitude();
        for(int i=0; i<marcadores.size();i++){
            if(marcadores.get(i).puedeEstacionar(lat,lon,vereda)==false){
                Toast.makeText(getApplicationContext(),"No puede estacionar aquí",Toast.LENGTH_LONG).show();
                break;
            }else{
                Toast.makeText(getApplicationContext(),"Puede estacionar libremente aquí",Toast.LENGTH_LONG).show();
            }
        };

    }

    private void refrescarMarcadores() {
        Location pos = mMap.getMyLocation();
        double lat = pos.getLatitude();
        double lon = pos.getLongitude();
        mMap.clear();
        agregarMarcadoresEn(lat, lon);
    }

    private void agregarMarcadoresEn(double lat, double lon) {
        //para testeo
        agregarMarcador(lat+0.001,lon,"1",Vereda.cualquiera,0.001);
        agregarMarcador(lat+0.001,lon+0.001,"2",Vereda.cualquiera,0.001);
        agregarMarcador(lat,lon+0.001,"3",Vereda.cualquiera,0.001);
        agregarMarcador(-34.590910, -58.393008,"asd",Vereda.derecha,0.001);
        agregarMarcador(-34.585873, -58.393936,"asd",Vereda.derecha,0.001);



    }

    private void agregarMarcador(double lat, double lon,String title, int vereda,double radio){
        MarkerOptions marcador = new MarkerOptions().position(new LatLng(lat,lon)).title(title);
        mMap.addMarker(marcador);
        marcadores.add(new Marcador(marcador,radio, vereda, lat, lon));

    }

    /*private void setUpPolyline(){
    // Instantiates a new Polyline object and adds points to define a rectangle
        //linea que no hace nada
    PolylineOptions rectOptions = new PolylineOptions()
            .add(new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude()))
            .add(new LatLng(mMap.getMyLocation().getLatitude() + 1, mMap.getMyLocation().getLongitude()))
            .add(new LatLng(mMap.getMyLocation().getLatitude() + 1, mMap.getMyLocation().getLongitude() + 1))
            .add(new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude()));

    // Get back the mutable Polyline
    Polyline polyline = mMap.addPolyline(rectOptions);
    }*/




}
