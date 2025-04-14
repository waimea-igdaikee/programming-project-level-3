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

As a form of progression, my game will doors that need to be unlocked with found keys.

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
  - If the player tries to use or drop an item they don't have (depending on how I lay out my UI, an example of this type of invalid input could be pressing the 'use item 3' button when the player does not have an item 3), nothing should happen - ideally, the button will also be greyed out to provide a visual cue that that button can't be pressed.
  - If the player tries to pick up an item with a full inventory or drop an item into a full room, the game shouldn't let them. As above, the UI should also reflect that this action isn't possible.

---

## Door Unlocking

As a form of progression, my game will doors that need to be unlocked with found keys.

### Test Data To Use

- Valid unlock input (using the right key for the door)
- Invalid inputs:
    - Attempting to unlock a door without a key
    - Attempting to unlock the door with another door's key

### Expected Test Result

Statement detailing what should happen. Statement detailing what should happen. Statement detailing what should happen. Statement detailing what should happen.

---

