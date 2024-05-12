package hu.inf.szte.szegedimenetrend.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import hu.inf.szte.szegedimenetrend.R;
import hu.inf.szte.szegedimenetrend.adapter.JaratCommentAdapter;
import hu.inf.szte.szegedimenetrend.adapter.JaratHoursAdapter;
import hu.inf.szte.szegedimenetrend.misc.ArrayAdapterCallback;
import hu.inf.szte.szegedimenetrend.misc.NotificationHandler;
import hu.inf.szte.szegedimenetrend.misc.StringListCallback;
import hu.inf.szte.szegedimenetrend.model.Comment;
import hu.inf.szte.szegedimenetrend.model.StopTimes;
import hu.inf.szte.szegedimenetrend.model.Stops;
import hu.inf.szte.szegedimenetrend.model.Trips;

public class JaratActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private final String TAG = this.getClass().getName();
    private FirebaseFirestore db;
    private ListView listView;
    private Spinner headsignSpinner;
    private Spinner stopSpinner;
    private ArrayList<Trips> trips;
    private Dictionary<String, Long> stopNamesAndIds;
    private ArrayList<String> tripIds;
    private RecyclerView commentsRecyclerView;
    private TextView commentTextView;
    private MaterialButton commentButton;
    private ConstraintLayout innerConstraintLayout;
    private TextView headsignTextView;
    private TextView stopTextView;
    private TextView appBarTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_jarat);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_jarat), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = FirebaseFirestore.getInstance();
        listView = findViewById(R.id.listView);

        headsignSpinner = findViewById(R.id.headsignSpinner);
        headsignSpinner.setOnItemSelectedListener(this);

        stopSpinner = findViewById(R.id.stopSpinner);
        stopSpinner.setOnItemSelectedListener(this);

        commentsRecyclerView = findViewById(R.id.commentsRecyclerView);
        commentButton = findViewById(R.id.commentButton);

        headsignTextView = findViewById(R.id.headsignTextView);
        stopTextView = findViewById(R.id.stopTextView);
        appBarTitle = findViewById(R.id.appBarTitle);

        Long route_id = getIntent().getLongExtra("route_id", 0);

        String route_short_name = getIntent().getStringExtra("route_short_name");
        Log.d(TAG, "route_id: " + route_id + ", route_short_name: " + route_short_name);
        appBarTitle.setText(route_short_name);

        getTodaysServices(new StringListCallback() {
            @Override
            public void onCallback(List<String> serviceIds) {
                tripIds = new ArrayList<>();
                db.collection("trips")
                        .whereEqualTo("route_id", route_id)
                        .whereIn("service_id", serviceIds)
                        .orderBy("direction_id")
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                trips = (ArrayList<Trips>) task.getResult().toObjects(Trips.class);
                                if (trips.isEmpty()) {
                                    Toast.makeText(JaratActivity.this, "Ez a járat a mai napon nem közlekedik", Toast.LENGTH_SHORT).show();
                                    headsignSpinner.setVisibility(View.GONE);
                                    stopSpinner.setVisibility(View.GONE);
                                    listView.setVisibility(View.GONE);
                                    headsignTextView.setVisibility(View.GONE);
                                    stopTextView.setVisibility(View.GONE);
                                    innerConstraintLayout = findViewById(R.id.innerConstraintLayout);
                                    ConstraintSet constraintSet = new ConstraintSet();
                                    constraintSet.clone(innerConstraintLayout);
                                    constraintSet.connect(R.id.commentTextView, ConstraintSet.TOP, R.id.innerConstraintLayout, ConstraintSet.TOP, 50);
                                    constraintSet.connect(R.id.commentButton, ConstraintSet.TOP, R.id.innerConstraintLayout, ConstraintSet.TOP, 50);
                                    constraintSet.applyTo(innerConstraintLayout);
                                    return;
                                }
                                for (Trips trip : trips) {
                                    tripIds.add(trip.getTrip_id());
                                }

                                for (int i = 0; i < trips.size(); i++) {
                                    Log.d(TAG, "Trip[" + i + "]@trip: " + trips.get(i).getTrip_id()
                                            + ", service_id: " + trips.get(i).getService_id());
                                }

                                ArrayAdapter<String> headsignSpinnerAdapter = getHeadsignSpinnerAdapter(trips);
                                headsignSpinner.setAdapter(headsignSpinnerAdapter);
                            } else {
                                Log.e(TAG, "Hiba az adatbázis olvasásakor: ", task.getException());
                            }
                        });
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        db.collection("comments")
                .whereEqualTo("route_short_name", getIntent().getStringExtra("route_short_name"))
                .orderBy("last_modified", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        ArrayList<Comment> comments = new ArrayList<>();
                        List<DocumentSnapshot> documents = task.getResult().getDocuments();
                        if (!documents.isEmpty()) {
                            for (DocumentSnapshot document : documents) {
                                document.getId();
                                Comment comment = document.toObject(Comment.class);
                                comment._setFirebase_id(document.getId());
                                comments.add(comment);
                            }
                        }
                        JaratCommentAdapter adapter = new JaratCommentAdapter(comments);
                        commentsRecyclerView.setAdapter(adapter);
                        commentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                    } else {
                        Log.e(TAG, "Hiba az adatbázis olvasásakor: ", task.getException());
                    }
                });
    }

    @NonNull
    private ArrayAdapter<String> getHeadsignSpinnerAdapter(ArrayList<Trips> trips) {
        ArrayList<String> headsigns = new ArrayList<>();
        for (Trips trip : trips) {
            String headsign = trip.getTrip_headsign();
            if (!headsign.equals("Kocsiszínbe") && !headsigns.contains(headsign)) {
                headsigns.add(headsign);
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, headsigns);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    public void cancel(View view) {
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "Selected item: " + parent.getItemAtPosition(position));
        Log.d(TAG, "Selected spinner: " + parent.getId());
        if (parent.getId() == R.id.headsignSpinner) {

            Trips oneTrip = new Trips();
            if (trips != null) {
                for (Trips trip : trips) {
                    if (trip.getDirection_id() == id) {
                        oneTrip = trip;
                        break;
                    }
                }
                db.collection("stop_times")
                        .whereEqualTo("trip_id", oneTrip.getTrip_id())
                        .orderBy("stop_sequence")
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                ArrayList<StopTimes> stops = (ArrayList<StopTimes>) task.getResult().toObjects(StopTimes.class);
                                getStopSpinnerAdapter(stops, new ArrayAdapterCallback() {
                                    @Override
                                    public void onCallback(ArrayAdapter<String> adapter) {
                                        stopSpinner.setAdapter(adapter);
                                    }
                                });

                            } else {
                                Log.e(TAG, "Hiba az adatbázis olvasásakor: ", task.getException());
                            }
                        });
            }
        } else if (parent.getId() == R.id.stopSpinner) {
            db.collection("stop_times")
                    .whereEqualTo("stop_id", stopNamesAndIds.get(parent.getItemAtPosition(position)))
                    .whereEqualTo("pickup_type", 0)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            ArrayList<StopTimes> stopTimes = (ArrayList<StopTimes>) task.getResult().toObjects(StopTimes.class);
                            for (StopTimes stopTime : stopTimes) {
                                if (tripIds.contains(stopTime.getTrip_id())) {
                                    stopTime.setArrival_time(new Date(stopTime.getArrival_time().getTime() - 60 * 60 * 1000));
                                }
                            }
                            stopTimes.sort(StopTimes::compareTo);
                            ArrayList<StopTimes> stopTimesFiltered = new ArrayList<>();
                            for (StopTimes stopTime : stopTimes) {
                                if (tripIds.contains(stopTime.getTrip_id())) {
                                    stopTimesFiltered.add(stopTime);
                                }
                            }

                            int lastHour = -1;
                            ArrayList<ArrayList<StopTimes>> stopTimesByTheHour = new ArrayList<>();
                            for (StopTimes stopTime : stopTimesFiltered) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(stopTime.getArrival_time());
                                if (lastHour < calendar.get(Calendar.HOUR_OF_DAY)) {
                                    lastHour = calendar.get(Calendar.HOUR_OF_DAY);
                                    stopTimesByTheHour.add(new ArrayList<>());
                                }
                                stopTimesByTheHour.get(stopTimesByTheHour.size() - 1).add(stopTime);
                            }

                            JaratHoursAdapter adapter = new JaratHoursAdapter(this, stopTimesByTheHour);
                            listView.setAdapter(adapter);
                        } else {
                            Log.e(TAG, "Hiba az adatbázis olvasásakor: ", task.getException());
                        }
                    });

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.d(TAG, "onNothingSelected: ");
    }

    private void getStopSpinnerAdapter(ArrayList<StopTimes> stops, ArrayAdapterCallback callback) {
        ArrayList<Long> stopIds = new ArrayList<>();
        ArrayList<String> stopSequenceOrdered = new ArrayList<>();
        for (StopTimes stop : stops) {
            stopIds.add(stop.getStop_id());
        }
        db.collection("stops")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        stopNamesAndIds = new Hashtable<>();
                        ArrayList<Stops> stopSequence = (ArrayList<Stops>) task.getResult().toObjects(Stops.class);
                        for (StopTimes stop : stops) {
                            for (Stops stopSequenceItem : stopSequence) {
                                if (stop.getStop_id().equals(stopSequenceItem.getStop_id())) {
                                    stopSequenceOrdered.add(stopSequenceItem.getStop_name());
                                    stopNamesAndIds.put(stopSequenceItem.getStop_name(), stopSequenceItem.getStop_id());
                                }
                            }
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, stopSequenceOrdered);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        callback.onCallback(adapter);
                    } else {
                        Log.e(TAG, "Hiba az adatbázis olvasásakor: ", task.getException());
                    }
                });
    }

    public void getTodaysServices(StringListCallback callback) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_WEEK, -1);        // valamiért vasárnap azt mondja a Java, hogy hétfő van :D
        DayOfWeek dayOfWeek = DayOfWeek.of(cal.get(Calendar.DAY_OF_WEEK));
        Log.d(TAG, "Today is: " + dayOfWeek.toString().toLowerCase());
        ArrayList<String> serviceIds = new ArrayList<>();

        db.collection("calendar")
                .whereEqualTo(dayOfWeek.toString().toLowerCase(), 1)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<hu.inf.szte.szegedimenetrend.model.Calendar> calendars = task.getResult().toObjects(hu.inf.szte.szegedimenetrend.model.Calendar.class);
                        for (hu.inf.szte.szegedimenetrend.model.Calendar calendar : calendars) {
                            serviceIds.add(calendar.getService_id());
                        }
                        callback.onCallback(serviceIds);
                    } else {
                        Log.e(TAG, "Hiba az adatbázis olvasásakor: ", task.getException());
                    }
                });
    }

    public void newComment(View view) {
        Intent intent = new Intent(this, HozzaszolasActivity.class);
        intent.putExtra("route_short_name", getIntent().getStringExtra("route_short_name"));
        startActivity(intent);
    }
}