# LaGroupHardcore
A spigot server plugin

Minecraft group hardcore, with a shared pool of lives, and a day timer for all players 

## Objective
You have a set number of lives and days shared with all players.

Your goal is to kill the ender dragon before you run out of days or lives! 

Or else the world will end!

### On Failed
When lives or days are out...

The world will end and all players will be kicked.

There is a world end event that casuse tnt, lava and mobs to spawn to each player at a interval.

NOTE: The world end event is set to off by defualt since it may cuase lag to the server. Command to turn it on is down below


## Commands

### /setlives [number]

Set the number of lives.

### /setdays [number]

Set the number of days.

### /reset

Resets plugin back to defualt. Can be used to reset a world that failed the hardcore.

Note: this will not roll back any block changes casued by the world end event.
   
### /setworldend [0 | 1]

Sets the world end event to happend when you failed the hardcore.

Beware: TNT explosions, Lava, Ghast Explosions, Players spawn point will be change to the abyss

Note: this may cuased server lag! The world end event compute complexity scales based on how many players are in your server!

### /resetplayerspawn

Resets all online players spawn point to the world spawn point.

Useful to fix the spawn point changes casued by the world end event.

