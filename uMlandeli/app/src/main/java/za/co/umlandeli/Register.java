package za.co.umlandeli;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;



public class Register extends AppCompatActivity {

    Button register_btn;
    TextView login_btn;
    TextInputLayout fname_et, lname_et, email_et, password_et, confirmpassword_et, code_et, school_et;
    DatabaseReference insertdb;
    FirebaseAuth auth;
    String userID;
    String province, subj_1, subj_2, subj_3, subj_4, subj_5, subj_6, subj_7;
    ArrayAdapter<CharSequence> provinceAdapter, subj1Adapter, subj2Adapter, subj3Adapter, subj4Adapter, subj5Adapter, subj6Adapter, subj7Adapter;
    Spinner province_spinner, subj_1_spin,subj_2_spin,subj_3_spin,subj_4_spin,subj_5_spin,subj_6_spin,subj_7_spin;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register_btn = findViewById(R.id.register_btn);
        login_btn = findViewById(R.id.loginistead);
        fname_et = findViewById(R.id.fname);
        lname_et = findViewById(R.id.lname);
        email_et = findViewById(R.id.email);
        password_et = findViewById(R.id.password);
        confirmpassword_et = findViewById(R.id.conpassword);
        progressDialog = new ProgressDialog(this);
        province_spinner = findViewById(R.id.province_spinner);
        subj_1_spin = findViewById(R.id.subj_1);
        subj_2_spin = findViewById(R.id.subj_2);
        subj_3_spin = findViewById(R.id.subj_3);
        subj_4_spin = findViewById(R.id.subj_4);
        subj_5_spin = findViewById(R.id.subj_5);
        subj_6_spin = findViewById(R.id.subj_6);
        subj_7_spin = findViewById(R.id.subj_7);
        code_et = findViewById(R.id.grade);
        school_et = findViewById(R.id.school_name);
        auth = FirebaseAuth.getInstance();
        insertdb = FirebaseDatabase.getInstance().getReference("Users");

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // validate input fields
                validatefields();
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), Login.class));

            }
        });

        //Spinners
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
        //End of Spinners
    }

    private void validatefields() {

        if (!validateFirstName() | !validateLastName() | !validateEmailAddress() | !validatePassword() | !validateConfirmPassword() | !validateSchool() | !validateGrade() | !validateProvince() | !validateSubjects()) {
            return;
        }

        String sfname = fname_et.getEditText().getText().toString();
        String slname = lname_et.getEditText().getText().toString();
        String semail = email_et.getEditText().getText().toString();
        String spassword = password_et.getEditText().getText().toString();
        String sGrade = code_et.getEditText().getText().toString();
        String sSchool = school_et.getEditText().getText().toString();
        registerUser(sfname, slname, semail, spassword, sGrade, sSchool);

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

    private boolean validateEmailAddress() {

        String value = email_et.getEditText().getText().toString().trim();


        if (value.isEmpty()) {
            email_et.setError("Field cannot be empty");
            email_et.setBoxStrokeErrorColor(getResources().getColorStateList(R.color.black));
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            email_et.setError("Invalid email address!");
            email_et.setBoxStrokeErrorColor(getResources().getColorStateList(R.color.black));
            return false;
        } else {
            email_et.setError(null);
            email_et.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validatePassword() {

        String value = password_et.getEditText().getText().toString().trim();
        String checkPassword = "^" +
                "(?=.*[0-9])" +             //at least 1 digit
                "(?=.*[a-z])" +             //at least 1 lower case letter
                "(?=.*[A-Z])" +             // at least 1 upper case letter
                "(?=.*[a-zA-Z])" +          //any letter
                "(?=.*[@#!%$^&*()+=?/.,;])" +           // at least 1 special character
                "(?=\\S+$)" +                       // no white spaces
                ".{8,}" +                       // at least 8 characters
                "$";

        if (value.isEmpty()) {
            password_et.setError("Field cannot be empty");
            password_et.setBoxStrokeErrorColor(getResources().getColorStateList(R.color.black));
            password_et.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
            return false;
        } else if (!value.matches(checkPassword)) {
            password_et.setError("Password must have 8 char, at least 1 number, small & big letter, spacial char");
            password_et.setBoxStrokeErrorColor(getResources().getColorStateList(R.color.black));
            password_et.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
            return false;
        } else {
            password_et.setError(null);
            password_et.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateConfirmPassword() {

        String value = confirmpassword_et.getEditText().getText().toString().trim();

        if (value.isEmpty()) {
            confirmpassword_et.setError("Confirm password!");
            confirmpassword_et.setBoxStrokeErrorColor(getResources().getColorStateList(R.color.black));
            return false;
        } else if (!value.equals(password_et.getEditText().getText().toString().trim())) {
            confirmpassword_et.setError("Passwords don't match");
            confirmpassword_et.setBoxStrokeErrorColor(getResources().getColorStateList(R.color.black));
            return false;
        } else {
            confirmpassword_et.setError(null);
            confirmpassword_et.setErrorEnabled(false);
            return true;
        }
    }
    //validate province
    private boolean validateProvince() {
        if (province.equalsIgnoreCase("Select Province")) {
            return false;
        } else {
            return true;
        }
    }

    private boolean validateSubjects() {
        if (subj_1.equalsIgnoreCase("Select Subject")||subj_2.equalsIgnoreCase("Select Subject")||subj_3.equalsIgnoreCase("Select Subject")||subj_4.equalsIgnoreCase("Select Subject")||subj_5.equalsIgnoreCase("Select Subject")||subj_6.equalsIgnoreCase("Select Subject")||subj_7.equalsIgnoreCase("Select Subject")) {

            if (subj_1.equalsIgnoreCase("Select Subject")) {
                subj_1_spin.setBackgroundColor(getResources().getColor(R.color.red));
            }
            if (subj_2.equalsIgnoreCase("Select Subject")) {
                subj_2_spin.setBackgroundColor(getResources().getColor(R.color.red));
            }
            if (subj_3.equalsIgnoreCase("Select Subject")) {
                subj_3_spin.setBackgroundColor(getResources().getColor(R.color.red));
            }
            if (subj_4.equalsIgnoreCase("Select Subject")) {
                subj_4_spin.setBackgroundColor(getResources().getColor(R.color.red));
            }
            if (subj_5.equalsIgnoreCase("Select Subject")) {
                subj_5_spin.setBackgroundColor(getResources().getColor(R.color.red));
            }
            if (subj_6.equalsIgnoreCase("Select Subject")) {
                subj_6_spin.setBackgroundColor(getResources().getColor(R.color.red));
            }
            if (subj_7.equalsIgnoreCase("Select Subject")) {
                subj_7_spin.setBackgroundColor(getResources().getColor(R.color.red));
            }
            return false;
        } else {
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
    private void registerUser(String sfname, String slname, String semail, String spassword, String sGrade, String sSchool) {

        progressDialog.setMessage("Please wait while we process your registration");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        auth.createUserWithEmailAndPassword(semail, spassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            // this line will send a verification storeEmail
                            auth.getCurrentUser().sendEmailVerification();

                            userID = auth.getCurrentUser().getUid();

                            HashMap<String, Object> inputdata = new HashMap<>();
                            inputdata.put("FName", sfname);
                            inputdata.put("LName", slname);
                            inputdata.put("emailAddress", semail);
                            inputdata.put("Uid", userID);
                            inputdata.put("Province", province);
                            inputdata.put("SchoolName", sSchool);
                            inputdata.put("Grade", sGrade);
                            inputdata.put("Subject_1", subj_1);
                            inputdata.put("Subject_2", subj_2);
                            inputdata.put("Subject_3", subj_3);
                            inputdata.put("Subject_4", subj_4);
                            inputdata.put("Subject_5", subj_5);
                            inputdata.put("Subject_6", subj_6);
                            inputdata.put("Subject_7", subj_7);

                            insertdb.child(userID).child("Profile").updateChildren(inputdata)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                                progressDialog.dismiss();
                                                startActivity(new Intent(getApplicationContext(), Login.class));
                                                finish();

                                            } else {
                                                progressDialog.dismiss();
                                                Toast.makeText(Register.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(Register.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }
}