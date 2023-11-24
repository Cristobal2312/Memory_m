package com.example.memory_m.vista;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.memory_m.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap myMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        myMap = googleMap;

        // Configuración de ubicaciones y marcadores
        LatLng cesfamFrayJorge = new LatLng(-30.605994699902993, -71.20313287501332);
        myMap.addMarker(new MarkerOptions().position(cesfamFrayJorge).title("Cesfam Fray Jorge"));

        LatLng hospitalRegional = new LatLng(-30.577907953004164, -71.19000757494169);
        myMap.addMarker(new MarkerOptions().position(hospitalRegional).title("Hospital de ovalle"));

        LatLng CentrodeSaludFamiliar = new LatLng(-30.589400571075476, -71.19273598818133);
        myMap.addMarker(new MarkerOptions().position(CentrodeSaludFamiliar).title("Centro de Salud Familiar Jorge Jordán Domic"));

        LatLng CentrodeSaludMarcosMacuada = new LatLng(-30.589400571075476, -71.19273598818133);
        myMap.addMarker(new MarkerOptions().position(CentrodeSaludMarcosMacuada).title("Centro de Salud Familiar Marcos Macuada"));
        // Mueve la cámara al primer marcador (Cesfam Fray Jorge) y ajusta el zoom
        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cesfamFrayJorge, 12));

        // Configuración de la interfaz de usuario
        myMap.getUiSettings().setZoomControlsEnabled(true);
        myMap.getUiSettings().setCompassEnabled(true);
        myMap.getUiSettings().setZoomControlsEnabled(true);
        myMap.getUiSettings().setScrollGesturesEnabled(true);
    }
}
