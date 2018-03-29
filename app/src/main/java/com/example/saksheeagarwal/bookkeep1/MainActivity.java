package com.example.saksheeagarwal.bookkeep1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    EditText uname;
    EditText pass;
    Button Login;
    Button Register;


    TextView tv ;

    private SensorManager mSensorManager;
    private Sensor mSensorProximity;
    private Sensor mSensorLight;

    SQLiteDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager =
                (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensorLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        //mSensorManager.registerListener(this, mSensorLight,
              //  SensorManager.SENSOR_DELAY_NORMAL);



         Sensor mSensorProximity;
         Sensor mSensorLight;
         String mTextSensorLight;
        String mTextSensorProximity;

       mSensorProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

//        if (mSensorLight == null) {
//            Toast.makeText(getApplicationContext(),"nope",Toast.LENGTH_LONG).show();
//        }

        uname = (EditText) findViewById(R.id.uname);
        pass = (EditText) findViewById(R.id.pass);
        Login = (Button) findViewById(R.id.login);
        Register = (Button) findViewById(R.id.reg);


        try {
            database = openOrCreateDatabase("Bookkeep", MODE_PRIVATE, null);
            database.execSQL("CREATE TABLE IF NOT EXISTS Loginn (invite VARCHAR(10) PRIMARY KEY, user VARCHAR(20), pass VARCHAR(10));");
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
        try {
            database.execSQL("INSERT INTO Loginn VALUES('" + "stu3" + "', '" + " " + "', '" + " " + "')");
            database.execSQL("INSERT INTO Loginn VALUES('" + "fac3" + "', '" + " " + "', '" + " " + "')");
            Toast.makeText(getApplicationContext(), "Inserted!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {

           // if (e.toString().contains("code 1555"))
                //Toast.makeText(getApplicationContext(), "Not inserted", Toast.LENGTH_SHORT).show();
        }



        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this,Reg.class);
                startActivity(i);
                setContentView(R.layout.regactivity);

            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {

                String user = uname.getText().toString();
                String pwd = pass.getText().toString();
                if (user.equals("") || pwd.equals("") ) {
                    Toast.makeText(getApplicationContext(), "Please enter all details!", Toast.LENGTH_LONG).show();
                }
                else{
                Cursor c = database.rawQuery("SELECT * FROM Loginn WHERE TRIM(user) = '"+user.trim()+"' AND TRIM(pass)= '"+pwd.trim()+"'", null);
                float[] results = new float[1];
                double centerLatitude=13.3525;
                double centerLongitude=74.7928;
                double testLatitude=13.3525;
                double testLongitude=74.7928;



                Location.distanceBetween(centerLatitude, centerLongitude, testLatitude, testLongitude, results);
                float distanceInMeters = results[0];
                boolean isWithin10km = distanceInMeters < 100000;

                if(c.moveToFirst() && isWithin10km){
                    String s= c.getString(0);


                    if(s.contains("stu")){

                    Intent i = new Intent(MainActivity.this,StudentHome.class);
                    startActivity(i);
                    //setContentView(R.layout.shome);
                         }
                    else{


                        Intent i = new Intent(MainActivity.this,TeacherHome.class);
                        startActivity(i);
                       // setContentView(R.layout.thome);
                    }


                }
                else {
                    Toast.makeText(getApplicationContext(), "Invalid Credentials or Not in Manipal Campus", Toast.LENGTH_SHORT).show();

                }
            }}
        });


    }

    protected void onStart() {
        super.onStart();

           // mSensorManager.registerListener(this, mSensorProximity,
             //       SensorManager.SENSOR_DELAY_NORMAL);


            mSensorManager.registerListener(this, mSensorLight,
                    SensorManager.SENSOR_DELAY_NORMAL);

    }

    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int sensorType = sensorEvent.sensor.getType();
        float currentValue = sensorEvent.values[0];
        switch (sensorType) {
            // Event came from the light sensor.
            case Sensor.TYPE_LIGHT:
                if(currentValue>20000)
                {
                    //setTheme(android.R.style.Theme_Holo_Light);
                    //this.recreate();
                  // Utils.changeToTheme(this, Utils.THEME_BLUE);
                    findViewById(R.id.ff).setBackgroundColor(Color.parseColor("#ffffff"));
                }
                else{
                    //setTheme(android.R.style.Theme_Holo);
                    //this.recreate();
                    //Utils.changeToTheme(this, Utils.THEME_WHITE);


                    findViewById(R.id.ff).setBackgroundColor(Color.parseColor("#000000"));

                }

                break;
            default:
                // do nothing
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
