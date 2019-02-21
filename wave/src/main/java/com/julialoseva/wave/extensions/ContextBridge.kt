package com.julialoseva.wave.extensions

import android.content.Context
import com.julialoseva.wave.engine.Input
import com.julialoseva.wave.engine.Output

fun Context.waveInput(
    filePath: String
): Input = Input(
    filePath = filePath,
    context = this
)

fun Context.waveOutput(
    filePath: String
): Output = Output(
    filePath = filePath
)