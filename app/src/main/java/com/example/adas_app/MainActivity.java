package com.example.adas_app;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;


public class MainActivity extends AppCompatActivity {

    // Used to load the 'adas_app' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }


    //Declare objects for the views
    private TextView myText;
    private SeekBar speedSlider;
    private CircularProgressBar progressBar;
    private TextView progressText;
    private TextView warnings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Declare all views
        myText = findViewById(R.id.speedTextview);
        speedSlider = findViewById(R.id.speedSlider);
        progressBar = findViewById(R.id.fuelEfficiencyBar);
        progressText = findViewById(R.id.fuelEfficiencyText);
        warnings = findViewById(R.id.warningMessage);

        Log.i("ADAS_APP", "Message from native code: ");

        myText.setText(String.valueOf(speedSlider.getProgress()));
        speedSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //Display speed and speed color logic
                myText.setText(String.valueOf(progress));
                //call the native function to update the current speed
                String speedColor = updateSpeed(progress);
                switch (speedColor) {
                    case "YELLOW":
                        myText.setTextColor(Color.YELLOW);
                        showWarning("Warning!!! Slower the Speed");
                        break;
                    case "RED":
                        myText.setTextColor(Color.RED);
                        showWarning("Danger!!! Slower the Speed");
                        break;
                    case "WHITE":
                        myText.setTextColor(Color.WHITE);
                        break;

                }

                //set the progressBarStatus as per Speed
                int progressBarTextColor = updateProgressStats(progress);
                progressBar.setProgress(progressBarTextColor);
                progressText.setText(String.valueOf(progressBarTextColor));

                //set progressBarTextColor
                if(progressBarTextColor == 30){
                    progressBar.setProgressBarColor(Color.RED);
                    showWarning("Fuel is inefficient");
                }else if(progressBarTextColor == 80){
                    progressBar.setProgressBarColor(Color.GREEN);
                }else if(progressBarTextColor == 50){
                    progressBar.setProgressBarColor(Color.GRAY);
                }

            }

            private void showWarning(String message) {
                warnings.setText(message);
                warnings.setVisibility(View.VISIBLE);

                // Auto-hide after 3 seconds
                new Handler().postDelayed(() -> warnings.setVisibility(View.INVISIBLE), 3000);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }



    // Call the native method
    private native String updateSpeed(int progress);
    private native int updateProgressStats(int progress);



    /*
     * A native method that is implemented by the 'adas_app' native library,
     * which is packaged with this application.
     */


}