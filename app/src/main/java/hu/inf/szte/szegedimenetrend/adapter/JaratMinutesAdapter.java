package hu.inf.szte.szegedimenetrend.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Calendar;

import hu.inf.szte.szegedimenetrend.R;
import hu.inf.szte.szegedimenetrend.model.StopTimes;

public class JaratMinutesAdapter extends ArrayAdapter<StopTimes> {

    public JaratMinutesAdapter(@NonNull Context context, ArrayList<StopTimes> stopTimes) {
        super(context, 0, stopTimes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.jarat_minutes, parent, false);
        }

        TextView minutes = listitemView.findViewById(R.id.minutes);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getItem(position).getArrival_time());
        minutes.setText(String.format("%02d", calendar.get(Calendar.MINUTE)));


        return listitemView;
    }
}
