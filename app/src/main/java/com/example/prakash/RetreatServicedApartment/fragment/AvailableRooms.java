package com.example.prakash.RetreatServicedApartment.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.prakash.RetreatServicedApartment.Pojo.Available_rooms_getter;
import com.example.prakash.RetreatServicedApartment.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prakash on 5/15/2017.
 */

public class AvailableRooms extends Fragment
{


    View mainView;

    String Unit_type, Body, Image, Unit_type_machinename;
    int unit_no;

    Available_rooms_getter availableRooms;
    ArrayList<Available_rooms_getter> arrayRoom;

    static String url;
    RecyclerView recyclerView;
    LinearLayoutManager mLinearlayout;
    String roomType;
    String mUnitname;
    int bookingprice;
    private String parameterString;
    ProgressDialog pDilog;
    String start, end, unit1, unit2, unit3;
    int unit;
    RecyclerBookingAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView=inflater.inflate(R.layout.availableroom_layout,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Availaible rooms");

        recyclerView = (RecyclerView)mainView.findViewById(R.id.avilable_room_id);
        recyclerView.setNestedScrollingEnabled(false);


        pDilog = ProgressDialog.show(getActivity(), null, null, true);
        pDilog.setContentView(R.layout.apartment_layout);
        pDilog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        pDilog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        pDilog.show();

        start=getArguments().getString("arrival");
        end=getArguments().getString("departure");
        unit=getArguments().getInt("unit");




        roomType = getArguments().getString("mtype");

        mUnitname = getArguments().getString("unit_name");



        unit1 = "1";
        unit2 = "1";
        unit3 = "1";

        mLinearlayout = new LinearLayoutManager(getContext());
        parameterString = "?type=" + roomType + "&"
                + "rooms_group_size1="
                + unit1 + "&" + "rooms_group_size2="
                + unit2 + "&" + "rooms_group_size3="
                + unit3;

        url = "http://demo.nuza.solutions/androidapi/v1/app_availability_search/" + start + "/" + end + "/"
                + unit + parameterString;


        Log.e("myurl", url);
        sendRequest();


        return mainView;
    }


    void noRoomDilog() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
        builder1.setTitle("Sorry");

        builder1.setMessage("Unfortunately no units are available - try different dates if possible.");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });



        AlertDialog alert11 = builder1.create();
        alert11.show();


    }




    void  alertDilog()
    {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
        builder1.setTitle("Sorry");

        builder1.setMessage("For the specified dates, all units of " + mUnitname + " are booked. Please select available units below.");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });



        AlertDialog alert11 = builder1.create();
        alert11.show();

    }


    void sendRequest() {
        JsonArrayRequest request = new JsonArrayRequest(url,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                arrayRoom = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {

                    try {


                        JSONObject obj = response.getJSONObject(i);

                        int wasnotavailable = obj.getInt("was_not_available");
                        String notfund = String.valueOf(wasnotavailable);

                        Unit_type = obj.getString("unit_type");
                        Unit_type_machinename = obj.getString("unit_type_machine_name");
                        if (notfund.equals("1")) {
                            alertDilog();
                        }


                        Double Booking_price = obj.getDouble("booking_price");
                        bookingprice = Booking_price.intValue();

                        JSONObject nestedObj = obj.getJSONObject("unit_type_details");


                       JSONObject Bodyobj = nestedObj.getJSONObject("body");
                        Body=Bodyobj.getString("value");
                        Image = nestedObj.getString("uri");
                        String uri=Image.replace("public://","");


                        JSONObject UnitObj = obj.getJSONObject("units");
                        unit_no = UnitObj.length();


                        availableRooms = new Available_rooms_getter(Unit_type_machinename, Unit_type, bookingprice, Body, uri, unit_no);
                        arrayRoom.add(availableRooms);


                        pDilog.dismiss();


                        adapter = new RecyclerBookingAdapter(arrayRoom, getContext());
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(mLinearlayout);






                    } catch (JSONException e) {
                        e.printStackTrace();

                        pDilog.dismiss();
                        noRoomDilog();
                    }
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



}

class RecyclerBookingAdapter extends RecyclerView.Adapter<BookingHolder> {

    private LayoutInflater layoutInflater;
    List<Available_rooms_getter> data=new ArrayList<>();
    Context context;
    String pish = "<html><head><style type=\"text/css\">@font-face {font-family: 'Raleway';" +
            "src: url(\"file:///android_asset/fonts/Raleway-ExtraLight.ttf\")}body {font-family: 'Raleway';font-size: medium;text-align: justify;}</style></head><body>";
    String pas = "</body></html>";


    public RecyclerBookingAdapter(List<Available_rooms_getter> data,Context context) {
        this.data=data;
        this.context=context;
        layoutInflater=LayoutInflater.from(context);



    }

    @Override
    public BookingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= layoutInflater.inflate(R.layout.single_available_room_design,parent,false);
        BookingHolder holder=new BookingHolder(view,context, (ArrayList<Available_rooms_getter>) data);
        return holder;
    }

    @Override
    public void onBindViewHolder(final BookingHolder holder, int position) {
        final Available_rooms_getter current=data.get(position);


        holder.Unit_name.setText(current.getApartmentName());
        holder.Unit_price.setText("Total price : $"+current.getBookingPrice().toString());


        holder.Unit_body.loadData(current.getBody(), "text/html; charset=utf-8", "UTF-8");
        String image=current.getImage();



        String myHtmlString1 = pish +current.getBody() + pas;
        holder.Unit_body.loadDataWithBaseURL(null, myHtmlString1, "text/html", "UTF-8", null);


        Picasso.with(context)
                .load("http://www.retreatservicedapartments.com/sites/default/files/"+image)
                .placeholder(R.drawable.defult)   // optional
                .error(R.drawable.defult)      // optional
//                .resize(600, 340)                        // optional
                // optional
                .into(holder.Unit_image);




        final List<String> list=new ArrayList<String>();
        int countNo=current.getUnitNo();
        for (int i=0;i<=countNo;i++)
        {
            list.add(""+i);

        }
        holder.Unit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String no_of_unit=holder.unit_no.getSelectedItem().toString();

                if (holder.unit_no.getSelectedItem().equals("0"))
                {
                    Toast.makeText(context, "Please select a unit in order to continue with booking.", Toast.LENGTH_SHORT).show();
                }else {

                    FragmentTransaction transaction=((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                    Checkout check=new Checkout();

                    Bundle args = new Bundle();

                    args.putString("Unit_Type",current.getApartmentName());
                    args.putString("Unit_Price",""+current.getBookingPrice());

                    args.putString("Unit_no",no_of_unit);
                    args.putString("Unit_machinename",current.getUnit_tyep_Machinename());


                    check.setArguments(args);
                    transaction.replace(R.id.mainFragment,check);
                    transaction.addToBackStack("Checkout");
                    transaction.commit();




                }}
        });
        holder.unit_no.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<String> adp1=new ArrayAdapter<String>(context,
                android.R.layout.simple_list_item_1,list);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.unit_no.setAdapter(adp1);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
class BookingHolder extends RecyclerView.ViewHolder {

    TextView Unit_name,Unit_price;
    WebView Unit_body;
    ImageView Unit_image;
    Button Unit_button;
    Spinner unit_no;



    ArrayList<Available_rooms_getter> data=new ArrayList<>();
    Context context;






    public BookingHolder(View itemView,Context context,ArrayList<Available_rooms_getter> data) {
        super(itemView);

        this.data=data;
        this.context=context;




        Unit_name=(TextView)itemView.findViewById(R.id.unit_name);
        Unit_price=(TextView)itemView.findViewById(R.id.unit_price);
        Unit_body=(WebView)itemView.findViewById(R.id.unit_body);
        Unit_image=(ImageView)itemView.findViewById(R.id.unit_image);
        Unit_button=(Button)itemView.findViewById(R.id.unit_button);
        unit_no=(Spinner)itemView.findViewById(R.id.spinner_unit);
        Unit_body .setBackgroundColor(Color.TRANSPARENT);






    }
}