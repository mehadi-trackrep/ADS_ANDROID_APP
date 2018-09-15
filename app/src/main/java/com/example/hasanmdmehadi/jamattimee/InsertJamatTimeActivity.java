package com.example.hasanmdmehadi.jamattimee;

import android.app.TimePickerDialog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class InsertJamatTimeActivity extends Fragment {
    View view;

    ImageButton fajr,zohar,asr,magrib,isha;
    TimePickerDialog timePickerDialog;
    Button submit_button;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        submit_button = (Button)  view.findViewById(R.id.btnInsertJamatTime);

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Successfully submitted!! ",Toast.LENGTH_LONG).show();
            }
        });

        fajr = (ImageButton) view.findViewById(R.id.btn_fajr);
        fajr.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                openTimePickerDialog(false);

            }});

        zohar = (ImageButton) view.findViewById(R.id.btn_zohar);
        zohar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                openTimePickerDialog(false);

            }});


        asr = (ImageButton) view.findViewById(R.id.btn_asr);
        asr.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                openTimePickerDialog(false);

            }});


        magrib = (ImageButton) view.findViewById(R.id.btn_magrib);
        magrib.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                openTimePickerDialog(false);

            }});


        isha = (ImageButton) view.findViewById(R.id.btn_isha);
        isha.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                openTimePickerDialog(false);

            }});
    }
    private void openTimePickerDialog(boolean is24r){
        Calendar calendar = Calendar.getInstance();

        timePickerDialog = new TimePickerDialog(
                getActivity(),
                onTimeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                is24r);
        timePickerDialog.setTitle("Set Time");

        timePickerDialog.show();

    }

    TimePickerDialog.OnTimeSetListener onTimeSetListener
            = new TimePickerDialog.OnTimeSetListener(){

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            Calendar calNow = Calendar.getInstance();
            Calendar calSet = (Calendar) calNow.clone();

            calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calSet.set(Calendar.MINUTE, minute);
            calSet.set(Calendar.SECOND, 0);
            calSet.set(Calendar.MILLISECOND, 0);

            if(calSet.compareTo(calNow) <= 0){
                //Today Set time passed, count to tomorrow
                calSet.add(Calendar.DATE, 1);
            }

            setTime(calSet);
        }};

    private void setTime(Calendar targetCal) {

        Toast.makeText(getActivity(), "Selected Time is: " + targetCal.getTime(),Toast.LENGTH_LONG).show();

//        textAlarmPrompt.setText(
//                "\n\n***\n"
//                        + "Alarm is set@ " + targetCal.getTime() + "\n"
//                        + "***\n");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.activity_insert_jamat_time, container, false);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
}
