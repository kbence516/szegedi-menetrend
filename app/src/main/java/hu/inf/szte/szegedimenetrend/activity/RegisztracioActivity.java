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
import com.google.firebase.auth.UserProfileChangeRequest;

import hu.inf.szte.szegedimenetrend.R;

public class RegisztracioActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getName();

    private FirebaseAuth mAuth;

    TextInputLayout usernameTIL;
    TextInputLayout emailTIL;
    TextInputLayout passwordTIL;
    TextInputLayout password2TIL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_regisztracio);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_regisztracio), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        usernameTIL = findViewById(R.id.usernameTIL);
        emailTIL = findViewById(R.id.emailTIL);
        passwordTIL = findViewById(R.id.passwordTIL);
        password2TIL = findViewById(R.id.password2TIL);
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

        EditText usernameET = usernameTIL.getEditText();
        EditText emailET = emailTIL.getEditText();
        EditText passwordET = passwordTIL.getEditText();
        EditText password2ET = password2TIL.getEditText();

        if (usernameET == null || emailET == null || passwordET == null || password2ET == null) {
            Log.e(TAG, "Nem érkezett be minden adat a felhasználótól!");
            Toast.makeText(this, "Minden adatot ki kell tölteni!", Toast.LENGTH_SHORT).show();
            return;
        }

        String username = usernameET.getText().toString();
        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();
        String password2 = password2ET.getText().toString();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || password2.isEmpty()) {
            Log.e(TAG, "Nem érkezett be minden adat a felhasználótól!");
            Toast.makeText(this, "Minden adatot ki kell tölteni!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Log.e(TAG, "A jelszó túl rövid!");
            Toast.makeText(this, "A jelszó túl rövid!", Toast.LENGTH_SHORT).show();
            return;
        }


        if (!password.equals(password2)) {
            Log.e(TAG, "A két jelszó nem egyezik meg!");
            Toast.makeText(this, "A két jelszó nem egyezik meg!", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        mAuth.getCurrentUser().updateProfile(
                                new UserProfileChangeRequest.Builder()
                                        .setDisplayName(username)
                                        .build());
                        Log.d(TAG, "register: Sikeres regisztráció!");
                        Toast.makeText(RegisztracioActivity.this, "Sikeres regisztráció!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Log.e("RegisztracioActivity", "register: Sikertelen regisztráció!", task.getException());
                        Toast.makeText(RegisztracioActivity.this, "Sikertelen regisztráció!", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void cancel(View view) {
        finish();
    }
}
