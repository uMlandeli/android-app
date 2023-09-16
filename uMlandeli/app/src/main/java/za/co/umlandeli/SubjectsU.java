package za.co.umlandeli;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import za.co.umlandeli.model.ProfilePOJO;

public class SubjectsU extends AppCompatActivity {
    private DatabaseReference dbref;
    private FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        dbref = FirebaseDatabase.getInstance().getReference("Users").child(currentUser.getUid());

        //Navigation Bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_subjects);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId(); // Get the selected item's ID

            if (itemId == R.id.bottom_home) {
                // Handle the Home case
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (itemId == R.id.bottom_profile) {
                startActivity(new Intent(getApplicationContext(), Profile.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (itemId == R.id.bottom_connect) {
                startActivity(new Intent(getApplicationContext(), ConnectionsU.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (itemId == R.id.bottom_subjects) {
                return true;
            }

            return false;
        });
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

        // Fetch user's subjects from the database
        dbref.child("Profile").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    ProfilePOJO profile = snapshot.getValue(ProfilePOJO.class);
                    if (profile != null) {
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
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled event
            }
        });

    }
}