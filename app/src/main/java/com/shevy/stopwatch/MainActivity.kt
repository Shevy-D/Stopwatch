package com.shevy.stopwatch

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.TextView
import java.util.*

class MainActivity : AppCompatActivity() {
    private var seconds: Int = 0
    private var running: Boolean = false
    private var wasRunning: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds")
            running = savedInstanceState.getBoolean("running")
            wasRunning = savedInstanceState.getBoolean("wasRunning")
        }
        runTime()
    }

    override fun onSaveInstanceState(saveInstanceState: Bundle) {
        super.onSaveInstanceState(saveInstanceState)
        saveInstanceState.putInt("seconds", seconds)
        saveInstanceState.putBoolean("running", running)
        saveInstanceState.putBoolean("wasRunning", wasRunning)

    }

    override fun onPause() {
        super.onPause()
        wasRunning = running
        running = false
    }

    override fun onResume() {
        super.onResume()
        if (wasRunning) {
            running = true
        }
    }

    fun onClickStart(view: View) {
        running = true
    }

    fun onClickStop(view: View) {
        running = false
    }

    fun onClickReset(view: View) {
        running = false
        seconds = 0
    }

    private fun runTime() {
        val timeView: TextView = findViewById(R.id.time_view)
        val handler = Handler()
        handler.post(object: Runnable {
            override fun run() {
                val hours: Int = seconds / 3600
                val minutes: Int = (seconds % 3600) / 60
                val secs: Int = seconds % 60
                val time: String = String.format(
                    Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, secs
                )
                timeView.text = time
                if (running) {
                    seconds++
                }
                handler.postDelayed(this, 1000)
            }
        })
    }
}