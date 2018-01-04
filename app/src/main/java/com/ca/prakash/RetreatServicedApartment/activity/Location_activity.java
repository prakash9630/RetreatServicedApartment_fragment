package com.ca.prakash.RetreatServicedApartment.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ca.prakash.RetreatServicedApartment.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;

public class Location_activity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    float zoomLevel = (float) 17.0;

    Button btn;

    TextView textView;
    Typeface tf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_activity);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btn=(Button)findViewById(R.id.location_id);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDirection();

            }
        });
        textView=(TextView)findViewById(R.id.location_description);


        tf= Typeface.createFromAsset(getAssets(),"fonts/Raleway-ExtraLight.ttf");
        textView.setTypeface(tf);
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
        LatLng kathmandu = new LatLng(27.713337, 85.297986);
        mMap.addMarker(new MarkerOptions().position(kathmandu).title("Retreat Serviced Apartment"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(kathmandu));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(kathmandu, zoomLevel));

    }

    void getDirection()
    {

        String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?q="+"Retreat Serviced Apartments"+"","Retreat Serviced Apartments");
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        try
        {
            startActivity(intent);
        }
        catch(ActivityNotFoundException ex)
        {
            try
            {
                Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(unrestrictedIntent);
            }
            catch(ActivityNotFoundException innerEx)
            {
                Toast.makeText(Location_activity.this, "Please install a maps application", Toast.LENGTH_LONG).show();
            }
        }

    }
}
