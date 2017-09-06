package com.example.prakash.RetreatServicedApartment.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prakash.RetreatServicedApartment.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

/**
 * Created by prakash on 6/29/2017.
 */

public class Video_fragment extends Fragment {
    View mainView;
    YouTubePlayerView youTubePlayerView;
    YouTubePlayer.OnInitializedListener onInitializedListener;
    TextView mBody;

    String link,body;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView=inflater.inflate(R.layout.videos_layout,container,false);

        youTubePlayerView=(YouTubePlayerView)mainView.findViewById(R.id.youtube_id);


        link=getArguments().getString("link");
        body=getArguments().getString("body");
        mBody=(TextView)mainView.findViewById(R.id.video_body);
        mBody.setText(body);


        onInitializedListener=new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(link);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(getContext(), "Fail", Toast.LENGTH_SHORT).show();
            }
        };



        youTubePlayerView.initialize("AIzaSyBeKUrS0KSQK_70qLRLHo7Nk2zm40-xNzA",onInitializedListener);

        return mainView;
    }
}
