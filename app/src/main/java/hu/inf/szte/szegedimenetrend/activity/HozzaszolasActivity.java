package hu.inf.szte.szegedimenetrend.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

import hu.inf.szte.szegedimenetrend.R;
import hu.inf.szte.szegedimenetrend.misc.NotificationHandler;
import hu.inf.szte.szegedimenetrend.model.Comment;

public class HozzaszolasActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getName();
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private TextView appBarTitle;
    private EditText commentText;
    private MaterialButton newCommentButton;
    private MaterialButton editCommentButton;
    private NotificationHandler notificationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hozzaszolas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_hozzaszolas), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = FirebaseFirestore.getInstance();
        appBarTitle = findViewById(R.id.appBarTitle);
        commentText = findViewById(R.id.hozzaszolasEditText);
        newCommentButton = findViewById(R.id.newCommentButton);
        editCommentButton = findViewById(R.id.editCommentButton);
        notificationHandler = new NotificationHandler(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null) {
            finish();
        }
        if (getIntent().getStringExtra("firebase_id") != null) {
            appBarTitle.setText("Hozzászólás módosítása");
            EditText commentText = findViewById(R.id.hozzaszolasEditText);
            commentText.setText(getIntent().getStringExtra("comment"));
            newCommentButton.setVisibility(View.GONE);
        } else {
            appBarTitle.setText("Új hozzászólás");
            editCommentButton.setVisibility(View.GONE);
        }
    }

    public void addComment(View view) {
        Comment comment = new Comment();
        EditText commentText = findViewById(R.id.hozzaszolasEditText);
        comment.setRoute_short_name(getIntent().getStringExtra("route_short_name"));
        comment.setAuthor_email(mAuth.getCurrentUser().getEmail());
        comment.setAuthor_name(mAuth.getCurrentUser().getDisplayName());
        comment.setComment(commentText.getText().toString());
        comment.setLast_modified(new Date());

        db.collection("comments").add(comment)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        notificationHandler.send("Új hozzászólás hozzáadva a járathoz: " + getIntent().getStringExtra("route_short_name"),
                                '"' + comment.getComment() + '"');
                        finish();
                    } else {
                        Toast.makeText(this, "Hiba történt", Toast.LENGTH_SHORT).show();
                    }
                });

        finish();
    }

    public void updateComment(View view) {
        Comment comment = new Comment();
        EditText commentText = findViewById(R.id.hozzaszolasEditText);
        comment.setRoute_short_name(getIntent().getStringExtra("route_short_name"));
        comment.setAuthor_email(mAuth.getCurrentUser().getEmail());
        comment.setAuthor_name(mAuth.getCurrentUser().getDisplayName());
        comment.setComment(commentText.getText().toString());
        comment.setLast_modified(new Date());

        db.collection("comments").document(getIntent().getStringExtra("firebase_id")).set(comment)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        notificationHandler.send("Hozzászólás módosítva a járathoz: " + getIntent().getStringExtra("route_short_name"),
                                '"' + comment.getComment() + '"');
                        finish();
                    } else {
                        Toast.makeText(this, "Hiba történt", Toast.LENGTH_SHORT).show();
                    }
                });
        finish();
    }


    public void cancel(View view) {
        finish();
    }
}