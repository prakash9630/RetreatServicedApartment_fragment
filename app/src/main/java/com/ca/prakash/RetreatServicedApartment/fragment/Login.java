package com.ca.prakash.RetreatServicedApartment.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ca.prakash.RetreatServicedApartment.Public_Url;
import com.ca.prakash.RetreatServicedApartment.R;
import com.ca.prakash.RetreatServicedApartment.activity.MainActivity;
import com.ca.prakash.RetreatServicedApartment.app.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by prakash on 4/11/2017.
 */

public class Login extends Fragment {
    View mainView;
Button loginbtn;

    ProgressDialog pDilog;

    EditText mUsername,mPassword;

    TextView mForgot;


    String sessionid,sessionname,token,Uname,mail,mCookie,userid;


    static  String Loginurl= Public_Url.LoginUrl;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView=inflater.inflate(R.layout.login_layout,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Login");


loginbtn=(Button)mainView.findViewById(R.id.sign_in_button);
        mUsername=(EditText) mainView.findViewById(R.id.login_username);
        mPassword=(EditText)mainView.findViewById(R.id.login_password);
        mForgot=(TextView)mainView.findViewById(R.id.forgot_text);




//        SharedPreferences preferences=getActivity().getSharedPreferences("Authentication", Context.MODE_PRIVATE);
//        String mCookies = preferences.getString("cookies",null);
//
//        if (mCookies!=null)
//        {
//
//            FragmentTransaction transaction=getFragmentManager().beginTransaction();
//           Dashboard dashboard=new Dashboard();
//            transaction.replace(R.id.mainFragment,dashboard);
//
//            transaction.remove(this).commit();
//
//
//
//        }





        mForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction manager=getFragmentManager().beginTransaction();
                Forgot_password forgot_password=new Forgot_password();
                manager.replace(R.id.mainFragment,forgot_password);
                manager.addToBackStack("Login");
                manager.commit();


            }
        });

        mForgot.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    mForgot.setPaintFlags(mForgot.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    mForgot.setPaintFlags(mForgot.getPaintFlags() & (~ Paint.UNDERLINE_TEXT_FLAG));


                }return false;
            }
        });






        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mUsername.length()<1)
                {
                    mUsername.setError("Please enter your username");
                }
                if(mPassword.length()<1)
                {
                    mPassword.setError("Please enter your password");
                }
                else {

                    if (!isOnline()) {
                        Toast.makeText(getContext(), "No internet", Toast.LENGTH_SHORT).show();

                    }
                    else
                    {

                    Login();

                }}
            }
        });



        return mainView;
    }

    void Login()
    {
        pDilog=new ProgressDialog(getActivity());
        pDilog.setMessage("Loading...");
        pDilog.setCancelable(false);
        pDilog.show();
        StringRequest request=new StringRequest(Request.Method.POST, Loginurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                try {



                    JSONObject obj = new JSONObject(response);
                    sessionid = obj.getString("sessid");
                    sessionname = obj.getString("session_name");
                    token = obj.getString("token");


                    JSONObject user = obj.getJSONObject("user");

                    Uname = user.getString("name");
                    mail = user.getString("mail");
                    userid=user.getString("uid");


                    mCookie = sessionname+ "=" + sessionid;







                    SharedPreferences preferences=getActivity().getSharedPreferences("Authentication", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("sessionid",sessionid);
                    editor.putString("sessionname",sessionname);
                    editor.putString("token",token);
                    editor.putString("username",Uname);
                    editor.putString("email",mail);
                    editor.putString("cookies",mCookie);
                    editor.putString("uid",userid);
                    editor.commit();





                    pDilog.dismiss();


                    Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();

//                    FragmentTransaction transaction=getFragmentManager().beginTransaction();
//                    User_orders orders=new User_orders();
//                    transaction.replace(R.id.mainFragment,orders);
//                    transaction.addToBackStack("Login");
//                    transaction.commit();



                            Intent refresh = new Intent(getContext(),MainActivity.class);
                    startActivity(refresh);//Start the same Activity
                    getActivity().finish(); //finish Activity.


                } catch (JSONException e) {
                    e.printStackTrace();
                    pDilog.dismiss();
                }





            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"wrong username or password", Toast.LENGTH_LONG).show();

                pDilog.dismiss();


            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String,String> parms=new HashMap<>();
                parms.put("username", mUsername.getText().toString());
                parms.put("password", mPassword.getText().toString());


                return parms;
            }


        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue q= Volley.newRequestQueue(getContext());
        q.add(request);
    }


    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
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
        MyApplication.getInstance().trackScreenView("Login form");
    }

}
