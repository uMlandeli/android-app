package za.co.umlandeli;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    TextInputLayout email;
    Button submit;
    FirebaseAuth auth;
    AlertDialog.Builder alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        submit = findViewById(R.id.submit_btn);
        email = findViewById(R.id.email);
        auth = FirebaseAuth.getInstance();
        alert = new AlertDialog.Builder(this);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validateEmail()){
                    return;
                }

                String address = email.getEditText().getText().toString().trim();

                sendLink(address);
            }
        });
    }

    private void sendLink(String address) {
        alert.setTitle("Forgot Password?");
        alert.setMessage("Please confirm that the email address provided is correct: \n" + address);
        alert.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                auth.sendPasswordResetEmail(address).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(ForgotPassword.this, "Password reset link was sent tour email address.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(ForgotPassword.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        alert.setCancelable(false);
        alert.show();
    }

    // validate fields
    private boolean validateEmail() {

        String value = email.getEditText().getText().toString().trim();

        if (value.isEmpty()) {
            email.setError("Field cannot be empty");
            email.setBoxStrokeErrorColor(getResources().getColorStateList(R.color.black));
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            email.setError("Invalid email address!");
            email.setBoxStrokeErrorColor(getResources().getColorStateList(R.color.black));
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }

    }
    @Override
    public void onBackPressed() {
        finish();
    }
}