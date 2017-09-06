package com.example.prakash.RetreatServicedApartment.fragment;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prakash.RetreatServicedApartment.R;
import com.example.prakash.RetreatServicedApartment.app.MyApplication;

/**
 * Created by prakash on 3/30/2017.
 */

public class Contact_us extends Fragment {

    View mainView;
    TextView phone,mobile,info;
    Button Call,Email,messenger;
    ImageView fb,twitter,instagram,gplus,youtube,p;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView=inflater.inflate(R.layout.contactus_layout,container,false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Contact");
        messenger=(Button)mainView.findViewById(R.id.messenger_btn);
        phone=(TextView)mainView.findViewById(R.id.ph_no);
        mobile=(TextView)mainView.findViewById(R.id.mobile_no);
        Call=(Button)mainView.findViewById(R.id.call_btn);
        Email=(Button)mainView.findViewById(R.id.email_btn);
        info=(TextView)mainView.findViewById(R.id.link);
        fb=(ImageView)mainView.findViewById(R.id.fb_icon);
        twitter=(ImageView)mainView.findViewById(R.id.twiter_icon);
        gplus=(ImageView)mainView.findViewById(R.id.gplus_icon);
        youtube=(ImageView)mainView.findViewById(R.id.youtube_icon);
        p=(ImageView)mainView.findViewById(R.id.p_icond);
        instagram=(ImageView)mainView.findViewById(R.id.insta_icon);



        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/retreatapartment/?fref=ts"));
                startActivity(intent);
            }
        });
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/intent/follow?original_referer=https%3A%2F%2Ffbapp.us%2Fapps%2Ftwittertab%2F&ref_src=twsrc%5Etfw&screen_name=RetreatKTM&tw_p=followbutton"));
                startActivity(intent);

            }
        });
        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/retreatsa/"));
                startActivity(intent);

            }
        });
        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.pinterest.com/retreatserviced/"));
                startActivity(intent);

            }
        });
        gplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/u/0/+RetreatKathmandu/"));
                startActivity(intent);

            }
        });
        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UC2Wh76L9BkMWzKjw9KfAjJg"));
                startActivity(intent);

            }
        });




        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:014274837"));
                startActivity(i);
            }
        });
        mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(Intent.ACTION_DIAL,Uri.parse("tel:9801098941"));
                startActivity(i);
            }
        });


        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse("http://info@retreatapartment.com/"));
                startActivity(viewIntent);
            }
        });

        Email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_EMAIL,
                        new String[] {"retreatservicedapartments@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "subject");
                i.putExtra(Intent.EXTRA_TEXT, "message");
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getContext(),
                            "There are no email clients installed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        messenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.me/retreatapartment"));
                startActivity(intent);
            }
        });
        Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(Intent.ACTION_DIAL,Uri.parse("tel:9801098941"));
                startActivity(i);
            }
        });
        phone.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    phone.setPaintFlags(phone.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    phone.setPaintFlags(phone.getPaintFlags() & (~ Paint.UNDERLINE_TEXT_FLAG));


                }return false;
            }
        });
        mobile.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    mobile.setPaintFlags(mobile.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    mobile.setPaintFlags(mobile.getPaintFlags() & (~ Paint.UNDERLINE_TEXT_FLAG));


                }return false;
            }
        });

        info.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    info.setPaintFlags(info.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    info.setPaintFlags(info.getPaintFlags() & (~ Paint.UNDERLINE_TEXT_FLAG));


                }return false;
            }
        });


        return mainView;
    }


    @Override
    public void onResume() {
        super.onResume();

        // Tracking the screen view
        MyApplication.getInstance().trackScreenView("Contact us");
    }
}
