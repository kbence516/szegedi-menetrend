package hu.inf.szte.szegedimenetrend.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

import hu.inf.szte.szegedimenetrend.R;

public class FiokomActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getName();
    private FirebaseAuth mAuth;
    private TextView username;
    private TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fiokom);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_fiokom), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        username = findViewById(R.id.usernameValueTextView);
        email = findViewById(R.id.emailValueTextView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null) {
            finish();
        } else {
            username.setText(mAuth.getCurrentUser().getDisplayName());
            email.setText(mAuth.getCurrentUser().getEmail());
        }
    }

    public void logout(View view) {
        mAuth.signOut();
        finish();
    }

    public void cancel(View view) {
        finish();
    }
}