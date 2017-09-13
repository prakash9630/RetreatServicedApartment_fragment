package com.example.prakash.RetreatServicedApartment.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;

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
import org.json.JSONTokener;

/**
 * Created by prakash on 6/21/2017.
 */

public class Event_detail extends Fragment {
    View mainView;
    String url= Public_Url.detailimages;
    ProgressDialog pDilog;

    SliderLayout sliderShow;

    String title,body,image;
    WebView mBody;

    String pish = "<html><head><style type=\"text/css\">@font-face {font-family: 'Raleway';" +
            "src: url(\"file:///android_asset/fonts/Raleway-ExtraLight.ttf\")}body {font-family: 'Raleway';font-size: medium;text-align: justify;}</style></head><body>";
    String pas = "</body></html>";
    String nid,when;
    TextView time;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView=inflater.inflate(R.layout.event_detail,container,false);

        mBody=(WebView)mainView.findViewById(R.id.event_body);
        mBody.setBackgroundColor(Color.parseColor("#00000000"));
        time=(TextView)mainView.findViewById(R.id.event_time);
        sliderShow=(SliderLayout)mainView.findViewById(R.id.slider_event);




        pDilog = ProgressDialog.show(getActivity(), null, null, true);
        pDilog.setContentView(R.layout.apartment_layout);
        pDilog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        pDilog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        pDilog.show();

        nid=getArguments().getString("nid");
        when=getArguments().getString("when");

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getArguments().getString("title"));



getData();
        return mainView;
    }

    void getData()
    {

        JsonObjectRequest request=new JsonObjectRequest(url+nid,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {



                pDilog.dismiss();
                time.setVisibility(View.VISIBLE);


                try {
                    time.setText("Date :"+when);

                    String mobilebody=response.getString("field_mobile_body");
                    Object json = new JSONTokener(mobilebody).nextValue();


                    if (json instanceof JSONArray)
                    {



                        JSONObject p2 = response.getJSONObject("body");
                        JSONArray array = p2.getJSONArray("und");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject paragraph = array.getJSONObject(i);
                            body = paragraph.getString("value");


                        }
                    }
                    else if (json instanceof JSONObject)
                    {



                        JSONArray mobilearray=((JSONObject) json).getJSONArray("und");

                        for (int i=0;i<mobilearray.length();i++)
                        {
                            JSONObject moblieobject=mobilearray.getJSONObject(i);

                            body = moblieobject.getString("value");
                        }

                    }

                    String mobileimage=response.getString("field_mobile_image");
                    Object jsonimage = new JSONTokener(mobileimage).nextValue();

                    if (jsonimage instanceof JSONArray)
                    {
                        JSONObject fieldImage=response.getJSONObject("field_image");
                        JSONArray imagearrey=fieldImage.getJSONArray("und");
                        for (int ii=0;ii<4;ii++)
                        {
                            JSONObject objimage=imagearrey.getJSONObject(ii);

                            image=objimage.getString("uri");
                            String uri=image.replace("public://","");

                            TextSliderView textSliderView = new TextSliderView(getContext());
                            textSliderView
                                    .image("http://www.retreatservicedapartments.com/sites/default/files/"+uri);


                            sliderShow.addSlider(textSliderView);

                            sliderShow.setPresetTransformer(SliderLayout.Transformer.Background2Foreground);
//        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
//                    slider.setCustomAnimation(new DescriptionAnimation());
                            sliderShow.setDuration(5000);





                        }

                    }
                    else if (jsonimage instanceof JSONObject)
                    {
                        JSONObject mobilefieldImage=response.getJSONObject("field_mobile_image");
                        JSONArray mobileimagearrey=mobilefieldImage.getJSONArray("und");
                        for (int i=0;i<mobileimagearrey.length();i++)
                        {
                            JSONObject mobileobject=mobileimagearrey.getJSONObject(i);
                            image=mobileobject.getString("uri");
                            String uri=image.replace("public://","");

                            TextSliderView textSliderView = new TextSliderView(getContext());
                            textSliderView
                                    .image("http://www.retreatservicedapartments.com/sites/default/files/"+uri);


                            sliderShow.addSlider(textSliderView);

                            sliderShow.setPresetTransformer(SliderLayout.Transformer.Background2Foreground);

                            sliderShow.setDuration(5000);



                        }

                    }



                    String myHtmlString = pish + body + pas;


                    mBody.loadDataWithBaseURL(null, myHtmlString, "text/html", "UTF-8", null);










                } catch (JSONException e) {

                    Toast.makeText(getContext(), ""+e, Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDilog.dismiss();

                Toast.makeText(getContext(), ""+error, Toast.LENGTH_SHORT).show();
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
        MyApplication.getInstance().trackScreenView("Event detail");
    }

}
