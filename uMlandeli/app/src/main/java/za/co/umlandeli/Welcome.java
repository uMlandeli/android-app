package za.co.umlandeli;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.paperdb.Paper;

public class Welcome extends AppCompatActivity {

    Button register, login;
    public static String userEmailAddress = "", userPassword = "";
    public static String userId;
    private DatabaseReference dbRef;
    private FirebaseAuth auth;
    AlertDialog.Builder alertDialog;

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // connect buttons to their IDs in the interface
        register = findViewById(R.id.reg_btn);
        login = findViewById(R.id.login_btn);

        // connect buttons to their IDs in the interface
        register = findViewById(R.id.reg_btn);
        login = findViewById(R.id.login_btn);
        alertDialog = new AlertDialog.Builder(this);
        Paper.init(this);

        // make the buttons clickable
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // go to register page
                startActivity(new Intent(getApplicationContext(), Register.class));

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // go to login page
                startActivity(new Intent(getApplicationContext(), Login.class));

            }
        });

        // remember me
        userEmailAddress = Paper.book().read("email");
        userPassword = Paper.book().read("password");
        auth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference("Users");

        if (userEmailAddress != "" && userPassword != "")
        {
            if (!TextUtils.isEmpty(userEmailAddress) && !TextUtils.isEmpty(userPassword))
            {
                allowAccess(userEmailAddress, userPassword);
            }
        }
    }

    private void allowAccess(String userEmailAddress, String userPassword) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        // log in user
        auth.signInWithEmailAndPassword(userEmailAddress, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    userId = auth.getCurrentUser().getUid();

                    if(auth.getCurrentUser().isEmailVerified()){
                        progressDialog.dismiss();

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("userID", userId);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        progressDialog.dismiss();
                        //Toast.makeText(Login.this, "Error: Email address is not verified, please verify your email address.", Toast.LENGTH_SHORT).show();

                        alertDialog.setTitle("Resend verification email to " + userEmailAddress);
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
                else{
                    progressDialog.dismiss();
                    Toast.makeText(Welcome.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Login.class));
                    finish();
                }
            }
        }).addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {
                progressDialog.dismiss();
            }
        });

    }
}