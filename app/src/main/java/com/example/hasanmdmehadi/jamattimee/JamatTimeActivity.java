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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class JamatTimeActivity extends Fragment implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "JamatTimeActivity";

    View view;
    Spinner spinner,spinner1;

    private int press_count_spinner = 0;
    private int press_count_spinner1 = 0;

    private Button HomeDatePicker, GetMasjidsLocation;
    private TextView textView_home_tvSalatTime;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    final SalatTime fajr1,zohar1,asr1,magrib1,isha1;
    final SalatTime fajr2,zohar2,asr2,magrib2,isha2;
    final SalatTime fajr3,zohar3,asr3,magrib3,isha3;
    final SalatTime fajr4,zohar4,asr4,magrib4,isha4;
    final SalatTime fajr5,zohar5,asr5,magrib5,isha5;
    final SalatTime fajr6,zohar6,asr6,magrib6,isha6;
    final SalatTime fajr7,zohar7,asr7,magrib7,isha7;

//    final ListView mListView;
    final ArrayList<SalatTime> salattimelists = new ArrayList<>();
    private SalatTimesAdapter adapter; // = new SalatTimesAdapter(getActivity(), R.layout.adapter_view_layout, salattimelists);
    private ListView mListView;



    public JamatTimeActivity() {
        fajr1 = new SalatTime("Fajr", "04:45 AM");
        zohar1 = new SalatTime("Zohar", "1:20 PM");
        asr1 = new SalatTime("Asr", "04:35 PM");
        magrib1 = new SalatTime("Magrib", "06:50 PM");
        isha1 = new SalatTime("Isha", "08:15 PM");

        fajr2 = new SalatTime("Fajr", "04:50 AM");
        zohar2 = new SalatTime("Zohar", "1:25 PM");
        asr2 = new SalatTime("Asr", "05:10 PM");
        magrib2 = new SalatTime("Magrib", "06:50 PM");
        isha2 = new SalatTime("Isha", "08:10 PM");

        fajr3 = new SalatTime("Fajr", "04:56 AM");
        zohar3 = new SalatTime("Zohar", "1:25 PM");
        asr3 = new SalatTime("Asr", "05:05 PM");
        magrib3 = new SalatTime("Magrib", "06:53 PM");
        isha3 = new SalatTime("Isha", "08:15 PM");

        fajr4 = new SalatTime("Fajr", "04:55 AM");
        zohar4 = new SalatTime("Zohar", "1:30 PM");
        asr4 = new SalatTime("Asr", "05:15 PM");
        magrib4 = new SalatTime("Magrib", "06:55 PM");
        isha4 = new SalatTime("Isha", "08:15 PM");

        fajr5 = new SalatTime("Fajr", "05:05 AM");
        zohar5 = new SalatTime("Zohar", "1:25 PM");
        asr5 = new SalatTime("Asr", "05:15 PM");
        magrib5 = new SalatTime("Magrib", "06:57 PM");
        isha5 = new SalatTime("Isha", "08:30 PM");

        fajr6 = new SalatTime("Fajr", "05:10 AM");
        zohar6 = new SalatTime("Zohar", "1:30 PM");
        asr6 = new SalatTime("Asr", "05:10 PM");
        magrib6 = new SalatTime("Magrib", "07:00 PM");
        isha6 = new SalatTime("Isha", "08:20 PM");

        fajr7 = new SalatTime("Fajr", "05:05 AM");
        zohar7 = new SalatTime("Zohar", "1:25 PM");
        asr7 = new SalatTime("Asr", "05:05 PM");
        magrib7 = new SalatTime("Magrib", "07:00 PM");
        isha7 = new SalatTime("Isha", "08:25 PM");
//        mListView = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListView = (ListView) view.findViewById(R.id.listView); // It's for showing the salat time by listing horizontally
        GetMasjidsLocation = (Button) view.findViewById(R.id.btnShowLocations);
        spinner = (Spinner) view.findViewById(R.id.spinner);
        spinner1 = (Spinner) view.findViewById(R.id.spinner1);
        textView_home_tvSalatTime = (TextView) view.findViewById(R.id.home_tvSalatTime);

        GetMasjidsLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), LocationActivity.class));
            }
        });

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.planets_array,
                android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat month_date = new SimpleDateFormat("MMM", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String actualDate = year + "-" + month + "-" + day ;//"2016-03-20";
        Date date = null;
        try {
            date = sdf.parse(actualDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String month_name = month_date.format(date);
        String actual_date = month_name + " " + day + ", " + year;

        textView_home_tvSalatTime.setText("Jamat time of " + actual_date + " :");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.activity_jamat_time, container, false);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

        adapter = new SalatTimesAdapter(getActivity(), R.layout.adapter_view_layout, salattimelists);

        Spinner spinner = (Spinner) parent;
        if(spinner.getId() == R.id.spinner)
        {
            String sp = String.valueOf(spinner.getSelectedItem());
            if(press_count_spinner > 0) {
                Toast.makeText(getActivity(), sp, Toast.LENGTH_SHORT).show();
            }
            if(sp.contentEquals("Sylhet")) {
                List<String> list = new ArrayList<String>();
                list.add("SUST Central Masjid");
                list.add("Topobon Jame Masjid");
                list.add("Khulia Para Masjid");
                list.add("Madina Market Masjid");
                list.add("ShahPoran Hall Masjid");
                list.add("Surma Masjid");
                ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_spinner_item, list);
                dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dataAdapter1.notifyDataSetChanged();
                press_count_spinner++;

                spinner1.setAdapter(dataAdapter1);
                spinner1.setOnItemSelectedListener(this);
            }
            if(sp.contentEquals("Dhaka")) {
                List<String> list = new ArrayList<String>();
                list.add("Baitul Mukarram National Masjid, Dhaka.");
                list.add("Saat Gombuj Jame Masjid");
                list.add("Khan Mohammad Masjid");
                list.add("Binot Bibir Masjid");
                list.add("Shaat Gombuj Masjid");
                list.add("Chawkbazar Shahi Jame Mosque");
                list.add("Kartalab Khan's Masjid");
                list.add("DU Masjid");
                list.add("Gulistan Masjid");
                list.add("Bonshal Masjid");
                list.add("Malibag Masjid");
                list.add("Star Mosque");
                list.add("KAKRAIL MASJID");

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_spinner_item, list);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dataAdapter.notifyDataSetChanged();
                press_count_spinner++;

                spinner1.setAdapter(dataAdapter);
                spinner1.setOnItemSelectedListener(this);
            }
            if(sp.contentEquals("Chittagong")) {
                List<String> list = new ArrayList<String>();
                list.add("Love Lane Tablig Masjid Complex");
                list.add("Wali Begh Khan Jame Masjid");
                list.add("Masjid-E-Siraj-Ud-Daulah");
                list.add("Jamyiatul Falah Masjid Eidgah");
                list.add("Jame Mosque");
                list.add("Allahar Dan Masjid");

                ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_spinner_item, list);
                dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dataAdapter1.notifyDataSetChanged();
                press_count_spinner++;

                spinner1.setAdapter(dataAdapter1);
                spinner1.setOnItemSelectedListener(this);
            }
            if(sp.contentEquals("Barisal")) {
                List<String> list = new ArrayList<String>();

                list.add("Nathullahbad Central Bus Terminal Jame Masjid");
                list.add("Syed Hatem Ali College Jame Masjid");
                list.add("Barishal Launchghat Jame Masjid");
                list.add("At Tawhid Jame Masjid");
                list.add("Mohammadia Jame Masjid");

                ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_spinner_item, list);
                dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dataAdapter1.notifyDataSetChanged();
                press_count_spinner++;

                spinner1.setAdapter(dataAdapter1);
                spinner1.setOnItemSelectedListener(this);

            }
            if(sp.contentEquals("Khulna")) {
                List<String> list = new ArrayList<String>();
                list.add("Rajapur Shahi Jame Masjid, Rupsha, Khulna");
                list.add("Fulbari Gate Bazar Masjid");
                list.add("Siddiqia Madrasah Masjid");
                list.add("Haji Bari Jame Masjid");
                list.add("Nirala 2nd Jame Masjid");
                list.add("Crescent Jame Masjid");
                list.add("BNS Titumir Masjid");

                ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_spinner_item, list);
                dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dataAdapter1.notifyDataSetChanged();
                press_count_spinner++;

                spinner1.setAdapter(dataAdapter1);
                spinner1.setOnItemSelectedListener(this);

            }
            if(sp.contentEquals("Rajshahi")) {
                List<String> list = new ArrayList<String>();
                list.add("Horogram Notun Para Jame Masjid");
                list.add("Shaheb Bazar Boro Jame Mosque");
                list.add("Pathanpara Jame Masjid");
                list.add("Padma Garden Oyaktiya Jame Masjid");
                list.add("Ambagan Jame Masjid");

                ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_spinner_item, list);
                dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dataAdapter1.notifyDataSetChanged();
                press_count_spinner++;

                spinner1.setAdapter(dataAdapter1);
                spinner1.setOnItemSelectedListener(this);
            }
            if(sp.contentEquals("Rongpur")) {
                List<String> list = new ArrayList<String>();
                list.add("Rangpur Medical College & Hospital Jame Masjid");
                list.add("Dhap Char Tola Masjid");
                list.add("Thikadar Para Jame Masjid");
                list.add("Mahiganj Degree College Jame Masjid");
                list.add("Babupara Jame Masjid");
                list.add("Park & Morh Waktia Masjid");
                list.add("Satmatha Jame Masjid");

                ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_spinner_item, list);
                dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dataAdapter1.notifyDataSetChanged();
                press_count_spinner++;

                spinner1.setAdapter(dataAdapter1);
                spinner1.setOnItemSelectedListener(this);
            }
            if(sp.contentEquals("Mymansingh")) {
                List<String> list = new ArrayList<String>();
                list.add("Mymensingh Zilla Motor Malik Somoti Jame Masjid");
                list.add("Khagdohor Bazar Jame Masjid");
                list.add("Mahmudia Madrasha Jame Masjid");
                list.add("Police Line Jame Masjid");
                list.add("Tangail Bus Stand Masjid");
                list.add("Masjid-e Nur");

                ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_spinner_item, list);
                dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dataAdapter1.notifyDataSetChanged();
                press_count_spinner++;

                spinner1.setAdapter(dataAdapter1);
                spinner1.setOnItemSelectedListener(this);
            }

            if(press_count_spinner == 6){
                press_count_spinner = 1;
            }

        }
        else if(spinner.getId() == R.id.spinner1)
        {
            String sp1 = String.valueOf(spinner.getSelectedItem());
            if(press_count_spinner1 > 0) {
                salattimelists.clear(); // clear the listview items ..
                Toast.makeText(getActivity(), sp1, Toast.LENGTH_SHORT).show();
            }

            if(press_count_spinner1 == 0){
                salattimelists.add(fajr1);
                salattimelists.add(zohar1);
                salattimelists.add(asr1);
                salattimelists.add(magrib1);
                salattimelists.add(isha1);
            }
            if(press_count_spinner1 == 1){
                salattimelists.add(fajr2);
                salattimelists.add(zohar2);
                salattimelists.add(asr2);
                salattimelists.add(magrib2);
                salattimelists.add(isha2);
            }
            if(press_count_spinner1 == 2){
                salattimelists.add(fajr3);
                salattimelists.add(zohar3);
                salattimelists.add(asr3);
                salattimelists.add(magrib3);
                salattimelists.add(isha3);
            }
            if(press_count_spinner1 == 3){
                salattimelists.add(fajr4);
                salattimelists.add(zohar4);
                salattimelists.add(asr4);
                salattimelists.add(magrib4);
                salattimelists.add(isha4);
            }
            if(press_count_spinner1 == 4){
                salattimelists.add(fajr5);
                salattimelists.add(zohar5);
                salattimelists.add(asr5);
                salattimelists.add(magrib5);
                salattimelists.add(isha5);
            }
            if(press_count_spinner1 == 5){
                salattimelists.add(fajr6);
                salattimelists.add(zohar6);
                salattimelists.add(asr6);
                salattimelists.add(magrib6);
                salattimelists.add(isha6);
            }
            if(press_count_spinner1 == 6){
                salattimelists.add(fajr7);
                salattimelists.add(zohar7);
                salattimelists.add(asr7);
                salattimelists.add(magrib7);
                salattimelists.add(isha7);
            }

            mListView.setAdapter(adapter);

            press_count_spinner1++;

            if(press_count_spinner1 == 6){
                press_count_spinner1 = 1;
            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
