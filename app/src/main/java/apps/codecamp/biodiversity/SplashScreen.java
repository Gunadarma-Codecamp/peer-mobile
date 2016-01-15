package apps.codecamp.biodiversity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    public static final int detik = 3;
    public static final int milidetik = detik*1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);
        progressAnimasi();
    }
    public void progressAnimasi()
    {
        new CountDownTimer(milidetik,1000)
        {
            @Override
            public void onTick(long millisUntilFinished) {
                process_progress(millisUntilFinished);
            }
            @Override
            public void onFinish() {
                Intent newform = new Intent(SplashScreen.this,MainActivity.class);
                startActivity(newform);
                finish();
            }
        }.start();
    }

    public int process_progress(long miliseconds)
    {
        return (int) ((milidetik-miliseconds)/1000);
    }
}
