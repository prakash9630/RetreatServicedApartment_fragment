package com.ca.prakash.RetreatServicedApartment.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ca.prakash.RetreatServicedApartment.R;

import com.ca.prakash.RetreatServicedApartment.fragment.Booking_form;

import com.ca.prakash.RetreatServicedApartment.fragment.Dashboard;
import com.ca.prakash.RetreatServicedApartment.fragment.Events;
import com.ca.prakash.RetreatServicedApartment.fragment.Feedback;

import com.ca.prakash.RetreatServicedApartment.fragment.Login;
import com.ca.prakash.RetreatServicedApartment.fragment.User_orders;
import com.ca.prakash.RetreatServicedApartment.fragment.Video_list;
import com.ca.prakash.RetreatServicedApartment.fragment.Web;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    boolean doubleBackToExitPressedOnce = false;

FrameLayout frameLayout;
    NavigationView navigationView;
    private FloatingActionButton fabcall,fabmessenger,fmail;
    FloatingActionMenu fabmenu;
    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);

        Runtime.getRuntime().gc();
         frameLayout=(FrameLayout)findViewById(R.id.mainFragment);

fabcall=(FloatingActionButton)findViewById(R.id.call_id);
        fabmessenger=(FloatingActionButton)findViewById(R.id.messenger_id);
        fabmenu=(FloatingActionMenu) findViewById(R.id.menu);
        fmail=(FloatingActionButton)findViewById(R.id.email_id);





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        drawer.setScrimColor(getResources().getColor(android.R.color.transparent));
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        fabcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:014274837"));
                startActivity(i);

            }
        });


        fmail.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(MainActivity.this,
                            "There are no email clients installed.",
                            Toast.LENGTH_SHORT).show();

                }

            }
        });

        fabmessenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.me/retreatapartment"));
                startActivity(intent);
            }
        });


final FragmentManager fragmentmanager=getSupportFragmentManager();

        final FragmentTransaction fragmenttranscation=fragmentmanager.beginTransaction();
 Dashboard dashboard=new Dashboard();
        fragmenttranscation.replace(R.id.mainFragment, dashboard);
        fragmenttranscation.commit();

        hideItem();




    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {


            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
            } else if (!doubleBackToExitPressedOnce) {
                this.doubleBackToExitPressedOnce = true;


                FrameLayout frameLayout=(FrameLayout)findViewById(R.id.mainFragment);

                Snackbar snackbar = Snackbar
                        .make(frameLayout, "Please click BACK again to exit.", Snackbar.LENGTH_LONG);


                View snackBarView = snackbar.getView();
                snackBarView.setBackgroundColor(Color.BLUE);
                TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.WHITE);
                snackbar.show();


                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);
            } else {
                super.onBackPressed();

            }

        }
    }


    private void hideItem()
    {
        SharedPreferences preferences=getSharedPreferences("Authentication", Context.MODE_PRIVATE);
        String mCookies = preferences.getString("cookies",null);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        if (mCookies != null)
        {
            nav_Menu.findItem(R.id.nav_login).setVisible(false);

        }
        else
        {
            nav_Menu.findItem(R.id.nav_order).setVisible(false);


        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.rating_dilogue);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            final ImageView ivZoomedImage = (ImageView) dialog.findViewById(R.id.fullscreen_image_dilogue);






            Picasso.with(this).setIndicatorsEnabled(false);


            Picasso.with(this)
                    .load(R.drawable.ratings)
                    .into(ivZoomedImage);

            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.show();

            return true;
        }


       else if (id == R.id.action_phone) {
            Intent i=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:014274837"));
            startActivity(i);

            return true;
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        fragmentTransaction.commit();


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        // Handle navigation view item clicks here.


        FragmentManager fragmentManager = getSupportFragmentManager();

        //middle man
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            toolbar.setTitle("Home");
            setSupportActionBar(toolbar);
            Dashboard fragmentDashboard = new Dashboard();
            fragmentTransaction.replace(R.id.mainFragment, fragmentDashboard);
            fragmentTransaction.addToBackStack(null);




        }
        else if (id==R.id.nav_book)
        {
            toolbar.setTitle("Book now");
            setSupportActionBar(toolbar);
            Booking_form book=new Booking_form();
            Bundle arg=new Bundle();
            arg.putString("unit_name","All");
            book.setArguments(arg);

            fragmentTransaction.replace(R.id.mainFragment,book);
            fragmentTransaction.addToBackStack("book now");

        }
        else if (id==R.id.nav_event)
        {
            toolbar.setTitle("Events");
            setSupportActionBar(toolbar);
           Events events=new Events();


            fragmentTransaction.replace(R.id.mainFragment,events);
            fragmentTransaction.addToBackStack("book now");

        }
//        else if (id == R.id.nav_apartment) {
//            toolbar.setTitle("Apartment");
//            setSupportActionBar(toolbar);
//            Apartments apartment = new Apartments();
//            fragmentTransaction.replace(R.id.mainFragment, apartment);
//            fragmentTransaction.addToBackStack("apartment");
//
//        }else if (id == R.id.nav_aboutus) {
//
//            toolbar.setTitle("About us");
//            setSupportActionBar(toolbar);
//            AboutUs abutus=new AboutUs();
//            fragmentTransaction.replace(R.id.mainFragment, abutus);
//            fragmentTransaction.addToBackStack("aboutus");
//
//        }


        else if (id == R.id.nav_videos) {

            toolbar.setTitle("Videos");
            setSupportActionBar(toolbar);
            Video_list video=new Video_list();
            fragmentTransaction.replace(R.id.mainFragment,video);
            fragmentTransaction.addToBackStack("Videos");

        }
//        else if (id == R.id.nav_gallery) {
//
//            toolbar.setTitle("Gallery");
//            setSupportActionBar(toolbar);
//        Gallery gallery=new Gallery();
//            fragmentTransaction.replace(R.id.mainFragment, gallery);
//            fragmentTransaction.addToBackStack("gallery");
//
//        }
//        else if (id == R.id.nav_locaiton) {
//
//     Intent i=new Intent(this,Location_activity.class);
//            startActivity(i
//            );
//
//        } else if (id == R.id.nav_contactus) {
//            toolbar.setTitle("Contact");
//            setSupportActionBar(toolbar);
//            Contact_us contact=new Contact_us();
//            fragmentTransaction.replace(R.id.mainFragment, contact);
//            fragmentTransaction.addToBackStack("contact");
//
//        }
//        else if (id==R.id.nav_travels)
//        {
//            Intent i=new Intent(this,Travells.class);
//            startActivity(i);
//
//        }

        else if (id == R.id.nav_notification) {
            Intent i=new Intent(MainActivity.this,NewsNotification.class);
            startActivity(i);
        }
        else if (id==R.id.nav_website)
        {
            toolbar.setTitle("Kathmandu gallery");
            setSupportActionBar(toolbar);
            Web web=new Web();
            fragmentTransaction.replace(R.id.mainFragment,web);
            fragmentTransaction.addToBackStack("web");

        }
//        else if (id==R.id.nav_facilities)
//        {
//            toolbar.setTitle("Facilities");
//            Facilities facilities=new Facilities();
//            fragmentTransaction.replace(R.id.mainFragment,facilities);
//            fragmentTransaction.addToBackStack("facilities");
//
//        }
        else if (id==R.id.nav_feedback)
        {
            toolbar.setTitle("Feedback");
            setSupportActionBar(toolbar);
            Feedback feedback=new Feedback();
            fragmentTransaction.replace(R.id.mainFragment,feedback);
            fragmentTransaction.addToBackStack("feedback");

        }
        else if (id==R.id.nav_login)
        {
            toolbar.setTitle("Login");
            setSupportActionBar(toolbar);
            Login login=new Login();
            fragmentTransaction.replace(R.id.mainFragment,login);
            fragmentTransaction.addToBackStack("login");

        }

        else if (id==R.id.nav_qrscanner)
        {
           Intent i=new Intent(MainActivity.this,QRscanner.class);
            startActivity(i);

        }
        else if (id==R.id.nav_order)
        {
            toolbar.setTitle("order");
            setSupportActionBar(toolbar);
            User_orders order=new User_orders();

            fragmentTransaction.replace(R.id.mainFragment,order);
            fragmentTransaction.addToBackStack("order");

        }





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        fragmentTransaction.commit();
        return true;
    }





}
