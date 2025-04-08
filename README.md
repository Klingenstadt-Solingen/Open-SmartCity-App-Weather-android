<div style="display:flex;gap:1%;margin-bottom:20px">
  <h1 style="border:none">Open SmartCity Weather Module of the Open SmartCity App</h1>
  <img height="100px" alt="logo" src="logo.svg">
</div>

## Important Notice

- **Read-Only Repository:** This GitHub repository is a mirror of our project's source code. It is not intended for direct changes.
- **Contribution Process:** Once our Open Code platform is live, any modifications, improvements, or contributions must be made through our [Open Code](https://gitlab.opencode.de/) platform. Direct changes via GitHub are not accepted.

---

- [Important Notice](#important-notice)
- [Changelog 📝](#changelog-)
- [License](#license)

<div style="text-align:center">
<img src="https://img.shields.io/badge/Platform%20Compatibility%20-android-green">
<img src="https://img.shields.io/badge/Kotlin%20Compatibility%20-1.6.0-blue">
<img src="https://img.shields.io/badge/Dokka-active">
</div>


<div style="text-align:center">
<img src=".gitkeep/images/weather.png" width="300px">
</div>


## Features
- [x] WeatherScreen
- [x] Design components: WeatherInfoCard
- [x] WeatherApiService
- [x] Data classes: Weather, WeatherData, WindData
- [x] Hilt Dependency Injection module: WeatherModule
- [ ] Unit tests
- [x] Navigation items: WeatherNavItems

## Requirements

- Android 8 Oreo + (SDK 26+)
- Kotln 1.6.0

### Installation

On your app's `build.gradle`:
```kotlin
...
    dependencies {
        implementation "de.osca.android.weather:weather:$weather_version"
    }
...

```

Make sure to include location permissions on your Manifest:
```
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
```

## Other
### Developments and Tests

Any contributing and pull requests are warmly welcome. However, before you plan to implement some features or try to fix an uncertain issue, it is recommended to open a discussion first. It would be appreciated if your pull requests could build and with all tests green.

## Changelog 📝

Please see the [Changelog](CHANGELOG.md).

## License

OSCA Server is licensed under the [Open SmartCity License](LICENSE.md).
