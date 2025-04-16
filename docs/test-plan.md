# Plan for Testing the Program

The test plan lays out the actions and data I will use to test the functionality of my program.

Terminology:

- **VALID** data values are those that the program expects
- **BOUNDARY** data values are at the limits of the valid range
- **INVALID** data values are those that the program should reject

---

## Movement

Testing the movement system of my game, to make sure players can move where they should be able to, and not where they shouldn't

### Test Data To Use

- Valid inputs (moving left when there is an unlocked room to the left, down when there is an unlocked room below, etc)
- Invalid inputs:
  - Trying to move into a room that does exist, but is locked
  - Trying to move somewhere that doesn't exist, e.g. off the map or into a wall.

### Expected Test Result

If the input is valid, the player should move into that room, and the game UI should update accordingly. Otherwise, the game should not let the player try to move into a room that isn't unlocked / doesn't exist.

---

## Door Unlocking

As a form of progression, my game will have doors that need to be unlocked with found keys.

### Test Data To Use

- Valid unlock input (using the right key for the room)
- Invalid inputs:
  - Attempting to unlock a room without a key
  - Attempting to unlock the room with another door's key

### Expected Test Result

If the player attempts to unlock the door with the right key, it should unlock, and the UI should change to reflect this. The player should then be able to walk into that room. If the player doesn't have a key, there shouldn't even be an option to unlock the door - this would be confusing to the player. If they do have a key, but it's for another room, the game should let them try to unlock it - a big part of my game is going to be figuring out what items to use where - but it shouldn't actually unlock the room.

---

## Inventory System

Items and an inventory system are going to be an integral part of my game. I want the player to be able to pick up, use, and drop items.

### Test Data To Use

- Attempting to pick up items:
  - With spare slots in the inventory (valid)
  - With only 1 space left in the inventory (boundary case)
  - With a full inventory (invalid)
- Attempting to use items:
  - In the room they are intended to be used in (valid)
  - In rooms other than they were intended to be used in (e.g. trying to use a key on a door that doesn't exist) (invalid)
- Dropping items:
  - With spare space in that room (valid)
  - With only space for 1 item in that room (boundary case)
  - With no space for items in that room (invalid)

### Expected Test Result

- For valid inputs, the item should be picked up / used / dropped as intended. For consumables, the item should be removed from the player's inventory after being used.
- For boundary inputs, the item should be picked up or dropped just as with any other valid input - but the UI should react to show that there is no longer any space left in that room / the player's inventory
- For invalid inputs:
  - If the player tries to use or drop an item they don't have (depending on how I lay out my UI, an example of this type of invalid input could be pressing the 'use item 3' button when the player does not have an item 3), nothing should happen - ideally, the button will also be greyed out to provide a visual cue that it can't be pressed.
  - If the player tries to pick up an item with a full inventory or drop an item into a full room, the game shouldn't let them. As above, the UI should also reflect that this action isn't possible.

---

## Activation system

Another integral part of my game is the scene activation system. Specific items can be used to 'activate' one or more scenes - for example, using the jerry can in the generator room activates some of the rooms surrounding rooms, e.g. by changing their description to reflect that the lights have turned on. Some scenes may have multiple stages of activation, e.g. the control room has 3: Unpowered -> Powered -> Computer enabled. In this case, the control room should only be able to be activated from powered to computer enabled IF the power has been activated.

### Test Data To Use

- Valid inputs, such as:
  - Using fuel in the generators
  - Enabling the control room computer once the power is on
- Invalid inputs, such as:
  - Trying to activate a scene with the wrong item (e.g. trying to use a keycard to power the generators)
  - Trying to activate a scene that can't be activated (e.g. most hallways - these don't have an activator)
  - Trying to activate a scene with the right item, but when that scene isn't ready to be activated (e.g. trying to enable the control room computer before the power has been turned on)
Note that the above inputs, while invalid in terms of game progression, aren't actually invalid to the player - a big part of my game is finding items and figuring out where they can be used, so it is intentional that a new player would make some of these 'invalid' moves.

### Expected Test Result

- For valid inputs, the scene (and any scenes said scene activated, e.g. generators + surrounding rooms) should be activated. Said scenes' descriptions should be changed to reflect this. The activator item should also be removed from the player's inventory as it has been 'consumed'.
- For invalid inputs, nothing should happen - no scenes should be activated, the item should stay in the player's inventory, and no errors / bugs should occur - see the above speel about new players figuring out which items can be used where.

---

## Win State

The player wins when they activate the portal and press the 'Enter the Portal' button.

### Test Data To Use

- Valid win attempt (complete the game as intended and go through the portal after activating it)
- Invalid win attempt (Try to go through the portal before activating it, when this shouldn't be allowed)

### Expected Test Result

Upon entering the portal room, if the player:
  - Has validly completed the game, replace the movement keys with the enter portal button. When this button is pressed, display a popup dialog congratulating the player.
  - Has not completed the game (i.e. hasn't activated the portal from the control room), keep the movement keys as they are and do NOT show the enter portal button. There should be no way the player can finish the game if they have not completed it.

---

