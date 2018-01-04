package com.ca.prakash.RetreatServicedApartment.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.ca.prakash.RetreatServicedApartment.Pojo.Notification_data;
import com.ca.prakash.RetreatServicedApartment.Public_Url;
import com.ca.prakash.RetreatServicedApartment.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;


/**
 * Created by prakash on 5/12/2017.
 */

public class NewsNotification extends AppCompatActivity {

    static String notificationsurl= Public_Url.notifications;
    RecyclerView recyclerView;
    Notification_data data;
    NotificationAdapter adapter;
    ProgressDialog pDilog;
    ArrayList<Notification_data> list;
    Toolbar toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsnotification);
        toolbar=(Toolbar)findViewById(R.id.notification_toolbar);
        setSupportActionBar(toolbar);

        recyclerView=(RecyclerView)findViewById(R.id.notification_recycler);
        pDilog = ProgressDialog.show(this, null, null, true);
        pDilog.setContentView(R.layout.apartment_layout);
        pDilog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        pDilog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        pDilog.show();

        getNotification();

    }

         void getNotification()
        {
            JsonArrayRequest request=new JsonArrayRequest(notificationsurl, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    list=new ArrayList<>();
                    pDilog.dismiss();

                    for (int i=0;i<response.length();i++)
                    {
                        try {
                            JSONObject obj=response.getJSONObject(i);
                            String id=obj.getString("id");
                            String payload=obj.getString("payload");
                            String time=obj.getString("timestamp");

                            long date = Long.parseLong(time);

                            Date ndate = new Date(date * 1000L);

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z"); // the format of your date
                            sdf.setTimeZone(TimeZone.getTimeZone("GMT-4")); // give a timezone reference for formating (see comment at the bottom
                            String actualdate = sdf.format(ndate);

                            data=new Notification_data(id,payload,actualdate);
                            list.add(data);
                            adapter=new NotificationAdapter(NewsNotification.this,list);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(NewsNotification.this));




                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(NewsNotification.this, "exception occure", Toast.LENGTH_SHORT).show();
                        }


                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pDilog.dismiss();
                    if (isOnline())
                    {
                        Toast.makeText(NewsNotification.this, "Server error", Toast.LENGTH_SHORT).show();
                    }
                    else {

                        Toast.makeText(NewsNotification.this, "No Internet connection", Toast.LENGTH_SHORT).show();

                    }

                }
            });
            request.setRetryPolicy(new DefaultRetryPolicy(
                    5000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            RequestQueue queue= Volley.newRequestQueue(this);
            queue.add(request);




        }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

}


class NotificationAdapter extends RecyclerView.Adapter<NotificationHolder>
{

    Context context;
    ArrayList<Notification_data> data;
    LayoutInflater layoutInflater;

    String pish = "<html><head><style type=\"text/css\">@font-face {font-family: 'Raleway';" +
            "src: url(\"file:///android_asset/fonts/Raleway-ExtraLight.ttf\")}body {font-family: 'Raleway';font-size: medium;text-align: justify;}</style></head><body>";
    String pas = "</body></html>";

    public NotificationAdapter(Context context, ArrayList<Notification_data> data) {
        this.context = context;
        this.data = data;
        layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public NotificationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=layoutInflater.inflate(R.layout.notification_design,parent,false);
        NotificationHolder holder=new NotificationHolder(view,context,data);

        return holder;    }


    @Override
    public void onBindViewHolder(NotificationHolder holder, int position) {
        Notification_data current=data.get(position);

        holder.nText.setText(current.getNotification());
        holder.nTime.setText(current.getTime());

;



    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
class NotificationHolder extends RecyclerView.ViewHolder
{

    TextView nText,nTime;

    Context context;
    ArrayList<Notification_data> data;


    public NotificationHolder(View item,Context context,ArrayList<Notification_data> data) {
        super(item);
        this.context=context;
        this.data=data;


        nText=(TextView) item.findViewById(R.id.notification_text);
        nTime=(TextView)item.findViewById(R.id.notification_time);



    }

}