package za.co.umlandeli;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.annotation.Nullable;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import za.co.umlandeli.model.Academic;
import za.co.umlandeli.model.ProfilePOJO;


public class MainActivity extends AppCompatActivity {
    DatabaseReference dbref;
    FirebaseAuth auth;
    private FirebaseFirestore db;
    TextView welcomeText, youHave;
    String userId;
    TableLayout tableLayout;
    String[][] data = new String[7][2];
    private MeowBottomNavigation bottomNavigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.app_primary_color, getTheme()));
        window.setNavigationBarColor(getResources().getColor(R.color.app_primary_color, getTheme()));

        WindowInsetsControllerCompat windowInsetsController =
                ViewCompat.getWindowInsetsController(getWindow().getDecorView());
        if (windowInsetsController == null) {
            return;
        }
        bottomNavigation=findViewById(R.id.bottomNavigation);
        bottomNavigation.show(1, true);
        auth = FirebaseAuth.getInstance();
        dbref = FirebaseDatabase.getInstance().getReference("Users");
        userId = auth.getCurrentUser().getUid();
        welcomeText = findViewById(R.id.Welcome_text);
        youHave = findViewById(R.id.you_have);
        tableLayout = findViewById(R.id.tableLayout);
        db = FirebaseFirestore.getInstance();
        displayName();

        String[][] tableData = displayTabledata(tableLayout);
        for (String[] row : tableData) {
            TableRow tableRow = new TableRow(this);

            for (String cell : row) {
                TextView textView = new TextView(this);
                textView.setText(cell);
                textView.setPadding(8, 8, 8, 8);
                textView.setGravity(Gravity.CENTER);
                tableRow.addView(textView);
            }
            tableLayout.addView(tableRow);
        }
// Hide the system bars.
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());

// Show the system bars.
        windowInsetsController.show(WindowInsetsCompat.Type.systemBars());

        //Navigation Bar

        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.baseline_home_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.baseline_person_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.connect_without_contact_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(4,R.drawable.baseline_menu_book_24));

        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {

                switch(model.getId())
                {
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                }
                return null;
            }
        });

        bottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {

                switch(model.getId())
                {
                    case 1:
                        break;
                }
                return null;
            }
        });

        bottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {

                switch(model.getId())
                {
                    case 2:
                        break;
                }
                return null;
            }
        });


        bottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {

                switch(model.getId())
                {
                    case 3:
                        break;
                }
                return null;
            }
        });

        bottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {

                switch(model.getId())
                {
                    case 4:
                        break;
                }
                return null;
            }
        });

        //End of Navigation Bar

        //Notification Animation
        LottieAnimationView animationView = findViewById(R.id.lottieAnimationView);
        animationView.playAnimation();
        //End of Notification Animation

    }

    private String[][] displayTabledata(TableLayout tableLayout) {
        Academic schoolPerf = new Academic();
        db.collection("users").document(userId).collection("Profile").document("UserData")
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (snapshot != null && snapshot.exists()) {
                            ProfilePOJO profile = snapshot.toObject(ProfilePOJO.class);
                            data = new String[][]{
                                    {profile.getSubj_1(), "100", "25", "20/180"},
                                    {profile.getSubj_2(), "10", "5", "12/80"},
                                    {profile.getSubj_3(), "16", "0", "70/100"},
                                    {profile.getSubj_4(), "36", "25", "105/180"},
                                    {profile.getSubj_5(), "61", "33", "80/120"},
                                    {profile.getSubj_6(), "5", "1", "9/50"},
                                    {profile.getSubj_7(), "12", "2", "38/220"},
                            };
                            // Now, update your tableLayout here.
                        } else {
                            Toast.makeText(MainActivity.this, "Error: Profile not found", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return data;
    }

    private void displayName() {
        youHave.setText("You have 2 new notifications");
        db.collection("users").document(userId).collection("Profile").document("UserData")
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (snapshot != null && snapshot.exists()) {
                            ProfilePOJO profile = snapshot.toObject(ProfilePOJO.class);
                            welcomeText.setText("Hi " + profile.getFName());
                        } else {
                            welcomeText.setText("Hi user " + userId.toLowerCase().substring(5));
                        }
                    }
                });
    }
}