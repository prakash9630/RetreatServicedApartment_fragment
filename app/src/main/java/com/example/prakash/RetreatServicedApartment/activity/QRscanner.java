package com.example.prakash.RetreatServicedApartment.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prakash.RetreatServicedApartment.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by prakash on 8/9/2017.
 */

public class QRscanner extends AppCompatActivity {


    private IntentIntegrator qrScan;
    TextView scantx;
LinearLayout lineartext;
    WebView webView;
    SwipeRefreshLayout mySwipeRefreshLayout;
    String url;

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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrscanner_layout);


        scantx=(TextView)findViewById(R.id.scan_text);

        lineartext=(LinearLayout)findViewById(R.id.content_text);


        qrScan = new IntentIntegrator(this);



                qrScan.initiateScan();


        webView = (WebView) findViewById(R.id.scanner_id);
        webView.setBackgroundColor(Color.parseColor("#00000000"));



        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);






        webSettings.setDomStorageEnabled(true);


        webView.setOnKeyListener(new View.OnKeyListener(){

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK
                        && event.getAction() == MotionEvent.ACTION_UP
                        && webView.canGoBack()) {
                    handler.sendEmptyMessage(1);
                    return true;
                }


//                mySwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeContainer_scanner);
//                mySwipeRefreshLayout.setOnRefreshListener(
//                        new SwipeRefreshLayout.OnRefreshListener() {
//                            @Override
//                            public void onRefresh() {
//                                webView.reload();
//                                mySwipeRefreshLayout.setRefreshing(false);
//                            }
//                        }
//                );


                return false;
            }

        });


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
            private ProgressDialog mProgress;

            @Override
            public void onProgressChanged(WebView view, int progress) {

                if (mProgress == null) {
                    mProgress = new ProgressDialog(QRscanner.this);
                    mProgress.show();
                }
                mProgress.setMessage("Loading " + String.valueOf(progress) + "%");
                if (progress == 100) {
                    mProgress.dismiss();
                    mProgress = null;
                }

            }
        });



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                this.finish();
                scantx.setText("Result Not Found");

            } else {
                //if qr contains data
                try {
                    //converting the data to json
                    JSONObject obj = new JSONObject(result.getContents());
                    //setting values to textviews
//                    textViewName.setText(obj.getString("name"));

                    Toast.makeText(this, ""+obj.getString("name"), Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast
//                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();



                    String URL_REGEX = "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";
                    Pattern p = Pattern.compile(URL_REGEX);
                    Matcher m = p.matcher(result.getContents());//replace with string to compare
                    if(m.find()) {
                        lineartext.setVisibility(View.GONE);

                        webView.loadUrl(result.getContents());
                    }
                    else
                    {
                        Toast.makeText(this, "Thats not url", Toast.LENGTH_SHORT).show();
                        scantx.setText(result.getContents());
                    }
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void webViewGoBack(){
        webView.goBack();
    }
}
