package za.co.umlandeli;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class Splash_screen extends AppCompatActivity {
    private static int SPLASH_TIMER = 2500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(Splash_screen.this, Welcome.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIMER);
    }
}