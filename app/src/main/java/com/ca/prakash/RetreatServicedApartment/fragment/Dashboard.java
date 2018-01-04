package com.ca.prakash.RetreatServicedApartment.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidadvance.topsnackbar.TSnackbar;
import com.daimajia.slider.library.SliderLayout;

import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.ca.prakash.RetreatServicedApartment.Data.Channel;
import com.ca.prakash.RetreatServicedApartment.Data.Item;
import com.ca.prakash.RetreatServicedApartment.Pojo.Main_sliderData;
import com.ca.prakash.RetreatServicedApartment.Public_Url;
import com.ca.prakash.RetreatServicedApartment.R;
import com.ca.prakash.RetreatServicedApartment.activity.Location_activity;
import com.ca.prakash.RetreatServicedApartment.activity.Travells;
import com.ca.prakash.RetreatServicedApartment.app.MyApplication;
import com.ca.prakash.RetreatServicedApartment.service.WeatherServiceCallback;
import com.ca.prakash.RetreatServicedApartment.service.YahooWeatherService;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by prakash on 3/28/2017.
 */

public class Dashboard extends android.support.v4.app.Fragment implements WeatherServiceCallback{

    View mainView;
    SliderLayout sliderLayout;
    LinearLayout contactus,apartment,gallery,location,travel,facilities,aboutus,booknow,testimonial;
    int duration=200;
    LinearLayout lineartop;
    YahooWeatherService service;
    Main_sliderData sliderdata;
    static String Registerurl=Public_Url.RegisterToken;

    TextView temperature;
    TextView condition;
    TextView weather_location;
    LinearLayout weather;
    ArrayList <Main_sliderData> data;

    TextView booknowtxt,facilitytxt,contacttxt;

    Typeface type;

    static String url=Public_Url.galleryAlbum;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        mainView=inflater.inflate(R.layout.dashboard_layout,container,false);


        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Home");

        final Animation animScale = AnimationUtils.loadAnimation(getContext(), R.anim.button_animation);


        Log.e("token id:",""+FirebaseInstanceId.getInstance().getToken());


        RegisterToken();

        temperature=(TextView)mainView.findViewById(R.id.degree_id);
        condition=(TextView)mainView.findViewById(R.id.condition_id);
        weather_location=(TextView)mainView.findViewById(R.id.place_id);
        weather=(LinearLayout)mainView.findViewById(R.id.linear_weather);
        booknowtxt=(TextView)mainView.findViewById(R.id.booknow_text);
        facilitytxt=(TextView)mainView.findViewById(R.id.facility_text);
        contacttxt=(TextView)mainView.findViewById(R.id.contact_text);



        aboutus=(LinearLayout) mainView.findViewById(R.id.aboutus_id);
        gallery=(LinearLayout) mainView.findViewById(R.id.gallery_id);
        location=(LinearLayout) mainView.findViewById(R.id.location_id);
        travel=(LinearLayout)mainView.findViewById(R.id.travel_id);
        apartment=(LinearLayout)mainView.findViewById(R.id.apartment_id);
        facilities=(LinearLayout)mainView.findViewById(R.id.linear_facilities);
        contactus=(LinearLayout)mainView.findViewById(R.id.linear_contact);
        booknow=(LinearLayout)mainView.findViewById(R.id.booknow);
        testimonial=(LinearLayout)mainView.findViewById(R.id.testimonial_id);

        sendRequest();


        type = Typeface.createFromAsset(getContext().getAssets(),"fonts/Raleway-ExtraLight.ttf");
        booknowtxt.setTypeface(type);
        facilitytxt.setTypeface(type);
        contacttxt.setTypeface(type);

        condition.setTypeface(type);
        weather_location.setTypeface(type);
        temperature.setTypeface(type);

        service=new YahooWeatherService((WeatherServiceCallback) this);


        booknow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animScale);


                Handler handler = new Handler();
                handler.postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        FragmentTransaction transaction=getFragmentManager().beginTransaction();
                        Booking_form form=new Booking_form();

                        Bundle arg=new Bundle();
                        arg.putString("unit_name","All");
                        form.setArguments(arg);

                        transaction.replace(R.id.mainFragment,form);
                        transaction.addToBackStack("book now");
                        transaction.commit();

                    }
                }, duration);
            }
        });

        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.startAnimation(animScale);


                Handler handler = new Handler();
                handler.postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Contact Us");
                        FragmentTransaction transaction=getFragmentManager().beginTransaction();
                        Contact_us contact=new Contact_us();
                        transaction.replace(R.id.mainFragment,contact);
                        transaction.addToBackStack("Contact Us");
                        transaction.commit();

                    }
                }, duration);
            }
        });


        travel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animScale);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable(){
                    @Override
                    public void run(){

                        Intent intent=new Intent(getContext(), Travells.class);
                        startActivity(intent);



                    }
                }, duration);




            }
        });
        facilities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.startAnimation(animScale);


                Handler handler = new Handler();
                handler.postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Facilities");
                        FragmentTransaction transaction=getFragmentManager().beginTransaction();
                        Facilities facilities=new Facilities();
                        transaction.replace(R.id.mainFragment,facilities);
                        transaction.addToBackStack("Facilities");
                        transaction.commit();

                    }
                }, duration);

            }
        });


        apartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.startAnimation(animScale);


                Handler handler = new Handler();
                handler.postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Apartments");
                        FragmentTransaction transaction=getFragmentManager().beginTransaction();
                        Apartments apartments=new Apartments();
                        transaction.replace(R.id.mainFragment,apartments);
                        transaction.addToBackStack("apartment");
                        transaction.commit();

                    }
                }, duration);



            }
        });

        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("About us");
                view.startAnimation(animScale);


                Handler handler = new Handler();
                handler.postDelayed(new Runnable(){
                    @Override
                    public void run(){

                        FragmentTransaction transaction=getFragmentManager().beginTransaction();
                        AboutUs aboutus=new AboutUs();

                        transaction.replace(R.id.mainFragment,aboutus);
                        transaction.addToBackStack("aboutus");
                        transaction.commit();

                                         }
                }, duration);





            }
        });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Location");
                view.startAnimation(animScale);




                Handler handler = new Handler();
                handler.postDelayed(new Runnable(){
                    @Override
                    public void run(){

//                        FragmentTransaction transaction=getFragmentManager().beginTransaction();
//                        Location location=new Location();
//                        transaction.replace(R.id.mainFragment,location);
//                        transaction.addToBackStack("location");
//                        transaction.commit();

                        Intent intent=new Intent(getContext(), Location_activity.class);
                        startActivity(intent);

                    }
                }, duration);


            }
        });

        testimonial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animScale);


                Handler handler = new Handler();
                handler.postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        FragmentTransaction transaction=getFragmentManager().beginTransaction();
                        Testimonials test=new Testimonials();

                        Bundle arg=new Bundle();
                        arg.putString("unit_name","All");
                        test.setArguments(arg);

                        transaction.replace(R.id.mainFragment,test);
                        transaction.addToBackStack("Testimonials");
                        transaction.commit();

                    }
                }, duration);
            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Gallery");
                view.startAnimation(animScale);


                Handler handler = new Handler();
                handler.postDelayed(new Runnable(){
                    @Override
                    public void run(){

                        FragmentTransaction transaction=getFragmentManager().beginTransaction();
                        Gallery gallery=new Gallery();
                        transaction.replace(R.id.mainFragment,gallery);
                        transaction.addToBackStack("gallery");
                        transaction.commit();

                    }
                }, duration);

            }
        });

        sliderLayout = (SliderLayout) mainView.findViewById(R.id.dashboard_slider);


        lineartop=(LinearLayout)mainView.findViewById(R.id.linearapt);


        if (!isOnline())
        {






            TSnackbar snackbar = TSnackbar.make(lineartop, "No Internet connection", TSnackbar.LENGTH_LONG);
            snackbar.setActionTextColor(Color.WHITE);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(Color.parseColor("#FF0000"));
            TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();






        }
        else
        {
            service.refreshWeather("Kathmandu");

        }




lineartop.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {


        FragmentTransaction transaction=getFragmentManager().beginTransaction();
        Apartments apartments=new Apartments();
        transaction.replace(R.id.mainFragment,apartments);
        transaction.addToBackStack("apartment");
        transaction.commit();

    }
});


        return mainView;








    }
    void RegisterToken()
    {
        StringRequest request=new StringRequest(Request.Method.POST, Registerurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject obj = new JSONObject(response);




                } catch (JSONException e) {
                    e.printStackTrace();

                    Toast.makeText(getContext(), "Parse error", Toast.LENGTH_SHORT).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (isOnline())
                {
                    Toast.makeText(getContext(), "Unable to register token", Toast.LENGTH_SHORT).show();
                }




            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parms=new HashMap<>();
                parms.put("token",FirebaseInstanceId.getInstance().getToken());
                parms.put("type","android");
                parms.put("language","en");

                Log.d("Token no :",""+FirebaseInstanceId.getInstance().getToken());



                return parms;



            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }

    void sendRequest() {
        final JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(final JSONArray response) {


                data=new ArrayList<>();


                for (int i = 0; i < response.length(); i++) {




                    try {




                        final JSONObject obj = response.getJSONObject(i);
                        if (obj != null) {

                            sliderdata=new Main_sliderData();





                            sliderdata.setImage(obj.getString("image_url"));
                            sliderdata.setRoomname(obj.getString("Room Type"));



                            data.add(sliderdata);

                            final TextSliderView textSliderView = new TextSliderView(getContext());
                            textSliderView
                                    .description(obj.getString("Room Type"))
                                    .image(obj.getString("image_url"));





                            sliderLayout.setPresetTransformer(SliderLayout.Transformer.Fade);




                            sliderLayout.addSlider(textSliderView);




                        } else {
                            Toast.makeText(getContext(), "NO data found", Toast.LENGTH_SHORT).show();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
//                    slider.setPresetTransformer(SliderLayout.Transformer.FlipHorizontal);
                    sliderLayout.setDuration(5000);







                }







            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                if (isOnline()) {
                    Toast.makeText(getContext(), "Server error", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onResume() {
        super.onResume();

        // Tracking the screen view
        MyApplication.getInstance().trackScreenView("Home Page");
    }


    @Override
    public void serviceSuccess(Channel channel) {
        Item item = channel.getItem();


        weather_location.setText(service.getLocation());
        condition.setText(item.getCondition().getDescription()+",");
        temperature.setText(item.getCondition().getTemperature()+"\u00B0 "+channel.getUnits().getTemperature());
    }

    @Override
    public void serviceFalure(Exception exception) {

        weather.setVisibility(View.GONE);

    }





}
