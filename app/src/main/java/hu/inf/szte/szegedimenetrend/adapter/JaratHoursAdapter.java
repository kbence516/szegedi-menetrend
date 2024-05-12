package hu.inf.szte.szegedimenetrend.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.util.ArrayList;
import java.util.Calendar;

import hu.inf.szte.szegedimenetrend.R;
import hu.inf.szte.szegedimenetrend.model.StopTimes;

public class JaratHoursAdapter extends ArrayAdapter<ArrayList<StopTimes>> {
    private static int lastHour = -1;

    public JaratHoursAdapter(@NonNull Context context, ArrayList<ArrayList<StopTimes>> stopTimes) {
        super(context, 0, stopTimes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.jarat_hour, parent, false);
        }

        TextView hour = listitemView.findViewById(R.id.hour);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getItem(position).get(0).getArrival_time());
        hour.setText(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)));

        GridView minutesList = listitemView.findViewById(R.id.minutesList);
        JaratMinutesAdapter adapter = new JaratMinutesAdapter(getContext(), getItem(position));
        minutesList.setAdapter(adapter);

        return listitemView;
    }
}
