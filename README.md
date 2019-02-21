<p align="center" >
	<img src="images/logo_2048_2048.png" alt="Wave" title="Wave" width="300" height="300">
</p>

<p align="center">
	<a href="https://http://www.android.com">
		<img src="https://img.shields.io/badge/android-23-green.svg?style=flat">
	</a>
	<a href="https://jitpack.io/#julialoseva/Wave">
		<img src="https://jitpack.io/v/julialoseva/Wave.svg">
	</a>
	<a href="https://tldrlegal.com/license/apache-license-2.0-(apache-2.0)">
		<img src="https://img.shields.io/badge/License-Apache 2.0-blue.svg?style=flat">
	</a>
</p>

## At a Glance

`Wave` is a convenient tool for recording with microphone and playing audio files.

## How to Get Started

Add `jitpack.io` repository to your project:

```javascript
allprojects {
 repositories {
    jcenter()
    maven { url "https://jitpack.io" }
 }
}
```

Then add `Wave` to dependencies list:

```javascript
dependencies {
    implementation 'com.github.julialoseva:Wave:1.0'
}
```

## Requirements

* Android SDK 23 and later
* Android Studio 3.3 and later
* Kotlin 1.3.20 or later

## Usage

### Recording

You have to use `Input` instance for recording sound with microphone. Example:

```kotlin
val input = Input(
    filePath = "record.3gpp",
    context = this
)
input.start() // Recording started
input.stop() // Recording stopped
```

### Playing

Use `Output` instance for playing your sound. Example:

```kotlin
val output = Output(
    filePath = "record.3gpp"
)
output.start() // Playing started
output.stop() // Playing stopped
```

### Syntactic Sugar

Please note that `waveInput` is intended for recording, while `waveOutput` is for playing your record. Both methods are available from any `Context` instance.

Just type `waveInput` or `waveOutput` in your activity, then pass your file path as an argument and call `start()` or `stop()` according to your purposes, as here:

```kotlin
val input = waveInput("record.3gpp")
    .apply { start() }

val output = waveOutput("record.3gpp")
    .apply { start() }
```

## License

`Wave` is available under the Apache 2.0 license. See the [LICENSE](./LICENSE) file for more info.
