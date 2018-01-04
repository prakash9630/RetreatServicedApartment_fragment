package com.ca.prakash.RetreatServicedApartment.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.ca.prakash.RetreatServicedApartment.R;
import com.ca.prakash.RetreatServicedApartment.app.MyApplication;

/**
 * Created by prakash on 4/11/2017.
 */

public class Feedback extends Fragment {
    View mainView;
    EditText mName,mMessage;
    Button mSent;
    RatingBar rate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView=inflater.inflate(R.layout.feedback,container,false);

        mName=(EditText)mainView.findViewById(R.id.feedback_name);




        mMessage=(EditText)mainView.findViewById(R.id.feedback_message);
        mSent=(Button)mainView.findViewById(R.id.feedback_send);

rate=(RatingBar)mainView.findViewById(R.id.rating_feedback);
        mSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
            }
        });


        return mainView;
    }


    void validation()
    {
        String name=mName.getText().toString();

        String message=mMessage.getText().toString();
        float numberOfStars = rate.getRating();

        if (name.length()<1)
        {
            mName.setError("Please enter your name");
        }
        else if (message.length()<1)
        {
            mMessage.setError("Please type feedback");

        }
        else if (rate.getRating()<1)
        {
            Toast.makeText(getContext(), "Pleasing provide the ratings also", Toast.LENGTH_SHORT).show();
        }

        else
        {
            if(isOnline())
            {



                Intent Email = new Intent(Intent.ACTION_SEND);

                Email.putExtra(Intent.EXTRA_EMAIL, new String[] { "info@retreatapartment.com" });
                Email.putExtra(Intent.EXTRA_SUBJECT, "Android Feedback form "+mName.getText().toString());
                Email.putExtra(Intent.EXTRA_TEXT, message+"      Ratings :"+numberOfStars );
                Email.setType("message/rfc822");
                try {
                    startActivity(Intent.createChooser(Email, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(getContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
            }
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

    @Override
    public void onResume() {
        super.onResume();

        // Tracking the screen view
        MyApplication.getInstance().trackScreenView("Feedback");
    }
}
