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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import com.androidadvance.topsnackbar.TSnackbar;
import com.ca.prakash.RetreatServicedApartment.Pojo.Album_data;
import com.ca.prakash.RetreatServicedApartment.Public_Url;
import com.ca.prakash.RetreatServicedApartment.R;
import com.ca.prakash.RetreatServicedApartment.app.MyApplication;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by prakash on 3/30/2017.
 */

public class Gallery extends Fragment {
    View mainView;
    RecyclerView recycler;
    String url = Public_Url.galleryAlbum;

    ArrayList<Album_data> data;
    Album_data gallery;
    GalleryRecycler adapter;
FrameLayout frame;
ProgressDialog pDilog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
mainView=inflater.inflate(R.layout.gallery_layout,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Gallery");
recycler=(RecyclerView)mainView.findViewById(R.id.album_recycler);

        pDilog = ProgressDialog.show(getActivity(), null, null, true);
        pDilog.setContentView(R.layout.apartment_layout);
        pDilog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        pDilog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        pDilog.show();

        if (!isOnline())
        {

frame=(FrameLayout)mainView.findViewById(R.id.frame) ;



            TSnackbar snackbar = TSnackbar.make(frame, "No Internet connection", TSnackbar.LENGTH_LONG);
            snackbar.setActionTextColor(Color.WHITE);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(Color.parseColor("#FF0000"));
            TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();




        }
        sendRequest();

        return mainView;
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
        MyApplication.getInstance().trackScreenView("Gallery album");
    }
    class GalleryRecycler extends RecyclerView.Adapter<GalleryHolder> {

        Context context;
        ArrayList<Album_data> data;
        LayoutInflater layoutinflater;

        public GalleryRecycler(Context context, ArrayList<Album_data> data) {
            this.context = context;
            this.data = data;
            layoutinflater = LayoutInflater.from(context);

        }

        @Override
        public GalleryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = layoutinflater.inflate(R.layout.gallery_album_design, parent, false);
            GalleryHolder holder = new GalleryHolder(view, context, data);
            return holder;
        }

        @Override
        public void onBindViewHolder(final GalleryHolder holder, int position) {
            final Album_data current = data.get(position);
            holder.mText.setText(current.getMunitname());

            Picasso.with(context)
                    .load(current.getmImage())
                    .placeholder(R.drawable.defult)   // optional
                    .error(R.drawable.defult)      // optional
                    .resize(600, 340)
                    .into(holder.gImage);



        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }




    void sendRequest() {
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
pDilog.dismiss();

                data = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject obj = response.getJSONObject(i);
                        if (obj != null) {
                            gallery=new Album_data();


                            gallery.setmImage(obj.getString("image_url"));
                            gallery.setMunitname(obj.getString("Room Type"));
                            gallery.setNid(obj.getString("Nid"));



                            data.add(gallery);


                            adapter = new GalleryRecycler(getContext(), data);
                            recycler.setAdapter(adapter);
//                            recycler.setLayoutManager(new LinearLayoutManager(Album_View_Gallery.this));
                            recycler.setLayoutManager(new GridLayoutManager(getContext(),2));


                        } else {
                            Toast.makeText(getContext(), "NO data found", Toast.LENGTH_SHORT).show();
                        }


                    } catch (JSONException e) {
                        Toast.makeText(getContext(), "Network problem", Toast.LENGTH_SHORT).show();

                    }
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (isOnline()) {
                    Toast.makeText(getContext(), "Server error", Toast.LENGTH_SHORT).show();
                }
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



    class GalleryHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView gImage;
        TextView mText;

        Context context;
        ArrayList<Album_data> data;

        public GalleryHolder(View itemView, Context context, ArrayList<Album_data> data) {
            super(itemView);
            this.context = context;
            this.data = data;

            itemView.setOnClickListener((View.OnClickListener) this);

            mText = (TextView) itemView.findViewById(R.id.gallery_label);
            gImage = (ImageView) itemView.findViewById(R.id.gallary_image);

        }

        @Override
        public void onClick(View v) {
            int positon=getAdapterPosition();
            Album_data current = data.get(positon);


//            Intent i = new Intent(context, Gallery_album.class);
//            i.putExtra("nid",current.getNid());
//            context.startActivity(i);






//            getFragmentManager().beginTransaction().add(R.id.mainFragment, photos).commit();



            FragmentTransaction transaction=getFragmentManager().beginTransaction();

            Gallery_photos photos = new Gallery_photos();
            Bundle args = new Bundle();
            args.putString("nid", current.getNid());
            photos.setArguments(args);

            transaction.replace(R.id.mainFragment,photos);
            transaction.addToBackStack("Gallery");
            transaction.commit();



        }
    }

}
