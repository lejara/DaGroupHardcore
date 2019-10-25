# LaGroupHardcore 1.1
A spigot server plugin

Minecraft group hardcore, with a shared pool of lives, and a day timer for all players on your server!

Days and Lives are displayed on a scorebroad.

## Objective

Your goal is to kill the ender dragon before you run out of days or lives! 

Or else the world will end!

### When lives or days are out...

All players will be kicked, server will lockout any players that try to join the server

There is a world end event that casuse tnt, lava and mobs to spawn to each player at a interval. Then drops all players to the abyss

*NOTE: The world end event is set to off by defualt since it may cuase lag to the server. Command to turn it on is [here](https://github.com/lejara/LaGroupHardcore/blob/master/README.md#setworldend-0--1)*


## Commands

### /setlives [number]

Set the number of lives.

### /setdays [number]

Set the number of days.

### /reset

Resets plugin back to defualt. Can be used to reset a world that failed the hardcore.

Note: this will not roll back any block changes casued by the world end event.
   
### /setworldend [0 | 1]

Sets the world end event to happend when you fail the hardcore.

Beware: TNT explosions, Lava, Ghast Explosions, Players spawn point will be change to the abyss

Note: this may cuased server lag! The world end event compute complexity scales based on how many players are in your server!

### /resetplayerspawn

Resets all online players spawn point to the world spawn point.

Useful to fix the spawn point changes casued by the world end event.

