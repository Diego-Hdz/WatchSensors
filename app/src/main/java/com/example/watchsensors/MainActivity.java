package com.example.watchsensors;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MainActivity extends Activity implements SensorEventListener {
    private static final String TAG = "MainActivity";

    private SensorManager sensorManager;
    Sensor accelerometer;
    Sensor gyroscope;

    TextView accel_xValue, accel_yValue, accel_zValue;
    TextView gyro_xValue, gyro_yValue, gyro_zValue;

    /**
     * Initializes activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize accelerometer display values
        accel_xValue = (TextView) findViewById(R.id.accel_xValue);
        accel_yValue = (TextView) findViewById(R.id.accel_yValue);
        accel_zValue = (TextView) findViewById(R.id.accel_zValue);

        // Initialize gyroscope display values
        gyro_xValue = (TextView) findViewById(R.id.gyro_xValue);
        gyro_yValue = (TextView) findViewById(R.id.gyro_yValue);
        gyro_zValue = (TextView) findViewById(R.id.gyro_zValue);


        // Setting up the SensorManager
        Log.d(TAG, "onCreate: Initializing Sensor Serves");
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // Setting up the accelerometer Sensor in the SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(MainActivity.this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        Log.d(TAG, "onCreate: Registered Accelerometer Listener");

        // Setting up the gyroscope Sensor in the SensorManager
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager.registerListener(MainActivity.this, gyroscope, SensorManager.SENSOR_DELAY_GAME);
        Log.d(TAG, "onCreate: Registered Gyroscope Listener");
    }

    /**
     * Called when the accuracy of the registered sensor has changed.
     *
     * @param sensor The Sensor that is changed
     * @param accuracy The new accuracy of this sensor
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * Update watch display with gyroscope and accelerometer values
     * and displays data to the Log, if connected
     *
     * @param sensorEvent
     */
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        DecimalFormat df = new DecimalFormat("###.#####");
        df.setRoundingMode(RoundingMode.DOWN);
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            Log.d(TAG, "onSensorChanged (accelerometer): X: " + sensorEvent.values[0] + " Y: " + sensorEvent.values[1] + " Z: " + sensorEvent.values[2]);
            accel_xValue.setText("x value: " + df.format(sensorEvent.values[0]));
            accel_yValue.setText("y value: " + df.format(sensorEvent.values[1]));
            accel_zValue.setText("z value: " + df.format(sensorEvent.values[2]));
        }
        if (sensorEvent.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            Log.d(TAG, "onSensorChanged (gyroscope): X: " + sensorEvent.values[0] + " Y: " + sensorEvent.values[1] + " Z: " + sensorEvent.values[2]);
            gyro_xValue.setText("x value: " + df.format(sensorEvent.values[0]));
            gyro_yValue.setText("y value: " + df.format(sensorEvent.values[1]));
            gyro_zValue.setText("z value: " + df.format(sensorEvent.values[2]));
        }
    }
}