package com.ca.prakash.RetreatServicedApartment.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ca.prakash.RetreatServicedApartment.R;

/**
 * Created by prakash on 11/1/2017.
 */

public class GuessLog extends Fragment {
    View mainView;
    RecyclerView recyclerView;
    EditText commenttext;
    Button postbtn;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      mainView=inflater.inflate(R.layout.guess_log,container,false);

      recyclerView=(RecyclerView)mainView.findViewById(R.id.guess_posts);
      commenttext=(EditText) mainView.findViewById(R.id.edit_text);
      postbtn=(Button)mainView.findViewById(R.id.post_btn);



      postbtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

          }
      });










      return mainView;
    }
}
