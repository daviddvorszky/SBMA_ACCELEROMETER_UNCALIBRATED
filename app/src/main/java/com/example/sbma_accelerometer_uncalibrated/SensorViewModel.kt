package com.example.sbma_accelerometer_uncalibrated

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.Float.max

class SensorViewModel: ViewModel() {
    private val _value: MutableLiveData<List<Float>> = MutableLiveData()
    val value: LiveData<List<Float>> = _value

    private val _maxValue: MutableLiveData<List<Float>> = MutableLiveData()
    val maxValue: LiveData<List<Float>> = _maxValue

    fun updateValue(value: FloatArray){
        _value.value = value.toList()
        val maxValues: List<Float> = listOf(
            max(value[0], maxValue.value?.get(0) ?: 0f),
            max(value[1], maxValue.value?.get(1) ?: 0f),
            max(value[2], maxValue.value?.get(2) ?: 0f),
        )
        _maxValue.value = maxValues
    }
}

@Composable
fun ShowSensorData(sensorViewModel: SensorViewModel) {
    val value by sensorViewModel.value.observeAsState()
    val maxValue by sensorViewModel.maxValue.observeAsState()
    Column() {
        value?.joinToString(", ")?.let { Text("Current: $it") }
        maxValue?.joinToString(", ")?.let { Text("Max: $it") }
    }
}