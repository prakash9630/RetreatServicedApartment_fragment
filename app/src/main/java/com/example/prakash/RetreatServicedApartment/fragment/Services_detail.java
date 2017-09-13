package com.example.prakash.RetreatServicedApartment.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.prakash.RetreatServicedApartment.Public_Url;
import com.example.prakash.RetreatServicedApartment.R;
import com.example.prakash.RetreatServicedApartment.app.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by prakash on 8/30/2017.
 */

public class Services_detail extends Fragment {

    View mainView;

    String url= Public_Url.detailimages;
    ProgressDialog pDilog;

    SliderLayout sliderShow;

    String title,body,image;
    WebView mBody;

    String pish = "<html><head><style type=\"text/css\">@font-face {font-family: 'Raleway';" +
            "src: url(\"file:///android_asset/fonts/Raleway-ExtraLight.ttf\")}body {font-family: 'Raleway';font-size: medium;text-align: justify;}</style></head><body>";
    String pas = "</body></html>";
    String nid;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    mainView=inflater.inflate(R.layout.service_detail_layout,container,false);





        mBody=(WebView)mainView.findViewById(R.id.service_event_body);
        sliderShow=(SliderLayout)mainView.findViewById(R.id.service_slider_event);

        pDilog = ProgressDialog.show(getActivity(), null, null, true);
        pDilog.setContentView(R.layout.apartment_layout);
        pDilog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        pDilog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        pDilog.show();


        mBody.setBackgroundColor(Color.TRANSPARENT);


//        nid=getArguments().getString("nid");

        ViewTreeObserver vot=sliderShow.getViewTreeObserver();
        vot.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                sliderShow.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int width  = sliderShow.getMeasuredWidth();
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) sliderShow.getLayoutParams();
                params.height = width;
                sliderShow.setLayoutParams(params);
            }
        });



        if (!isOnline())
        {
            Toast.makeText(getContext(), "No internet connection", Toast.LENGTH_SHORT).show();
        }

        getData();






        return mainView;

    }


    void getData()
    {

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url+getArguments().getString("nid"),null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                pDilog.dismiss();


                try {


                    title=response.getString("title");

                    JSONObject fieldImage=response.getJSONObject("field_image");

                    JSONArray array=fieldImage.getJSONArray("und");

                    for (int i=0;i<array.length();i++)
                    {
                        JSONObject imgobj=array.getJSONObject(i);
                        image=imgobj.getString("uri");

                        String uri=image.replace("public://","");

                        TextSliderView textSliderView = new TextSliderView(getContext());
                        textSliderView
                                .image("http://www.retreatservicedapartments.com/sites/default/files/"+uri);


                        sliderShow.addSlider(textSliderView);

                    }
                    JSONObject fieldbody=response.getJSONObject("body");

                    JSONArray bodyarray=fieldbody.getJSONArray("und");

                    for (int i=0;i<bodyarray.length();i++)
                    {
                        JSONObject imgobj=bodyarray.getJSONObject(i);
                        body=imgobj.getString("value");




                    }






                    String myHtmlString = pish + body + pas;


                    mBody.loadDataWithBaseURL(null, myHtmlString, "text/html", "UTF-8", null);




                    ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(title);




                } catch (JSONException e) {

                    Toast.makeText(getContext(), "parse exception" +e, Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), "Server error", Toast.LENGTH_SHORT).show();
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
        MyApplication.getInstance().trackScreenView("Services detail");
    }

}
