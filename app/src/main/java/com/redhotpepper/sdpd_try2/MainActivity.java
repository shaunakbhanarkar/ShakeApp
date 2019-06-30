package com.redhotpepper.sdpd_try2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.ImageView;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
public class MainActivity extends AppCompatActivity implements SensorEventListener {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    final DatabaseReference ledstatus1 = myRef.child("led1").child("status");

    final DatabaseReference ledstatus2 = myRef.child("led2").child("status");

    final DatabaseReference ledstatus3 = myRef.child("led3").child("status");

    Button calibratebutton;
    TextView rotdisplay;
    int mAzimuth;
    private Sensor mRotationV; //mAccelerometer, mMagnetometer;
    private SensorManager mSensorManager;
    float rMat[] = new float[9];
    float orientation[] = new float[3];
    boolean haveSensor = false;

    int lineofsight;
    Button b1;
    Button b2;
//    Button b3;
//    Button b4;
//    Button b5;
//    Button b6;
    TextView textView1;
    //TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calibratebutton = (Button) findViewById(R.id.calibratebutton);
//        rotdisplay = (TextView) findViewById(R.id.rotdisplay);
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
//
        mRotationV = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        haveSensor = mSensorManager.registerListener(this, mRotationV, SensorManager.SENSOR_DELAY_UI);

        b1 = (Button)findViewById(R.id.button1);
        b2 = (Button)findViewById(R.id.button2);
//        b3 = (Button)findViewById(R.id.button3);
//        b4 = (Button)findViewById(R.id.button4);
//        b5 = (Button)findViewById(R.id.button5);
//        b6 = (Button)findViewById(R.id.button6);
        textView1 = (TextView)findViewById(R.id.textView1);
       // textView2 = (TextView)findViewById(R.id.textView2);
        //updateText(ledstatus1,textView1);

        ledstatus1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d("file", "Value is: " + value);
                textView1.setText(value);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("file", "Failed to read value.", error.toException());
            }
        });
        ledstatus2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d("file", "Value is: " + value);
                textView1.setText(value);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("file", "Failed to read value.", error.toException());
            }
        });

        ledstatus3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d("file", "Value is: " + value);
                textView1.setText(value);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("file", "Failed to read value.", error.toException());
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAzimuth >= lineofsight-20 && mAzimuth <= lineofsight+2)

                    ledstatus2.setValue("ON");

                else if (mAzimuth < lineofsight - 20 )
                    ledstatus1.setValue("ON");

                else if (mAzimuth > lineofsight + 20)
                    ledstatus3.setValue("ON");
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAzimuth >= lineofsight-20 && mAzimuth <= lineofsight+2)

                    ledstatus2.setValue("OFF");

                else if (mAzimuth < lineofsight - 20 )
                    ledstatus1.setValue("OFF");

                else if (mAzimuth > lineofsight + 20)
                    ledstatus3.setValue("OFF");
            }
        });
//        b3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ledstatus2.setValue("ON");
//            }
//        });
//
//        b4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ledstatus2.setValue("OFF");
//            }
//        });
//        b5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ledstatus3.setValue("ON");
//            }
//        });
//
//        b6.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ledstatus3.setValue("OFF");
//            }
//        });

        calibratebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lineofsight = mAzimuth;
            }
        });



    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        SensorManager.getRotationMatrixFromVector(rMat, event.values);
        mAzimuth = (int) (Math.toDegrees(SensorManager.getOrientation(rMat, orientation)[0]) + 360) % 360;

        mAzimuth = Math.round(mAzimuth);

//        rotdisplay.setText(mAzimuth);
//        if (mAzimuth >100)
//            rotdisplay.setText("abcd");
//        else if (mAzimuth <= 100)
//            rotdisplay.setText("pqrs");

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}