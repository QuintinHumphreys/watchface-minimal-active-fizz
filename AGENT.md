# Minimal Active Fizz watch face - agent setup + install guide

You are an AI coding agent. Your job: install the **Minimal Active Fizz** watch face onto the
user's **Wear OS 4+ watch** (built/tested on a Google **Pixel Watch 4**). Follow
the FAST PATH unless the user wants to rebuild from source.

This face is Watch Face Format (WFF) - declarative XML packaged in an Android
APK. A prebuilt debug-signed APK is included (`active-fizz.apk`), so you normally do
NOT need a JDK, Android Studio, or Gradle - just `adb`.

- Package id / watchFaceId: `com.quintinhumphreys.watchfaces.activefizz`
- Requires: Wear OS 4 or newer (WFF format v2). Round display, 456x456.

--------------------------------------------------------------------------------
## FAST PATH - install the prebuilt APK (no build)

### 1. Get `adb` (Android platform-tools) on the computer

You need a reasonably RECENT adb (v34+; needed for wireless pairing). If `adb
version` shows something older than ~34, replace it.

- Windows: download and unzip
  `https://dl.google.com/android/repository/platform-tools-latest-windows.zip`
  and use `platform-tools\adb.exe`. (Or `winget install Google.AndroidPlatformTools`.)
- macOS: `brew install --cask android-platform-tools`
- Linux: `sudo apt-get install android-tools-adb` (or the platform-tools zip:
  `https://dl.google.com/android/repository/platform-tools-latest-linux.zip`).

Verify: `adb version`.

Gotcha: if `adb pair` later fails with "unknown host service" or "protocol
fault", an OLD adb server is running. Run `adb kill-server` then `adb
start-server` using the NEW adb, and make sure the new adb is first on PATH.

### 2. Put the watch into Wireless debugging

Ask the user to do this on the watch (you cannot):
1. Ensure the watch and this computer are on the SAME Wi-Fi network.
2. Settings -> System -> About -> tap **Build number** 7 times (enables
   Developer options).
3. Settings -> Developer options -> turn on **Wireless debugging**, then open it.
4. Note the **"IP address & Port"** shown (this is the CONNECT address, e.g.
   `10.0.0.97:38797`).
5. Tap **"Pair new device"** -> it shows a 6-digit **pairing code** and a
   DIFFERENT `IP:port` (the PAIR address). Keep this dialog open (it expires).

Ask the user to give you: the PAIR address, the pairing code, and the CONNECT
address.

### 3. Pair, connect, install

```
adb pair <PAIR_IP:PORT> <CODE>
adb connect <CONNECT_IP:PORT>
adb devices                      # confirm the watch shows as "device"
adb -s <CONNECT_IP:PORT> install -r active-fizz.apk
```

### 4. Set it as the active watch face

```
adb -s <CONNECT_IP:PORT> shell am broadcast \
  -a com.google.android.wearable.app.DEBUG_SURFACE \
  --es operation set-watchface \
  --es watchFaceId 'com.quintinhumphreys.watchfaces.activefizz'
```
A `result=1` line means it worked. If that broadcast is unavailable, tell the
user to long-press the current face and pick **Minimal Active Fizz** from the carousel.

### 5. Grant sensor/activity permissions (for live heart rate + steps)

```
adb -s <CONNECT_IP:PORT> shell pm grant com.quintinhumphreys.watchfaces.activefizz android.permission.BODY_SENSORS
adb -s <CONNECT_IP:PORT> shell pm grant com.quintinhumphreys.watchfaces.activefizz android.permission.ACTIVITY_RECOGNITION
```

### 6. Confirm

Ask the user to raise their wrist. Expected: big digital time, a date under the
weather at top, and four complications - weather (temperature, top), steps
(left), heart rate (right), battery (bottom) - with a blue/orange particle
"fizz" along two side arcs. Heart rate and steps populate on-wrist; weather
comes from the system weather provider. Tapping heart opens the heart-rate view,
tapping battery opens battery settings, tapping steps opens the step provider.

Done.

--------------------------------------------------------------------------------
## OPTIONAL - build from source

Source is in `watchface/` (a single-module Gradle project with the wrapper).

Prereqs: **JDK 17** (e.g. Eclipse Temurin 17) and the **Android SDK**
command-line tools with `platforms;android-34` and `build-tools;34.0.0` (the
Gradle wrapper downloads Gradle + the Android Gradle Plugin itself). Set
`JAVA_HOME` to the JDK 17 and `ANDROID_HOME` to the SDK.

```
cd watchface
./gradlew assembleDebug          # Windows: .\gradlew.bat assembleDebug
# APK: watchface/build/outputs/apk/debug/*.apk  -> install as in the FAST PATH
```

To modify the design, edit `watchface/src/main/res/raw/watchface.xml`
(declarative WFF; plain ASCII only). If you have the WFF validator JAR from the
`google/watchface` releases, validate with:
`java -jar wff-validator.jar 2 watchface/src/main/res/raw/watchface.xml`.

--------------------------------------------------------------------------------
## Notes / troubleshooting

- Nothing shows / screenshot is black: the watch is dozing; wake it
  (`adb -s <dev> shell input keyevent KEYCODE_WAKEUP`) or raise the wrist.
- Heart rate shows "--": the watch is off-wrist or on the charger (no reading).
- Weather blank: it needs the system weather provider / a location; it can take a
  moment after first setup.
- This is a debug-signed build for personal sideloading, not a Play Store
  release.
