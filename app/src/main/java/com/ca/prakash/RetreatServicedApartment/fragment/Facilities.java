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
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.androidadvance.topsnackbar.TSnackbar;
import com.ca.prakash.RetreatServicedApartment.Pojo.ServicesData;
import com.ca.prakash.RetreatServicedApartment.Pojo.ServicesList;
import com.ca.prakash.RetreatServicedApartment.Pojo.Servicestitle;
import com.ca.prakash.RetreatServicedApartment.Public_Url;


import com.ca.prakash.RetreatServicedApartment.R;
import com.ca.prakash.RetreatServicedApartment.adapter.TitleAdapter;
import com.ca.prakash.RetreatServicedApartment.app.MyApplication;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by prakash on 4/10/2017.
 */

public class Facilities extends Fragment {
    View mainView;

    String url=Public_Url.facilitiesApi;
    String contentUrl=Public_Url.contetentServices;
    String listUrl=Public_Url.Serviceslist;

    RecyclerView recyclerView;
    String img,optimizedimage,value;
    LinearLayoutManager mLinearlayout;
    ArrayList<ServicesData> data;
    ServicesAdapter adapter;
    ServicesData servicedata;
    WebView webView;
    ProgressDialog pDilog;
    FrameLayout frame;

    String pish = "<html><head><style type=\"text/css\">@font-face {font-family: 'Raleway';" +
            "src: url(\"file:///android_asset/fonts/Raleway-ExtraLight.ttf\")}body {font-family: 'Raleway';font-size: medium;text-align: justify;}</style></head><body>";
    String pas = "</body></html>";


    RecyclerView servicelist;

    TitleAdapter mAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView=inflater.inflate(R.layout.amenities_layout,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Facilities");

        webView=(WebView)mainView.findViewById(R.id.content_webview);
        webView.setBackgroundColor(Color.parseColor("#00000000"));

        recyclerView=(RecyclerView)mainView.findViewById(R.id.services_list);
        recyclerView.setNestedScrollingEnabled(false);
        servicelist=(RecyclerView)mainView.findViewById(R.id.aminities_lists);
        servicelist.setNestedScrollingEnabled(false);

        mLinearlayout = new LinearLayoutManager(getContext());

        pDilog = ProgressDialog.show(getActivity(), null, null, true);
        pDilog.setContentView(R.layout.apartment_layout);
        pDilog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        pDilog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        pDilog.show();


        if (!isOnline())
        {

            frame=(FrameLayout) mainView.findViewById(R.id.frame_message);




            TSnackbar snackbar = TSnackbar.make(frame, "No Internet connection", TSnackbar.LENGTH_LONG);
            snackbar.setActionTextColor(Color.WHITE);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(Color.parseColor("#FF0000"));
            TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();






        }


getServices();

        return mainView;
    }

    void getServices()
    {

        final JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET, url,null,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                pDilog.dismiss();
                data = new ArrayList<>();

                getContaint();
                getList();
                for (int i=0;i<response.length();i++)
                {
                    try {
                        JSONObject objectdata=response.getJSONObject(i);
                        String nid=objectdata.getString("nid");
                        String title=objectdata.getString("title");
                        JSONArray imgarray=objectdata.getJSONArray("field_image");
                        for (int j=0;j<imgarray.length();j++)
                        {




                            JSONObject imgobj=imgarray.getJSONObject(j);
                            img=imgobj.getString("uri");


                            optimizedimage=img.replace("public://","");
                        }

                        servicedata=new ServicesData(nid,title,optimizedimage);
                        data.add(servicedata);
                        adapter=new ServicesAdapter(getContext(),data);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(mLinearlayout);




                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Exception occure", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDilog.dismiss();

                if (isOnline())
                    if (isOnline())
                    {

                Toast.makeText(getContext(), "Server error", Toast.LENGTH_SHORT).show();
            }}
        });

        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }

    void getContaint()
    {

        JsonObjectRequest objectRequest=new JsonObjectRequest(Request.Method.GET,contentUrl,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject obj=response.getJSONObject("field_amenities_introduction");
                    JSONArray array=obj.getJSONArray("und");
                    for (int p=0;p<array.length();p++)
                    {
                        JSONObject valueobj=array.getJSONObject(p);
                        String value=valueobj.getString("value");

                        String myHtmlString = pish + value + pas;


                        webView.loadDataWithBaseURL(null, myHtmlString, "text/html", "UTF-8", null);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });

        objectRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(objectRequest);
    }


    ArrayList getList()
    {

        final ArrayList<Servicestitle> genres = new ArrayList<>();

        JsonArrayRequest request=new JsonArrayRequest(listUrl, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {



                for (int i=0;i<response.length();i++)
                {

                    try {
                        JSONObject obj=response.getJSONObject(i);
                        String title=obj.getString("title");
                        JSONArray array=obj.getJSONArray("Amenities List");


                        List<ServicesList> list = new ArrayList<>(array.length());

                        for (int j=0;j<array.length();j++)
                        {

                            JSONObject valueobj=array.getJSONObject(j);
                            value=valueobj.getString("value");


                            list.add(new ServicesList(value));

                        }

                        genres.add(new Servicestitle(title,list));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                    mAdapter= new TitleAdapter(genres);
                    servicelist.setLayoutManager(new LinearLayoutManager(getContext()));
                    servicelist.setAdapter(mAdapter);

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);


        return genres;

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
        MyApplication.getInstance().trackScreenView("Facilities");
    }
}
class ServicesAdapter extends RecyclerView.Adapter<ServicesHolder>
{

    Context context;
    ArrayList<ServicesData> data;
    LayoutInflater layoutInflater;

    public ServicesAdapter(Context context, ArrayList<ServicesData> data) {
        this.context = context;
        this.data = data;
        layoutInflater=LayoutInflater.from(context);

    }

    @Override
    public ServicesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=layoutInflater.inflate(R.layout.single_services_design,parent,false);
        ServicesHolder holder=new ServicesHolder(view,context,data);
        return holder;
    }

    @Override
    public void onBindViewHolder(ServicesHolder holder, int position) {
        ServicesData current=data.get(position);
        holder.textView.setText(current.getTitle());

        Picasso.with(context)
                .load("https://www.retreatservicedapartments.com/sites/default/files/"+current.getImage())
                .placeholder(R.drawable.defult)   // optional
                .error(R.drawable.defult)      // optional
                .into(holder.servicesimg);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
class ServicesHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    TextView textView;
    ImageView servicesimg;
    Context context;
    ArrayList<ServicesData> data;

    public ServicesHolder(View itemView,Context context,ArrayList<ServicesData> data) {
        super(itemView);
        this.context=context;
        this.data=data;
        itemView.setOnClickListener((View.OnClickListener) this);
        textView=(TextView)itemView.findViewById(R.id.sercives_title);
        servicesimg=(ImageView)itemView.findViewById(R.id.services_image);

    }

    @Override
    public void onClick(View v) {
        int positon=getAdapterPosition();

        ServicesData current=data.get(positon);



        FragmentTransaction transaction=((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();

        Services_detail detail=new Services_detail();

        Bundle arg=new Bundle();
        arg.putString("nid",current.getNid());
        detail.setArguments(arg);

        transaction.replace(R.id.mainFragment,detail);
        transaction.addToBackStack("Services detail");
        transaction.commit();


//        Intent i=new Intent(context,Services_detail.class);
//        i.putExtra("nid",current.getNid());
//        context.startActivity(i);
    }
}