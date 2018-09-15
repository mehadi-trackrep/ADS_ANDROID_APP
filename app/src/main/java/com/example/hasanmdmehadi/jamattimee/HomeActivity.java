package com.example.hasanmdmehadi.jamattimee;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HomeActivity extends Fragment {

    private static final String TAG = "HomeActivity";

    View view;
    private int press_count = 0;
    private Button HomeDatePicker;
    private TextView textView_home_tvSalatTime;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // show salat times ..
        final ListView mListView = (ListView) view.findViewById(R.id.listView); // It's for showing the salat time by listing horizontally

        final SalatTime fajr1 = new SalatTime("Fajr", "03:56 AM");
        final SalatTime zohar1 = new SalatTime("Zohar", "11:59 AM");
        final SalatTime asr1 = new SalatTime("Asr", "03:25 PM");
        final SalatTime magrib1 = new SalatTime("Magrib", "06:37 PM");
        final SalatTime isha1 = new SalatTime("Isha", "08:01 PM");

        final SalatTime fajr2 = new SalatTime("Fajr", "03:49 AM");
        final SalatTime zohar2 = new SalatTime("Zohar", "12:01 PM");
        final SalatTime asr2 = new SalatTime("Asr", "03:33 PM");
        final SalatTime magrib2 = new SalatTime("Magrib", "06:40 PM");
        final SalatTime isha2 = new SalatTime("Isha", "08:10 PM");

        // Salat time show section Starts here ..
        final ArrayList<SalatTime> salattimelists = new ArrayList<>();

        salattimelists.add(fajr1);
        salattimelists.add(zohar1);
        salattimelists.add(asr1);
        salattimelists.add(magrib1);
        salattimelists.add(isha1);

        final SalatTimesAdapter adapter = new SalatTimesAdapter(getActivity(), R.layout.adapter_view_layout, salattimelists);
        mListView.setAdapter(adapter);
        // Salat time show section Ends here ..

        press_count++;

        HomeDatePicker = (Button) view.findViewById(R.id.btnPickDate);
        textView_home_tvSalatTime = (TextView) view.findViewById(R.id.home_tvSalatTime);

        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

        Date d = new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("hh:mm a");
        String dateTime = sdf.format(d);
        dateTime = dateTime + " PM";

        currentDateTimeString = currentDateTimeString.substring(0,dateTime.length());
        textView_home_tvSalatTime.setText("Salat time of " + currentDateTimeString + " :");

        HomeDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(press_count%2 == 0) {
                    if(press_count >= 2) salattimelists.clear();
                    salattimelists.add(fajr1);
                    salattimelists.add(zohar1);
                    salattimelists.add(asr1);
                    salattimelists.add(magrib1);
                    salattimelists.add(isha1);
                }else {
                    if(press_count >= 1) salattimelists.clear();
                    salattimelists.clear();
                    salattimelists.add(fajr2);
                    salattimelists.add(zohar2);
                    salattimelists.add(asr2);
                    salattimelists.add(magrib2);
                    salattimelists.add(isha2);
                }
                press_count++;
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                mListView.setAdapter(adapter);
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                Log.d(TAG, "onDateSet: dd/mm/yy: "+ dayOfMonth + "/" + month + "/" + year);
                String date_str = dayOfMonth + "/" + month + "/" + year;

                SimpleDateFormat month_date = new SimpleDateFormat("MMM", Locale.ENGLISH);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                String actualDate = year + "-" + month + "-" + dayOfMonth ;//"2016-03-20";
                Date date = null;
                try {
                    date = sdf.parse(actualDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String month_name = month_date.format(date);
                String actual_date = month_name + " " + dayOfMonth + ", " + year;

                textView_home_tvSalatTime.setText("Salat time of " + actual_date + " :");
                //textView_home_tvSalatTime.setText("Salat time of " + date_str + " :");
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.activity_home, container, false);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void login_activity(){
        getActivity().finish();
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        inflater.inflate(R.menu.menu_home, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menuLogin : login_activity();
        }
        return super.onOptionsItemSelected(item);
    }

}
