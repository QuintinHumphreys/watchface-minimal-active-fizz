# watchface/

The WFF project for this face lives here. Generated in Phase 1.

Expected shape (confirm exact keys/paths against current WFF docs; see
`../../../docs/tooling.md`):

```
watchface/
  build.gradle(.kts)
  settings.gradle(.kts)
  src/main/
    AndroidManifest.xml
    res/
      raw/watchface.xml      <- the WFF document
      values/strings.xml     <- face name, descriptions
      drawable*/             <- images / vectors
      xml/watch_face.xml     <- declares the watch face + format version
```

Build, validate, and deploy commands: `../../../docs/tooling.md`.
