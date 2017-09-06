package com.example.prakash.RetreatServicedApartment.Firebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.prakash.RetreatServicedApartment.R;


/**
 * Created by prakash on 5/12/2017.
 */

public class NewsNotification extends AppCompatActivity {
    TextView notification;
    String someData,someData2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsnotification);
        notification=(TextView)findViewById(R.id.notificationtext);





        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            Bundle extras = intent.getExtras();
            someData= extras.getString("someData");
            someData2 = extras.getString("someData2");


//            notification.setText(someData);
        }

processExtrasData();
    }
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);    //must store the new intent unless getIntent() will return the old one
        processExtrasData();
    }

    private void processExtrasData() {

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            String myInfo= extras.getString("myInfo",someData);

            notification.setText(myInfo);

            Log.i("MyApp", myInfo);
        }
}

}
