package com.example.prakash.RetreatServicedApartment.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.prakash.RetreatServicedApartment.app.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by prakash on 4/18/2017.
 */

public class Recreational_sites extends Fragment {
    View mainView;
    String filename;
    RecyclerView recyclerView;
    String url= Public_Url.resturantApi;
    ArrayList<Place_data> arraylist;
    Place_data data;
    RestaurantAdapter adapter;

    TextView txt;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
mainView=inflater.inflate(R.layout.recreational_sites,container,false);


        recyclerView=(RecyclerView)mainView.findViewById(R.id.recycle_recrational);

        txt=(TextView)mainView.findViewById(R.id.recreational_textview);

        getData();




        return mainView;
    }

    void getData()
    {

        final JsonArrayRequest request=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                arraylist=new ArrayList<>();

                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject jsonObject=response.getJSONObject(i);

                        String attractionrecreational=jsonObject.getString("Attraction Type");
txt.setText("No Recreational sites added");

                        if (attractionrecreational.equals("Recreational Sites"))
                        {
                            String Nid=jsonObject.getString("Nid");
                            String name=jsonObject.getString("Name");

                            JSONArray arrayimg=jsonObject.getJSONArray("Images");

                            for (int j=0;j < arrayimg.length();j++)
                            {

                                JSONObject imgobj=arrayimg.getJSONObject(j);

                                filename=imgobj.getString("filename");
                            }

                            data=new Place_data(Nid,name,filename,attractionrecreational);
                            arraylist.add(data);

                            adapter=new RestaurantAdapter(getContext(),arraylist);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
txt.setVisibility(View.GONE);
                        }









                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(),"error" +
                        "", Toast.LENGTH_SHORT).show();

            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }
    @Override
    public void onResume() {
        super.onResume();

        // Tracking the screen view
        MyApplication.getInstance().trackScreenView("Recreational sites");
    }
}

