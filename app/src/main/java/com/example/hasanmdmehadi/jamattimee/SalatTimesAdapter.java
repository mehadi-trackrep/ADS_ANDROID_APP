package com.example.hasanmdmehadi.jamattimee;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SalatTimesAdapter extends ArrayAdapter<SalatTime> {
    private final String TAG = "SalatTimesAdapter";
    private Context mContext;
    int mResource;

    public SalatTimesAdapter(@NonNull Context context, int resource, @NonNull ArrayList<SalatTime> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).getSalatname();
        String time = getItem(position).getSalattime();

        SalatTime  salatTime = new SalatTime(name, time);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvName = (TextView) convertView.findViewById(R.id.tvSalatName);
        TextView tvTime = (TextView) convertView.findViewById(R.id.tvSalatTime);

        tvName.setText(name);
        tvTime.setText(time);

        return convertView;
    }
}
