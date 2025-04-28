/**
 * =====================================================================
 * Programming Project for NCEA Level 3, Standard 91906
 * ---------------------------------------------------------------------
 * Project Name:   PROJECT NAME HERE
 * Project Author: Indiana Daikee
 * GitHub Repo:    https://github.com/waimea-igdaikee/programming-project-level-3
 * ---------------------------------------------------------------------
 * Notes:
 * PROJECT NOTES HERE
 * =====================================================================
 */



import com.formdev.flatlaf.FlatDarkLaf
import java.awt.*
import java.awt.event.*
import javax.swing.*


/**
 * Launch the application
 */


/**
 * The application class (model)
 * This is the place where any application data should be
 * stored, plus any application logic functions
 */
class App {
    // Data fields
    var currentScene: Scene = entrance
    val inventory = mutableListOf<Item>()


    /**
     * Define all the application functions that the game needs
     */

    // Move the player to the relevant adjacent scene
    fun move(dir: Char) {
        if (currentScene.adjacentScene(dir)?.locked == 0) { // Is the player allowed to go that way?
            when (dir) {
                // Move the player. Nullable as we've surrounded it by a null check
                'n' -> currentScene = currentScene.adjacentScene('n')!!
                'e' -> currentScene = currentScene.adjacentScene('e')!!
                's' -> currentScene = currentScene.adjacentScene('s')!!
                'w' -> currentScene = currentScene.adjacentScene('w')!!
                'v' -> currentScene = currentScene.adjacentScene('v')!!
                }
        }
    }

    // Return the requested item object if it exists. Used for displaying the scene's items
    fun getSceneItem(itemNumber: Int): Item? {
        return if (currentScene.items.size >= itemNumber) {
            currentScene.items[itemNumber - 1]
        } else {
            null
        }
    }

    // Same as above; return the requested item if it exists, but from the player's inventory instead of the scene.
    fun getInventoryItem(itemNumber: Int): Item? {
        return if (inventory.size >= itemNumber) {
            inventory[itemNumber - 1]
        } else {
            null
        }
    }

    // Move the requested item from the scene to the player's inventory
    fun takeItem(itemNumber: Int) {
        val item = getSceneItem(itemNumber)
        if (item != null) {
            inventory.add(item)
            currentScene.items.remove(item)
        }
    }

    // Use the item. What this does depends on what type the item is.
    fun useItem (itemNumber: Int) {
        val item = getInventoryItem(itemNumber)
        when (item?.type) {
            "Key" -> currentScene.unlockAdjacentRooms(item.id) // If it's a key, try to unlock the relevant room(s)
            /*
            If it's a scene activator (this is further explained in the scene class's comments), attempt to activate
            the current scene. Only remove the item if activate() returns true - meaning the activation was successful.
             */
            "Activator" -> {
                if (currentScene.activate(item)) { // If activation was successful,
                    inventory.remove(item)         // remove the item from the player's inventory.
                }
            }
        }
    }

    // Drop the item from the player's inventory into the current scene
    fun dropItem(itemNumber: Int) {
        val item = getInventoryItem(itemNumber)
        if (item != null) {
            currentScene.items.add(item)
            inventory.remove(item)
        }
    }

    // Returns true when the player has met the win condition.
    fun hasWon(): Boolean {
        return (currentScene == portalRoom) && (portalRoom.activated > 0)
    }
}

// Game map that holds all the scenes and their positions.
val gameMap = mutableMapOf<Triple<Int, Int, Int>, Scene>()

// Shared hallway description arrays
val poweredHallwayDescriptions = arrayOf(
    "Faint overhead lights flicker to life, casting long shadows down the narrow hallway."
)
val unpoweredHallwayDescriptions = arrayOf(
    "The hallway is pitch black, its end swallowed by shadow.",
    "Lights hum to life overhead, revealing smeared handprints on the walls."
)

/**
 * Define the map
 */
val entrance = Scene(Triple(1,1,1), "Entrance", arrayOf(
    "The heavy entrance looms ahead, rusted shut and silent."
))

val hallway112 = Scene(Triple(1,1,2), "Hallway", poweredHallwayDescriptions)

val blastDoor = Scene(Triple(1,1,3), "Blast Door", arrayOf(
    "A reinforced blast door blocks the way, scorched and dented from the outside."
))

val hallway213 = Scene(Triple(2,1,3), "Hallway", poweredHallwayDescriptions)
val hallway313 = Scene(Triple(3,1,3), "Hallway", poweredHallwayDescriptions)
val hallway413 = Scene(Triple(4,1,3), "Hallway", poweredHallwayDescriptions)
val hallway114 = Scene(Triple(1,1,4), "Hallway", poweredHallwayDescriptions)
val hallway115 = Scene(Triple(1,1,5), "Hallway", poweredHallwayDescriptions)
val hallway215 = Scene(Triple(2,1,5), "Hallway", poweredHallwayDescriptions)

val medBay = Scene(Triple(3,1,2), "Medical Bay", arrayOf(
    "An infirmary soaked in rot. Blood-slick tiles and a mound of limbs fester in the corner."
))

val elevator315 = Scene(Triple(3,1,5), "Elevator", arrayOf(
    "An untrustworthy elevator at the top, its doors twitching as if barely hanging on."
))

val messHall = Scene(Triple(4,1,4), "Mess Hall", arrayOf(
    "An untidy mess hall filled with overturned trays and flickering ceiling lights."
))

val computerRoom = Scene(Triple(5,1,3), "Computer Room", arrayOf(
    "Rows of outdated terminals sit in silence. A thick layer of dust clings to every screen."
))

val serverRoom = Scene(Triple(5,1,2), "Server Room", arrayOf(
    "Towers of servers buzz quietly in the dark, their fans spinning like anxious whispers."
))

val elevator325 = Scene(Triple(3,2,5), "Elevator", arrayOf(
    "The bottom of the shaft. Rust streaks the walls. The elevator car looks ready to drop at any moment."
))

val hallway425 = Scene(Triple(4,2,5), "Hallway", unpoweredHallwayDescriptions)
val hallway525 = Scene(Triple(5,2,5), "Hallway", unpoweredHallwayDescriptions)
val hallway625 = Scene(Triple(6,2,5), "Hallway", unpoweredHallwayDescriptions)
val hallway624 = Scene(Triple(6,2,4), "Hallway", unpoweredHallwayDescriptions)
val hallway623 = Scene(Triple(6,2,3), "Hallway", unpoweredHallwayDescriptions)
val hallway423 = Scene(Triple(4,2,3), "Hallway", unpoweredHallwayDescriptions)

val storageRoom526 = Scene(Triple(5,2,6), "Storage Room", arrayOf(
    "A damp, reeking storage room cluttered with unmarked crates and oily puddles."
))

val storageRoom622 = Scene(Triple(6,2,2), "Storage Room", arrayOf(
    "A foul stench thickens the air. Mold coats crates stacked like forgotten memories."
))

val controlRoom = Scene(Triple(4,2,4), "Control Room", arrayOf(
    "A dark room. A massive, ancient computer rests silently in the middle — if only there were a way to bring it back to life...",
    "The room is bathed in cold light. The massive computer blinks expectantly, awaiting a fingerprint scan...",
    "The fingerprint is accepted. The computer thrums, and deep below, something mechanical shifts awake..."
))

val generatorRoom = Scene(Triple(3,2,3), "Generator Room", arrayOf(
    "An open hall where colossal diesel generators lie dormant, like beasts in slumber.",
    "An open hall where colossal diesel generators lie dormant, like beasts in slumber.",
    "The generators roar to life. The walls shake with their pulse as power surges outward."
), 1)

val elevator724 = Scene(Triple(7,2,4), "Elevator", arrayOf(
    "A sharp ledge overlooks an empty elevator shaft. Frayed wires sway in the void, inviting only the reckless."
))

val labRoom = Scene(Triple(5,2,3), "Lab Room", arrayOf(
    "Chemical stains and shattered glass litter this abandoned lab. Jerry cans filled with fuel sit in the corner, waiting."
))

val elevator734 = Scene(Triple(7,3,4), "Elevator", arrayOf(
    "At the shaft's bottom, dust and debris blanket the floor. The walls echo with metallic groans above."
))

val hallway634 = Scene(Triple(6,3,4), "Hallway", unpoweredHallwayDescriptions)
val hallway633 = Scene(Triple(6,3,3), "Hallway", unpoweredHallwayDescriptions)
val hallway635 = Scene(Triple(6,3,5), "Hallway", unpoweredHallwayDescriptions)

val portalRoom = Scene(Triple(5,3,5), "Portal Chamber", arrayOf(
    "The frame of what looks to be a portal towers ahead. It's carved with pulsating runes, but they are dark and lifeless.",
    "The portal hums to life, its runes glowing brightly. White mist fills the frame, swirling rapidly as if something is about to emerge."
))

val portalControlRoom = Scene(Triple(5,3,3), "Portal Controls", arrayOf(
    "An array of levers and buttons stretches across the wall. A dusty screen reads: 'Disabled at master control room'...",
    "The array lights up. A message glows on the screen: 'Insert lockout key to continue'...",
    "The controls hum with power. The screen now displays: 'Portal Online'. The levers click into place, and the air grows tense with anticipation."
))

val unpoweredHallways = arrayOf(hallway425, hallway525, hallway625, hallway624, hallway623, hallway423, hallway634, hallway633, hallway635)


/**
 * Scene class. Of note is the activation system - each scene has up to 3 different activation states. Their description
 * and/or game logic can differ depending on these states. Relevant scenes have an activator - a specific item class
 * object that activates said scene and possibly other scenes.
 */

class Scene(
    val location: Triple<Int, Int, Int>,
    val name: String,
    val descriptions: Array<String>,
    var activated: Int = 0
) {
    var verticalConnection = 'n'
    var locked = 0

    var activator: Item? = null

    // For activating other scenes that change their description when this scene is activated, e.g. turning on the lights when the generator is activated
    lateinit var scenesToActivate: Array<Scene>
    var currentDescription = descriptions[0]

    val items = mutableListOf<Item>()

    /**
     * Define methods
     */

    // Maps this scene to its location in the gameMap
    fun addToMap() {
        gameMap[location] = this
    }

    // Same as above but the room spawns locked
    fun addToMapLocked(keyId: Int) {
        gameMap[location] = this
        locked = keyId
    }

    // Scenes such as stairs or elevators will use this method
    fun enableVerticalConnection(direction: Char) {
        if (direction == 'u' || direction == 'd') {
            verticalConnection = direction
        }
    }

    // Returns the adjacent scene object in a given direction. Used by multiple other methods.
    fun adjacentScene(direction: Char): Scene? {
        return when (direction) {
            'n' -> gameMap[Triple(location.first, location.second, location.third - 1)]
            'e' -> gameMap[Triple(location.first + 1, location.second, location.third)]
            's' -> gameMap[Triple(location.first, location.second, location.third + 1)]
            'w' -> gameMap[Triple(location.first - 1, location.second, location.third)]
            'v' -> {
                when (verticalConnection) {
                    'u' -> gameMap[Triple(location.first, location.second - 1, location.third)]
                    'd' -> gameMap[Triple(location.first, location.second + 1, location.third)]
                    else -> null
                }
            }
            else -> null
        }
    }

    /*
    When the player attempts to use a key, check all the relevant surrounding rooms to see
    if that key unlocks any of them.
     */
    fun unlockAdjacentRooms(keyId: Int) {
        charArrayOf('n', 'e', 's', 'w').forEach { direction -> // Iterate through, attempting to unlock each adjacent room
            if (adjacentScene(direction) != null ) {
                if (adjacentScene(direction)!!.locked == keyId) {
                    adjacentScene(direction)!!.locked = 0
                }
            }
        }
    }

    // Adds an item object to this scene
    fun addItem(obj: Item) {
        items.add(obj)
    }

    // As detailed in the scene class's block comment, set an item object to be this room's activator
    fun addActivator(activatorItem: Item, scenesToActivate: Array<Scene>) {
        activator = activatorItem
        this.scenesToActivate = scenesToActivate
    }

    // Activates the room to progress the game e.g. putting fuel in the generators
    fun activate(attemptActivator: Item): Boolean {
        /*
        Check both whether the scene is ready to be activated (e.g. a keycard can't be inserted into a computer if the
        scene's power is off i.e. activated == 0), and whether the right item is being used. If so, increment this
        scene's and the relevant other scenes that this room activates' activation states.
         */
        if (activated == 1 && activator == attemptActivator) {
            (arrayOf(this)+scenesToActivate).forEach {sceneToActivate ->
                sceneToActivate.activated ++
                sceneToActivate.currentDescription = sceneToActivate.descriptions[sceneToActivate.activated]
            }
            return true
        } else { // If activation conditions not met
            return false
        }
    }
}

/**
 * Define the class for items. Items spawn within scenes, and can be moved into the player's inventory.
 */

class Item(val name: String, val spawnLocations: Array<Scene>, val type: String, val id: Int) {
    // Pick a random scene in spawnLocations and spawn the item in that room.
    fun spawn() {
        spawnLocations[spawnLocations.indices.random()].addItem(this)
    }
}


/**
 * Define item objects
 */

// Keys
val facilityKey = Item("Green Keycard", arrayOf(blastDoor), "Key", 1)
val medBayKey = Item("Blue Keycard", arrayOf(messHall, serverRoom), "Key", 2)
val labKey = Item("Orange Keycard", arrayOf(storageRoom526, storageRoom622), "Key", 3)

// Activator items
val finger = Item("Rotting Finger", arrayOf(medBay), "Activator", 5)
val jerryCan = Item("Jerry Can", arrayOf(labRoom), "Activator", 4)
val portalKey = Item("Portal Lockout Key", arrayOf(portalControlRoom), "Activator", 6)


fun main() {
    FlatDarkLaf.setup()     // Flat, dark look-and-feel
    val app = App()         // Create the app model
    MainWindow(app)         // Create and show the UI, using the app model

    /**
     * Add all the scenes to the game map. Lock or enable a vertical connection for some.
     */

    // Level 1 (Top level)
    entrance.addToMap()
    hallway112.addToMap()
    blastDoor.addToMap()
    hallway213.addToMapLocked(1)
    hallway313.addToMap()
    hallway413.addToMap()
    hallway114.addToMapLocked(1)
    hallway115.addToMap()
    hallway215.addToMap()
    medBay.addToMapLocked(2)
    elevator315.addToMap()
    elevator315.enableVerticalConnection('d')
    messHall.addToMap()
    computerRoom.addToMap()
    serverRoom.addToMap()

    // Level 2
    elevator325.addToMap()
    elevator325.enableVerticalConnection('u')
    hallway425.addToMap()
    hallway525.addToMap()
    hallway625.addToMap()
    hallway624.addToMap()
    hallway623.addToMap()
    hallway423.addToMap()
    storageRoom526.addToMap()
    storageRoom622.addToMap()
    controlRoom.addToMap()
    generatorRoom.addToMap()
    elevator724.addToMap()
    elevator724.enableVerticalConnection('d')
    labRoom.addToMapLocked(3)

    // Level 3 (Bottom level)
    elevator734.addToMap()
    elevator734.enableVerticalConnection('u')
    hallway634.addToMap()
    hallway633.addToMap()
    hallway635.addToMap()
    portalControlRoom.addToMap()
    portalRoom.addToMapLocked(2)

    /**
     * Spawn all the items in their rooms. Make some activators for scenes.
     * This is further explained in the scene class comments if neccesary.
     */

    // Scene keys
    facilityKey.spawn()
    medBayKey.spawn()
    labKey.spawn()

    // Activators
    jerryCan.spawn()
    // Using the jerry can in the generator room activates the generator room, control room, and some hallways
    generatorRoom.addActivator(jerryCan, arrayOf(controlRoom) + unpoweredHallways)

    finger.spawn()
    controlRoom.addActivator(finger, arrayOf(portalControlRoom))

    portalKey.spawn()
    portalControlRoom.addActivator(portalKey, arrayOf(portalRoom))
}

/**
 * Main UI window (view)
 * Defines the UI and responds to events
 * The app model should be passed as an argument
 */
class MainWindow(val app: App) : JFrame(), ActionListener, KeyListener {

    // Popups for title/instructions and winning
    private lateinit var introPopUp: IntroPopUpDialog
    private lateinit var winPopUp: WinPopUpDialog


    /**
     * Fields to hold all the UI elements
     */
    // Borders
    private lateinit var sceneBorder: JLabel
    private lateinit var controlsBorder: JLabel
    private lateinit var inventoryBorder: JLabel

    private lateinit var titleLabel: JLabel
    private lateinit var descriptionLabel: JLabel

    // Lower left quadrant buttons
    private lateinit var northButton: JButton
    private lateinit var eastButton: JButton
    private lateinit var southButton: JButton
    private lateinit var westButton: JButton
    private lateinit var verticalButton: JButton
    private lateinit var helpButton: JButton
    private lateinit var winButton: JButton

    // Item and inventory labels and buttons
    private lateinit var itemLabel1: JLabel
    private lateinit var itemLabel2: JLabel
    private lateinit var itemLabel3: JLabel
    private lateinit var itemLabel4: JLabel

    private lateinit var inventoryLabel: JLabel
    private lateinit var inventoryLabel1: JLabel
    private lateinit var inventoryLabel2: JLabel
    private lateinit var inventoryLabel3: JLabel
    private lateinit var inventoryLabel4: JLabel

    private lateinit var takeLabel: JLabel
    private lateinit var takeButton1 : JButton
    private lateinit var takeButton2 : JButton
    private lateinit var takeButton3 : JButton
    private lateinit var takeButton4 : JButton

    private lateinit var useLabel: JLabel
    private lateinit var useButton1 : JButton
    private lateinit var useButton2 : JButton
    private lateinit var useButton3 : JButton
    private lateinit var useButton4 : JButton

    private lateinit var dropLabel: JLabel
    private lateinit var dropButton1 : JButton
    private lateinit var dropButton2 : JButton
    private lateinit var dropButton3 : JButton
    private lateinit var dropButton4 : JButton

    /**
     * Configure the UI and display it
     */
    init {
        configureWindow()               // Configure the window
        addControls()                   // Build the UI

        setLocationRelativeTo(null)     // Centre the window
        isVisible = true                // Make it visible

        updateView()                    // Initialise the UI

        introPopUp.isVisible = true     // Show the title / instructions dialog
    }

    /**
     * Configure the main window
     */
    private fun configureWindow() {
        title = "Kotlin Swing GUI Demo"
        contentPane.preferredSize = Dimension(600, 480)
        defaultCloseOperation = EXIT_ON_CLOSE
        isResizable = false
        layout = null

        pack()
    }

    /**
     * Populate the UI with UI controls
     */
    private fun addControls() {
        this.addKeyListener(this)

        // Set popups
        introPopUp = IntroPopUpDialog()
        winPopUp = WinPopUpDialog()

        /**
         * Define important position constants for the labels and controls
         */
        val largeFont = Font(Font.SANS_SERIF, Font.PLAIN, 32)
        val smallFont = Font(Font.SANS_SERIF, Font.PLAIN, 16)
        val tinyFont = Font(Font.SANS_SERIF, Font.PLAIN, 12)

        val descriptionX = 35
        val descriptionY = 0
        val descriptionWidth = 300

        val itemsX = 380
        val itemsTopY = 80
        val itemsBottomY = 320
        val itemsButtonSize = 30
        val itemsSpacing = 6

        val controlsX = 160
        val controlsY = 280

        // Common colours and borders
        val lightGrey = Color(75, 80, 82)

        val elementBorder = BorderFactory.createLineBorder(lightGrey, 3, true)

        /**
         * Set up all the labels and buttons.
         * Some are iterated over to save lines of code.
         */


        titleLabel = JLabel("Title")
        titleLabel.horizontalAlignment = SwingConstants.CENTER
        titleLabel.bounds = Rectangle(0, 0, 600, 100)
        titleLabel.font = largeFont
        add(titleLabel)

        descriptionLabel = JLabel("DESCRIPTION HERE")
        descriptionLabel.horizontalAlignment = SwingConstants.LEFT
        descriptionLabel.verticalAlignment = SwingConstants.TOP
        descriptionLabel.bounds = Rectangle(descriptionX, descriptionY + 80, descriptionWidth, 150)
        descriptionLabel.font = smallFont
        // Create hidden empty border for padding
        descriptionLabel.border = BorderFactory.createLineBorder(lightGrey, 5)
        descriptionLabel.background = lightGrey
        descriptionLabel.isOpaque = true
        add(descriptionLabel)

        helpButton = JButton("?")
        helpButton.bounds = Rectangle(15, 415,50,50)
        helpButton.font = largeFont
        helpButton.addActionListener(this)     // Handle any clicks
        helpButton.isFocusable = false
        add(helpButton)

        winButton = JButton("<html>Enter the portal</html>")
        winButton.bounds = Rectangle(descriptionX,270,220,100)
        winButton.horizontalAlignment = SwingConstants.CENTER
        winButton.font = largeFont
        winButton.addActionListener(this)     // Handle any clicks
        winButton.isFocusable = false
        winButton.isEnabled = false
        winButton.isVisible = false
        add(winButton)



        // Movement buttons
        northButton = JButton("N")
        northButton.bounds = Rectangle(controlsX, controlsY,50,50)
        northButton.font = largeFont
        northButton.addActionListener(this)     // Handle any clicks
        northButton.isFocusable = false
        add(northButton)

        eastButton = JButton("E")
        eastButton.bounds = Rectangle(controlsX + 60, controlsY + 60,50,50)
        eastButton.font = largeFont
        eastButton.addActionListener(this)     // Handle any clicks
        eastButton.isFocusable = false
        add(eastButton)

        southButton = JButton("S")
        southButton.bounds = Rectangle(controlsX, controlsY + 120,50,50)
        southButton.font = largeFont
        southButton.addActionListener(this)     // Handle any clicks
        southButton.isFocusable = false
        add(southButton)

        westButton = JButton("W")
        westButton.bounds = Rectangle(controlsX - 60, controlsY + 60,50,50)
        westButton.font = largeFont
        westButton.addActionListener(this)     // Handle any clicks
        westButton.isFocusable = false
        add(westButton)

        verticalButton = JButton("-")
        verticalButton.bounds = Rectangle(controlsX, controlsY + 60,50,50)
        verticalButton.font = largeFont
        verticalButton.addActionListener(this)     // Handle any clicks
        verticalButton.isFocusable = false
        add(verticalButton)

        // Inventory and item labels
        inventoryLabel = JLabel("Inventory")
        inventoryLabel.horizontalAlignment = SwingConstants.CENTER
        inventoryLabel.bounds = Rectangle(itemsX, itemsBottomY - 90, 200, 100)
        inventoryLabel.font = largeFont
        add(inventoryLabel)

        inventoryLabel1 = JLabel("")
        inventoryLabel2 = JLabel("")
        inventoryLabel3 = JLabel("")
        inventoryLabel4 = JLabel("")
        val inventoryLabels = arrayOf(inventoryLabel1, inventoryLabel2, inventoryLabel3, inventoryLabel4)
        inventoryLabels.forEachIndexed { index, label ->
            label.horizontalAlignment = SwingConstants.RIGHT
            label.bounds = Rectangle(
                itemsX - 60,
                itemsBottomY + (itemsButtonSize + itemsSpacing) * index,
                150,
                itemsButtonSize)
            label.font = smallFont
            add(label)
        }

        itemLabel1 = JLabel("")
        itemLabel2 = JLabel("")
        itemLabel3 = JLabel("")
        itemLabel4 = JLabel("")
        val itemLabels = arrayOf(itemLabel1, itemLabel2, itemLabel3, itemLabel4)
        itemLabels.forEachIndexed { index, label ->
            label.horizontalAlignment = SwingConstants.RIGHT
            label.bounds = Rectangle(
                itemsX - 20,
                itemsTopY + (itemsButtonSize + itemsSpacing) * index,
                150,
                itemsButtonSize)
            label.font = smallFont
            add(label)
        }

        takeLabel = JLabel("Take")
        takeLabel.horizontalAlignment = SwingConstants.LEFT
        takeLabel.font = tinyFont
        takeLabel.bounds = Rectangle(
            itemsX + 140,
            itemsTopY - 15,
            50,
            10
        )
        add(takeLabel)

        // Inventory buttons
        takeButton1 = JButton("")
        takeButton2 = JButton("")
        takeButton3 = JButton("")
        takeButton4 = JButton("")
        val takeButtons = arrayOf(takeButton1, takeButton2, takeButton3, takeButton4)
        takeButtons.forEachIndexed { index, button ->
            button.bounds = Rectangle(
                itemsX + 140,
                itemsTopY + (itemsButtonSize + itemsSpacing) * index,
                itemsButtonSize,
                itemsButtonSize
            )
            button.font = smallFont
            button.addActionListener(this)
            button.isFocusable = false
            add(button)
        }

        useLabel = JLabel("Use")
        useLabel.horizontalAlignment = SwingConstants.LEFT
        useLabel.font = tinyFont
        useLabel.bounds = Rectangle(
            itemsX + 104,
            itemsBottomY - 15,
            50,
            10
        )
        add(useLabel)

        useButton1 = JButton("")
        useButton2 = JButton("")
        useButton3 = JButton("")
        useButton4 = JButton("")
        val useButtons = arrayOf(useButton1, useButton2, useButton3, useButton4)
        useButtons.forEachIndexed { index, button ->
            button.bounds = Rectangle(
                itemsX + 100,
                itemsBottomY + (itemsButtonSize + itemsSpacing) * index,
                itemsButtonSize,
                itemsButtonSize
            )
            button.font = smallFont
            button.addActionListener(this)
            button.isFocusable = false
            add(button)
        }

        dropLabel = JLabel("Drop")
        dropLabel.horizontalAlignment = SwingConstants.LEFT
        dropLabel.font = tinyFont
        dropLabel.bounds = Rectangle(
            itemsX + 140,
            itemsBottomY - 15,
            50,
            10
        )
        add(dropLabel)

        dropButton1 = JButton("")
        dropButton2 = JButton("")
        dropButton3 = JButton("")
        dropButton4 = JButton("")
        val dropButtons = arrayOf(dropButton1, dropButton2, dropButton3, dropButton4)
        dropButtons.forEachIndexed { index, button ->
            button.bounds = Rectangle(
                itemsX + 140,
                itemsBottomY + (itemsButtonSize + itemsSpacing) * index,
                itemsButtonSize,
                itemsButtonSize
            )
            button.font = smallFont
            button.addActionListener(this)
            button.isFocusable = false
            add(button)
        }

        // Add borders to groups of related element to streamline the UX
        sceneBorder = JLabel()
        sceneBorder.bounds = Rectangle(10,20, 580, 220)
        sceneBorder.border = elementBorder
        sceneBorder.isOpaque = true
        add(sceneBorder)

        controlsBorder = JLabel()
        controlsBorder.bounds = Rectangle(10,controlsY - 30, 290, 220)
        controlsBorder.border = elementBorder
        controlsBorder.isOpaque = true
        add(controlsBorder)

        inventoryBorder = JLabel()
        inventoryBorder.bounds = Rectangle(itemsX - 60,controlsY - 30, 270, 220)
        inventoryBorder.border = elementBorder
        inventoryBorder.isOpaque = true
        add(inventoryBorder)

    }

    /**
     * Update the UI controls based on the current state of the application model
     */
    fun updateView() {
        requestFocus()
        titleLabel.text = app.currentScene.name
        descriptionLabel.text = "<html>" + app.currentScene.currentDescription + "</html>" // HTML tags enable line wrapping

        // Update labels and controls for both scene and inventory items
        itemLabel1.text = app.getSceneItem(1)?.name ?: ""
        itemLabel2.text = app.getSceneItem(2)?.name ?: ""
        itemLabel3.text = app.getSceneItem(3)?.name ?: ""
        itemLabel4.text = app.getSceneItem(4)?.name ?: ""

        inventoryLabel1.text = app.getInventoryItem(1)?.name ?: ""
        inventoryLabel2.text = app.getInventoryItem(2)?.name ?: ""
        inventoryLabel3.text = app.getInventoryItem(3)?.name ?: ""
        inventoryLabel4.text = app.getInventoryItem(4)?.name ?: ""

        // Enable and disable controls where applicable
        takeButton1.isEnabled = (app.getSceneItem(1) != null) && (app.getInventoryItem(4) == null)
        takeButton2.isEnabled = app.getSceneItem(2) != null && (app.getInventoryItem(4) == null)
        takeButton3.isEnabled = app.getSceneItem(3) != null && (app.getInventoryItem(4) == null)
        takeButton4.isEnabled = app.getSceneItem(4) != null && (app.getInventoryItem(4) == null)

        useButton1.isEnabled = app.getInventoryItem(1) != null
        useButton2.isEnabled = app.getInventoryItem(2) != null
        useButton3.isEnabled = app.getInventoryItem(3) != null
        useButton4.isEnabled = app.getInventoryItem(4) != null

        // Disable drop buttons if that inventory slot is empty, or if the room is full.
        dropButton1.isEnabled = app.getInventoryItem(1) != null  && (app.getSceneItem(4) == null)
        dropButton2.isEnabled = app.getInventoryItem(2) != null  && (app.getSceneItem(4) == null)
        dropButton3.isEnabled = app.getInventoryItem(3) != null  && (app.getSceneItem(4) == null)
        dropButton4.isEnabled = app.getInventoryItem(4) != null  && (app.getSceneItem(4) == null)


        /*
        Enable, grey out, or hide directional controls based on whether that adjacent scene in the respective
        direction is unlocked, locked, or doesn't exist
         */
        if (app.currentScene.adjacentScene('n') != null) {
            northButton.text = "↑"
        } else {
            northButton.text = ""
        }
        if (app.currentScene.adjacentScene('s') != null) {
            southButton.text = "↓"
        } else {
            southButton.text = ""
        }
        if (app.currentScene.adjacentScene('e') != null) {
            eastButton.text = "→"
        } else {
            eastButton.text = ""
        }
        if (app.currentScene.adjacentScene('w') != null) {
            westButton.text = "←"
        } else {
            westButton.text = ""
        }

        northButton.isEnabled = app.currentScene.adjacentScene('n')?.locked == 0
        eastButton.isEnabled = app.currentScene.adjacentScene('e')?.locked == 0
        southButton.isEnabled = app.currentScene.adjacentScene('s')?.locked == 0
        westButton.isEnabled = app.currentScene.adjacentScene('w')?.locked == 0

        // Make the vertical movement button reflect the vertical direction that player can move in, if at all
        when (app.currentScene.verticalConnection) {
            'u' -> {
                verticalButton.text = "^"
                verticalButton.isEnabled = true
            }
            'd' -> {
                verticalButton.text = "v"
                verticalButton.isEnabled = true
            }
            else -> {
                verticalButton.text = "-"
                verticalButton.isEnabled = false
            }
        }

        /**
         * If the player wins, replace the directional controls with an "enter the portal" button.
         */
        winButton.isEnabled = app.hasWon()
        winButton.isVisible = app.hasWon()

        arrayOf(northButton, eastButton, southButton, westButton, verticalButton).forEach { it.isVisible = !app.hasWon() }
    }

    /**
     * Handle any UI events (e.g. button clicks)
     * Usually this involves updating the application model
     * then refreshing the UI view
     */
    override fun actionPerformed(e: ActionEvent?) {
        when (e?.source) {
            // Show relevant popups if their respective buttons are clicked
            helpButton -> introPopUp.isVisible = true
            winButton -> winPopUp.isVisible = true

            //
            northButton -> app.move('n')
            eastButton -> app.move('e')
            southButton -> app.move('s')
            westButton -> app.move('w')
            verticalButton -> app.move('v')

            useButton1 -> app.useItem(1)
            useButton2 -> app.useItem(2)
            useButton3 -> app.useItem(3)
            useButton4 -> app.useItem(4)

            takeButton1 -> app.takeItem(1)
            takeButton2 -> app.takeItem(2)
            takeButton3 -> app.takeItem(3)
            takeButton4 -> app.takeItem(4)

            dropButton1 -> app.dropItem(1)
            dropButton2 -> app.dropItem(2)
            dropButton3 -> app.dropItem(3)
            dropButton4 -> app.dropItem(4)
        }
        updateView()
    }

    override fun keyPressed(e: KeyEvent?) {
        println("${e?.keyCode}")

//      Check which key was pressed and act upon it - unless the player has won, in which case they can't move
        if (!app.hasWon()) {
            when (e?.keyCode) {
                37 -> app.move('w')
                38 -> app.move('n')
                39 -> app.move('e')
                40 -> app.move('s')
            }
        }

        // Ensure view matched the updated app model data
        updateView()
    }

    override fun keyTyped(e: KeyEvent?) {
        // This function only exists so Kotlin doesn't get upset
    }

    override fun keyReleased(e: KeyEvent?) {
        // This function only exists so Kotlin doesn't get upset
    }

}

/**
 * Dialog Classes.
 * I am using 2 separate classes as they are too many differences between the two for one generic dialog class.
 */
class IntroPopUpDialog(): JDialog() {
    /**
     * Configure the UI
     */
    init {
        configureWindow()
        addControls()
        setLocationRelativeTo(null)     // Centre the window
    }

    /**
     * Set up the dialog window
     */
    private fun configureWindow() {
        title = "Instructions"
        contentPane.preferredSize = Dimension(500, 300)
        isResizable = false
        isModal = true
        layout = null
        pack()
    }

    /**
     * Populate the window with controls
     */
    private fun addControls() {
        val baseFont = Font(Font.SANS_SERIF, Font.PLAIN, 16)

        // Adding <html> to the label text allows it to wrap
        val message = JLabel("<html>You find yourself at the entrance of a forgotten facility, long sealed and untouched. Whispers of strange energy drift through its empty halls. Something waits at the heart of it—but what, or why, remains unknown.<br><br>Use the arrow keys or on-screen buttons to move. Click 'Take' to collect items, 'Use' to interact, and 'Drop' to make space. You can only carry four items at once, so plan carefully.<br><br>  Search, decide, and delve deeper. Your task is to discover the truth buried within.</html>")
        message.bounds = Rectangle(25, 25, 450, 270)
        message.verticalAlignment = SwingConstants.TOP
        message.font = baseFont
        add(message)
    }

}

class WinPopUpDialog(): JDialog() {
    /**
     * Configure the UI
     */
    init {
        configureWindow()
        addControls()
        setLocationRelativeTo(null)     // Centre the window
    }

    /**
     * Set up the dialog window
     */
    private fun configureWindow() {
        title = "Win"
        contentPane.preferredSize = Dimension(400, 200)
        isResizable = false
        isModal = true
        layout = null
        pack()
    }

    /**
     * Populate the window with controls
     */
    private fun addControls() {
        val baseFont = Font(Font.SANS_SERIF, Font.PLAIN, 16)

        // Adding <html> to the label text allows it to wrap
        val message = JLabel("<html>You go through the portal and emerge on the other side. You win!</html>")
        message.bounds = Rectangle(25, 25, 350, 150)
        message.verticalAlignment = SwingConstants.TOP
        message.font = baseFont
        add(message)
    }

}