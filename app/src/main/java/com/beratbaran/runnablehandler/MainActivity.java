package com.beratbaran.runnablehandler;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    int number;
    Runnable runnable;
    Handler handler;
    Button button;
    Switch lever;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        lever = findViewById(R.id.lever);

        lever.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                startTimer();
            } else {
                stopTimer();
            }
        });


        button.setOnClickListener(v -> reset());
    }


    public void startTimer() {
        handler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {
                textView.setText("Time: " + number);
                number++;
                handler.postDelayed(runnable, 1000);
            }
        };

        handler.post(runnable);
        Toast.makeText(this, "Time Started", Toast.LENGTH_SHORT).show();
    }


    public void stopTimer() {
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }

        Toast.makeText(this, "Time Stopped", Toast.LENGTH_SHORT).show();
    }


    public void reset() {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Reset Time");
        alert.setMessage("Are you sure you want to reset the time?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (handler != null && runnable != null) {
                    handler.removeCallbacks(runnable);

                }

                number = 0;
                textView.setText("Time: " + number);
                lever.setChecked(false);

                Toast.makeText(getApplicationContext(),"Time Reset", Toast.LENGTH_LONG).show();

            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Not Reset", Toast.LENGTH_LONG).show();
            }
        });

        alert.show();
    }

}
