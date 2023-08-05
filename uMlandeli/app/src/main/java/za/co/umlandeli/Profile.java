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
    String userId, province, subj_1, subj_2, subj_3, subj_4, subj_5, subj_6, subj_7;
    ArrayAdapter<CharSequence> provinceAdapter, subj1Adapter, subj2Adapter, subj3Adapter, subj4Adapter, subj5Adapter, subj6Adapter, subj7Adapter;
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
        school_et = findViewById(R.id.school_name);
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

        //Province Spinner
        provinceAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.provinces,
                android.R.layout.simple_spinner_item
        );
        provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        province_spinner.setAdapter(provinceAdapter);
        province_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                province = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Subject 1 Spinner
        subj1Adapter = ArrayAdapter.createFromResource(
                this,
                R.array.subjects,
                android.R.layout.simple_spinner_item
        );
        subj1Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subj_1_spin.setAdapter(subj1Adapter);
        subj_1_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subj_1 = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //Subject 2 Spinner
        subj2Adapter = ArrayAdapter.createFromResource(
                this,
                R.array.subjects,
                android.R.layout.simple_spinner_item
        );
        subj2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subj_2_spin.setAdapter(subj2Adapter);
        subj_2_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subj_2 = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Subject 3 Spinner
        subj3Adapter = ArrayAdapter.createFromResource(
                this,
                R.array.subjects,
                android.R.layout.simple_spinner_item
        );
        subj3Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subj_3_spin.setAdapter(subj3Adapter);
        subj_3_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subj_3 = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Subject 4 Spinner
        subj4Adapter = ArrayAdapter.createFromResource(
                this,
                R.array.subjects,
                android.R.layout.simple_spinner_item
        );
        subj4Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subj_4_spin.setAdapter(subj4Adapter);
        subj_4_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subj_4 = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Subject 5 Spinner
        subj5Adapter = ArrayAdapter.createFromResource(
                this,
                R.array.subjects,
                android.R.layout.simple_spinner_item
        );
        subj5Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subj_5_spin.setAdapter(subj5Adapter);
        subj_5_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subj_5 = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Subject 6 Spinner
        subj6Adapter = ArrayAdapter.createFromResource(
                this,
                R.array.subjects,
                android.R.layout.simple_spinner_item
        );
        subj6Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subj_6_spin.setAdapter(subj6Adapter);
        subj_6_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subj_6 = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Subject 7 Spinner
        subj7Adapter = ArrayAdapter.createFromResource(
                this,
                R.array.subjects,
                android.R.layout.simple_spinner_item
        );
        subj7Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subj_7_spin.setAdapter(subj7Adapter);
        subj_7_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subj_7 = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



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

    //validate province
    private boolean validateProvince() {
        if (province.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    private boolean validateSubjects() {
        if (subj_1.isEmpty()||subj_2.isEmpty()||subj_3.isEmpty()||subj_4.isEmpty()||subj_5.isEmpty()||subj_6.isEmpty()||subj_7.isEmpty()) {

            if (subj_1.isEmpty()) {
                subj_1_spin.setBackgroundColor(getResources().getColor(R.color.red));
            }
            if (subj_2.isEmpty()) {
                subj_2_spin.setBackgroundColor(getResources().getColor(R.color.red));
            }
            if (subj_3.isEmpty()) {
                subj_3_spin.setBackgroundColor(getResources().getColor(R.color.red));
            }
            if (subj_4.isEmpty()) {
                subj_4_spin.setBackgroundColor(getResources().getColor(R.color.red));
            }
            if (subj_5.isEmpty()) {
                subj_5_spin.setBackgroundColor(getResources().getColor(R.color.red));
            }
            if (subj_6.isEmpty()) {
                subj_6_spin.setBackgroundColor(getResources().getColor(R.color.red));
            }
            if (subj_7.isEmpty()) {
                subj_7_spin.setBackgroundColor(getResources().getColor(R.color.red));
            }
            return false;
        } else {
            return true;
        }
    }

    private void validate() {
        if (!validateFirstName() | !validateLastName() | !validateSchool() | !validateGrade() | !validateProvince() | !validateSubjects()) {
            return;
        }

        String sfname = fname_et.getEditText().getText().toString();
        String slname = lname_et.getEditText().getText().toString();
        String sGrade = code_et.getEditText().getText().toString();
        String sSchool = school_et.getEditText().getText().toString();

        updateProfile(sfname, slname, sSchool, sGrade, province, subj_1, subj_2, subj_3, subj_4, subj_5, subj_6, subj_7);
    }

    private void updateProfile(String sfname, String slname, String sSchool, String sGrade, String province, String subj_1, String subj_2, String subj_3, String subj_4, String subj_5, String subj_6, String subj_7) {

        progressDialog.setMessage("Updating your profile...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        HashMap<String, Object> update = new HashMap<>();
        update.put("FName", sfname);
        update.put("LName", slname);
        update.put("SchoolName", sSchool);
        update.put("Province", province);
        update.put("Grade", sGrade);
        update.put("Subject_1", subj_1);
        update.put("Subject_2", subj_2);
        update.put("Subject_3", subj_3);
        update.put("Subject_4", subj_4);
        update.put("Subject_5", subj_5);
        update.put("Subject_6", subj_6);
        update.put("Subject_7", subj_7);


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

                    if (profile.getGrade() != null) {
                        code_et.getEditText().setText(profile.getGrade());
                    }
                    if (profile.getProvince() != null) {
                        int spinnerPosition = provinceAdapter.getPosition(profile.getProvince());
                        province_spinner.setSelection(spinnerPosition);
                    }
                    if (profile.getSchool_name() != null) {
                        school_et.getEditText().setText(profile.getSchool_name());
                    }
                    if (profile.getSubj_1() != null) {
                        int spinnerPosition = subj1Adapter.getPosition(profile.getProvince());
                        subj_1_spin.setSelection(spinnerPosition);
                    }
                    if (profile.getSubj_2() != null) {
                        int spinnerPosition = subj2Adapter.getPosition(profile.getProvince());
                        subj_2_spin.setSelection(spinnerPosition);
                    }
                    if (profile.getSubj_3() != null) {
                        int spinnerPosition = subj3Adapter.getPosition(profile.getProvince());
                        subj_3_spin.setSelection(spinnerPosition);
                    }
                    if (profile.getSubj_4() != null) {
                        int spinnerPosition = subj4Adapter.getPosition(profile.getProvince());
                        subj_4_spin.setSelection(spinnerPosition);
                    }
                    if (profile.getSubj_5() != null) {
                        int spinnerPosition = subj5Adapter.getPosition(profile.getProvince());
                        subj_5_spin.setSelection(spinnerPosition);
                    }
                    if (profile.getSubj_6() != null) {
                        int spinnerPosition = subj6Adapter.getPosition(profile.getProvince());
                        subj_6_spin.setSelection(spinnerPosition);
                    }
                    if (profile.getSubj_7() != null) {
                        int spinnerPosition = subj7Adapter.getPosition(profile.getProvince());
                        subj_7_spin.setSelection(spinnerPosition);
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
    @Override
    public void onBackPressed() {
        finish();
    }
}