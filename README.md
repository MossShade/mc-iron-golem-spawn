# Iron Golem Spawn Checker
A client-side Minecraft Fabric mod to show an overlay where Iron Golems can spawn.  
This is helpful when building iron farms or villager farms in order to see exactly where
the game checks for Iron Golem spawns.

## Requirements
- Fabric API

## Installation
- Install [Fabric mod loader](https://fabricmc.net/use/installer/)
- Download Fabric API
- Download this mod's Jar file
- Copy both Fabric API and this Jar to your minecraft `mods` folder

## Usage
The mod will use the in-game code to check Iron Golem spawns in a 17×13×17 box centered around the player.

Left bracket `[`: calculates a new bounding box around the player and checks for spawns.  
Right bracket `]`: Hides the overlay.

## Issues
Since the mod uses the in-game logic to check for spawns, the following issue occurs:  
- When a mob/entity intersects a block that a spawn can occur on, it will not be highlighted by the overlay.  
This issue will be fixed in a later update. 

## TODO
- [ ] Allow rebinding of keys through config
- [ ] Support Mod menu
- [ ] Add option to ignore mob collision
