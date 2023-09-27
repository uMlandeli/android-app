package za.co.umlandeli;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import za.co.umlandeli.model.ProfilePOJO;

public class SubjectsU extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseFirestore db;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);
        auth = FirebaseAuth.getInstance();
        userId = auth.getCurrentUser().getUid();
        db = FirebaseFirestore.getInstance();

        //Navigation Bar


        //End of Navigation Bar

        // TextViews for subject names and teacher names
        TextView[] subjNameTextViews = {
                findViewById(R.id.subj1_name),
                findViewById(R.id.subj2_name),
                findViewById(R.id.subj3_name),
                findViewById(R.id.subj4_name),
                findViewById(R.id.subj5_name),
                findViewById(R.id.subj6_name),
                findViewById(R.id.subj7_name)
        };
        displaySubjects(subjNameTextViews);
    }
    private void displaySubjects(TextView[] subjNameTextViews) {
        DocumentReference profileRef = db.collection("users").document(userId).collection("Profile").document("UserData");
        profileRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@NonNull DocumentSnapshot snapshot, @NonNull FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(SubjectsU.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (snapshot != null && snapshot.exists()) {
                    ProfilePOJO profile = snapshot.toObject(ProfilePOJO.class);
                    String[] subjectNames = {
                            profile.getSubj_1(),
                            profile.getSubj_2(),
                            profile.getSubj_3(),
                            profile.getSubj_4(),
                            profile.getSubj_5(),
                            profile.getSubj_6(),
                            profile.getSubj_7()
                    };
                    for (int i = 0; i < subjectNames.length; i++) {
                        subjNameTextViews[i].setText(subjectNames[i]);
                    }
                } else {
                    Toast.makeText(SubjectsU.this, "Error: No such user exists!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}