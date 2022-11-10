package com.example.hci_hw3_anj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements SensorEventListener {
    int check = 0;
    private SensorManager sensorManager;
    private Sensor sensor;
    private EditText editText;
    private String str;
    private ArrayList<Character> templist = new ArrayList();
    private int flag = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        editText = findViewById(R.id.editText);
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (flag == 1){
                    flag = 0;

                }
                else {
                    templist.clear();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float a,b;
        a = sensorEvent.values[0];
        b = sensorEvent.values[1];
        if (Math.abs(a) > Math.abs(b)) {
            if (a < -2) {
                if(check!=1) {
                    check=1;
                    Context context = getApplicationContext();
                    CharSequence text = "You tilted the phone to your right";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    str = editText.getText().toString();
                    redo(str);
                }
            }
            if (a > 2) {
                if(check!=2) {
                    check = 2;
                    Context context = getApplicationContext();
                    CharSequence text = "You tilted the phone to your left";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    str = editText.getText().toString();
                    undo(str);

                }
            }
            if (a > (-2) && a < (2) && b > (-2) && b < (2)) {
                Context context = getApplicationContext();
                CharSequence text = "You did not tilt the phone";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

            }


    }}

    private void redo(String str) {
        Character s;
        if (templist.size() > 0){
            s= templist.get(templist.size() - 1);
            templist.remove(templist.size() - 1);
            str = str.concat(String.valueOf(s));
            flag = 1;
            editText.setText(str);



        }
    }

    private void undo(String str) {
        if (str.length() != 0){
            templist.add(str.charAt(str.length()-1));
            str=str.substring(0,str.length()-1);
            flag=1;
            editText.setText(str);
        }





    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //unregister Sensor listener
        sensorManager.unregisterListener(this);
    }
}