package com.example.prakash.RetreatServicedApartment.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.prakash.RetreatServicedApartment.R;
import com.example.prakash.RetreatServicedApartment.app.MyApplication;

/**
 * Created by prakash on 4/10/2017.
 */

public class Web extends Fragment {
    View mainView;

    WebView webView;
    SwipeRefreshLayout mySwipeRefreshLayout;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:{
                    webViewGoBack();
                }break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView=inflater.inflate(R.layout.web_layout,container,false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Kathmandu gallery");



        webView = (WebView)mainView.findViewById(R.id.website_id);
        webView.loadUrl("http://kathmandu.gallery/");


        webView.getSettings().setJavaScriptEnabled(true);

        webView.getSettings().setDomStorageEnabled(true);

        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webView.clearCache(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
//        webView.getSettings().setBuiltInZoomControls(true);






        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url)
            {
                webView.loadUrl("javascript:(function() { " +
                        "document.getElementsByClassName('mainNavigationWrapper')[0].style.display='none'; " +
                        "document.getElementsByClassName('breadcrumbContainer')[0].style.display='none'; " +
                        "document.getElementsByClassName('footerWrapper')[0].style.display='none'; " +
                        "document.getElementsByClassName('sideAds')[0].style.display='none'; " +
                        "})()");

            }


        });





        webView.setWebChromeClient(new WebChromeClient() {
//            private ProgressDialog mProgress;

//            @Override
//            public void onProgressChanged(WebView view, int progress) {
//
//                if (mProgress == null) {
//                    mProgress = new ProgressDialog(getContext());
//                    mProgress.show();
//                }
//                mProgress.setMessage("Loading " + String.valueOf(progress) + "%");
//                if (progress == 100) {
//                    mProgress.dismiss();
//                    mProgress = null;
//                }
//
//            }
        });



        webView.setOnKeyListener(new View.OnKeyListener(){

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK
                        && event.getAction() == MotionEvent.ACTION_UP
                        && webView.canGoBack()) {
                    handler.sendEmptyMessage(1);
                    return true;
                }

                return false;
            }

        });


        mySwipeRefreshLayout = (SwipeRefreshLayout)mainView.findViewById(R.id.swipeContainer);
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        webView.reload();
                        mySwipeRefreshLayout.setRefreshing(false);
                    }
                }
        );





        return mainView;
    }

    private void webViewGoBack(){
        webView.goBack();
    }

    @Override
    public void onResume() {
        super.onResume();

        // Tracking the screen view
        MyApplication.getInstance().trackScreenView("Website");
    }


}
