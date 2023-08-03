package za.co.umlandeli;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

    TextInputLayout fname_et, lname_et, email_et,code_et;
    Button update_btn, changeEmail_btn, changePassword_btn;
    ImageView back_btn;
    FirebaseAuth auth;
    DatabaseReference dbref;
    ProgressDialog progressDialog;
    AlertDialog.Builder alertDialog;
    String userId;
    Spinner province_spinner, subj_1_spin,subj_2_spin,subj_3_spin,subj_4_spin,subj_5_spin,subj_6_spin,subj_7_spin;
    List<ProfilePOJO> usersList = new ArrayList<>();

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
        province_spinner = findViewById(R.id.province_spinner);
        subj_1_spin = findViewById(R.id.subj_1);
        subj_2_spin = findViewById(R.id.subj_2);
        subj_3_spin = findViewById(R.id.subj_3);
        subj_4_spin = findViewById(R.id.subj_4);
        subj_5_spin = findViewById(R.id.subj_5);
        subj_6_spin = findViewById(R.id.subj_6);
        subj_7_spin = findViewById(R.id.subj_7);
        progressDialog = new ProgressDialog(this);
        alertDialog = new AlertDialog.Builder(this);

        auth = FirebaseAuth.getInstance();
        dbref = FirebaseDatabase.getInstance().getReference("Users");
        userId = auth.getCurrentUser().getUid();

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

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Dashboard.class));
            }
        });

        // display data
        displayData();

        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot s : snapshot.getChildren()) {
                        ProfileModel profile = s.child("Profile").getValue(ProfileModel.class);
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

    // validate input data
    private boolean validateSuburb() {
        String value = suburb_et.getEditText().getText().toString().trim();
        if (value.isEmpty()) {
            suburb_et.setError("Field cannot be empty");
            suburb_et.setBoxStrokeErrorColor(getResources().getColorStateList(R.color.black));
            return false;
        } else {
            suburb_et.setError(null);
            suburb_et.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateStreet() {
        String value = street_et.getEditText().getText().toString().trim();
        if (value.isEmpty()) {
            street_et.setError("Field cannot be empty");
            street_et.setBoxStrokeErrorColor(getResources().getColorStateList(R.color.black));
            return false;
        } else {
            street_et.setError(null);
            street_et.setErrorEnabled(false);
            return true;
        }
    }

    // validate input data
    private boolean validateCity() {
        String value = city_et.getEditText().getText().toString().trim();
        if (value.isEmpty()) {
            city_et.setError("Field cannot be empty");
            city_et.setBoxStrokeErrorColor(getResources().getColorStateList(R.color.black));
            return false;
        } else {
            city_et.setError(null);
            city_et.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateCode() {
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
        if (!validateFirstName() | !validateLastName() | !validateStreet() | !validateSuburb() | !validateCity() | !validateCode()) {
            return;
        }

        String sfname = fname_et.getEditText().getText().toString();
        String slname = lname_et.getEditText().getText().toString();
        String sstreet = street_et.getEditText().getText().toString();
        String ssuburb = suburb_et.getEditText().getText().toString();
        String scity = city_et.getEditText().getText().toString();
        String scode = code_et.getEditText().getText().toString();

        updateProfile(sfname, slname, sstreet, ssuburb, scity, scode);
    }

    private void updateProfile(String sfname, String slname, String sstreet, String ssuburb, String scity, String scode) {

        progressDialog.setMessage("Updating your profile...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        HashMap<String, Object> update = new HashMap<>();
        update.put("FName", sfname);
        update.put("LName", slname);
        update.put("Street", sstreet);
        update.put("Suburb", ssuburb);
        update.put("City", scity);
        update.put("PostalCode", scode);

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
                    ProfileModel profile = snapshot.getValue(ProfileModel.class);

                    fname_et.getEditText().setText(profile.getFName());
                    lname_et.getEditText().setText(profile.getLName());
                    email_et.getEditText().setText(profile.getEmailAddress());

                    if (profile.getStreet() != null) {
                        street_et.getEditText().setText(profile.getStreet());
                    }
                    if (profile.getSuburb() != null) {
                        suburb_et.getEditText().setText(profile.getSuburb());
                    }
                    if (profile.getCity() != null) {
                        city_et.getEditText().setText(profile.getCity());
                    }
                    if (profile.getPostalCode() != null) {
                        code_et.getEditText().setText(profile.getPostalCode());
                    }
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
}