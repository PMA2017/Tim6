package rs.maps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.SphericalUtil;
import com.google.maps.android.SphericalUtil.*;

import java.util.Arrays;
import java.util.List;

import rs.flightbooking.R;


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
        LatLng sydney = new LatLng(45, 0);
       // map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));

        Bundle bundle = this.getArguments();
        if(bundle!=null) {

            LatLng locationA = new LatLng(45,-122);
            LatLng locationB = new LatLng(45,18);
            String nameA = "Name A";
            String nameB = "Name B";
            try {
                locationA = new LatLng(this.getArguments().getDouble("latA"), this.getArguments().getDouble("lonA"));
                locationB = new LatLng(this.getArguments().getDouble("latB"), this.getArguments().getDouble("lonB"));
                nameA = this.getArguments().getString("nameA");
                nameB = this.getArguments().getString("nameB");
            }
            catch(NullPointerException e){
                e.printStackTrace();
                return;
            }
            PolylineOptions rectOptions = new PolylineOptions()
                    .width(4.7f)
                    .color(Color.rgb(255, 0, 127))
                    .pattern(PATTERN_POLYGON_ALPHA)
                    .add(locationA)
                    .add(locationB)
                    .geodesic(true); // Closes the polyline.


            Location startPoint = new Location("location a");
            Location endPoint = new Location("location b");
            startPoint.setLongitude(locationA.longitude);
            startPoint.setLatitude(locationA.latitude);
            endPoint.setLatitude(locationB.latitude);
            endPoint.setLongitude(locationB.longitude);
            double distance = startPoint.distanceTo(endPoint);
            double kmDistance = distance / 1000;
            BitmapDescriptor transparent = BitmapDescriptorFactory.fromResource(R.drawable.rulericon32681);

            /*
            double middleLat = (locationA.latitude + locationB.latitude)/2;
            double middleLng = locationA.longitude-locationB.longitude;
            if (middleLng <= 180 && middleLng >= -180) {
                middleLng = (locationA.longitude+locationB.longitude)/2;
            } else {
                middleLng = (locationA.longitude+locationB.longitude+360)/2;
            }

            LatLng middle = new LatLng(middleLat,middleLng);
            */



            LatLng middle = SphericalUtil.interpolate(locationA,locationB,0.5);
            map.moveCamera(CameraUpdateFactory.newLatLng(middle));
            Marker distanceWindow = map.addMarker(new MarkerOptions()

                    .icon(transparent)
                    .position(middle)
                    .title(nameA +" - " + nameB)
                    .snippet("Distance " + kmDistance + " km"));
            distanceWindow.showInfoWindow();


            Marker startPointMarker = map.addMarker(new MarkerOptions()
                    .position(locationA)
                    .title(nameA)
            );


            Marker endPointMarker = map.addMarker(new MarkerOptions()
                    .position(locationB)
                    .title(nameB)
            );

            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(startPointMarker.getPosition());
            builder.include(endPointMarker.getPosition());
            LatLngBounds bounds = builder.build();
            int padding = 50;
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);

            googleMap.moveCamera(cu);


            // Get back the mutable Polyline
            Polyline polyline = map.addPolyline(rectOptions);
        }
    }
}
