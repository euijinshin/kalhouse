package com.example.myapplication.activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);

        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
//                GoogleMap map = googleMap;

                MarkerOptions areum = new MarkerOptions();
                LatLng aLatLng = new LatLng(36.3738, 127.3567);
                areum.position(aLatLng);
                areum.title("아름관");
                areum.snippet("여자 기숙사");

                MarkerOptions sarang = new MarkerOptions();
                LatLng sLatLng = new LatLng(36.3737, 127.3586);
                sarang.position(sLatLng);
                sarang.title("사랑관");
                sarang.snippet("남자 기숙사");

                MarkerOptions ee = new MarkerOptions();
                LatLng eeLatLng = new LatLng(36.3739, 127.3656);
                ee.position(eeLatLng);
                ee.title("N1");
                ee.snippet("실습실");

                googleMap.addMarker(areum);
                googleMap.addMarker(sarang);
                googleMap.addMarker(ee);

                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(eeLatLng, 16));

                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(@NonNull LatLng latLng) {
                        // when clicked on map
//                        MarkerOptions markerOptions = new MarkerOptions();
//                        markerOptions.position(latLng);
//                        //Set title of marker
//                        markerOptions.title(latLng.latitude + " : " + latLng.longitude);
//                        //animations
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                latLng, 17
                        ));

//                        googleMap.addMarker(markerOptions);
                    }
                });
            }
        });

        return view;
    }
}