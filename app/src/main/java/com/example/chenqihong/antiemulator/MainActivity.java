package com.example.chenqihong.antiemulator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.chenqihong.antiemulator.controller.GeneralDetector;

public class MainActivity extends AppCompatActivity {

    GeneralDetector mDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView textView = (TextView)findViewById(R.id.main_text);
        mDetector = new GeneralDetector(this);
        mDetector.setEmulatorCheckedListener(new GeneralDetector.EmulatorCheckedListener() {
            @Override
            public void onChecked(boolean[] status) {
                textView.setText("Emulator: " + status[0] + ", "+ status[1] + ", "+
                        status[2] + ", "+ status[3] + ", "+ status[4] + ", "+
                        status[5] + ", "+ status[6] + ", ");
            }
        });

        mDetector.startChecking();
    }
}
