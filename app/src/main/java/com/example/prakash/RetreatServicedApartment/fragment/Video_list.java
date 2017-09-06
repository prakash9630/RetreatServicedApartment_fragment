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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.prakash.RetreatServicedApartment.Pojo.VideoData;
import com.example.prakash.RetreatServicedApartment.Public_Url;
import com.example.prakash.RetreatServicedApartment.R;
import com.example.prakash.RetreatServicedApartment.activity.Videos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by prakash on 6/29/2017.
 */

public class Video_list extends Fragment {
    RecyclerView recyclerView;

    String url= Public_Url.videolist;
    ArrayList<VideoData> list;
    VideoData data;
    videoAdapter adapter;
    ProgressDialog pDilog;
    View mainView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView=inflater.inflate(R.layout.videolist_layout,container,false);

        recyclerView=(RecyclerView)mainView.findViewById(R.id.Videolist_id);
        pDilog = ProgressDialog.show(getActivity(), null, null, true);
        pDilog.setContentView(R.layout.apartment_layout);
        pDilog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        pDilog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        pDilog.show();

        getVideos();

        return mainView;

    }

    void getVideos()

    {
        final JsonArrayRequest request=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                list=new ArrayList<>();
                pDilog.dismiss();

                for (int i=0; i<response.length();i++)
                {

                    try {
                        JSONObject object=response.getJSONObject(i);
                        String title=object.getString("title");
                        String url=object.getString("url");
                        String body=object.getString("body");

                        data=new VideoData(title,url,body);
                        list.add(data);
                        adapter=new videoAdapter(getContext(),list);
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
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(getContext(), "No Internet connection", Toast.LENGTH_SHORT).show();
                }


            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue queue= Volley.newRequestQueue(getContext());
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
}

class videoAdapter extends RecyclerView.Adapter<videoHolder>
{

    Context context;
    ArrayList<VideoData> data;
    LayoutInflater layoutInflater;

    public videoAdapter(Context context, ArrayList<VideoData> data) {
        this.context = context;
        this.data = data;
        layoutInflater=LayoutInflater.from(context);

    }

    @Override
    public videoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=layoutInflater.inflate(R.layout.videolist_design,parent,false);
        videoHolder holder=new videoHolder(view,context,data);
        return holder;
    }

    @Override
    public void onBindViewHolder(videoHolder holder, int position) {
        VideoData current=data.get(position);
        holder.textView.setText(current.getTitle());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
class videoHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    TextView textView;
    Context context;
    ArrayList<VideoData> data;

    public videoHolder(View itemView,Context context,ArrayList<VideoData> data) {
        super(itemView);
        this.context=context;
        this.data=data;
        itemView.setOnClickListener((View.OnClickListener) this);
        textView=(TextView)itemView.findViewById(R.id.video_name);

    }

    @Override
    public void onClick(View v) {
        int positon=getAdapterPosition();

        VideoData current=data.get(positon);
        Intent i=new Intent(context, Videos.class);
        i.putExtra("body",current.getBody());
        i.putExtra("link",current.getLink());
        context.startActivity(i);





//        FragmentTransaction fragmentTransaction=((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
//
//        Video_fragment video=new Video_fragment();
//        Bundle args = new Bundle();
//        args.putString("body", current.getBody());
//        args.putString("link",current.getLink());
//        video.setArguments(args);
//
//        fragmentTransaction.replace(R.id.mainFragment,video);
//        fragmentTransaction.addToBackStack("Video_fragment");
//        fragmentTransaction.commit();


    }
}