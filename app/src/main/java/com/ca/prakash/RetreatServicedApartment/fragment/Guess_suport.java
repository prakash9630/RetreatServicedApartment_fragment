package com.ca.prakash.RetreatServicedApartment.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ca.prakash.RetreatServicedApartment.R;

/**
 * Created by prakash on 11/1/2017.
 */

public class Guess_suport extends android.support.v4.app.Fragment {
    View mainView;
    Button locationfeedback,guesscomment;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mainView=inflater.inflate(R.layout.guess_support,container,false);

        locationfeedback=(Button)mainView.findViewById(R.id.locationfeedback_btn);
        guesscomment=(Button)mainView.findViewById(R.id.guess_comment);

        locationfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Support");

                FragmentTransaction transaction=getFragmentManager().beginTransaction();
                Location_feedback feedback=new Location_feedback();


                transaction.replace(R.id.mainFragment,feedback);
                transaction.addToBackStack("Guess support");
                transaction.commit();

            }
        });
        guesscomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction=getFragmentManager().beginTransaction();
                GuessLog log=new GuessLog();


                transaction.replace(R.id.mainFragment,log);
                transaction.addToBackStack("Guess comment");
                transaction.commit();

            }
        });


                return mainView;
    }
}
