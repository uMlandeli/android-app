package za.co.umlandeli;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import za.co.umlandeli.model.ProfilePOJO;

public class Profile extends AppCompatActivity {

    TextInputLayout fname_et, lname_et, email_et,code_et, school_et;
    Button update_btn, changeEmail_btn, changePassword_btn;
    FirebaseAuth auth;
    DatabaseReference dbref;
    ProgressDialog progressDialog;
    AlertDialog.Builder alertDialog;
    List<ProfilePOJO> usersList = new ArrayList<>();
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        update_btn = findViewById(R.id.update_btn);
        changeEmail_btn = findViewById(R.id.change_email_btn);
        changePassword_btn = findViewById(R.id.change_password_btn);
        fname_et = findViewById(R.id.fname);
        lname_et = findViewById(R.id.lname);
        email_et = findViewById(R.id.email);
        code_et = findViewById(R.id.grade);
        school_et = findViewById(R.id.school_name);
        progressDialog = new ProgressDialog(this);
        alertDialog = new AlertDialog.Builder(this);

        auth = FirebaseAuth.getInstance();
        dbref = FirebaseDatabase.getInstance().getReference("Users");
        userId = auth.getCurrentUser().getUid();

        //Navigation Bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_profile);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId(); // Get the selected item's ID

            if (itemId == R.id.bottom_home) {
                // Handle the Home case
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (itemId == R.id.bottom_profile) {
                return true;
            } else if (itemId == R.id.bottom_connect) {
                startActivity(new Intent(getApplicationContext(), ConnectionsU.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (itemId == R.id.bottom_subjects) {
                startActivity(new Intent(getApplicationContext(), SubjectsU.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }

            return false;
        });
        //End of Navigation Bar

        changeEmail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), ChangeEmailAddress.class);
                intent.putExtra("callingActivity", "profile");
                startActivity(intent);
                finish();
            }
        });

        changePassword_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ChangePassword.class));
            }
        });

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });

        // display data
        displayData();

        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot s : snapshot.getChildren()) {
                        ProfilePOJO profile = s.child("Profile").getValue(ProfilePOJO.class);
                        usersList.add(profile);
                    }
                }
            }
            @Override
            public void onCancelled (@NonNull DatabaseError error){
                Toast.makeText(getApplicationContext(), "" + error, Toast.LENGTH_SHORT).show();
            }
        });
    }


    // validate input data
    private boolean validateFirstName() {
        String valueName = fname_et.getEditText().getText().toString().trim();
        if (valueName.isEmpty()) {
            fname_et.setError("Field cannot be empty");
            fname_et.setBoxStrokeErrorColor(getResources().getColorStateList(R.color.black));
            return false;
        } else {
            fname_et.setError(null);
            fname_et.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateLastName() {
        String valueName = lname_et.getEditText().getText().toString().trim();
        if (valueName.isEmpty()) {
            lname_et.setError("Field cannot be empty");
            lname_et.setBoxStrokeErrorColor(getResources().getColorStateList(R.color.black));
            return false;
        } else {
            lname_et.setError(null);
            lname_et.setErrorEnabled(false);
            return true;
        }
    }


    private boolean validateSchool() {
        String value = school_et.getEditText().getText().toString().trim();
        if (value.isEmpty()) {
            school_et.setError("Field cannot be empty");
            school_et.setBoxStrokeErrorColor(getResources().getColorStateList(R.color.black));
            return false;
        } else {
            school_et.setError(null);
            school_et.setErrorEnabled(false);
            return true;
        }
    }


    private boolean validateGrade() {
        String value = code_et.getEditText().getText().toString().trim();
        if (value.isEmpty()) {
            code_et.setError("Field cannot be empty");
            code_et.setBoxStrokeErrorColor(getResources().getColorStateList(R.color.black));
            return false;
        } else {
            code_et.setError(null);
            code_et.setErrorEnabled(false);
            return true;
        }
    }



    private void validate() {
        if (!validateFirstName() | !validateLastName() | !validateSchool() | !validateGrade()) {
            return;
        }

        String sfname = fname_et.getEditText().getText().toString();
        String slname = lname_et.getEditText().getText().toString();
        String sGrade = code_et.getEditText().getText().toString();
        String sSchool = school_et.getEditText().getText().toString();

        updateProfile(sfname, slname, sSchool, sGrade);
    }

    private void updateProfile(String sfname, String slname, String sSchool, String sGrade) {

        progressDialog.setMessage("Updating your profile...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        HashMap<String, Object> update = new HashMap<>();
        update.put("FName", sfname);
        update.put("LName", slname);
        update.put("emailAddress",email_et.getEditText().getText().toString());
        update.put("SchoolName", sSchool);
        update.put("Grade", sGrade);
        update.put("Uid",userId);

        dbref.child(userId).child("Profile").updateChildren(update)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            displayData();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(Profile.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    private void displayData() {
        dbref.child(userId).child("Profile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    ProfilePOJO profile = snapshot.getValue(ProfilePOJO.class);

                    fname_et.getEditText().setText(profile.getFName());
                    lname_et.getEditText().setText(profile.getLName());
                    email_et.getEditText().setText(profile.getEmailAddress());
                    school_et.getEditText().setText(profile.getSchool_name());
                    code_et.getEditText().setText(profile.getGrade());
                } else {
                    Toast.makeText(Profile.this, "Error: No such user exists!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profile.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        finish();
    }
}