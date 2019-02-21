package com.julialoseva.wave.engine

import android.media.MediaPlayer
import java.lang.Exception

class Output(
    val filePath: String
) {

    private var mediaPlayer: MediaPlayer? = MediaPlayer()

    private fun prepareMediaPlayer() {
        this.mediaPlayer = MediaPlayer().apply {
            setDataSource(
                this@Output.filePath
            )
            prepare()
            start()
        }
    }

    fun start() {
        try {
            this.prepareMediaPlayer()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun stop() {
        this.mediaPlayer?.let {
            it.stop()
            it.release()
        }
    }
}