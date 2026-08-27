# Minimal Active Fizz - a Wear OS watch face

A minimal digital watch face for Wear OS 4+ (built and tested on the Google
Pixel Watch 4/5). Big stacked digital time, a date under a weather circle, and
four reassignable complications - top, left, right, bottom (default weather,
steps, heart rate, battery) - framed by two data-bound arcs (steps and heart
rate) with a subtle blue/orange particle "fizz". Fully color-free, burn-in-safe
ambient mode.

All four circles are editable: long-press the face -> edit -> tap a circle to
assign any complication. Whatever provider you pick keeps the face's styling -
its icon is recolored to the circle's colour (blue/orange) and shown inside our
thin ring. The top circle shows live weather until you assign something else.

![active](preview/watch-active.png)
![ambient](preview/watch-ambient.png)

## How to install - pick the guide that fits you

A prebuilt, debug-signed APK (`active-fizz.apk`) is included, so no building is
required either way - you just need `adb`.

- **Installing it yourself (a person):** follow **[`INSTALL.md`](INSTALL.md)**.
  It walks you through installing `adb`, turning on Wireless debugging, and the
  exact commands, with troubleshooting.
- **Letting an AI coding agent do it:** hand the agent this folder (or the
  release URL) and say *"Follow AGENT.md to install this watch face on my
  Pixel Watch."* See **[`AGENT.md`](AGENT.md)**.

Either way it ends the same: pick **Minimal Active Fizz** from the watch-face
carousel (long-press the current face).

## Contents

- `active-fizz.apk` - prebuilt, debug-signed. Sideload to a Wear OS 4+ watch.
- `INSTALL.md` - step-by-step install guide for a **person**.
- `AGENT.md` - install instructions written for an **AI coding agent**.
- `watchface/` - the full source (Watch Face Format XML + Gradle project) if you
  want to rebuild or customize.
- `preview/` - screenshots (active + ambient).

## Notes

- Debug-signed for personal sideloading; not a Play Store build.
- Defaults: heart rate and steps populate on-wrist (grant BODY_SENSORS +
  ACTIVITY_RECOGNITION, per AGENT.md); battery is the system battery; the top
  shows live system weather.
- All four circles are reassignable in the watch-face editor (long-press ->
  edit -> tap a circle). The assigned provider's icon is recolored to the
  circle's colour and framed by the thin ring; tapping a circle opens that
  provider.
