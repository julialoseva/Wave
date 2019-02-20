package com.julialoseva.wavedemo

import android.content.Context
import android.os.Environment

object AppConfiguration {

    private fun getRecordFolderPath(
        context: Context
    ): String {
        val environmentDirectory = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_MUSIC
        ).absolutePath
        val appDirectoryName = context.resources.getString(
            R.string.app_name
        )
        return "$environmentDirectory/$appDirectoryName"
    }

    private const val recordFileName = "record.3gpp"

    fun getRecordFilePath(
        context: Context
    ): String {
        return "${this.getRecordFolderPath(context)}/${this.recordFileName}"
    }
}