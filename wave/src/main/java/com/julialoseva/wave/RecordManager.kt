package com.julialoseva.wave

import android.content.Context
import android.media.MediaRecorder
import android.media.MediaScannerConnection
import java.io.File
import java.io.IOException

class RecordManager(
    private val context: Context
) {

    private var mediaRecorder: MediaRecorder? = null

    fun startRecord(filePath: String) {
        val outFile = File(filePath)
        outFile.mkdirs()

        if (outFile.exists()) {
            outFile.delete()
        }

        outFile.createNewFile()

        MediaScannerConnection.scanFile(
            this.context,
            arrayOf(
                outFile.absolutePath
            ),
            null,
            null
        )

        try {
            this.mediaRecorder = MediaRecorder().apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                setOutputFile(outFile.absolutePath)
                prepare()
                start()
            }
        } catch (e: java.lang.IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun stopRecord() {
        this.mediaRecorder?.let {
            try {
                it.stop()
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            }
        }
        this.mediaRecorder?.release()
        this.mediaRecorder = null
    }
}
