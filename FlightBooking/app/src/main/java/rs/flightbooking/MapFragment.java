package rs.flightbooking;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

    public GoogleMap getMap() {
        return map;
    }

    GoogleMap map;
    public MapFragment() {
        // Required empty public constructor
    }

    /*
    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        return fragment;
    }
    */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_maps, container, false);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        //i have no idea how to get current location
        if (ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
        } else {

            // Show rationale and request permission.
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        PatternItem DASH = new Dash(20);
        PatternItem GAP = new Gap(8);
        List<PatternItem> PATTERN_POLYGON_ALPHA = Arrays.asList(GAP, DASH);
        LatLng sydney = new LatLng(-34, 151);
        map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        PolylineOptions rectOptions = new PolylineOptions()
                .width(4.7f)
                .color(Color.rgb(255,0,127))
                .pattern(PATTERN_POLYGON_ALPHA)
                .add(new LatLng(45.35, -122.0))
                .add(new LatLng(45.35, 18.0))
                .add(new LatLng(45.45,18.0))
                .add(new LatLng(-20.25,25.0))
                .geodesic(true); // Closes the polyline.

// Get back the mutable Polyline
        Polyline polyline = map.addPolyline(rectOptions);
    }
}
