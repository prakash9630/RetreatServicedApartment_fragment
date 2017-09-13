package com.example.prakash.RetreatServicedApartment.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
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
import com.example.prakash.RetreatServicedApartment.activity.Place_detail;
import com.example.prakash.RetreatServicedApartment.app.MyApplication;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by prakash on 9/21/2016.
 */
public class Restaurant_activity extends Fragment {
    RecyclerView recyclerView;
    View mainView;

    String filename;
    String url= Public_Url.resturantApi;
    ArrayList<Place_data> arraylist;
    Place_data data;
    RestaurantAdapter adapter;
    ProgressDialog pDilog;
    TextView rtxt;
    String uri;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView=inflater.inflate(R.layout.resturant_layout,container,false);


        pDilog = ProgressDialog.show(getActivity(), null, null, true);
        pDilog.setContentView(R.layout.apartment_layout);
        pDilog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        pDilog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        pDilog.show();

        recyclerView=(RecyclerView)mainView.findViewById(R.id.recycle_restro);
        rtxt=(TextView)mainView.findViewById(R.id.resturant_textview);




        getData();

        return mainView;
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


                        String attractionrestaurant=jsonObject.getString("Attraction Type");

                        rtxt.setText("No Restaurant added");
                        if (attractionrestaurant.equals("Restaurants"))
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



                            data=new Place_data(Nid,name,uri,attractionrestaurant);
                            arraylist.add(data);

                            adapter=new RestaurantAdapter(getContext(),arraylist);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                            rtxt.setVisibility(View.GONE);

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
                    Toast.makeText(getContext(),"Check your internet connection", Toast.LENGTH_SHORT).show();

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

    @Override
    public void onResume() {
        super.onResume();

        // Tracking the screen view
        MyApplication.getInstance().trackScreenView("Resturants");
    }


}

class RestaurantAdapter extends RecyclerView.Adapter<RestaurantHolder>
{
    Context contex;
    LayoutInflater layoutinflater;
     ArrayList<Place_data> data;

    public RestaurantAdapter(Context contex,ArrayList<Place_data> data) {
        this.contex = contex;
        this.layoutinflater = LayoutInflater.from(contex);
        this.data = data;
    }

    @Override
    public RestaurantHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=layoutinflater.inflate(R.layout.travell_fragment_design,parent,false);
        RestaurantHolder holder=new RestaurantHolder(view,contex,data);
        return holder;
    }

    @Override
    public void onBindViewHolder(RestaurantHolder holder, int position) {
        Place_data current=data.get(position);
        holder.textview.setText(current.getPlacename());
        Picasso.with(contex)
                .load("https://www.retreatservicedapartments.com/sites/default/files/"+current.getImage())
                .placeholder(R.drawable.defult)   // optional
                .error(R.drawable.defult)      // optional
//                .resize(400, 190)
                .into(holder.image);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

class RestaurantHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    ImageView image;
    TextView textview;

    Context context;
    ArrayList<Place_data> data;


    public RestaurantHolder(View itemView,Context context,ArrayList<Place_data> data) {
        super(itemView);
this.context=context;
        this.data=data;
        itemView.setOnClickListener((View.OnClickListener) this);

        image=(ImageView)itemView.findViewById(R.id.recycler_img_travel);
        textview=(TextView)itemView.findViewById(R.id.name_place);
    }

    @Override
    public void onClick(View view) {
        int positon=getAdapterPosition();

        Place_data current=data.get(positon);

//        Intent i=new Intent(context,Place_data.class);
//        i.putExtra("nid",current.getNid());
//
//        context.startActivity(i);

        Intent i=new Intent(context,Place_detail.class);
        i.putExtra("nid",current.getNid());
        i.putExtra("name",current.getPlacename());
        i.putExtra("image",current.getImage());
        context.startActivity(i);





    }
}
