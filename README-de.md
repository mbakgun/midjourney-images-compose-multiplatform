# MidJourney Images Compose Multiplatform Mobile Application

Diese Anwendung wurde entwickelt, um die Bilder von MidJourney anzuzeigen. Die Anwendung wurde mit Compose Multiplatform entwickelt und funktioniert auf Android- und iOS-Plattformen.

<p align="center"><img src="image-assets/1.gif" alt="kmm-compose-header" /><br><br></p>
Die Anwendung wurde im MVVM-Konzept mit Kotlin und Jetpack Compose entwickelt. Es wurden Netzwerkanforderungszustände, Endlos-Pagination, Bildladeprozesse und Bildcaching durchgeführt.

## Verwendete Bibliotheken

- [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)
- [Ktor](https://ktor.io/)
- [Koin](https://insert-koin.io/)
- [Kotlinx Serialization](https://kotlinlang.org/docs/serialization.html)
- [Kotlinx Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- [Compose ImageLoader](https://github.com/qdsfdhvh/compose-imageloader)
- [KMM-ViewModel](https://github.com/rickclephas/KMM-ViewModel)

<div style="text-align: center;"><img src="image-assets/venn.png" alt="decisions"></div>

Verbraucheranwendungen haben keine komplexen Strukturen oder Operationen. Sie verwenden die gemeinsame UI-Schicht (einschließlich ViewModel) und sind dafür verantwortlich, [MjImagesApp](https://github.com/mbakgun/midjourney-images-compose-multiplatform/blob/e640ac5893478fa0b0b3ed6e71f2b3b66765ce0d/shared/src/commonMain/kotlin/ui/MjImagesApp.kt#L38-L38) zu erstellen. MjImagesApp und die Abhängigkeit dieses Composables, [MjImagesViewModel](https://github.com/mbakgun/midjourney-images-compose-multiplatform/blob/e640ac5893478fa0b0b3ed6e71f2b3b66765ce0d/shared/src/commonMain/kotlin/ui/MjImagesViewModel.kt#L15-L15), funktionieren auf Android- und iOS-Plattformen gleich.

## Anforderungen

- MacOS
- Die [Entwicklungsumgebung](https://github.com/JetBrains/compose-multiplatform-ios-android-template#set-up-the-environment) muss gemäß dem Link installiert sein.

<img src="image-assets/androidss.png" alt="android-phone" height="450" /> <img src="image-assets/iosss.png" alt="ios-phone"  height="455" />


## Android-Anwendung

Führen Sie den folgenden Befehl aus, um die Android-Anwendung zu kompilieren:

```bash
./gradlew :androidApp:assembleDebug
```

<br><img src="image-assets/android.gif" width="240" alt="android-compose"/>

## iOS-Anwendung

Nachdem das Projekt erstellt wurde, kann die iOS-Anwendung auf jedem Emulator oder Gerät ausgeführt werden. Android Studio oder Xcode können verwendet werden.

<br><img src="image-assets/ios.gif" width="240" alt="android-compose"/>

## Tests

Die Anwendung verfügt über UI-Tests und Unit-Tests. Unit-Tests werden unter dem gemeinsamen Paket mit Fake-Daten geschrieben. UI-Tests werden unter dem androidTest-Paket geschrieben.

* Führen Sie den folgenden Befehl aus, um die Unit-Tests auszuführen:

    ```bash
    ./gradlew :shared:cleanTestDebugUnitTest :shared:testDebugUnitTest
    ```

## API

Die für die Anwendung verwendete API ist [mj.mbkgun.com](https://mj.mbakgun.com/).

## Zukünftige Pläne

- [ ] Verbesserung des Bildladeprozesses
- [ ] Langzeit und Bildfreigabe
- [ ] Erstellen einer Webversion der Anwendung

## Übersetzungen
- [Englisch](/README.md)
- [Türkisch](/README-tr.md)

## Medium

Für weitere Informationen schauen Sie sich den [Medium](https://mbakgun.medium.com/mj-compose-multiplatform-e6f737b3cd18) Beitrag an.

## Beiträge 👏

Bitte zögern Sie nicht, ein Problem zu eröffnen oder einen Pull Request zu senden - alle Beiträge werden geschätzt! Im Voraus vielen Dank.

## Kontakt

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