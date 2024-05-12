package hu.inf.szte.szegedimenetrend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import hu.inf.szte.szegedimenetrend.R;
import hu.inf.szte.szegedimenetrend.adapter.JaratokAdapter;
import hu.inf.szte.szegedimenetrend.model.Routes;

public class JaratokActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getName();

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private GridView gridView;
    private MaterialToolbar toolbar;
    private ImageButton loginButton;
    private ImageButton myAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_jaratok);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_jaratok), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mAuth = FirebaseAuth.getInstance();
        gridView = findViewById(R.id.gridView);
        db = FirebaseFirestore.getInstance();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loginButton = findViewById(R.id.loginButton);
        myAccountButton = findViewById(R.id.myAccountButton);
        refreshToolbar();

        db.collection("routes")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        ArrayList<Routes> routes = (ArrayList<Routes>) task.getResult().toObjects(Routes.class);
                        routes.sort(Routes::compareTo);
                        JaratokAdapter adapter = new JaratokAdapter(this, routes);
                        gridView.setAdapter(adapter);
                    } else {
                        Log.e(TAG, "Hiba az adatbázis olvasásakor: ", task.getException());
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        refreshToolbar();
        gridView.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(this, R.anim.jaratok_anim));
    }

    private void refreshToolbar() {
        currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            loginButton.setVisibility(View.VISIBLE);
            myAccountButton.setVisibility(View.GONE);
        } else {
            loginButton.setVisibility(View.GONE);
            myAccountButton.setVisibility(View.VISIBLE);
        }
    }


    public void login(View view) {
        Intent intent = new Intent(this, BejelentkezesActivity.class);
        startActivity(intent);
    }

    public void myAccount(View view) {
        Intent intent = new Intent(this, FiokomActivity.class);
        startActivity(intent);
    }
}