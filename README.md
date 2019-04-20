<h1 align="center">Eirene <a href="https://github.com/javiersantos/AppUpdater#how-to-include"></h1>
<h4 align="center">Android Library</h4>

<p align="center">
  <a target="_blank" href="https://android-arsenal.com/api?level=8"><img src="https://img.shields.io/badge/API-21%2B-orange.svg"></a>
</p>

<p align="center">Android Library that aims to provide a simple, general purpose video player experience.


## Demo Project
A demo project is included in this repo under the `demo` directory

## Usage
#### Activity
If you're using Activities, simply extend from the `EireneActivity` class provided. 
You will have to provide implementations for methods detailing what URL to stream from and what OkHttpClient you are currently using in your app. The end result will look like something below:
```Kotlin
class DemoVideoActivity : EireneActivity() {
    override fun getUrl(): String {
        return Constants.testHLS2
    }

    override fun getOkHttpClient(): OkHttpClient {
        return NetworkManager.Instance.getHttpClient()!!.build()
    }
}
```

#### Fragment
If you're using Fragments, simply extend from the `EireneFragment` class provided. 
You will have to provide implementations for methods detailing what URL to stream from and what OkHttpClient you are currently using in your app. The end result will look like something below:
```Kotlin
class DemoVideoFragment : EireneFragment() {
    override fun getUrl(): String {
        return Constants.testHLS
    }

    override fun getOkHttpClient(): OkHttpClient {
        return NetworkManager.Instance.getHttpClient()!!.build()
    }
}
```

## Customizations
While `EireneActivity` and `EireneFragment` abstract classes both provide a good default template, you may want to add even more customizations to your video experience.

#### Custom Player Layout
If you want to provide a different layout than the library's default, you can simply override the `getPlayerView` method

```Kotlin
class DemoVideoFragment : EireneFragment() {

    //abstract method implementations here...
    
    override fun getPlayerView(inflater: LayoutInflater, @Nullable container: ViewGroup?): View {
        return inflater.inflate(R.layout.my_player_view, container, false)
    }
}
```

## Known Issues
* Need to provide a way for users to properly override lifecycle methods without interfering with video player capabilities
* Need to provide more customization for volume (layout, disable)
* Need to provide more customization for Key Events (Android TV remote buttons, swipes, taps)
* Need to provide more customization for the media control layout (play/pause, fast forward, closed captioning)
* Clean up warnings
* Get demo on Play Store

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
