# Install Minimal Active Fizz on your Wear OS watch (human guide)

This is the step-by-step guide for a person installing the watch face by hand.
If instead you want an AI coding agent to do it for you, hand it `AGENT.md`.

A prebuilt, debug-signed app (`active-fizz.apk`) is included, so you do NOT need
Android Studio, a JDK, or Gradle - just one tool called `adb`.

Works on any Wear OS 4+ watch (built and tested on the Google Pixel Watch 4/5).
You need the watch and a computer on the SAME Wi-Fi network.

--------------------------------------------------------------------------------
## What you need

- Your watch (Wear OS 4 or newer) and a Windows, macOS, or Linux computer.
- Both on the same Wi-Fi network.
- One piece of software on the computer: `adb` (Android Debug Bridge), which
  comes in Google's "platform-tools" package. Version 34 or newer.

--------------------------------------------------------------------------------
## Step 1 - Install adb (Android platform-tools)

Pick your operating system.

### Windows
Option A (easiest, if you have winget):
```
winget install Google.AndroidPlatformTools
```
Option B (manual): download and unzip this file, then use the `adb.exe` inside:
`https://dl.google.com/android/repository/platform-tools-latest-windows.zip`

### macOS
With Homebrew (https://brew.sh):
```
brew install --cask android-platform-tools
```

### Linux
Debian/Ubuntu:
```
sudo apt-get install android-tools-adb
```
Or download the zip and use the `adb` inside:
`https://dl.google.com/android/repository/platform-tools-latest-linux.zip`

### Check it works
Open a terminal (Windows: PowerShell or Command Prompt) and run:
```
adb version
```
You should see "Android Debug Bridge version 34..." or higher. If the command
is not found, make sure the folder containing `adb` is on your PATH, or `cd`
into that folder first.

--------------------------------------------------------------------------------
## Step 2 - Turn on Wireless debugging on the watch

On the WATCH (you have to do this part yourself):

1. Make sure the watch is on the SAME Wi-Fi as your computer.
2. Open Settings -> System -> About, and tap **Build number** 7 times. This
   unlocks "Developer options" (you will see a "you are now a developer" toast).
3. Go back to Settings -> Developer options and turn on **Wireless debugging**,
   then tap into it to open its screen.
4. Note the **"IP address & Port"** shown here - for example `10.0.0.50:38797`.
   This is the CONNECT address.
5. Tap **"Pair new device"**. It shows a 6-digit **pairing code** and a
   DIFFERENT `IP:port` (the PAIR address). Leave this screen open - the code
   expires after a minute or two.

--------------------------------------------------------------------------------
## Step 3 - Pair and connect from the computer

In your terminal, run these (substitute the addresses/code from your watch).
Use the PAIR address + code first, then the CONNECT address:
```
adb pair 10.0.0.50:37000 123456
adb connect 10.0.0.50:38797
adb devices
```
`adb devices` should list your watch's connect address followed by `device`.

Tip: if `adb pair` says "unknown host service" or "protocol fault", an old adb
is running. Run `adb kill-server` then `adb start-server` and try again.

--------------------------------------------------------------------------------
## Step 4 - Install the watch face

From the folder that contains `active-fizz.apk` (the CONNECT address is the one
from Step 2, e.g. `10.0.0.50:38797`):
```
adb -s 10.0.0.50:38797 install -r active-fizz.apk
```
You should see `Success`.

--------------------------------------------------------------------------------
## Step 5 - Select it as your watch face

Easiest: on the watch, press and hold the current watch face, then swipe through
the carousel and pick **Minimal Active Fizz**.

Or from the computer:
```
adb -s 10.0.0.50:38797 shell am broadcast -a com.google.android.wearable.app.DEBUG_SURFACE --es operation set-watchface --es watchFaceId "com.quintinhumphreys.watchfaces.activefizz"
```
A line containing `result=1` means it worked.

--------------------------------------------------------------------------------
## Step 6 - Allow heart rate and steps (one-time)

So the heart-rate and step readouts work, grant two permissions:
```
adb -s 10.0.0.50:38797 shell pm grant com.quintinhumphreys.watchfaces.activefizz android.permission.BODY_SENSORS
adb -s 10.0.0.50:38797 shell pm grant com.quintinhumphreys.watchfaces.activefizz android.permission.ACTIVITY_RECOGNITION
```

--------------------------------------------------------------------------------
## Using and customising the face

- Big stacked digital time, date + weather at the top, and four data circles:
  weather (top), steps (left), heart rate (right), battery (bottom).
- All four circles are reassignable: press and hold the face -> tap the pencil
  (Edit) -> tap a circle -> choose any complication. Whatever you pick is shown
  in the face's own style (its icon recolored to the circle's colour).
- Tapping a circle opens that data source's app.

--------------------------------------------------------------------------------
## Troubleshooting

- **`adb devices` shows the watch as `offline`, or connect fails**: the watch's
  wireless port changes when it sleeps or you toggle the setting. Re-open
  Wireless debugging on the watch, read the new "IP address & Port", and run
  `adb connect <new ip:port>` again. If it still fails, `adb kill-server` then
  `adb start-server`.
- **Screen looks blank / black in a screenshot**: the watch was asleep; raise
  your wrist or tap the screen.
- **Heart rate shows "--"**: the watch is off your wrist or on the charger, so
  there is no reading.
- **Weather is blank**: it needs the system weather provider and a location; it
  can take a few minutes after first setup.
- This is a debug-signed build for personal sideloading, not a Play Store
  release, so your watch may warn about installing from an unknown source -
  that is expected.
