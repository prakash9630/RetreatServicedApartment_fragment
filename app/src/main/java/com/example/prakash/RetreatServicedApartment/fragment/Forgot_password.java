package com.example.prakash.RetreatServicedApartment.fragment;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.prakash.RetreatServicedApartment.Public_Url;
import com.example.prakash.RetreatServicedApartment.R;
import com.example.prakash.RetreatServicedApartment.app.MyApplication;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by prakash on 4/12/2017.
 */

public class Forgot_password extends Fragment {

    View mainView;
    EditText mReset;
    Button mResetbtn;
    static  String url= Public_Url.PasswordReset;
    ProgressDialog pDilog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView=inflater.inflate(R.layout.forgetpassword_layout,container,false);


mReset=(EditText)mainView.findViewById(R.id.forgot_username);
mResetbtn=(Button)mainView.findViewById(R.id.forgotpassword_button);

        pDilog=new ProgressDialog(getContext());
        pDilog.setMessage("In progress .....");
        pDilog.setCancelable(false);


        mResetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=mReset.getText().toString();
                if (name.length()<1)
                {
                    mReset.setError("Please enter Username or Email");
                }
                else {

                    if (isOnline()) {
                        passwordReset();
                        pDilog.show();
                    }
                    else
                    {
                        Toast.makeText(getContext(), "No internet connection", Toast.LENGTH_SHORT).show();

                    }



                }}
        });





        return mainView;
    }

    void passwordReset()
    {

        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response!=null) {
                    Toast.makeText(getContext(), "Your password has been reset check your email", Toast.LENGTH_LONG).show();

                    pDilog.dismiss();
                }
                else
                {
                    Toast.makeText(getContext(), "Connection problem", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"Username not matched", Toast.LENGTH_SHORT).show();
                pDilog.dismiss();

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parms=new HashMap<>();
                parms.put("name",mReset.getText().toString());
                return parms;


            }

        };
        RequestQueue que= Volley.newRequestQueue(getContext());
        que.add(request);


        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

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
        MyApplication.getInstance().trackScreenView("Password resert");
    }
}
