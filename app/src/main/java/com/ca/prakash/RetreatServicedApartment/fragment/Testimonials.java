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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.androidadvance.topsnackbar.TSnackbar;
import com.ca.prakash.RetreatServicedApartment.Pojo.Testimonial_data;
import com.ca.prakash.RetreatServicedApartment.Public_Url;
import com.ca.prakash.RetreatServicedApartment.R;
import com.ca.prakash.RetreatServicedApartment.app.MyApplication;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by prakash on 8/29/2017.
 */

public class Testimonials extends Fragment {
    View mainView;

    RecyclerView recyclerView;
    static String url= Public_Url.Testimonials;
    ArrayList<Testimonial_data> list;
    Testimonial_data data;
    TestimonialAdapter adapter;
    ProgressDialog pDilog;
    FrameLayout frame;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView=inflater.inflate(R.layout.testimonials_layout,container,false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Testimonials");
        recyclerView=(RecyclerView)mainView.findViewById(R.id.testimonial_id);
        recyclerView.setNestedScrollingEnabled(false);
        pDilog = ProgressDialog.show(getActivity(), null, null, true);
        pDilog.setContentView(R.layout.apartment_layout);
        pDilog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        pDilog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        pDilog.show();




        getTestimonials();


        if (!isOnline())
        {
            frame=(FrameLayout) mainView.findViewById(R.id.testimonial_message);




            TSnackbar snackbar = TSnackbar.make(frame, "No Internet connection", TSnackbar.LENGTH_LONG);
            snackbar.setActionTextColor(Color.WHITE);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(Color.parseColor("#FF0000"));
            TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();

        }


        return mainView;
    }

    void getTestimonials()
    {

        final JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET, url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                pDilog.dismiss();

                list=new ArrayList<>();

                for (int i=0;i<response.length();i++)
                {
                    try {
                        JSONObject object=response.getJSONObject(i);
                        String name=object.getString("node_title");
                        String review=object.getString("Subtitle");
                        JSONObject ratings=object.getJSONObject("Rating");

                        String ratingno=ratings.getString("value");
                        String photo=object.getString("Photo");

                        data=new Testimonial_data(name,review,ratingno,photo);
                        list.add(data);
                        adapter=new TestimonialAdapter(getContext(),list);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDilog.dismiss();

                if (isOnline())
                {
                    Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
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
        MyApplication.getInstance().trackScreenView("Testimonials");
    }

}
class TestimonialAdapter extends RecyclerView.Adapter<TestimonialHolder>
{

    Context context;
    ArrayList<Testimonial_data> data;
    LayoutInflater layoutInflater;

    String pish = "<html><head><style type=\"text/css\">@font-face {font-family: 'Raleway';" +
            "src: url(\"file:///android_asset/fonts/Raleway-ExtraLight.ttf\")}body {font-family: 'Raleway';font-size: medium;text-align: justify;}</style></head><body>";
    String pas = "</body></html>";

    public TestimonialAdapter(Context context, ArrayList<Testimonial_data> data) {
        this.context = context;
        this.data = data;
        layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public TestimonialHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=layoutInflater.inflate(R.layout.testimonials_design,parent,false);
        TestimonialHolder holder=new TestimonialHolder(view,context,data);

        return holder;    }


    @Override
    public void onBindViewHolder(TestimonialHolder holder, int position) {
        Testimonial_data current=data.get(position);

        Picasso.with(context)
                .load(current.getImage())
//                .placeholder(R.drawable.defult)   // optional
//                .error(R.drawable.defult)      // optional
                .resize(290,290)
                .into(holder.image);
        holder.name.setText(current.getName());
//        holder.ratingtext.loadData(current.getReviewtext(), "text/html", "utf-8");

        String myHtmlString1 = pish +current.getReviewtext() + pas;
        holder.ratingtext.loadDataWithBaseURL(null, myHtmlString1, "text/html", "UTF-8", null);

        holder.rating.setRating(Float.parseFloat(current.getRatings()));





    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
class TestimonialHolder extends RecyclerView.ViewHolder
{
    ImageView image;
    TextView name;
    WebView ratingtext;
    RatingBar rating;
    Context context;
    ArrayList<Testimonial_data> data;


    public TestimonialHolder(View item,Context context,ArrayList<Testimonial_data> data) {
        super(item);
        this.context=context;
        this.data=data;


        name=(TextView) item.findViewById(R.id.person_name);
        ratingtext=(WebView) item.findViewById(R.id.testimonial_text);
        rating=(RatingBar) item.findViewById(R.id.rating);
        image=(ImageView)item.findViewById(R.id.person_image);


    }

}