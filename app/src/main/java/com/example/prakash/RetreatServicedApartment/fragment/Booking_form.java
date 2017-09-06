package com.example.prakash.RetreatServicedApartment.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prakash.RetreatServicedApartment.R;
import com.example.prakash.RetreatServicedApartment.app.MyApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by prakash on 4/17/2017.
 */

public class Booking_form extends Fragment {
    View mainView;
    EditText mArrive,mDeparture;
     Button aptbtn;
    ImageButton arivalbtn,departurebtn;
    DatePickerDialog datePickerDialog;
    String arival,departure,currentdate;
    Spinner mUnit;
    ArrayAdapter Unitarray;
    TextView Unitname;
    String mUnitname;
    String mType;
    String a,d;
    Typeface type;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView=inflater.inflate(R.layout.booking_layout,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Book now");

        mArrive=(EditText)mainView.findViewById(R.id.checkin_id);
        mDeparture=(EditText)mainView.findViewById(R.id.checkout_id);
        arivalbtn=(ImageButton) mainView.findViewById(R.id.arivalbtn);
        departurebtn=(ImageButton)mainView.findViewById(R.id.departurebtn);

        Unitname=(TextView)mainView.findViewById(R.id.unit_name);
        Unitarray = ArrayAdapter.createFromResource(getActivity(),R.array.Units, android.R.layout.simple_spinner_item);
        Unitarray.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        mUnit=(Spinner)mainView.findViewById(R.id.book_spinner);
        mUnit.setAdapter(Unitarray);








        mType=getArguments().getString("book");
        mUnitname=getArguments().getString("unit_name");

        if (mUnitname.equals("All")) {

            Unitname.setVisibility(View.GONE);

        }
        else
        {


            Unitname.setText(mUnitname + " was selected");
        }

//        mUnit.setOnClickListener((View.OnClickListener) getContext());

        final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        currentdate = sdf.format(new Date());

      aptbtn=(Button)mainView.findViewById(R.id.apartmetn_search_btn);

        aptbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                arival=mArrive.getText().toString();
                departure=mDeparture.getText().toString();
                if (arival.length()<1)
                {

                    Toast.makeText(getContext(), "Please choose arrival date", Toast.LENGTH_SHORT).show();
                }
                else if (departure.length()<1)
                {
                    Toast.makeText(getContext(), "Please choose departure date", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (isOnline())
                    {
                        isDateAfter(arival, departure, currentdate);


                    }
                    else
                    {
                        Toast.makeText(getContext(), "No internet connection", Toast.LENGTH_SHORT).show();
                    }


                }

            }
        });






        arivalbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final Calendar c = Calendar.getInstance();


                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {


                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                int month=monthOfYear+1;

                                mArrive.setText((dayOfMonth<10?("0"+dayOfMonth):(dayOfMonth)) + "-"+(month<10?("0"+month):(month))+ "-" + year);


                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();

            }
        });
        departurebtn.setEnabled(false);

        mArrive.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {


                a=mArrive.getText().toString();
                d=mDeparture.getText().toString();
                String myFormatString = "dd-MM-yyyy"; // for example
                SimpleDateFormat formatter = new SimpleDateFormat(myFormatString);

                if (mDeparture.length() < 1)
                {



                    long timeInMilliseconds = 0;

                    try{
                        Date startingDate = formatter.parse(mArrive.getText().toString());
                        timeInMilliseconds = startingDate.getTime();

                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    long selecteddate=timeInMilliseconds + 86400000;
                    departurebtn.setEnabled(true);

                    mDeparture.setText(formatter.format(selecteddate));


                }


//                else {
//
//                    try {
//                        long timeInMilliseconds = 0;
//
//                        Date currentDepDate = formatter.parse(mDeparture.getText().toString());
//
//                        Date startingDate = formatter.parse(mArrive.getText().toString());
//                        timeInMilliseconds = startingDate.getTime();
//
//                        if(currentDepDate.before(startingDate)){
//                            long selecteddate=timeInMilliseconds + 86400000;
//                            departurebtn.setEnabled(true);
//
//                            mDeparture.setText(formatter.format(selecteddate));
//                        }
//                    }
//                    catch (Exception e){
//                        e.printStackTrace();
//                    }
//
//                    // if departure date < arrival date then change date
//                }




                try {
                    Date dateA=sdf.parse(a);
                    Date dateD=sdf.parse(d);

                    if (dateA.after(dateD) || dateA.equals(dateD))
                    {



                        long timeInMilliseconds = 0;

                        try{
                            Date startingDate = formatter.parse( mArrive.getText().toString());
                            timeInMilliseconds = startingDate.getTime();




                        }
                        catch (Exception $e){
                            $e.printStackTrace();
                        }
                        long selecteddate=timeInMilliseconds + 86400000;
                        mDeparture.setEnabled(true);

                        mDeparture.setText(""+formatter.format(selecteddate));

                    }


                } catch (ParseException e) {
                    e.printStackTrace();
                }









            }
        });

        departurebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                final int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text

                                int departuremonth=monthOfYear+1;
                                mDeparture.setText((dayOfMonth<10?("0"+dayOfMonth):(dayOfMonth)) + "-"+((departuremonth<10?("0"+departuremonth):(departuremonth)))+ "-" + year);

                            }
                        }, mYear, mMonth, mDay);



                String myFormatString = "dd-MM-yyyy"; // for example
                SimpleDateFormat df = new SimpleDateFormat(myFormatString);

                String arrival=mArrive.getText().toString();
                long timeInMilliseconds = 0;


                try{
                    Date startingDate = df.parse(mArrive.getText().toString());
                    timeInMilliseconds = startingDate.getTime();

                }
                catch (Exception $e){
                    $e.printStackTrace();
                }

                datePickerDialog.getDatePicker().setMinDate(timeInMilliseconds + 86400000);
                datePickerDialog.show();





            }
        });


        return mainView;

    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }


    void isDateAfter(String arrive,String departure,String current)
    {
        try
        {

            String myFormatString = "dd-MM-yyyy"; // for example
            SimpleDateFormat df = new SimpleDateFormat(myFormatString);
            Date date1 = df.parse(departure);
            Date startingDate = df.parse(arrive);
            Date currentdate=df.parse(current);
//
//            if (startingDate.equals(currentdate))
//                Toast.makeText(getContext(), "Booking can only start from 1 day in advance", Toast.LENGTH_SHORT).show();
            if (
                    date1.after(startingDate))
            {




                int unitno= Integer.parseInt(mUnit.getSelectedItem().toString());

                FragmentTransaction transaction=getFragmentManager().beginTransaction();
                AvailableRooms rooms=new AvailableRooms();
                Bundle args = new Bundle();
                args.putString("mtype",mType);
                args.putString("unit_name",mUnitname);
                args.putString("arrival",mArrive.getText().toString());
                args.putString("departure",mDeparture.getText().toString());
                args.putInt("unit",unitno);

                rooms.setArguments(args);
                transaction.replace(R.id.mainFragment,rooms);
                transaction.addToBackStack("Rooms Availaible");
                transaction.commit();

                String mArrivalDate=mArrive.getText().toString();
                String mDepartureDate=mDeparture.getText().toString();
                String mUnitRoom=mUnit.getSelectedItem().toString();




                SharedPreferences sharedPreferences=getActivity().getSharedPreferences("booking", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("ArrivalDate",mArrivalDate);
                editor.putString("DepartureDate",mDepartureDate);
                editor.putString("UnitRoom",mUnitRoom);
                editor.putString("UnitName",mUnitname);
                editor.commit();

            }
            else
                Toast.makeText(getContext(), "Departure date should be greater than the arrival date", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {


        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // Tracking the screen view
        MyApplication.getInstance().trackScreenView("Booking form");
    }
}
