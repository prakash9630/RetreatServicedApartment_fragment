package com.example.prakash.RetreatServicedApartment.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.prakash.RetreatServicedApartment.Pojo.Order_data;
import com.example.prakash.RetreatServicedApartment.Public_Url;
import com.example.prakash.RetreatServicedApartment.R;
import com.example.prakash.RetreatServicedApartment.activity.MainActivity;
import com.example.prakash.RetreatServicedApartment.app.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by prakash on 4/12/2017.
 */

public class User_orders extends Fragment {
    View mainView;


    RecyclerView recyclerView;
    String url = Public_Url.viewOrder;
    Order_data orderData;
    ArrayList<Order_data> list;
    String userid;
    LinearLayoutManager mLinearlayout;
    OrderAdapter adapter;

    LinearLayout sentLocationbtn,logoutbtn;

    String username;


    ProgressDialog pDilog;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView=inflater.inflate(R.layout.users_order,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Users order");



        recyclerView = (RecyclerView)mainView.findViewById(R.id.order_lists);




        pDilog = ProgressDialog.show(getActivity(), null, null, true);
        pDilog.setContentView(R.layout.apartment_layout);
        pDilog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        pDilog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        pDilog.show();



        sentLocationbtn=(LinearLayout)mainView.findViewById(R.id.sent_location);

        sentLocationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getLatLong();


                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Reason");
                FragmentTransaction transaction=getFragmentManager().beginTransaction();
Location_feedback feedback=new Location_feedback();
                transaction.replace(R.id.mainFragment,feedback);
                transaction.addToBackStack("Reason");
                transaction.commit();


            }
        });
        logoutbtn=(LinearLayout)mainView.findViewById(R.id.logout_btn);

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences preferences=getActivity().getSharedPreferences("Authentication", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.clear();
                editor.commit();





                        Intent refresh = new Intent(getContext(),MainActivity.class);
                startActivity(refresh);//Start the same Activity
                getActivity().finish(); //finish Activity.





            }
        });




        mLinearlayout = new LinearLayoutManager(getContext());
        SharedPreferences preferences =getActivity().getSharedPreferences("Authentication", Context.MODE_PRIVATE);

        userid = preferences.getString("uid", "");
        username=preferences.getString("username","");



        if (!isOnline())
        {

            Toast.makeText(getContext(), "Check your Internet connection", Toast.LENGTH_SHORT).show();

        }


        getData();







        return mainView;
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
        MyApplication.getInstance().trackScreenView("User order");
    }

    void getData() {

        final JsonArrayRequest request = new JsonArrayRequest(url + userid, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

pDilog.dismiss();
                list = new ArrayList<>();

                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject obj = response.getJSONObject(i);


                        if (obj != null) {

                            String ordernumber = obj.getString("order_number");
                            String created = obj.getString("created");
                            String updated = obj.getString("updated");
                            String status = obj.getString("status");
                            String total = obj.getString("total");
                            String customerid = obj.getString("customer_id");

                            long currentdate = Long.parseLong(created);
                            long updateddate = Long.parseLong(updated);
                            Date cdate = new Date(currentdate * 1000L);
                            Date udate = new Date(updateddate * 1000L);
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z"); // the format of your date
                            sdf.setTimeZone(TimeZone.getTimeZone("GMT-4")); // give a timezone reference for formating (see comment at the bottom
                            String mCurrent = sdf.format(cdate);
                            String mUpdated = sdf.format(udate);


                            orderData = new Order_data(ordernumber, mCurrent, mUpdated, total, status, customerid);
                            list.add(orderData);


                            adapter = new OrderAdapter(list,getContext());
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(mLinearlayout);


                        } else {
                            Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();
                        }


                    } catch (JSONException e) {
                        Toast.makeText(getContext(),"No order has been made", Toast.LENGTH_SHORT).show();

                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDilog.dismiss();

                Toast.makeText(getContext(),"Error", Toast.LENGTH_SHORT).show();




            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);

    }

}

class OrderAdapter extends RecyclerView.Adapter<OrderHolder> {

    private LayoutInflater layoutInflater;
    List<Order_data> data = new ArrayList<>();
    Context context;

    public OrderAdapter(List<Order_data> data, Context context) {
        this.data = data;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public OrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.order_design, parent, false);
        OrderHolder holder = new OrderHolder(view, context, (ArrayList<Order_data>) data);
        return holder;
    }

    @Override
    public void onBindViewHolder(OrderHolder holder, int position) {

        final Order_data order = data.get(position);


        holder.mOrderNumber.setText(order.getOrderno());
        holder.mCreated.setText(order.getCreate());
        holder.mUpdatedDate.setText(order.getUpdated());
        holder.mTotal.setText(order.getTotal());
        holder.mOrderStatus.setText(order.getOrderStatus());
        holder.mViewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                FragmentTransaction transaction=((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();


                ViewDetailOrder detailOrder=new ViewDetailOrder();
                Bundle args = new Bundle();
                args.putString("order_number",order.getOrderno());
                detailOrder.setArguments(args);

                transaction.replace(R.id.mainFragment,detailOrder);
                transaction.addToBackStack("userOrder");
                transaction.commit();



            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

class OrderHolder extends RecyclerView.ViewHolder {

    TextView mOrderNumber;
    TextView mCreated;
    TextView mUpdatedDate;
    TextView mTotal;
    TextView mOrderStatus;
    Button mViewbtn;

    ArrayList<Order_data> data = new ArrayList<>();
    Context context;


    public OrderHolder(View itemView, Context context, ArrayList<Order_data> data) {
        super(itemView);

        this.data = data;
        this.context = context;


        mOrderNumber = (TextView) itemView.findViewById(R.id.order_no);
        mCreated = (TextView) itemView.findViewById(R.id.created_id);
        mUpdatedDate = (TextView) itemView.findViewById(R.id.updated_id);
        mTotal = (TextView) itemView.findViewById(R.id.total_id);
        mOrderStatus = (TextView) itemView.findViewById(R.id.order_status_id);
        mViewbtn = (Button) itemView.findViewById(R.id.single_btn);


    }


}