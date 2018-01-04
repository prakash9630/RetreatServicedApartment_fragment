package com.ca.prakash.RetreatServicedApartment.fragment;

import android.app.ProgressDialog;
import android.content.Context;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import com.android.volley.toolbox.Volley;

import com.androidadvance.topsnackbar.TSnackbar;
import com.ca.prakash.RetreatServicedApartment.Pojo.Appartment_type_data;
import com.ca.prakash.RetreatServicedApartment.Public_Url;
import com.ca.prakash.RetreatServicedApartment.R;
import com.ca.prakash.RetreatServicedApartment.app.MyApplication;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

/**
 * Created by prakash on 3/31/2017.
 */

public class Apartments extends Fragment {
    View mainView;
    String url = Public_Url.galleryAlbum;

    ArrayList<Appartment_type_data> data;
    Appartment_type_data apartments;
    ApartmentAdapter adapter;
    RecyclerView recyclerView;
    ProgressBar pbar;
    FrameLayout frameLayout;
    ProgressDialog pDilog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.apartment_layout, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Apartments");
        recyclerView = (RecyclerView) mainView.findViewById(R.id.apartment_design_id);
        pbar=(ProgressBar)mainView.findViewById(R.id.apartment_progress);

        pDilog = ProgressDialog.show(getActivity(), null, null, true);
        pDilog.setContentView(R.layout.apartment_layout);
        pDilog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        pDilog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        pDilog.show();



        if (!isOnline())
        {

            frameLayout=(FrameLayout) mainView.findViewById(R.id.apartment_frame);


            pbar.setVisibility(View.GONE);


            TSnackbar snackbar = TSnackbar.make(frameLayout, "No Internet connection", TSnackbar.LENGTH_LONG);
            snackbar.setActionTextColor(Color.WHITE);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(Color.parseColor("#FF0000"));
            TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        }
        sendRequest();
        return mainView;

    }


    void sendRequest() {





        pbar.setVisibility(View.GONE);
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                pDilog.dismiss();
                data = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject obj = response.getJSONObject(i);
                        if (obj != null) {
                            apartments = new Appartment_type_data();


                            String bannerimg=obj.getString("mobile_banner");

                            Object json = new JSONTokener(bannerimg).nextValue();
                            if (json instanceof JSONArray)
                            {

                                apartments.setImage(obj.getString("image_url"));
                            }
                            else
                            {

                                apartments.setImage(obj.getString("mobile_banner"));
                            }


                            apartments.setAppartmentname(obj.getString("Room Type"));
                            apartments.setPrice(obj.getString("Base Price"));
                            apartments.setWeekprice(obj.getString("Base Price Week"));
                            apartments.setMonthprice(obj.getString("Base Price Month"));
                            apartments.setNid(obj.getString("Nid"));
                            apartments.setMachinename(obj.getString("machine_name"));


                            data.add(apartments);
                            adapter = new ApartmentAdapter(getContext(), data);

                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


//


                        } else {

                        }


                    } catch (JSONException e) {
                        pDilog.dismiss();
                        Toast.makeText(getContext(), "Exception occur"+ e.toString(), Toast.LENGTH_LONG).show();

                    }
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                pDilog.dismiss();
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
    @Override
    public void onResume() {
        super.onResume();

        // Tracking the screen view
        MyApplication.getInstance().trackScreenView("Apartments");
    }

    class ApartmentAdapter extends RecyclerView.Adapter<ApartmetnHolder> {
        Context context;
        ArrayList<Appartment_type_data> data;
        LayoutInflater layoutInflater;

        public ApartmentAdapter(Context context, ArrayList<Appartment_type_data> data) {
            this.context = context;
            this.data = data;
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public ApartmetnHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = layoutInflater.inflate(R.layout.apartment_design_layout, parent, false);
            ApartmetnHolder holder = new ApartmetnHolder(view, context, data);

            return holder;
        }

        @Override
        public void onBindViewHolder(ApartmetnHolder holder, int position) {
            Appartment_type_data current = data.get(position);
            holder.uniteType.setText(current.getAppartmentname());
            holder.unitPrice.setText("$" + current.getPrice() + " /Night " + " $" + current.getWeekprice() + " /Week " + " $" + current.getMonthprice() + " /Month");

            Picasso.with(context)
                    .load(current.getImage())
                    .placeholder(R.drawable.defult)   // optional
                    .error(R.drawable.defult)      // optional
                    .into(holder.image);

        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    class ApartmetnHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image;
        TextView uniteType, unitPrice;
        Context context;
        ArrayList<Appartment_type_data> data;


        public ApartmetnHolder(View itemView, Context context, ArrayList<Appartment_type_data> data) {
            super(itemView);
            this.context = context;
            this.data = data;
            itemView.setOnClickListener((View.OnClickListener) this);

            image = (ImageView) itemView.findViewById(R.id.apartment_img);
            uniteType = (TextView) itemView.findViewById(R.id.apartment_name);
            unitPrice = (TextView) itemView.findViewById(R.id.price_unit);


        }


        @Override
        public void onClick(View v) {
            int positon = getAdapterPosition();

            Appartment_type_data current = data.get(positon);
//
//        Intent i=new Intent(context,Appartment_detail.class);
//        i.putExtra("nid",current.getNid());
//        i.putExtra("machinename",current.getMachinename());
//        context.startActivity(i);


            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            Apartment_detail aptdetail=new Apartment_detail();
            Bundle args = new Bundle();
            args.putString("nid", current.getNid());
            args.putString("machinename",current.getMachinename());
            aptdetail.setArguments(args);

            transaction.replace(R.id.mainFragment, aptdetail);
            transaction.addToBackStack("Gallery");
            transaction.commit();

        }
    }
}