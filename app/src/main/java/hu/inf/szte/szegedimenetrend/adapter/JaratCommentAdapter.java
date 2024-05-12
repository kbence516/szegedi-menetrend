package hu.inf.szte.szegedimenetrend.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

import hu.inf.szte.szegedimenetrend.R;
import hu.inf.szte.szegedimenetrend.activity.HozzaszolasActivity;
import hu.inf.szte.szegedimenetrend.model.Comment;

public class JaratCommentAdapter extends RecyclerView.Adapter<JaratCommentAdapter.ViewHolder> {
    ArrayList<Comment> comments;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    private int lastPos = -1;

    public JaratCommentAdapter(ArrayList<Comment> comments) {
        this.comments = comments;
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public JaratCommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listitemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.jarat_comment, parent, false);
        return new ViewHolder(listitemView);
    }

    @Override
    public void onBindViewHolder(@NonNull JaratCommentAdapter.ViewHolder holder, int position) {
        Comment comment = comments.get(position);

        holder.authorName.setText(comment.getAuthor_name());
        holder.comment.setText(comment.getComment());
        if (!Objects.equals(comment.getAuthor_email(), mAuth.getCurrentUser().getEmail())) {
            holder.editButton.setVisibility(View.GONE);
            holder.deleteButton.setVisibility(View.GONE);
        }
        holder.editButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), HozzaszolasActivity.class);
            intent.putExtra("firebase_id", comment._getFirebase_id());
            intent.putExtra("comment", comment.getComment());
            intent.putExtra("route_short_name", comment.getRoute_short_name());
            intent.putExtra("last_modified", comment.getLast_modified().getTime());
            v.getContext().startActivity(intent);
        });
        holder.deleteButton.setOnClickListener(v -> {
            db.collection("comments").document(comment._getFirebase_id()).delete()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(v.getContext(), "Hozzászólás törölve", Toast.LENGTH_SHORT).show();
                            comments.remove(position);
                            notifyDataSetChanged();

                        }
                    });
        });

        if (holder.getAdapterPosition() > lastPos) {
            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.scale_up);
            holder.itemView.startAnimation(animation);
            lastPos = holder.getAdapterPosition();
        }
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView authorName;
        TextView comment;
        MaterialButton editButton;
        MaterialButton deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);
            authorName = itemView.findViewById(R.id.username);
            comment = itemView.findViewById(R.id.comment);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
