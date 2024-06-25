# FPK Mod

An open source replacement for [MPK Mod](https://github.com/kurrycat2004/MpkMod), [MPK Mod 2](https://github.com/kurrycat2004/MPKMod_2), and [CyvClient](https://github.com/Morpheye/CyvClient).

Adds a bunch of useful parkour features to the game. Client side only!

While it is intended as a replacement, it should work with MPK.

## Current Features

Most labels and commands from MPK (see tables below).

The current priority is to add all MPK and CyvClient features, then to add new stuff.

### Labels

Labels show useful information on screen.<br>
You can edit their position and visibility using `/fpk gui`.

| Label          | Description                                                                           |
|----------------|---------------------------------------------------------------------------------------|
| Version        | The mod and game version                                                              |
| FPS            | The current FPS                                                                       |
| X              | The player's position on the `X` axis                                                 |
| Y              | The player's position on the `Y` axis                                                 |
| Z              | The player's position on the `Z` axis                                                 |
| F              | The player's yaw. Also shows the axis the player is facing                            |
| Pitch          | The player's pitch                                                                    |
| Date           | The current date                                                                      |
| Time           | The current time                                                                      |
| IP             | The current server IP                                                                 |
| Jump Angle     | The yaw on jump tick                                                                  |
| Jump X         | The `X` position on jump tick                                                         |
| Jump Y         | The `Y` position on jump tick                                                         |
| Jump Z         | The `Z` position on jump tick                                                         |
| Hit Angle      | The yaw on hit tick                                                                   |
| Hit X          | The `X` position on hit tick                                                          |
| Hit Y          | The `Y` position on hit tick                                                          |
| Hit Z          | The `Z` position on hit tick                                                          |
| Last Landing X | The `X` position on the tick before hit tick                                          |
| Last Landing Y | The `Y` position on the tick before hit tick                                          |
| Last Landing Z | The `Z` position on the tick before hit tick                                          |
| Speed X        | The speed on the `X` axis                                                             |
| Speed Y        | The speed on the `Y` axis                                                             |
| Speed Z        | The speed on the `Z` axis                                                             |
| Speed Vector   | The [speed vector][vector], shown as magnitude/angle                                  |
| Last Input     | The current `WASD` input                                                              |
| Jump Input     | The `WASD` input on jump tick                                                         |
| Last Turning   | The size of the turn made on the last tick                                            |
| Preturn        | The size of the turn made on the tick before jump tick                                |
| Last 45        | The size of the turn made on the tick after jump tick, if the player started strafing |
| Airtime        | The amount of ticks the player has been in the air for                                |
| Tier           | The [jump tier][tiers]                                                                |
| Grind          | The amount of [grinds][grinds] chained                                                |

[vector]: https://en.wikipedia.org/wiki/Vector_(mathematics_and_physics)
[tiers]: https://www.mcpk.wiki/wiki/Tiers
[grinds]: https://www.mcpk.wiki/wiki/Jump_Cancel#Ceiling_Variant

### Commands

Some commands have a keybind shortcut (unbound by default).

| Command               | Description                                                  |
|-----------------------|--------------------------------------------------------------|
| `/fpk`                | Same as running `help`                                       |
| `/fpk help`           | Sends a list of all commands and their help text in the chat |
| `/fpk config`         | Opens the main config GUI                                    |
| `/fpk reloadconfig`   | Reloads the config file                                      |
| `/fpk gui`            | Opens the label config GUI                                   |
| `/fpk df <precision>` | Changes the decimal precision. Range from 0 to 16            |
| `/fpk toggleall`      | Toggles visibility of all labels                             |
| `/fpk resetlabels`    | Resets all labels to their default positions                 |
| `/fpk fly`            | Enables/Disables flight when in creative mode                |
| `/fpk togglesprint`   | Enables/Disables togglesprint                                |

## Planned Features

- MPK Labels
  - Blip
  - Last Timing
  - Max Speed and Max Speed Vector
  - All the LB labels
  - Keystrokes
- Cyv Labels
  - Last Sidestep
- Unused labels GUI
- Landing blocks (multiple?)
- AntiCP
- Macro
- Some kind of [jump/strat](https://github.com/kurrycat2004/MpkMod/issues/86) manager thing
- Stratreminders
- Something something mothball?
- 1.12.2 version (not happening soon)

Please make feature requests if there's something you want that isn't in this list!

## Credits

Thanks to [kurrycat](https://github.com/kurrycat2004) for the original [MPK Mod](https://github.com/kurrycat2004/MpkMod) and [MPK Mod 2](https://github.com/kurrycat2004/MPKMod_2);
and to [Morpheye](https://github.com/Morpheye) for [CyvFabric](https://github.com/Morpheye/CyvFabric),
which were very helpful resources for making this!
