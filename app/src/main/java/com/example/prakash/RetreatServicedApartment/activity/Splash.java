package com.example.prakash.RetreatServicedApartment.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.prakash.RetreatServicedApartment.R;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


/**
 * Created by prakash on 8/4/2016.
 */
public class Splash extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 2000;

    ImageView mSplash_icon;
    String currentVersion, latestVersion;
    Dialog dialog;
ProgressBar pBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        mSplash_icon = (ImageView) findViewById(R.id.splash_logo);
        pBar=(ProgressBar)findViewById(R.id.progressbar_id);

//
//        Animation anim = AnimationUtils.loadAnimation(this, R.anim.anim_down);
//
//        mSplash_icon.setAnimation(anim);
//
//
//        anim.setDuration(2000);
//        anim.start();



        mainPage();

//        if (isOnline()) {
//            getCurrentVersion();
//            new GetLatestVersion().execute();
//
//
//        } else {
//
//
//            mainPage();
//
//
//
//
//
//        }


    }

    void mainPage() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */

                Intent mainIntent = new Intent(Splash.this,MainActivity.class);
                Splash.this.startActivity(mainIntent);
                Splash.this.finish();


            }
        }, SPLASH_DISPLAY_LENGTH);
    }


//    protected boolean isOnline() {
//        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo netInfo = cm.getActiveNetworkInfo();
//        if (netInfo != null && netInfo.isConnected()) {
//
//
//            return true;
//        }
//
//
//        else {
//            return false;
//        }
//    }
//
//
//    private void getCurrentVersion() {
//        PackageManager pm = this.getPackageManager();
//        PackageInfo pInfo = null;
//
//        try {
//            pInfo = pm.getPackageInfo(this.getPackageName(), 0);
//
//
//
//
//
//        } catch (PackageManager.NameNotFoundException e1) {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//
//
//
//        }
//        currentVersion = pInfo.versionName;
//
//
//
//
//
//    }
//
//    private class GetLatestVersion extends AsyncTask<String, String, JSONObject> {
//
//        private ProgressDialog progressDialog;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//        }
//
//
//        @Override
//        protected JSONObject doInBackground(String... params) {
//
//
//
//
//            try {
//
//
//
//
//
//
////It retrieves the latest version by scraping the content of current version from play store at runtime
//                Document doc = Jsoup.connect("https://play.google.com/store/apps/details?id=project.revision.tap.retre").get();
//                latestVersion = doc.getElementsByAttributeValue
//                        ("itemprop", "softwareVersion").first().text();
//
//            } catch (Exception e) {
//                e.printStackTrace();
//
//
//
//
//
//
//            }
//
//
//            return new JSONObject();
//
//
//
//
//        }
//
//        @Override
//        protected void onPostExecute(JSONObject jsonObject) {
//
//
//
//            if (latestVersion != null) {
//                if (!currentVersion.equalsIgnoreCase(latestVersion)) {
//                    if (!isFinishing()) { //This would help to prevent Error : BinderProxy@45d459c0 is not valid; is your activity running? error
//                        showUpdateDialog();
//                        pBar.setVisibility(View.GONE);
//                    }
//                } else {
//                    mainPage();
//
//
//                }
//            }
//
//
//
//            super.onPostExecute(jsonObject);
//        }
//    }
//
//
//
//    private void showUpdateDialog() {
//        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("A New Update is Available");
//        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse
//                        ("market://details?id=project.revision.tap.retre")));
//                dialog.dismiss();
//            }
//        });
//
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                /* Create an Intent that will start the Menu-Activity. */
//
//                        Intent mainIntent = new Intent(Splash.this, MainActivity.class);
//                        Splash.this.startActivity(mainIntent);
//                        Splash.this.finish();
//
//
//                    }
//                }, 300);
//            }
//        });
//
//        builder.setCancelable(false);
//        dialog = builder.show();
//    }

}
