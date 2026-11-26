# MẸ LAN ANH

A lightweight client-side addon framework for Minecraft 1.21.4, Fabric, and Meteor Client.

## Included
- Addon category registration.
- Configurable Info HUD with independent toggles for coordinates, FPS, and dimension.
- Text-shadow toggle.
- HUD editor support for placement and scale.
- GitHub Actions build workflow.

## Build
Requires Java 21 and Gradle 8.12+.

```sh
gradle build
```

The JAR is generated under `build/libs`.

## Install
1. Use Minecraft 1.21.4 with Fabric Loader and Meteor Client.
2. Copy the addon JAR into the instance's `mods` folder.
3. In Meteor HUD settings, add **MELANANH Info**.
4. Toggle the HUD rows and move it using the HUD editor.

This project is currently a foundation/UI utility addon; PvP combat automation, raid/base targeting, and anti-cheat bypass features are not included.
