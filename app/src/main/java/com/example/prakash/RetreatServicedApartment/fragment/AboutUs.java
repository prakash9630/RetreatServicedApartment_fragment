package com.example.prakash.RetreatServicedApartment.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.androidadvance.topsnackbar.TSnackbar;
import com.example.prakash.RetreatServicedApartment.Pojo.Award_data;
import com.example.prakash.RetreatServicedApartment.Public_Url;
import com.example.prakash.RetreatServicedApartment.R;
import com.example.prakash.RetreatServicedApartment.app.MyApplication;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by prakash on 3/30/2017.
 */

public class AboutUs extends Fragment {
    View mainView;
    FrameLayout frameLayout;

    WebView mFirst;
    String awardurl=Public_Url.Awards;
    String body;
    String pish = "<html><head><style type=\"text/css\">@font-face {font-family: 'Raleway';" +
            "src: url(\"file:///android_asset/fonts/Raleway-ExtraLight.ttf\")}body {font-family: 'Raleway';font-size: medium;text-align: justify;}</style></head><body>";
    String pas = "</body></html>";
    static String url = Public_Url.Aboutus;
    ImageView imageview;
    String image;
    RecyclerView recyclerView;
ProgressDialog pDilog;

    AwardAdapter adapter;
    Award_data data;
    ArrayList<Award_data> lists;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView=inflater.inflate(R.layout.aboutus_layout,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("About us");


        pDilog = ProgressDialog.show(getActivity(), null, null, true);
        pDilog.setContentView(R.layout.apartment_layout);
        pDilog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        pDilog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        pDilog.show();

        mFirst = (WebView)mainView.findViewById(R.id.aboutus_p1);

        imageview=(ImageView)mainView.findViewById(R.id.about_us_img);
        mFirst.setBackgroundColor(Color.parseColor("#00000000"));

        recyclerView=(RecyclerView)mainView.findViewById(R.id.guessAward);
        recyclerView.setNestedScrollingEnabled(false);





        if (!isOnline())
{
    frameLayout=(FrameLayout)mainView.findViewById(R.id.frame_aboutus);




    TSnackbar snackbar = TSnackbar.make(frameLayout, "No Internet connection", TSnackbar.LENGTH_LONG);
    snackbar.setActionTextColor(Color.WHITE);
    View snackbarView = snackbar.getView();
    snackbarView.setBackgroundColor(Color.parseColor("#FF0000"));
    TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
    textView.setTextColor(Color.WHITE);
    snackbar.show();

}
getApi();


        return mainView;
    }

    void getApi() {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDilog.dismiss();

                        try {

                            JSONObject fieldImage = response.getJSONObject("field_image");

                            JSONArray imagearrey = fieldImage.getJSONArray("und");

                            for (int ii = 0; ii < imagearrey.length(); ii++) {
                                JSONObject objimage = imagearrey.getJSONObject(ii);

                                image = objimage.getString("filename");


                                Picasso.with(getContext())
                                        .load("http://www.retreatservicedapartments.com/sites/default/files/"+image)
                                        .centerCrop()
                                        .error(R.drawable.defult)
                                        .placeholder(R.drawable.defult)
                                        .resize(1500, 900)
                                        .into(imageview);

//
                            }


                            JSONObject bodyobj = response.getJSONObject("body");
                            JSONArray undarray = bodyobj.getJSONArray("und");
                            for (int i = 0; i < undarray.length(); i++) {
                                JSONObject paragraph = undarray.getJSONObject(i);
                                body = paragraph.getString("value");
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("aboutus", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();


                        editor.putString("body", body);
                        editor.commit();

                        String myHtmlString = pish + body + pas;


                        mFirst.loadDataWithBaseURL(null, myHtmlString, "text/html", "UTF-8", null);
getAwards();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
pDilog.dismiss();
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("aboutus", Context.MODE_PRIVATE);
                        body = sharedPreferences.getString("body", "");
                        String myHtmlString = pish + body + pas;


                        mFirst.loadDataWithBaseURL(null, myHtmlString, "text/html", "UTF-8", null);


                    }
                });

        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }

    void getAwards()

    {

        final JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET,awardurl,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                lists=new ArrayList<>();

                for (int i=0;i<response.length();i++)
                {

                    try {
                        JSONObject object=response.getJSONObject(i);
                        String title=object.getString("title");
                        String image=object.getString("field_image");

                        data=new Award_data(title,image);
                        lists.add(data);

                        adapter=new AwardAdapter(getContext(),lists);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));




                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


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

        RequestQueue queue=Volley.newRequestQueue(getContext());
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
        MyApplication.getInstance().trackScreenView("About Us");
    }



}

class AwardAdapter extends RecyclerView.Adapter<AwardHolder>
{

    Context context;
    ArrayList<Award_data> data;
    LayoutInflater layoutInflater;

    Typeface type;

    public AwardAdapter(Context context, ArrayList<Award_data> data) {
        this.context = context;
        this.data = data;
        layoutInflater=LayoutInflater.from(context);

    }

    @Override
    public AwardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=layoutInflater.inflate(R.layout.award_design,parent,false);
        AwardHolder holder=new AwardHolder(view,context,data);

        return holder;    }


    @Override
    public void onBindViewHolder(AwardHolder holder, int position) {
        Award_data current=data.get(position);

        Picasso.with(context)
                .load(current.getImage())
                .placeholder(R.drawable.defult)   // optional
                .error(R.drawable.defult)      // optional
                .resize(380,350)
                .into(holder.image);

        holder.name.setText(current.getAward());


        type = Typeface.createFromAsset(context.getAssets(),"fonts/Raleway-ExtraLight.ttf");
        holder.name.setTypeface(type);



    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
class AwardHolder extends RecyclerView.ViewHolder
{
    ImageView image;
    TextView name;
    Context context;
    ArrayList<Award_data> data;


    public AwardHolder(View item,Context context,ArrayList<Award_data> data) {
        super(item);
        this.context=context;
        this.data=data;


        name=(TextView) item.findViewById(R.id.Award_name);

        image=(ImageView)item.findViewById(R.id.Award_image_id);


    }

}