package scu.book.campus.com.campusbook;

import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;
import java.util.Locale;

import scu.book.campus.com.campusbook.R;

/**
 * Created by qizhao on 6/5/16.
 */
public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    LatLng latLngSellLocation;
    Polyline connectLine=null;
    private double longitude;
    private double latitude;
    private String locationName;
    private String seller;
    private String seller_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_maps);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // set interesting locations

        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
        locationName = bundle.getString("location_name");
        seller = bundle.getString("seller");
        seller_email = bundle.getString("seller_email");
        Log.d("location", locationName);
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocationName(locationName,1);
            Address address = addresses.get(0);
            longitude = address.getLongitude();
            latitude = address.getLatitude();
        } catch (Exception e) {
            Log.d("error", "convert to lat lng error");
        }
        latLngSellLocation = new LatLng(latitude, longitude);






    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        mMap.addMarker(new MarkerOptions().position(latLngSellLocation).title(locationName).snippet(seller+", "+seller_email));



        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngSellLocation, 17.0f));

        // set up UI control
        UiSettings ui = mMap.getUiSettings();
        ui.setAllGesturesEnabled(true);
        ui.setCompassEnabled(true);
        ui.setZoomControlsEnabled(true);
    }
}
