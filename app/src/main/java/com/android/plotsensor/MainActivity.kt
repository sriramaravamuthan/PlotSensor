package com.android.plotsensor

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries


class MainActivity : AppCompatActivity(), SensorEventListener {

    var sensorManager: SensorManager? = null
    var accelerometer : Sensor? = null

    var xValue : TextView? = null
    var yValue : TextView? = null
    var zValue : TextView? = null
    var XlineChart : GraphView? = null
    var YlineChart : GraphView? = null
    var ZlineChart : GraphView? = null

    var xSeries : LineGraphSeries<DataPoint>? =
        null
    var ySeries : LineGraphSeries<DataPoint>? =
        null
    var zSeries : LineGraphSeries<DataPoint>? =
        null

    var count = 0.0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager!!.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        xValue = findViewById(R.id.xValue) as TextView
        yValue = findViewById(R.id.yValue) as TextView
        zValue = findViewById(R.id.zValue) as TextView
        XlineChart = findViewById(R.id.XlineGraph) as GraphView
        YlineChart = findViewById(R.id.YlineGraph) as GraphView
        ZlineChart = findViewById(R.id.ZlineGraph) as GraphView
        xSeries =
            LineGraphSeries(
                arrayOf<DataPoint>(

                )
            )
        ySeries =
            LineGraphSeries(
                arrayOf<DataPoint>(

                )
            )
        zSeries =
            LineGraphSeries(
                arrayOf<DataPoint>(

                )
            )
        xSeries!!.setColor(Color.GREEN);
        ySeries!!.setColor(Color.RED);
        zSeries!!.setColor(Color.BLUE);
        XlineChart!!.addSeries(xSeries)
        YlineChart!!.addSeries(ySeries)
        ZlineChart!!.addSeries(zSeries)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {
        Log.d(this.localClassName, "X: "+ event!!.values[0] + "Y:"+event!!.values[1] + "Z:" + event!!.values[2])
        xValue!!.setText("X Value: "+event!!.values[0])
        yValue!!.setText("Y Value: "+event!!.values[1])
        zValue!!.setText("Z Value: "+event!!.values[2])
        xSeries!!.appendData(DataPoint(count++ as Double, event!!.values[0].toDouble() ), false, 10)
        ySeries!!.appendData(DataPoint(count++ as Double, event!!.values[1].toDouble() ), false, 10)
        zSeries!!.appendData(DataPoint(count++ as Double, event!!.values[2].toDouble() ), false, 10)
    }
}