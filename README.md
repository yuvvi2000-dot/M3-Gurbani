# Gurbani UI Prototype

A standalone, single-module Compose sandbox for testing the **floating glass
chrome** described in `docs/spec/design-spec.md` before committing that
pattern across the real app. It is deliberately not part of the modular
architecture in `docs/spec/architecture.md` — no Hilt, no Room, no real
Gurbani data, just placeholder text — so it's fast to open and iterate on.

## What it demonstrates
- Edge-to-edge scrollable content behind inset, floating, blurred chrome
  (`FloatingTopBar`, `FloatingBottomBar`) using the [Haze](https://github.com/chrisbanes/haze)
  library for the blur-behind effect (real blur on API 31+, graceful
  tonal-surface degradation below it, matching `design-spec.md`'s "Blur approach").
- Pill nav bar with an under-damped spring-animated selection indicator +
  haptic tick on selection.
- The voice-follow FAB's circle → rounded-square shape morph (hero moment #1
  from `design-spec.md`), paired with a stronger "confirm" haptic.
- `MaterialExpressiveTheme` with dynamic color (Android 12+) and a fallback
  brand palette otherwise.
- Respecting the system's reduced-motion setting (`rememberReduceMotion()`),
  swapping bouncy springs for fast, non-bouncy ones.

## What it does NOT demonstrate yet
- Real Gurbani text/translations (placeholder lines only).
- The scallop-burst live-highlight animation or actual voice recognition
  (tapping the FAB just toggles a boolean — see `voice-follow-spec.md` for
  the real alignment design).
- Theme packs / paywall (see `theming-spec.md`, `monetization-spec.md`).
- Low-end-device perf validation of the blur — this needs to be run on a
  real mid/low-range device or a throttled emulator profile, not just judged
  on a dev machine's emulator.

## Running it locally

The Gradle wrapper (`./gradlew`, `gradle-wrapper.jar`) is committed, so no
local Gradle install is required — but you do need a JDK 17 and the Android
SDK (easiest via Android Studio).

1. Open the `android-ui-prototype/` folder in **Android Studio** (Koala or
   newer, since Material3 Expressive needs a recent Compose/Kotlin toolchain).
2. Run the `app` configuration on an emulator or device running **API 29+**
   (min SDK per the spec's assumption). To see real `RenderEffect` blur
   rather than the tonal fallback, use an **API 31+** emulator image.
3. Run the included smoke test: `./gradlew connectedAndroidTest` (or right-click
   `FloatingBottomBarTest.kt` in Android Studio → Run), which verifies the
   nav bar composes and responds to taps. It does not assert on visuals —
   eyeball the actual blur/spring/haptic feel on-device, that's the point of
   this prototype.

## Running the instrumented test on GitHub Actions (free, no local setup)

If you don't want to install Android Studio/SDK/JDK locally at all,
`.github/workflows/instrumented-test.yml` builds the app and runs
`FloatingBottomBarTest` on a real Android emulator spun up directly on a
GitHub-hosted runner — no Firebase/Google Cloud account needed.

1. Push this project to a GitHub repo (a **public** repo gets unlimited free
   Actions minutes; private repos get 2,000 free minutes/month, which is
   plenty for occasional runs of this one test).
2. Go to the repo's **Actions** tab → "Instrumented UI test (GitHub-hosted
   emulator)" → **Run workflow**, or just push to `main` / open a PR — the
   workflow triggers automatically.
3. It boots an API 31 emulator (so blur runs in real `RenderEffect` mode),
   runs the test, and uploads the HTML test report as a downloadable
   artifact on the workflow run's summary page.
4. First run takes a few minutes longer (building the AVD snapshot cache);
   subsequent runs reuse the cached snapshot and are faster.

Nothing beyond pushing to GitHub is required — no secrets, no external
accounts.

## Before treating this as production code
Dependency versions here (`material3:1.4.0-alpha14`, `haze:1.5.2`, AGP/Kotlin
versions) were current at time of writing and **should be re-checked**
against the latest releases before this pattern is carried into the real
`:core-ui` module — Material3 Expressive in particular is still moving
through alpha releases.
