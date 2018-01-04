package com.ca.prakash.RetreatServicedApartment.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ca.prakash.RetreatServicedApartment.Public_Url;
import com.ca.prakash.RetreatServicedApartment.R;
import com.ca.prakash.RetreatServicedApartment.activity.MainActivity;
import com.ca.prakash.RetreatServicedApartment.app.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by prakash on 5/17/2017.
 */

public class CompletPaymet extends AppCompatActivity {

    Toolbar toolbar;
    TextView textView;
    Button btn;
    String res;
    String orderid,unittype,mEmail;
    String CheckoutUrl= Public_Url.completCheckout;
    ProgressDialog pDilogue;


    Intent i;
    ProgressDialog progressdilog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.completpayment_layout);


        textView=(TextView)findViewById(R.id.completboking_text);
        btn=(Button)findViewById(R.id.complet_btn);
        pDilogue=new ProgressDialog(this);

        pDilogue.setCancelable(false);
        progressdilog=new ProgressDialog(this);
        progressdilog.setMessage("Waiting for response");
        progressdilog.setCancelable(false);


        i=getIntent();
        orderid=i.getStringExtra("order_id");
        unittype=i.getStringExtra("unitType");
        mEmail=i.getStringExtra("mEmail");







        textView.setText("Your order id is "+ orderid +" for "+ unittype +" in Retreat Serviced Apartments. Please click the complete button to complete the booking process");

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new CountDownTimer(50000, 1000) {

                    public void onTick(long millisUntilFinished) {


                        pDilogue.setMessage("Please wait for  "+ millisUntilFinished / 1000 +" seconds to complete your transaction");
                    }

                    public void onFinish() {

                    }
                }.start();
                pDilogue.show();

                Runnable clickButton = new Runnable() {
                    @Override
                    public void run() {

                        pDilogue.dismiss();


                        checkOut();




                    }

                };
                btn.postDelayed(clickButton, 50000);



            }
        });



    }

    void checkOut()
    {

        progressdilog.show();

        final StringRequest request=new StringRequest(Request.Method.POST,CheckoutUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                progressdilog.dismiss();



                try {
                    JSONObject obj=new JSONObject(response);


                    res=obj.getString("status");

                    Toast.makeText(CompletPaymet.this, ""+res, Toast.LENGTH_LONG).show();

                    Log.e("value of res",res);
                    if (res.equals("Booking Complete"))
                    {
                        aprovalDilog();


                    }
                    else
                    {

                        errorDilog();

                    }









                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressdilog.dismiss();

                errorDilog();


            }
        })
        {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("order_id",""+orderid);

                return params;






            }


        };




        RequestQueue queue= Volley.newRequestQueue(this);
        queue.add(request);

        request.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    void  aprovalDilog()
    {
        AlertDialog alertDialog = new AlertDialog.Builder(CompletPaymet.this).create();

        // Setting Dialog Title
        alertDialog.setTitle("Booking");

        // Setting Dialog Message
        alertDialog.setMessage("Booking completed succesfully. We'll send a confirmation to your email");



        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                pDilogue.dismiss();
                Intent intent=new Intent(CompletPaymet.this,MainActivity.class);
                startActivity(intent);

                finish();

            }
        });

        // Showing Alert Message
        alertDialog.show();

    }



    void  errorDilog()
    {
        AlertDialog alertDialog = new AlertDialog.Builder(
                CompletPaymet.this).create();

        // Setting Dialog Title
        alertDialog.setTitle("Booking");

        // Setting Dialog Message
        alertDialog.setMessage("Error occured.Please press complete button again");



        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


            }
        });

        // Showing Alert Message
        alertDialog.show();

    }


    @Override
    public void onBackPressed() {

    }

    @Override
    public void onResume() {
        super.onResume();

        // Tracking the screen view
        MyApplication.getInstance().trackScreenView("Complet payment");
    }

}
