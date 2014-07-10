well-core
=========

*Well done plugins core for your Minecraft server*

This plugin contains core components of "well-" plugins suite. Doesn't do anything by itself.

<br/>


### Hello, I'm required!

Please include well-core if you want to use at least one of the following plugins:

* [well-auction](http://dev.bukkit.org/bukkit-plugins/well-auction/)

<br/>

## Features

### Easy plugin resources system

If you provide a default config resource file to your users, and you'd like them to have this file in their data folder and like it to have at least all the (required) keys you specified, `WellConfig` will be your best friend.

At first, on first plugin run, `WellConfig` will copy your resource file (which has to be UTF-8 encoded) in data folder with system's default encoding. This will avoid encoding issues. On next runs, every required key will be checked from user's file and filled in if missing.


### Localization

Please welcome `WellLanguageConfig`, who is `WellConfig`'s cute little daughter. She will user `lang/<language>.yml` file in user's data folder to get localization keys, and use it to fetch messages.

She can colorize them in red/yellow/green if you fetch messages with `error`, `warn` or `success` methods.

Obviously, she will has `WellConfig` benefits, filling missing values from your default conf if necessary.

Additionally, if she doesn't find requested language key, she will fallback into english configuration and attempt to find the message there.


### Permissions

`WellPermissionManager` will make permissions simple.

For example, if you ask him whether a `player` can `build on the ocean` but `permission` isn't granted to this player, `WellPermissionManager` will throw a `WellPermissionException` and send `player` the following (localized) notification:
*You're not allowed to build on the ocean.*

Quite simple, right?


### Yet advanced but simplified database system

Still using `bukkit.yml`'s database credentials (later plugin-specifically configurable), `WellDatabase` wraps Bukkit's database manager and makes it stronger: no more crashes on `/reload`, no more problems with SQLite and `@Entity` relationships, future optimizations to come!

This one makes use of [LennardF1989's MyDatabase](https://forums.bukkit.org/threads/bukkit-persistance-reimplemented-no-code-changes-required.24987/), special thanks to him!


### Database and plugin versions

A plugin can evolve, and this often goes with database changes. This said, you can't expect users to drop their database then let your plugin build it again.

Here comes `WellDao`: just make a Dao for your plugin that will extend `WellDao`. Override `handleVersionChanges` which will handle version changes (surprise!). Then you'll then be able to use `checkVersion` based on you're plugin's version, which will check well-core's database last registered version of your plugin.


### All for now ;)