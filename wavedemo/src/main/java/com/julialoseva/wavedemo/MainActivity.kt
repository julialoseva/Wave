package com.julialoseva.wavedemo

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.julialoseva.wave.engine.Input
import com.julialoseva.wave.engine.Output
import com.julialoseva.wave.extensions.waveInput
import com.julialoseva.wave.extensions.waveOutput
import com.visuality.consent.bridge.getConsent
import com.visuality.consent.bridge.handleConsent

class MainActivity : AppCompatActivity() {

    private val recordButton by lazy {
        findViewById<ImageButton>(R.id.record_button)
    }

    private val playButton by lazy {
        findViewById<Button>(R.id.play_button)
    }

    private val stopButton by lazy {
        findViewById<Button>(R.id.stop_button)
    }

    private val input by lazy {
        waveInput(
            AppConfiguration.getRecordFilePath(
                this,
                RECORD_FILE_NAME
            )
        )
    }

    private val output by lazy {
        waveOutput(
            filePath = AppConfiguration.getRecordFilePath(
                this,
                RECORD_FILE_NAME
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_main)
        this.requestPermissions()
        this.prepareRecordButton()
        this.preparePlayButton()
        this.prepareStopButton()
    }

    override fun onDestroy() {
        this.output.stop()
        this.input.stop()
        super.onDestroy()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        this.handleConsent(
            requestCode,
            permissions,
            grantResults
        )
    }

    private fun requestPermissions() {
        this.getConsent(
            android.Manifest.permission.RECORD_AUDIO,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ).whenFinished {
            if (it.hasBlocked) {
                Toast.makeText(
                    this,
                    "Permissions are not available",
                    Toast.LENGTH_SHORT
                ).show()
            }

            this.recordButton.isEnabled = !it.hasBlocked
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun prepareRecordButton() {
        this.recordButton.setOnTouchListener(object: View.OnTouchListener{
            override fun onTouch(view: View?, motionEvent: MotionEvent?): Boolean {
                return when(motionEvent!!.action) {
                    MotionEvent.ACTION_DOWN -> {
                        this@MainActivity.input.start()
                        recordButton.setColorFilter(
                            resources.getColor(
                                R.color.rb_green_300
                            )
                        )
                        true
                    }
                    MotionEvent.ACTION_UP -> {
                        this@MainActivity.input.stop()
                        recordButton.setColorFilter(
                            resources.getColor(
                                R.color.rb_black
                            )
                        )
                        true
                    }
                    else -> false
                }
            }
        })
    }

    private fun preparePlayButton() {
        this.playButton.setOnClickListener {
            this.output.start()
        }
    }

    private fun prepareStopButton() {
        this.stopButton.setOnClickListener {
            this.output.stop()
        }
    }

    companion object {
        const val RECORD_FILE_NAME = "record.3gpp"
    }
}
