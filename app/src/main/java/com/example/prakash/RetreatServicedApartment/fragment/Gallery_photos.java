package com.example.prakash.RetreatServicedApartment.fragment;

import android.app.Dialog;
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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.androidadvance.topsnackbar.TSnackbar;
import com.example.prakash.RetreatServicedApartment.Pojo.Album_photo;
import com.example.prakash.RetreatServicedApartment.Public_Url;
import com.example.prakash.RetreatServicedApartment.R;
import com.example.prakash.RetreatServicedApartment.app.MyApplication;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by prakash on 4/3/2017.
 */

public class Gallery_photos extends Fragment {
    View mainView;
    RecyclerView recyclerView;

    ArrayList<Album_photo> data;


   Album_photo datagetter ;

    String url= Public_Url.detailimages;
    String id;
    AlbumRecycler adapter;
FrameLayout frameLayout;
ProgressDialog pDilog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView=inflater.inflate(R.layout.gallery_photo_layout,container,false);
        recyclerView=(RecyclerView)mainView.findViewById(R.id.galler_photo_recycler);

        id= getArguments().getString("nid");


        pDilog = ProgressDialog.show(getActivity(), null, null, true);
        pDilog.setContentView(R.layout.apartment_layout);
        pDilog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        pDilog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        pDilog.show();

if (!isOnline())
{
    frameLayout=(FrameLayout) mainView.findViewById(R.id.frame_gallery_img);




    TSnackbar snackbar = TSnackbar.make(frameLayout, "No Internet connection", TSnackbar.LENGTH_LONG);
    snackbar.setActionTextColor(Color.WHITE);
    View snackbarView = snackbar.getView();
    snackbarView.setBackgroundColor(Color.parseColor("#FF0000"));
    TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
    textView.setTextColor(Color.WHITE);
    snackbar.show();



}

        getData();

        return mainView;
    }

    void getData()
    {

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url+id,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
pDilog.dismiss();
                data=new ArrayList<>();

                try {
                    JSONObject fieldImage=response.getJSONObject("field_image");

                    String title=response.getString("title");

                    JSONArray imagearrey=fieldImage.getJSONArray("und");

                    for (int ii=0;ii<imagearrey.length();ii++)
                    {
                        JSONObject objimage=imagearrey.getJSONObject(ii);

                        String image=objimage.getString("uri");
                        String uri=image.replace("public://","");


                        datagetter=new Album_photo(uri);
                        data.add(datagetter);
                    }





                    adapter=new AlbumRecycler(getContext(),data);
                    recyclerView.setAdapter(adapter);

                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
//                    toolbar.setTitle(title);

                    ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(title);

                } catch (JSONException e) {

                    Toast.makeText(getContext(), "network problem", Toast.LENGTH_SHORT).show();
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
        MyApplication.getInstance().trackScreenView("Gallery photos");
    }

    class AlbumRecycler extends RecyclerView.Adapter<AlbumHolder>
    {
        Context context;
        LayoutInflater layoutInflater;
        ArrayList<Album_photo> data;
        Dialog dialog;


        public AlbumRecycler(Context context, ArrayList<Album_photo> data) {
            this.context = context;
            this.data = data;
            layoutInflater=LayoutInflater.from(context);
        }

        @Override
        public AlbumHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=layoutInflater.inflate(R.layout.gallery_picture_design_layout,parent,false);
            AlbumHolder holder=new AlbumHolder(view,context,data);
            return holder;

        }

        @Override
        public void onBindViewHolder(AlbumHolder holder, final int position) {
            final Album_photo current = data.get(position);

            Picasso.with(context)
                    .load("http://www.retreatservicedapartments.com/sites/default/files/"+current.getImage())
                    .placeholder(R.drawable.defult)   // optional
                    .error(R.drawable.defult)      // optional
                    .resize(600, 340)                        // optional
                    // optional
                    .into(holder.image);




            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.fullscreen_image);
                    final ImageView ivZoomedImage = (ImageView) dialog.findViewById(R.id.fullscreen_image);



                    String imgUrl = current.getImage();


                    Picasso.with(context).setIndicatorsEnabled(false);


                    Picasso.with(context)
                            .load("http://template.nuza.solutions/sites/default/files/"+imgUrl)
                            .into(ivZoomedImage);

                    dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                    dialog.show();






                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }
    class AlbumHolder extends RecyclerView.ViewHolder
    {

        ImageView image;

        Context context;
        ArrayList<Album_photo> data;


        public AlbumHolder(View itemView,Context context,ArrayList<Album_photo> data) {
            super(itemView);
            this.context=context;
            this.data=data;

            image=(ImageView)itemView.findViewById(R.id.singe_image);

        }
    }



}
