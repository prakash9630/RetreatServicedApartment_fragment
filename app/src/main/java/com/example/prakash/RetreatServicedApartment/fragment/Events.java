package com.example.prakash.RetreatServicedApartment.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.prakash.RetreatServicedApartment.Pojo.Event_data;
import com.example.prakash.RetreatServicedApartment.Public_Url;
import com.example.prakash.RetreatServicedApartment.R;
import com.example.prakash.RetreatServicedApartment.app.MyApplication;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by prakash on 6/21/2017.
 */

public class Events extends Fragment {
    View mainView;
    RecyclerView recyclerView;
    String url= Public_Url.event;
    ProgressDialog pDilog;
    ArrayList<Event_data> list;
    Event_data data;
    EventAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
mainView=inflater.inflate(R.layout.events_layout,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Events");

        recyclerView=(RecyclerView)mainView.findViewById(R.id.events_recycler);
        pDilog = ProgressDialog.show(getActivity(), null, null, true);
        pDilog.setContentView(R.layout.apartment_layout);
        pDilog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        pDilog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        pDilog.show();
        getData();
        return mainView;
    }

    void getData()
    {

        final JsonArrayRequest request=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                pDilog.dismiss();

                list=new ArrayList<>();


                if (response.length()>0){

                    for (int i = 0; i < response.length(); i++) {

                        try {

                            JSONObject obj = response.getJSONObject(i);


                            String when = obj.getString("When");
                            String nid = obj.getString("Nid");
                            String title = obj.getString("title");
                            String image = obj.getString("mobile_banner");


                            data = new Event_data(when, title, image, nid);
                            list.add(data);

                            adapter = new EventAdapter(getContext(), list);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



                        } catch (JSONException e) {
                            e.printStackTrace();


                        }
                    }
                }
                else
                {
                    noPost();
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

    void noPost() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
        builder1.setTitle("Event");

        builder1.setMessage("There is no Event added right now");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });



        AlertDialog alert11 = builder1.create();
        alert11.show();


    }
    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
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
        MyApplication.getInstance().trackScreenView("Events");
    }

}

class EventAdapter extends RecyclerView.Adapter<EventHolder>
{

    Context context;
    ArrayList<Event_data> data;
    LayoutInflater layoutInflater;

    public EventAdapter(Context context, ArrayList<Event_data> data) {
        this.context = context;
        this.data = data;
        layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=layoutInflater.inflate(R.layout.event_design,parent,false);
        EventHolder holder=new EventHolder(view,context,data);

        return holder;
    }

    @Override
    public void onBindViewHolder(EventHolder holder, int position) {
        Event_data current=data.get(position);

        Picasso.with(context)
                .load(current.getImage())
                .placeholder(R.drawable.defult)   // optional
                .error(R.drawable.defult)      // optional
                .resize(100, 100)
                .into(holder.image);
//        holder.event_name.loadData(current.getTitle(), "text/html", "UTF-8");
        holder.event_name.setText(current.getTitle());
        holder.event_time.setText(current.getWhen());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    ImageView image;
    TextView event_name,event_time;
    Context context;
    ArrayList<Event_data> data;


    public EventHolder(View item,Context context,ArrayList<Event_data> data) {
        super(item);
        this.context=context;
        this.data=data;
        item.setOnClickListener((View.OnClickListener) this);

        event_time=(TextView) item.findViewById(R.id.event_date);
        event_name=(TextView) item.findViewById(R.id.event_name);
        image=(ImageView)item.findViewById(R.id.event_image);


    }

    @Override
    public void onClick(final View v) {


        int positon=getAdapterPosition();

        Event_data current=data.get(positon);



        FragmentTransaction transaction=((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();

        Event_detail detail=new Event_detail();
        Bundle args = new Bundle();
        args.putString("nid", current.getNid());
        args.putString("when",current.getWhen());
        args.putString("title",current.getTitle());
        detail.setArguments(args);

        transaction.replace(R.id.mainFragment, detail);
        transaction.addToBackStack("Gallery");
        transaction.commit();







    }
}