package za.co.umlandeli;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ChangePassword extends AppCompatActivity {

    TextInputLayout password_et, confirmpassword_et;
    Button change_btn;
    ImageView back_btn;
    FirebaseAuth auth;
    FirebaseDatabase dbref;
    ProgressDialog progressDialog;
    AlertDialog.Builder alertDialog;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        password_et = findViewById(R.id.update_password);
        confirmpassword_et = findViewById(R.id.confirm_update_password);
        change_btn = findViewById(R.id.update_password_button);
        progressDialog = new ProgressDialog(this);
        alertDialog = new AlertDialog.Builder(this);

        auth = FirebaseAuth.getInstance();
        userId = auth.getCurrentUser().getUid();

        change_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Profile.class));
            }
        });


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

    private void validate() {
        progressDialog.setMessage("Please wait while we change your password.");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        if (!validatePassword() | !validateConfirmPassword()) {
            progressDialog.dismiss();
            return;
        }

        //String sconfirmPassword = confirmpassword_et.getEditText().getText().toString();
        String spassword = password_et.getEditText().getText().toString();

        auth.getCurrentUser().updatePassword(spassword).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    alertDialog.setTitle("Password was changed successfully");
                    alertDialog.setMessage("Please log in with your new password.");
                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(getApplicationContext(), Login.class));
                        }
                    });
                    alertDialog.setCancelable(false);
                    alertDialog.show();
                } else {
                    progressDialog.dismiss();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage(task.getException().getMessage());
                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(getApplicationContext(), Profile.class));
                        }
                    });
                    alertDialog.setCancelable(false);
                    alertDialog.show();
                }
            }
        });
    }

}