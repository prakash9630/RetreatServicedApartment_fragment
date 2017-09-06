package com.example.prakash.RetreatServicedApartment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prakash.RetreatServicedApartment.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;



/**
 * Created by prakash on 6/6/2017.
 */

public class Videos extends YouTubeBaseActivity {
    YouTubePlayerView youTubePlayerView;
    YouTubePlayer.OnInitializedListener onInitializedListener;
TextView mBody;
    Intent i;
    String link,body;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videos_layout);
        youTubePlayerView=(YouTubePlayerView)findViewById(R.id.youtube_id);

        i=getIntent();
        link=i.getStringExtra("link");
        body=i.getStringExtra("body");
        mBody=(TextView)findViewById(R.id.video_body);
        mBody.setText(body);





        onInitializedListener=new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
               youTubePlayer.loadVideo(link);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(Videos.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        };



        youTubePlayerView.initialize("AIzaSyBeKUrS0KSQK_70qLRLHo7Nk2zm40-xNzA",onInitializedListener);




    }
}
