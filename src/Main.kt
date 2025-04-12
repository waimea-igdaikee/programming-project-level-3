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
class App() {
    // Constants defining any key values

    // Data fields
    var currentScene: Scene = entrance
    val inventory = mutableListOf<Item>()


    // Application logic functions
    fun move(dir: Char) {
        if (currentScene.adjacentScene(dir)?.locked == 0) {
            when (dir) {
                // Move to relevant adjacent scene. Nullable as we've surrounded it by a null check
                'n' -> currentScene = currentScene.adjacentScene('n')!!
                'e' -> currentScene = currentScene.adjacentScene('e')!!
                's' -> currentScene = currentScene.adjacentScene('s')!!
                'w' -> currentScene = currentScene.adjacentScene('w')!!
                'v' -> currentScene = currentScene.adjacentScene('v')!!
                }
        }
    }

    fun getSceneItem(itemNumber: Int): Item? {
        return if (currentScene.items.size >= itemNumber) {
            currentScene.items[itemNumber - 1]
        } else {
            null
        }
    }

    fun getInventoryItem(itemNumber: Int): Item? {
        return if (inventory.size >= itemNumber) {
            inventory[itemNumber - 1]
        } else {
            null
        }
    }

    fun takeItem(itemNumber: Int) {
        val item = getSceneItem(itemNumber)
        if (item != null) {
            inventory.add(item)
            currentScene.items.remove(item)
        }
    }

    fun useItem (itemNumber: Int) {
        val item = getInventoryItem(itemNumber)
        when (item?.type) {
            "Key" -> currentScene.unlockAdjacentRooms(item.id)
            "Activator" -> {
                if (currentScene.activate(item)) { // if item to be taken
                    inventory.remove(item)
                }
            }

        }
    }

    fun dropItem(itemNumber: Int) {
        val item = getInventoryItem(itemNumber)
        if (item != null) {
            currentScene.items.add(item)
            inventory.remove(item)
        }
    }
}


val gameMap = mutableMapOf<Triple<Int, Int, Int>, Scene>()

// Define the map

// Shared hallway description arrays
val poweredHallwayDescriptions = arrayOf(
    "Faint overhead lights flicker to life, casting long shadows down the narrow hallway."
)

val unpoweredHallwayDescriptions = arrayOf(
    "The hallway is pitch black, its end swallowed by shadow.",
    "Lights hum to life overhead, revealing smeared handprints on the walls."
)

// Define the map
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
    "Chemical stains and shattered glass litter this abandoned lab. Jerry cans filled with volatile liquid sit in the corner, waiting."
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
    "The array lights up. A different message now glows on the screen: 'Insert keycard to continue'...",
    "The controls hum with power. The screen now displays: 'Portal Online'. The levers click into place, and the air grows tense with anticipation."
))

val unpoweredHallways = arrayOf(hallway425, hallway525, hallway625, hallway624, hallway623, hallway423, hallway634, hallway633, hallway635)

// 


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
    fun enableVerticalConnection(direction: Char) {
        if (direction == 'u' || direction == 'd') {
            verticalConnection = direction
        }
    }


    fun addItem(obj: Item) {
        items.add(obj)
    }

    fun adjacentScene(direction: Char): Scene? {
        return when (direction) {
            'n' -> gameMap[Triple(location.first, location.second, location.third - 1)]
            'e' -> gameMap[Triple(location.first + 1, location.second, location.third)]
            's' -> gameMap[Triple(location.first, location.second, location.third + 1)]
            'w' -> gameMap[Triple(location.first - 1, location.second, location.third)]
            'v' -> {
                when (verticalConnection) {
                    'u' -> gameMap[Triple(location.first, location.second - 1, location.third)]
                    else -> gameMap[Triple(location.first, location.second + 1, location.third)]
                }
            }
            else -> null
        }
    }

    fun unlockAdjacentRooms(keyId: Int) {
        charArrayOf('n', 'e', 's', 'w').forEach { direction -> // Iterate through, unlocking each adjacent room
            if (adjacentScene(direction) != null ) {
                if (adjacentScene(direction)!!.locked == keyId) {
                    adjacentScene(direction)!!.locked = 0
                }
            }
        }
    }

    fun addToMap() {
        gameMap[location] = this
    }

    fun addActivator(activatorItem: Item, scenesToActivate: Array<Scene>) {
        activator = activatorItem
        this.scenesToActivate = scenesToActivate
    }

    // Makes the room locked. KeyID
    fun addToMapLocked(keyId: Int) {
        gameMap[location] = this
        locked = keyId
    }

    

    // Activates the room to progress the game e.g. putting fuel in the generators
    fun activate(attemptActivator: Item): Boolean {
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

class Item(val name: String, val spawnLocations: Array<Scene>, val type: String, val id: Int) {
    fun spawn() {
        spawnLocations[spawnLocations.indices.random()].addItem(this) // Random location in list
    }
}


// Initialise items
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

    // Add scenes
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

    elevator734.addToMap()
    elevator734.enableVerticalConnection('u')
    hallway634.addToMap()
    hallway633.addToMap()
    hallway635.addToMap()
    portalControlRoom.addToMap()
    portalRoom.addToMapLocked(2)

    // Initialise Items
    facilityKey.spawn()
    medBayKey.spawn()
    labKey.spawn()

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


    // Fields to hold the UI elements
    private lateinit var titleLabel: JLabel
    private lateinit var northButton: JButton
    private lateinit var eastButton: JButton
    private lateinit var southButton: JButton
    private lateinit var westButton: JButton
    private lateinit var verticalButton: JButton
    private lateinit var descriptionLabel: JLabel
    private lateinit var itemsLabel: JLabel


    private lateinit var itemLabel1: JLabel
    private lateinit var itemLabel2: JLabel
    private lateinit var itemLabel3: JLabel
    private lateinit var itemLabel4: JLabel
    private lateinit var itemLabel5: JLabel

    private lateinit var inventoryLabel: JLabel
    private lateinit var inventoryLabel1: JLabel
    private lateinit var inventoryLabel2: JLabel
    private lateinit var inventoryLabel3: JLabel
    private lateinit var inventoryLabel4: JLabel
    private lateinit var inventoryLabel5: JLabel

    private lateinit var takeButton1 : JButton
    private lateinit var takeButton2 : JButton
    private lateinit var takeButton3 : JButton
    private lateinit var takeButton4 : JButton
    private lateinit var takeButton5 : JButton

    private lateinit var useButton1 : JButton
    private lateinit var useButton2 : JButton
    private lateinit var useButton3 : JButton
    private lateinit var useButton4 : JButton
    private lateinit var useButton5 : JButton

    private lateinit var dropButton1 : JButton
    private lateinit var dropButton2 : JButton
    private lateinit var dropButton3 : JButton
    private lateinit var dropButton4 : JButton
    private lateinit var dropButton5 : JButton

    /**
     * Configure the UI and display it
     */
    init {
        configureWindow()               // Configure the window
        addControls()                   // Build the UI

        setLocationRelativeTo(null)     // Centre the window
        isVisible = true                // Make it visible

        updateView()                   // Initialise the UI
    }

    /**
     * Configure the main window
     */
    private fun configureWindow() {
        title = "Kotlin Swing GUI Demo"
        contentPane.preferredSize = Dimension(1000, 500)
        defaultCloseOperation = EXIT_ON_CLOSE
        isResizable = false
        layout = null

        pack()
    }

    /**
     * Populate the UI with UI controls
     */
    private fun addControls() {
        val baseFont = Font(Font.SANS_SERIF, Font.PLAIN, 36)
        val smallFont = Font(Font.SANS_SERIF, Font.PLAIN, 16)

        this.addKeyListener(this)


        val DESCRIPTION_X = 35
        val DESCRIPTION_Y = 0
        val DESCRIPTION_WIDTH = 400

        val BUTTONS_X = 200
        val BUTTONS_Y = 250

        titleLabel = JLabel("Title")
        titleLabel.horizontalAlignment = SwingConstants.CENTER
        titleLabel.bounds = Rectangle(DESCRIPTION_X, DESCRIPTION_Y, DESCRIPTION_WIDTH, 100)
        titleLabel.font = baseFont
        add(titleLabel)

        descriptionLabel = JLabel("DESCRIPTION HERE")
        descriptionLabel.horizontalAlignment = SwingConstants.LEFT
        descriptionLabel.verticalAlignment = SwingConstants.TOP
        descriptionLabel.bounds = Rectangle(DESCRIPTION_X, DESCRIPTION_Y + 80, DESCRIPTION_WIDTH, 120)
        descriptionLabel.font = smallFont
        descriptionLabel.background = Color(75, 80, 82)
        descriptionLabel.isOpaque = true
        add(descriptionLabel)

        northButton = JButton("N")
        northButton.bounds = Rectangle(BUTTONS_X, BUTTONS_Y,50,50)
        northButton.font = baseFont
        northButton.addActionListener(this)     // Handle any clicks
        northButton.isFocusable = false
        add(northButton)

        eastButton = JButton("E")
        eastButton.bounds = Rectangle(BUTTONS_X + 60, BUTTONS_Y + 60,50,50)
        eastButton.font = baseFont
        eastButton.addActionListener(this)     // Handle any clicks
        eastButton.isFocusable = false
        add(eastButton)

        southButton = JButton("S")
        southButton.bounds = Rectangle(BUTTONS_X, BUTTONS_Y + 120,50,50)
        southButton.font = baseFont
        southButton.addActionListener(this)     // Handle any clicks
        southButton.isFocusable = false
        add(southButton)

        westButton = JButton("W")
        westButton.bounds = Rectangle(BUTTONS_X - 60, BUTTONS_Y + 60,50,50)
        westButton.font = baseFont
        westButton.addActionListener(this)     // Handle any clicks
        westButton.isFocusable = false
        add(westButton)

        verticalButton = JButton("-")
        verticalButton.bounds = Rectangle(BUTTONS_X, BUTTONS_Y + 60,50,50)
        verticalButton.font = baseFont
        verticalButton.addActionListener(this)     // Handle any clicks
        verticalButton.isFocusable = false
        add(verticalButton)


//        val ITEMS_X = 500
//        val ITEMS_Y = 100
//        val ITEMS_BUTTON_SIZE = 30
//        val ITEMS_SPACING = 10

        val INVENTORY_X = 600
        val INVENTORY_Y = 0
        val INVENTORY_BUTTON_SIZE = 30
        val INVENTORY_SPACING = 10

        itemsLabel = JLabel("Items")
        itemsLabel.horizontalAlignment = SwingConstants.LEFT
        itemsLabel.bounds = Rectangle(INVENTORY_X, INVENTORY_Y, 200, 100)
        itemsLabel.font = baseFont
        add(itemsLabel)

        inventoryLabel = JLabel("Inventory")
        inventoryLabel.horizontalAlignment = SwingConstants.LEFT
        inventoryLabel.bounds = Rectangle(INVENTORY_X + 200, INVENTORY_Y, 200, 100)
        inventoryLabel.font = baseFont
        add(inventoryLabel)

        inventoryLabel1 = JLabel("")
        inventoryLabel2 = JLabel("")
        inventoryLabel3 = JLabel("")
        inventoryLabel4 = JLabel("")
        inventoryLabel5 = JLabel("")

        val inventoryLabels = arrayOf(inventoryLabel1, inventoryLabel2, inventoryLabel3, inventoryLabel4, inventoryLabel5)

        inventoryLabels.forEachIndexed() {index, label ->
            label.horizontalAlignment = SwingConstants.RIGHT
            label.bounds = Rectangle(
                INVENTORY_X + 140,
                100 + INVENTORY_Y + (INVENTORY_BUTTON_SIZE + INVENTORY_SPACING) * index,
                150,
                INVENTORY_BUTTON_SIZE)
            label.font = smallFont
            add(label)
        }

        itemLabel1 = JLabel("")
        itemLabel2 = JLabel("")
        itemLabel3 = JLabel("")
        itemLabel4 = JLabel("")
        itemLabel5 = JLabel("")

        val itemLabels = arrayOf(itemLabel1, itemLabel2, itemLabel3, itemLabel4, itemLabel5)

        itemLabels.forEachIndexed() {index, label ->
            label.horizontalAlignment = SwingConstants.RIGHT
            label.bounds = Rectangle(
                INVENTORY_X - 60,
                100 + INVENTORY_Y + (INVENTORY_BUTTON_SIZE + INVENTORY_SPACING) * index,
                150,
                INVENTORY_BUTTON_SIZE)
            label.font = smallFont
            add(label)
        }

        takeButton1 = JButton("")
        takeButton2 = JButton("")
        takeButton3 = JButton("")
        takeButton4 = JButton("")
        takeButton5 = JButton("")

        val takeButtons = arrayOf(takeButton1, takeButton2, takeButton3, takeButton4, takeButton5)

        takeButtons.forEachIndexed() {index, button ->
            button.bounds = Rectangle(
                INVENTORY_X + 100,
                100 + INVENTORY_Y + (INVENTORY_BUTTON_SIZE + INVENTORY_SPACING) * index,
                INVENTORY_BUTTON_SIZE,
                INVENTORY_BUTTON_SIZE
            )
            button.font = smallFont
            button.addActionListener(this)
            button.isFocusable = false
            add(button)
        }

        useButton1 = JButton("")
        useButton2 = JButton("")
        useButton3 = JButton("")
        useButton4 = JButton("")
        useButton5 = JButton("")

        val useButtons = arrayOf(useButton1, useButton2, useButton3, useButton4, useButton5)

        useButtons.forEachIndexed() {index, button ->
            button.bounds = Rectangle(
                INVENTORY_X + 300,
                100 + INVENTORY_Y + (INVENTORY_BUTTON_SIZE + INVENTORY_SPACING) * index,
                INVENTORY_BUTTON_SIZE,
                INVENTORY_BUTTON_SIZE
            )
            button.font = smallFont
            button.addActionListener(this)
            button.isFocusable = false
            add(button)
        }

        dropButton1 = JButton("")
        dropButton2 = JButton("")
        dropButton3 = JButton("")
        dropButton4 = JButton("")
        dropButton5 = JButton("")

        val dropButtons = arrayOf(dropButton1, dropButton2, dropButton3, dropButton4, dropButton5)

        dropButtons.forEachIndexed() {index, button ->
            button.bounds = Rectangle(
                INVENTORY_X + 350,
                100 + INVENTORY_Y + (INVENTORY_BUTTON_SIZE + INVENTORY_SPACING) * index,
                INVENTORY_BUTTON_SIZE,
                INVENTORY_BUTTON_SIZE
            )
            button.font = smallFont
            button.addActionListener(this)
            button.isFocusable = false
            add(button)
        }
    }




    /**
     * Update the UI controls based on the current state
     * of the application model
     */
    fun updateView() {
        requestFocus()
        titleLabel.text = app.currentScene.name
        descriptionLabel.text = "<html>" + app.currentScene.currentDescription + "</html>" // Wrap in html tags to enable line wrapping
        // I should probably store all these in an array and then use .forEach()
        itemLabel1.text = app.getSceneItem(1)?.name ?: ""
        itemLabel2.text = app.getSceneItem(2)?.name ?: ""
        itemLabel3.text = app.getSceneItem(3)?.name ?: ""
        itemLabel4.text = app.getSceneItem(4)?.name ?: ""
        itemLabel5.text = app.getSceneItem(5)?.name ?: ""

        inventoryLabel1.text = app.getInventoryItem(1)?.name ?: ""
        inventoryLabel2.text = app.getInventoryItem(2)?.name ?: ""
        inventoryLabel3.text = app.getInventoryItem(3)?.name ?: ""
        inventoryLabel4.text = app.getInventoryItem(4)?.name ?: ""
        inventoryLabel5.text = app.getInventoryItem(5)?.name ?: ""

        // Enable and disable item buttons where applicable
        takeButton1.isEnabled = app.getSceneItem(1) != null
        takeButton2.isEnabled = app.getSceneItem(2) != null
        takeButton3.isEnabled = app.getSceneItem(3) != null
        takeButton4.isEnabled = app.getSceneItem(4) != null
        takeButton5.isEnabled = app.getSceneItem(5) != null

        //useButton1.isEnabled = app.getInventoryItem(1) != null
        useButton2.isEnabled = app.getInventoryItem(2) != null
        useButton3.isEnabled = app.getInventoryItem(3) != null
        useButton4.isEnabled = app.getInventoryItem(4) != null
        useButton5.isEnabled = app.getInventoryItem(5) != null


        dropButton1.isEnabled = app.getInventoryItem(1) != null
        dropButton2.isEnabled = app.getInventoryItem(2) != null
        dropButton3.isEnabled = app.getInventoryItem(3) != null
        dropButton4.isEnabled = app.getInventoryItem(4) != null
        dropButton5.isEnabled = app.getInventoryItem(5) != null


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
    }

    /**
     * Handle any UI events (e.g. button clicks)
     * Usually this involves updating the application model
     * then refreshing the UI view
     */
    override fun actionPerformed(e: ActionEvent?) {
        when (e?.source) {
            northButton -> app.move('n')
            eastButton -> app.move('e')
            southButton -> app.move('s')
            westButton -> app.move('w')
            verticalButton -> app.move('v')

            useButton1 -> app.useItem(1)
            useButton2 -> app.useItem(2)
            useButton3 -> app.useItem(3)
            useButton4 -> app.useItem(4)
            useButton5 -> app.useItem(5)

            takeButton1 -> app.takeItem(1)
            takeButton2 -> app.takeItem(2)
            takeButton3 -> app.takeItem(3)
            takeButton4 -> app.takeItem(4)
            takeButton5 -> app.takeItem(5)

            dropButton1 -> app.dropItem(1)
            dropButton2 -> app.dropItem(2)
            dropButton3 -> app.dropItem(3)
            dropButton4 -> app.dropItem(4)
            dropButton5 -> app.dropItem(5)
        }
        updateView()
    }

    override fun keyTyped(e: KeyEvent?) {
    }

    override fun keyPressed(e: KeyEvent?) {
        println("${e?.keyCode}")

//      Check which key was pressed and act upon it
        when (e?.keyCode) {
            37 -> app.move('w')
            38 -> app.move('n')
            39 -> app.move('e')
            40 -> app.move('s')
        }

        // Ensure view matched the updated app model data
        updateView()
    }

    override fun keyReleased(e: KeyEvent?) {

    }

}