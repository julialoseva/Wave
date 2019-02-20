package com.julialoseva.wavedemo

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.julialoseva.wave.PlayManager
import com.julialoseva.wave.RecordManager
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

    private val recordManager by lazy {
        RecordManager(this)
    }

    private val playManager by lazy {
        PlayManager(
            AppConfiguration.getRecordFilePath(
                this
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
        this.playManager.stopPlay()
        this.recordManager.stopRecord()
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
                    "Включите необходимые разрешения",
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
                        recordManager.startRecord(
                            AppConfiguration.getRecordFilePath(
                                this@MainActivity
                            )
                        )
                        recordButton.setColorFilter(
                            resources.getColor(
                                R.color.rb_green_300
                            )
                        )
                        true
                    }
                    MotionEvent.ACTION_UP -> {
                        recordManager.stopRecord()
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
            this.playManager.startPlay()
        }
    }

    private fun prepareStopButton() {
        this.stopButton.setOnClickListener {
            this.playManager.stopPlay()
        }
    }
}
