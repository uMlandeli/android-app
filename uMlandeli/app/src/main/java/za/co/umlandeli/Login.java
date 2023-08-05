package za.co.umlandeli;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.paperdb.Paper;

public class Login extends AppCompatActivity {
    TextInputLayout email_et, password_et;
    Button login_btn;
    TextView reg_btn, forgotPassword;
    CheckBox checkBox;
    DatabaseReference db;
    FirebaseAuth auth;
    String userID;
    ProgressDialog progressDialog;
    AlertDialog.Builder alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email_et = findViewById(R.id.email);
        password_et = findViewById(R.id.password);
        login_btn = findViewById(R.id.login_btn);
        reg_btn = findViewById(R.id.registerinstead);
        forgotPassword = findViewById(R.id.forgot_password);
        checkBox = findViewById(R.id.checkbox);
        progressDialog = new ProgressDialog(this);
        alertDialog = new AlertDialog.Builder(this);
        Paper.init(this);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();

        // set button on click
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validatefields();
            }
        });

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), Register.class));

            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), ForgotPassword.class));

            }
        });
    }

    // validate fields
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

        if (value.isEmpty()) {
            password_et.setError("Field cannot be empty");
            password_et.setBoxStrokeErrorColor(getResources().getColorStateList(R.color.black));
            password_et.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
            return false;
        } else  {
            password_et.setError(null);
            password_et.setErrorEnabled(false);
            return true;
        }
    }

    private void validatefields(){
        if(!validateEmailAddress() | !validatePassword()){
            return;
        }

        String semail = email_et.getEditText().getText().toString().trim();
        String spassword = password_et.getEditText().getText().toString().trim();

        loginuser(semail, spassword);
    }

    private void loginuser(String semail, String spassword) {

        progressDialog.setMessage("Please wait while we verify your credentials");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        auth.signInWithEmailAndPassword(semail, spassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            userID = auth.getCurrentUser().getUid();

                            if (checkBox.isChecked()){
                                Paper.book().write("email", semail);
                                Paper.book().write("password", spassword);
                            }

                            if(auth.getCurrentUser().isEmailVerified()){
                                progressDialog.dismiss();

                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.putExtra("userID", userID);
                                startActivity(intent);
                            }
                            else{
                                progressDialog.dismiss();
                                //Toast.makeText(Login.this, "Error: Email address is not verified, please verify your email address.", Toast.LENGTH_SHORT).show();

                                alertDialog.setTitle("Resend verification email to " + semail);
                                alertDialog.setMessage("Error: Email address is not verified, please verify your email address.\n\nPlease confirm if you want to receive a verification message again, or change your email address.");
                                alertDialog.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        auth.getCurrentUser().sendEmailVerification();
                                    }
                                });
                                alertDialog.setNegativeButton("CHANGE", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(getApplicationContext(), ChangeEmailAddress.class);
                                        intent.putExtra("callingActivity", "login");
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                                alertDialog.setCancelable(false);
                                alertDialog.show();
                            }

                        }
                        else {
                            progressDialog.dismiss();
                            Toast.makeText(Login.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(Login.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}