package com.julialoseva.wave.engine

import android.media.MediaPlayer
import android.media.MediaRecorder
import android.provider.MediaStore
import android.view.Gravity
import android.widget.TextView
import java.lang.Exception

class Output(
    val filePath: String,
    private val setupMediaPlayer: ((mediaPlayer: MediaPlayer) -> Unit)?
) {

    private var mediaPlayer = MediaPlayer().also {
        this.setupMediaPlayer?.invoke(it)
    }

    var progress = PlayProgress()
        private set

    var onPlayListener: OnPlayListener? = null

    private fun updateProgress() {
        this.progress = PlayProgress(
            duration = this.mediaPlayer.duration,
            currentPosition = this.mediaPlayer.currentPosition
        )
    }

    fun start() {
        try {
            this.mediaPlayer.apply {
                setDataSource(
                    this@Output.filePath
                )
                prepare()
                start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return
        }

        this.updateProgress()
        this.onPlayListener?.onStarted(this)
        this.onPlayListener?.onProgressChanged(this)

        while (
            this.mediaPlayer.isPlaying
            && this.mediaPlayer.currentPosition < this.mediaPlayer.duration
        ) {
            this.updateProgress()
            this.onPlayListener?.onProgressChanged(this)
        }
    }

    fun pause() {
        this.mediaPlayer.pause()
        this.onPlayListener?.onPause(this)
    }

    fun stop() {
        this.mediaPlayer.apply {
            stop()
            release()
        }
        this.onPlayListener?.onCompleted(
            CompletionReason.STOPPED,
            this
        )
    }

    interface OnPlayListener {
        fun onStarted(output: Output)
        fun onProgressChanged(output: Output)
        fun onPause(output: Output)
        fun onCompleted(reason: CompletionReason, output: Output)
    }

    enum class CompletionReason {
        STOPPED,
        FINISHED
    }
}
