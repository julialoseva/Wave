package com.julialoseva.wave

import android.media.MediaPlayer
import java.lang.Exception

class PlayManager(
    val filePath: String
) {

    private var mediaPlayer: MediaPlayer? = MediaPlayer()

    private fun prepareMediaPlayer() {
        this.mediaPlayer = MediaPlayer().apply {
            setDataSource(
                this@PlayManager.filePath
            )
            prepare()
            start()
        }
    }

    fun startPlay() {
        try {
            this.prepareMediaPlayer()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun stopPlay() {
        this.mediaPlayer?.let {
            it.stop()
            it.release()
        }
    }
}