package hu.inf.szte.szegedimenetrend.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import hu.inf.szte.szegedimenetrend.R;
import hu.inf.szte.szegedimenetrend.activity.JaratActivity;
import hu.inf.szte.szegedimenetrend.model.Routes;

public class JaratokAdapter extends ArrayAdapter<Routes> {
    public JaratokAdapter(@NonNull Context context, ArrayList<Routes> routes) {
        super(context, 0, routes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.jaratok_button, parent, false);
        }

        Routes route = getItem(position);
        MaterialButton button = listitemView.findViewById(R.id.button);
        String route_short_name = route.getRoute_short_name();
        button.setText(route_short_name);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), JaratActivity.class);
            intent.putExtra("route_short_name", route_short_name);
            intent.putExtra("route_id", route.getRoute_id());
            getContext().startActivity(intent);
        });



        return listitemView;
    }
}
