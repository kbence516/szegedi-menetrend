package hu.inf.szte.szegedimenetrend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import hu.inf.szte.szegedimenetrend.R;

public class BejelentkezesActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getName();

    private FirebaseAuth mAuth;
    TextInputLayout emailTIL;
    TextInputLayout passwordTIL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bejelentkezes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_bejelentkezes), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        emailTIL = findViewById(R.id.emailTIL);
        passwordTIL = findViewById(R.id.passwordTIL);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            finish();
        }
    }

    public void register(View view) {
        Intent intent = new Intent(this, RegisztracioActivity.class);
        startActivity(intent);
    }

    public void loginWithEmailAndPassword(View view) {
        EditText emailET = emailTIL.getEditText();
        EditText passwordET = passwordTIL.getEditText();

        if (emailET == null || passwordET == null) {
            Log.e(TAG, "Nem érkezett be minden adat a felhasználótól!");
            Toast.makeText(this, "Minden adatot ki kell tölteni!", Toast.LENGTH_SHORT).show();
            return;
        }

        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Log.e(TAG, "Nem érkezett be minden adat a felhasználótól!");
            Toast.makeText(this, "Minden adatot ki kell tölteni!", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.d(TAG, "Sikeres bejelentkezés!");
                    Toast.makeText(this, "Sikeres bejelentkezés!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Log.e(TAG, "Sikertelen bejelentkezés!");
                    Toast.makeText(this, "Sikertelen bejelentkezés!", Toast.LENGTH_SHORT).show();
                }
            });
    }

    public void cancel(View view) {
        finish();
    }
}
