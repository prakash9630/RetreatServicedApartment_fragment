package com.example.prakash.RetreatServicedApartment.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.prakash.RetreatServicedApartment.Helper.GPSTracker;
import com.example.prakash.RetreatServicedApartment.R;

/**
 * Created by prakash on 8/1/2017.
 */

public class Location_feedback extends Fragment{

    Context mContext;
    GPSTracker gps;
    double latitude;
    double longitude;
    String username;
    String userid;
    Button sentLocationbtn;
    EditText reason;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mainview=inflater.inflate(R.layout.location_feedback,container,false);
mContext=getContext();

        SharedPreferences preferences =getActivity().getSharedPreferences("Authentication", Context.MODE_PRIVATE);

        userid = preferences.getString("uid", "");
        username=preferences.getString("username","");
        sentLocationbtn=(Button)mainview.findViewById(R.id.send_btn);
        reason=(EditText)mainview.findViewById(R.id.send_location_edittext);


        sentLocationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isOnline()) {

//                    Intent i=new Intent(getContext(),Location_feedback.class);
//                    startActivity(i);
getLatLong();


                }
                else
                {
                    Toast.makeText(mContext, "Check your Internet connection", Toast.LENGTH_SHORT).show();
                }

            }
        });


        return mainview;
    }




    void getLatLong()
    {
        if (ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        } else {

            gps = new GPSTracker(mContext, getActivity());

            // Check if GPS enabled
            if (gps.canGetLocation()) {

                latitude = gps.getLatitude();
                longitude = gps.getLongitude();

                if (latitude==0.0 && longitude==0.0)
                {

                    Toast.makeText(mContext, "Could't get location coordinate please try again", Toast.LENGTH_SHORT).show();


                }
                else
                {
                    sendLocation();
                }


            } else {
                // Can't get location.
                // GPS or network is not enabled.
                // Ask user to enable GPS/network in settings.
                gps.showSettingsAlert();
            }
        }

    }

    void sendLocation()
    {
        Intent Email = new Intent(Intent.ACTION_SEND);

        Email.putExtra(Intent.EXTRA_EMAIL, new String[] { "info@retreatapartment.com" });
        Email.putExtra(Intent.EXTRA_SUBJECT, "Users location");
        Email.putExtra(Intent.EXTRA_TEXT,username +" is in "+latitude+","+longitude +" right now.  "+ Html.fromHtml("<br/>"+"Reason : "+reason.getText().toString()));
        Email.setType("message/rfc822");
        try {
            startActivity(Intent.createChooser(Email, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }


    }



    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }
}
