package com.example.prakash.RetreatServicedApartment.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.prakash.RetreatServicedApartment.Pojo.Place_data;
import com.example.prakash.RetreatServicedApartment.Public_Url;
import com.example.prakash.RetreatServicedApartment.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by prakash on 9/21/2016.
 */
public class Cultural_places_activity extends Fragment {
    RecyclerView recyclerView;
    View mainView;


    String filename;
    String url= Public_Url.resturantApi;
    ArrayList<Place_data> arraylist;
    Place_data data;
    RestaurantAdapter adapter;
    ProgressDialog pDilog;
    TextView landmark;
String uri;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView=inflater.inflate(R.layout.cultural_places_layout,container,false);


        pDilog = ProgressDialog.show(getActivity(), null, null, true);
        pDilog.setContentView(R.layout.apartment_layout);
        pDilog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        pDilog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        pDilog.show();

recyclerView=(RecyclerView)mainView.findViewById(R.id.cultural_recyclerview);

        landmark=(TextView)mainView.findViewById(R.id.landmark_textview);


        getData();



        return mainView;
    }
    void getData()
    {

        final JsonArrayRequest request=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                pDilog.dismiss();
                arraylist=new ArrayList<>();


                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject jsonObject=response.getJSONObject(i);


                        String attractionlandmark=jsonObject.getString("Attraction Type");

                        landmark.setText("No place added");



                        if (attractionlandmark.equals("Landmark"))

                        {


                            String Nid=jsonObject.getString("Nid");
                            String name=jsonObject.getString("Name");

                            JSONArray arrayimg=jsonObject.getJSONArray("Images");

                            for (int j=0;j < arrayimg.length();j++)
                            {

                                JSONObject imgobj=arrayimg.getJSONObject(j);

                                filename=imgobj.getString("uri");
                                uri=filename.replace("public://","");
                            }



                            data=new Place_data(Nid,name,uri,attractionlandmark);
                            arraylist.add(data);

                            adapter=new RestaurantAdapter(getContext(),arraylist);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                            landmark.setVisibility(View.GONE);



                        }










                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDilog.dismiss();

                if (isOnline()) {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(),"No internet connection", Toast.LENGTH_SHORT).show();

                }
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
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


}


