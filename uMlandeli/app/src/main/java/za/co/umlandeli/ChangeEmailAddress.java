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
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class ChangeEmailAddress extends AppCompatActivity {

    TextInputLayout email_et;
    Button changeEmail_btn;
    ImageView back_btn;
    FirebaseAuth auth;
    ProgressDialog progressDialog;
    AlertDialog.Builder alertDialog;
    DatabaseReference insertdb;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email_address);

        email_et = findViewById(R.id.email);
        changeEmail_btn = findViewById(R.id.changeEmail_btn);
        back_btn = findViewById(R.id.back_button);
        progressDialog = new ProgressDialog(this);
        alertDialog = new AlertDialog.Builder(this);
        auth = FirebaseAuth.getInstance();
        insertdb = FirebaseDatabase.getInstance().getReference();
        db = FirebaseFirestore.getInstance();
        changeEmail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String callingActivity = getIntent().getStringExtra("callingActivity");
                if(callingActivity.equals("profile")){
                    startActivity(new Intent(getApplicationContext(), Profile.class));
                }
                else{
                    startActivity(new Intent(getApplicationContext(), Login.class));
                }
            }
        });
    }

    private void validate(){
        if(!validateEmailAddress()){
            return;
        }

        String email = email_et.getEditText().getText().toString().trim();

        changeEmail(email);

    }

    private void changeEmail(String email) {

        progressDialog.setMessage("Please wait while we change your email address!");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        String userID = auth.getCurrentUser().getUid();

        CollectionReference usersCollection = db.collection("users");
        DocumentReference userDocRef = usersCollection.document(userID).collection("Profile").document("UserData");

        auth.getCurrentUser().updateEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            //progressDialog.dismiss();
                            auth.getCurrentUser().sendEmailVerification();

                            // update email address in database
                            HashMap<String, Object> inputdata = new HashMap<>();
                            inputdata.put("EmailAddress", email);

                            userDocRef.update(inputdata)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                progressDialog.dismiss();
                                                alertDialog.setTitle("Email verification was sent to " + email);
                                                alertDialog.setMessage("A new verification message was sent to your new email address. Verify your email before logging in.");
                                                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        startActivity(new Intent(getApplicationContext(), Login.class));
                                                        finish();
                                                    }
                                                });
                                                alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        startActivity(new Intent(getApplicationContext(), Login.class));
                                                        finish();
                                                    }
                                                });
                                                alertDialog.setCancelable(false);
                                                alertDialog.show();

                                            } else {
                                                progressDialog.dismiss();
                                                Toast.makeText(ChangeEmailAddress.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                        else{
                            progressDialog.dismiss();
                            Toast.makeText(ChangeEmailAddress.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(ChangeEmailAddress.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

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
}