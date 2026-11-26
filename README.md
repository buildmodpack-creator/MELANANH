# MẸ LAN ANH (melananh)

Client-side addon for Minecraft **1.21.4**, Fabric and Meteor Client.

## Implemented
- [x] Meteor addon entrypoint and category registration
- [x] Lightweight info HUD: block coordinates, client FPS, and dimension
- [x] HUD settings for dimension visibility and text shadow
- [x] GitHub Actions build workflow

## Build and install
Requires Java 21 and Gradle 8.12+.

```sh
gradle build
```

The build produces JAR files under `build/libs/`. Copy the regular `melananh-<version>.jar` into the Meteor addons/mods location used by your launcher. The `-sources.jar` file is source code and is not the installable addon.

## HUD
After loading the addon, open Meteor's HUD editor and add **MELANANH Info**. It displays:
- Current block coordinates (X/Y/Z)
- Client FPS
- Current dimension (optional)

The HUD is lightweight and avoids scanning entities or chunks.
