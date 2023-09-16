package za.co.umlandeli;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
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
    TextInputLayout fname_et, lname_et, email_et, password_et, confirmpassword_et;
    DatabaseReference insertdb;
    FirebaseAuth auth;
    String userID;
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

    }

    private void validatefields() {

        if (!validateFirstName() | !validateLastName() | !validateEmailAddress() | !validatePassword() | !validateConfirmPassword()) {
            return;
        }

        String sfname = fname_et.getEditText().getText().toString();
        String slname = lname_et.getEditText().getText().toString();
        String semail = email_et.getEditText().getText().toString();
        String spassword = password_et.getEditText().getText().toString();

        registerUser(sfname, slname, semail, spassword);

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

    private void registerUser(String sfname, String slname, String semail, String spassword) {

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