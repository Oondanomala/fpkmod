# FPK Mod

An open source replacement for [MPK Mod](https://github.com/kurrycat2004/MpkMod), [MPK Mod 2](https://github.com/kurrycat2004/MPKMod_2), and [CyvClient](https://github.com/Morpheye/CyvClient),
for 1.8.9 forge (a 1.12.2 port is planned).

Adds a bunch of useful parkour features to the game. Client side only!

While it is intended as a replacement, it should work alongside MPK.

## Current Features

- Most labels and commands from MPK, and a little more (see tables below)
- Landing blocks

Landing blocks are massively improved over MPK
(way better wall calculations, better `setlb` command, float precise offsets, etc...)

The current priority is to add all MPK and CyvClient features, then to add new stuff.

### Labels

Labels show useful information on screen.<br>
You can edit their position and visibility using `/fpk gui`.

| Label            | Description                                                                                                                 |
|------------------|-----------------------------------------------------------------------------------------------------------------------------|
| Version          | The mod and game version                                                                                                    |
| FPS              | The current FPS                                                                                                             |
| X                | The player's position on the `X` axis                                                                                       |
| Y                | The player's position on the `Y` axis                                                                                       |
| Z                | The player's position on the `Z` axis                                                                                       |
| F                | The player's yaw. Also shows the axis the player is facing                                                                  |
| Raw Yaw          | The raw, unmodified yaw value                                                                                               |
| Pitch            | The player's pitch                                                                                                          |
| Date             | The current date                                                                                                            |
| Time             | The current time                                                                                                            |
| IP               | The current server IP                                                                                                       |
| Jump Angle       | The yaw on jump tick                                                                                                        |
| Jump X           | The `X` position on jump tick                                                                                               |
| Jump Y           | The `Y` position on jump tick                                                                                               |
| Jump Z           | The `Z` position on jump tick                                                                                               |
| Hit Angle        | The yaw on hit tick                                                                                                         |
| Hit X            | The `X` position on hit tick                                                                                                |
| Hit Y            | The `Y` position on hit tick                                                                                                |
| Hit Z            | The `Z` position on hit tick                                                                                                |
| Last Landing X   | The `X` position on the tick before hit tick                                                                                |
| Last Landing Y   | The `Y` position on the tick before hit tick                                                                                |
| Last Landing Z   | The `Z` position on the tick before hit tick                                                                                |
| Speed X          | The speed on the `X` axis                                                                                                   |
| Speed Y          | The speed on the `Y` axis                                                                                                   |
| Speed Z          | The speed on the `Z` axis                                                                                                   |
| Speed Vector     | The [speed vector][vector], shown as magnitude/angle                                                                        |
| Max Speed X      | The highest speed achieved on the `X` axis. Resets when the movement direction changes, or when `/fpk clearmaxspeed` is run |
| Max Speed Y      | The highest speed achieved on the `Y` axis. Resets when the movement direction changes, or when `/fpk clearmaxspeed` is run |
| Max Speed Z      | The highest speed achieved on the `Z` axis. Resets when the movement direction changes, or when `/fpk clearmaxspeed` is run |
| Max Speed Vector | The [vector][vector] of the highest speed achieved, shown as magnitude/angle                                                |
| Last Input       | The current `WASD` input                                                                                                    |
| Jump Input       | The `WASD` input on jump tick                                                                                               |
| Last Turning     | The size of the turn made on the last tick                                                                                  |
| Preturn          | The size of the turn made on the tick before jump tick                                                                      |
| Last 45          | The size of the turn made on the tick after jump tick, if the player started strafing                                       |
| Airtime          | The amount of ticks the player has been in the air for                                                                      |
| Tier             | The [jump tier][tiers]                                                                                                      |
| Grinds           | The amount of [grinds][grinds] chained                                                                                      |
| Keystrokes       | The current `WASD`, `sprint`, `jump`, and `sneak` input in a familiar looking interface                                     |
| Togglesprint     | Whether togglesprint is enabled or disabled                                                                                 |

[vector]: https://en.wikipedia.org/wiki/Vector_(mathematics_and_physics)
[tiers]: https://www.mcpk.wiki/wiki/Tiers
[grinds]: https://www.mcpk.wiki/wiki/Jump_Cancel#Ceiling_Variant

### Commands

Some commands have a keybind shortcut (unbound by default).<br>
Certain commands can also be run by right-clicking signs with the command and command arguments on them.

| Command                                                                      | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              |
|------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `/fpk`                                                                       | Same as running `help`                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   |
| `/fpk help`                                                                  | Sends a list of all commands and their help text in the chat                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             |
| `/fpk config`                                                                | Opens the main config GUI                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                |
| `/fpk reloadconfig`                                                          | Reloads the config file                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  |
| `/fpk gui`                                                                   | Opens the label config GUI                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               |
| `/fpk df <precision>`                                                        | Changes the decimal precision. Range from 0 to 16                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        |
| `/fpk setlb [target\|below\|x y z] [land\|zneo\|enter\|hit] [box] [x\|z\|~]` | Sets the landing block. The first argument specifies what block to select (defaults to `target`). `target` will select the block the player is looking at, `below` will select the block the player is standing on, and `x y z` will select the block at said coordinates. The second argument specifies the landing mode to use (defaults to `land`). The third argument specifies whether to use `box` mode. The last argument specifies the axis the offsets should use. Arguments after the first can go in any order. See more in [Landing Blocks](#Landing-Blocks) |
| `/fpk clearlb`                                                               | Clears the landing block                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 |
| `/fpk clearpb`                                                               | Clears the landing block PB                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              |
| `/fpk setcond <minX> <maxX> <minZ> <maxZ>`                                   | Sets the landing block condition box to the provided coordinates. The Y coordinate is reused from the previous condition box                                                                                                                                                                                                                                                                                                                                                                                                                                             |
| `/fpk toggleall`                                                             | Toggles visibility of all labels                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         |
| `/fpk resetlabels`                                                           | Resets all labels to their default positions                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             |
| `/fpk clearmaxspeed`                                                         | Resets the max speed labels back to 0                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    |
| `/fpk fly`                                                                   | Enables/Disables flight when in creative mode                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            |
| `/fpk togglesprint`                                                          | Enables/Disables togglesprint                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            |
| `/fpk tp <x> <y> <z> [yaw] [pitch]`                                          | Improved version of /tp. Allows teleporting to saved coordinates if no arguments are given                                                                                                                                                                                                                                                                                                                                                                                                                                                                               |
| `/fpk coords <save\|copy\|clear>`                                            | Manages the player coordinates. Allows copying the current coordinates to the clipboard and saving said coordinates for use with `/fpk tp`                                                                                                                                                                                                                                                                                                                                                                                                                               |

### Landing Blocks

Landing blocks let you see how much you missed/made by on a jump.<br>
You can set one on a block with the `/fpk setlb` command, or with the command keybind,
and edit its properties in the `/fpk lb` GUI menu.

The landing block GUI has 5 buttons:

- `Land Mode` lets you change the block's landing mode
- `Axis` lets you change what axis the offsets should use
- `Render LB` lets you choose whether to render the landing block in the world
- `Render Cond` lets you choose whether to render the condition box in the world
- `Recalculate Walls` will recalculate the walls around the landing block, useful if they change

The landing mode specified changes how an offset is calculated
and different modes are useful for different types of jumps:

- `Land` is the default and should work fine for most jumps
- `Z Neo` is useful for `Z` facing neos where you can slide off a wall next to the landing block
- `Enter` is useful for climbing ladders or vines (or any other climbable block)
- `Hit` is useful for bouncing on slimes

A landing block created with `box` mode will get the offset from the player coordinates instead of using the player hitbox,
meaning it will check whether the player is "inside" a block instead of simply being able to land on it.
This is useful for climbing ladders/vines and bouncing on slimes.

## Planned Features

- MPK Labels
  - Blip
  - Last Timing
  - All the LB labels
- Cyv Labels
  - Last Sidestep
- Input and turn history
- Multiple landing blocks
- AntiCP
- Macro
- Some kind of [jump/strat](https://github.com/kurrycat2004/MpkMod/issues/86) manager thing
- Stratreminders
- Something something mothball?
- 1.12.2 version (not happening soon)

Please make a feature request if there's something you want that isn't in this list!

## Credits

Thanks to [kurrycat](https://github.com/kurrycat2004) for the original [MPK Mod](https://github.com/kurrycat2004/MpkMod) and [MPK Mod 2](https://github.com/kurrycat2004/MPKMod_2);
and to [Morpheye](https://github.com/Morpheye) for [CyvForge](https://github.com/Morpheye/CyvForge),
which were very helpful resources for making this!
