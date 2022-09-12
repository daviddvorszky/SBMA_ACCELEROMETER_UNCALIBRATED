package com.example.sbma_accelerometer_uncalibrated

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text

class MainActivity : ComponentActivity(), SensorEventListener {

    companion object{
        private lateinit var sensorManager: SensorManager
        private var sAccelero: Sensor? = null
        private val sensorViewModel = SensorViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sAccelero = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER_UNCALIBRATED)
        if(sAccelero == null){
            Log.i("pengb", "No uncalibrated accelerometer.")
            sAccelero = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        }
        if(sAccelero == null){
            Log.i("pengb", "No accelerometer.")
        }

        setContent {
            if(sAccelero == null){
                Text("No accelerometer.")
            }else{
                ShowSensorData(sensorViewModel)
                Log.i("pengb", "It should work")
            }
        }

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        Log.d("pengb", "onSensorChanged")
        p0 ?: return


        if(p0.sensor == sAccelero){
            Log.d("pengb", "sAccelero")
            sensorViewModel.updateValue(p0.values)
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        Log.d("pengb", "onAccuracyChanged ${p0?.name}: $p1")
    }

    override fun onResume() {
        super.onResume()
        sAccelero?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(this)
    }
}
