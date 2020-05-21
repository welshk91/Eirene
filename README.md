<h1 align="center">Eirene <a href="https://github.com/welshk91/Eirene#how-to-include"><img src="https://jitpack.io/v/javiersantos/AppUpdater.svg"></a></h1>
<h4 align="center">Android Library</h4>

<p align="center">
  <a target="_blank" href="https://android-arsenal.com/api?level=16"><img src="https://img.shields.io/badge/API-16%2B-orange.svg"></a>
</p>

<p align="center">Android Library that aims to provide a simple, general purpose video player experience.

## Description
This library aims to ease the burden of creating video experiences. It's built off of the [ExoPlayer](https://github.com/google/ExoPlayer) provided by Google. 

Unlike a pure ExoPlayer development environment, this library tries to handle much of the boiler plate code and setup. While this removes some of the configurability, the library allows for an easy, general use case out-of-the-box.

## Demo Project
A demo project is included in this repo under the `app` directory. A more advanced demo will be provided in a separate repository in the future. The plan is to release the demos to the Google Play Store when they mature.

## How To Include
#### Warning: This library is still in 'pre-alpha'. Expect changes to the APIs!
Add the [Jitpack.io](https://jitpack.io/) repository to your project **build.gradle** under `allprojects`:
```Gradle
allprojects {
    repositories {
        google()
        jcenter()

        maven {
            url "https://jitpack.io"
        }
    }
}
```

And add the library to your module **build.gradle**:
```Gradle
dependencies {
    implementation 'com.github.welshk91:Eirene:master-SNAPSHOT'
}
```

## Usage
#### Activity
If you're using Activities, simply extend from the `EireneActivity` class provided. The end result will look like something below:
```Kotlin
class DemoVideoActivity : EireneActivity() {
    val baseUrl = "https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8"
    override fun getUri(): Uri {
        return Uri.parse(baseUrl)
    }
}
```

#### Fragment
If you're using Fragments, simply extend from the `EireneFragment` class provided. The end result will look like something below:
```Kotlin
class DemoVideoFragment : EireneFragment() {
    val baseUrl = "https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8"
    override fun getUri(): Uri {
        return Uri.parse(baseUrl)
    }
}
```
For both methods, you will have to provide an implementation detailing what Uri to play.

## Customizations
While the `EireneActivity` and `EireneFragment` abstract classes both provide a good default template, you may want to add even more customizations to your video experience.

#### Custom Layout
If you want to provide a different layout than the library's default, you can simply override the `getRootView` method

```Kotlin
class DemoVideoFragment : EireneFragment() {

    //abstract method implementations here...
    
    override fun getRootView(inflater: LayoutInflater, @Nullable container: ViewGroup?): View {
        return inflater.inflate(R.layout.my_player_view, container, false)
    }
}
```

#### Custom Network Client
Already have an awesome OkHttpClient configured with caching, interceptors, or retry logic (perhaps from [RetroFit](https://square.github.io/retrofit/) or [OkHttp](https://square.github.io/okhttp/) itself)? You can simply override the `getOkHttpClient` method to provide this for the player

```Kotlin
class DemoVideoFragment : EireneFragment() {

    //abstract method implementations here...
    
    override fun getOkHttpClient(): OkHttpClient? {
        return NetworkManager.Instance.getHttpClient()!!.build()
    }
}
```

## Coming Soon
* ChromeCast support (Highest priority feature)
* DRM support
* Customization for volume (layout, disable especially on phones that already manage media volume)
* Customization for Key Events (Android TV remote buttons, swipes, taps)
* Customization for the media control layout (play/pause, fast forward, closed captioning)
* An interface for classes that don't want to use inheritance
* And more in GitHub's `Projects` tab!

## License
	Copyright 2019 Kevin Welsh
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	   http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
