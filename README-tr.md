# MidJourney Images Compose Multiplatform Mobile Application
![kotlin-version](https://img.shields.io/badge/kotlin-2.0.0-blue?logo=kotlin)
[![Android Weekly #567](https://androidweekly.net/issues/issue-567/badge)](https://androidweekly.net/issues/issue-567)
<a href="https://mailchi.mp/kotlinweekly/kotlin-weekly-352"><img alt="Kotlin Weekly" src="image-assets/kotlin-weekly.svg"/></a>
[![Build & Ship](https://github.com/mbakgun/midjourney-images-compose-multiplatform/actions/workflows/main.yml/badge.svg)](https://github.com/mbakgun/midjourney-images-compose-multiplatform/actions/workflows/main.yml)
#### [Google Dev](https://devlibrary.withgoogle.com/products/android/repos/mbakgun-midjourney-images-compose-multiplatform) Öne Çıkanlar

Bu uygulama, çoklu platform desteği ile MidJourney'ın oluşturduğu resimleri göstermek için geliştirilmiştir. Uygulama,
Compose Multiplatform ile geliştirilmiştir. Uygulama, Android, iOS, Web, Wear OS, Android Automotive, Android TV platformlarında çalışmaktadır.

<p align="center"><img src="image-assets/1.gif" alt="kmm-compose-header" /><br><br></p>
Kotlin ve Jetpack Compose kullanılarak MVVM konseptinde geliştirtirildi. Network request state'leri, endless pagination, image loading ve image caching işlemleri yapılmıştır.

## Kullanılan Kütüphaneler

- [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)
- [Ktor](https://ktor.io/)
- [Koin](https://insert-koin.io/)
- [Kotlinx Serialization](https://kotlinlang.org/docs/serialization.html)
- [Kotlinx Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- [Coil](https://coil-kt.github.io/coil/)
- [KMM-ViewModel](https://github.com/rickclephas/KMM-ViewModel)
- [Multiplatform Settings](https://github.com/russhwolf/multiplatform-settings)

<div style="text-align: center;"><img src="image-assets/venn.png" alt="decisions"></div>

Consumer App'ler herhangi bir komplex yapıya sahip değildir. Ortak ui layer'ı kullanırlar(viewModel dahil) ve
MjImagesApp'i oluşturmakla sorumludurlar. [MjImagesApp](https://github.com/mbakgun/midjourney-images-compose-multiplatform/blob/e640ac5893478fa0b0b3ed6e71f2b3b66765ce0d/shared/src/commonMain/kotlin/ui/MjImagesApp.kt#L38-L38) ve bu Composable'nin dependency'si [MjImagesViewModel](https://github.com/mbakgun/midjourney-images-compose-multiplatform/blob/e640ac5893478fa0b0b3ed6e71f2b3b66765ce0d/shared/src/commonMain/kotlin/ui/MjImagesViewModel.kt#L15-L15), Tüm platformlarda ortak çalışır.

## Gereksinimler

- MacOS
- Linkteki gibi [ortam](https://github.com/JetBrains/compose-multiplatform-ios-android-template#set-up-the-environment)
  kurulu olmalıdır.

<img src="image-assets/androidss.png" alt="android-phone" height="450" /> <img src="image-assets/iosss.png" alt="ios-phone"  height="455" />

## Android Application

Android uygulamasını derlemek için aşağıdaki komutu çalıştırın:

```bash
./gradlew :androidApp:assembleDebug
```

<br><img src="image-assets/android.gif" width="240" alt="android-compose"/>

## iOS Application

Proje build edildikten sonra iOS uygulaması herhangi bir emulator veya cihaz üzerinde çalıştırılabilir. Android Studio
veya Xcode kullanılabilir.

<br><img src="image-assets/ios.gif" width="240" alt="android-compose"/>

## Wear OS Application

Proje build edildikten sonra Wear OS uygulaması herhangi bir emulator veya cihaz üzerinde çalıştırılabilir.

```bash
./gradlew :wearApp:assembleDebug
```

<img src="image-assets/wearos.png" alt="android-compose"/>

## Android TV Application

Proje build edildikten sonra Android TV uygulaması herhangi bir emulator veya cihaz üzerinde çalıştırılabilir.

```bash
./gradlew :televisionApp:assembleDebug
```

<img src="image-assets/television.gif" alt="android-compose"/>

## Android Automotive Application

Proje build edildikten sonra Android Automotive uygulaması herhangi bir emulator(Android Studio Hedgehog gerektirir) veya cihaz üzerinde çalıştırılabilir.

```bash
./gradlew :automotiveApp:assembleDebug
```

<img src="image-assets/automotive.gif" alt="android-compose"/>

## Desktop Application

Bu proje Windows, Debian ve MacOS için oluşturulabilir.

```bash
./gradlew desktopApp:run
```

### Desktop için dağıtım oluşturma

```
./gradlew :desktop:packageDistributionForCurrentOS
# çıktılar desktopApp/build/compose/binaries dizinine yazılır
```

<img src="image-assets/desktop.gif" alt="desktop-compose"/>

## Web Application

Bu proje Web(JS) için oluşturulabilir.
[Demo](https://mj.akgns.com/compose)

```
./gradlew :compose-web:jsBrowserDevelopmentRun
```

### Web için dağıtım oluşturma

```
./gradlew :webApp:jsBrowserDevelopmentExecutableDistribution
./gradlew :webApp:jsBrowserDistribution
```

## Wasm Application
Bu proje Web(Wasm) için oluşturulabilir.
[Demo](https://mj.akgns.com/wasm)

```
./gradlew :wasmApp:wasmBrowserDevelopmentRun
```

### Wasm için dağıtım oluşturma

```
./gradlew :wasmApp:wasmBrowserDevelopmentExecutableDistribution
./gradlew :wasmApp:wasmJsBrowserDistribution
```

<img src="image-assets/web.gif" alt="web-compose"/>

## Test

Uygulama compose ui test,maestro ui test ve unit testlere sahiptir. Unit testler common paket altında, fake data ile yazılmıştır. UI testler ise
androidTest paketi altında yazılmıştır. Maestro testleri ise [maestro paketi](https://github.com/mbakgun/midjourney-images-compose-multiplatform/tree/master/.maestro) altında yazılmıştır.

* Unit Testleri çalıştırmak için aşağıdaki komutu çalıştırın:

    ```bash
    ./gradlew :shared:cleanTestDebugUnitTest :shared:testDebugUnitTest
    ```
* Maestro Testleri çalıştırmak için aşağıdaki komutu çalıştırın:

    ```bash
    maestro test .maestro
    ```
* Android Instrumented UI testleri çalıştırmak için aşağıdaki komutu çalıştırın:

    ```bash
    ./gradlew :shared:connectedDebugAndroidTest
    ```

## API

Uygulama için kullanılan API, [mj.akgns.com](https://mj.akgns.com/) şeklindedir.

## Çeviri
- [İngilizce](/README.md)
- [Almanca](/README-de.md)

## Medium

Daha fazla bilgi için [Medium](https://mbakgun.medium.com/mj-compose-multiplatform-e6f737b3cd18) yazısına göz atabilirsiniz.

## Katkı 👏

Lütfen issue açmakta özgür hissedin; her türlü katkı memnuniyetle karşılanacaktır! Şimdiden elinize
sağlık ✌️

<a href="https://github.com/mbakgun/midjourney-images-compose-multiplatform/graphs/contributors">
<img src="https://contrib.rocks/image?repo=mbakgun/midjourney-images-compose-multiplatform" />
</a>

## İletişim

* Linkedin: https://www.linkedin.com/in/mbakgun
* Twitter: https://twitter.com/mbakguns
* Mastodon: https://androiddev.social/@mbakgun

License
-----------------

      MIT License

      Copyright (c) 2023 Mehmet Burak Akgün 
      
      Permission is hereby granted, free of charge, to any person obtaining a copy
      of this software and associated documentation files (the "Software"), to deal
      in the Software without restriction, including without limitation the rights
      to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
      copies of the Software, and to permit persons to whom the Software is
      furnished to do so, subject to the following conditions:
      
      The above copyright notice and this permission notice shall be included in
      all copies or substantial portions of the Software.
      
      THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
      IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
      FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
      AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
      LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
      OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
      THE SOFTWARE.