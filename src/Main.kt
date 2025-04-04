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
    var currentLocation: Scene = entrance
    val inventory = mutableListOf<Item>()


    // Application logic functions
    fun move(dir: Char) {
        when (dir) {
            'n' -> currentLocation = currentLocation.adjacentScene('n')!! // Nullable as buttons disabled
            'e' -> currentLocation = currentLocation.adjacentScene('e')!!
            's' -> currentLocation = currentLocation.adjacentScene('s')!!
            'w' -> currentLocation = currentLocation.adjacentScene('w')!!
            'v' -> currentLocation = currentLocation.adjacentScene('v')!!
        }
    }

    fun getSceneItem(itemNumber: Int): Item? {
        return if (currentLocation.items.size >= itemNumber) {
            currentLocation.items[itemNumber - 1]
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
            currentLocation.items.remove(item)
        }
    }

    fun useItem (itemNumber: Int) {
        val item = getInventoryItem(itemNumber)
        when (item?.type) {
            "Key" -> currentLocation.unlockAdjacentRooms(item.id)
        }
    }

    fun dropItem(itemNumber: Int) {
        val item = getInventoryItem(itemNumber)
        if (item != null) {
            currentLocation.items.add(item)
            inventory.remove(item)
        }
    }
}


val gameMap = mutableMapOf<Triple<Int, Int, Int>, Scene>()

// Define the map
val entrance = Scene( Triple(1,1,1), "Entrance", "The entrance to the facility")
val hallway112 = Hallway(Triple(1,1,2))
val blastDoor = Scene(Triple(1,1,3), "Blast Door", "A reinforced blast door")
val hallway213 = Hallway(Triple(2,1,3))
val hallway313 = Hallway(Triple(3,1,3))
val hallway413 = Hallway(Triple(4,1,3))
val hallway114 = Hallway(Triple(1,1,4))
val hallway115 = Hallway(Triple(1,1,5))
val hallway215 = Hallway(Triple(2,1,5))
val weaponsRoom = Scene(Triple(3,1,2), "Weapons Room", "A open room, shelves lines with weapons")
val elevator315 = Scene(Triple(3,1,5), "Elevator", "An untrustworthy elevator at the top")
val messHall = Scene(Triple(4,1,4), "Mess Hall", "An untidy mess hall, with flickering lights")
val computerRoom = Scene(Triple(5,1,3), "Computer Room", "A computer room with seemingly outdated technology")
val serverRoom = Scene(Triple(5,1,2), "Server Room", "A server room, fans spinning and lights blinking")
val elevator325 = Scene(Triple(3,2,5), "Elevator", "An untrustworthy elevator at the bottom")
val hallway425 = Hallway(Triple(4,2,5))
val hallway525 = Hallway(Triple(5,2,5))
val hallway625 = Hallway(Triple(6,2,5))
val hallway624 = Hallway(Triple(6,2,4))
val hallway623 = Hallway(Triple(6,2,3))
val hallway423 = Hallway(Triple(4,2,3))
val storageRoom526 = Scene(Triple(5,2,6), "Storage Room", "A dank storage room")
val storageRoom622 = Scene(Triple(6,2,2), "Storage Room", "A smelly storage room")
val controlRoom = Scene(Triple(4,2,4),"Control Room", "A control room with an ancient computer whirring in the centre")
val generatorRoom = Scene(Triple(3,2,3),"Generator Room", "An open hall with huge diesel generators")
val elevator724 = Scene(Triple(7,2,4), "Elevator", "A sharp ledge to an empty elevator shaft. There's some dangling wires you could climb down - if you dared")
val labRoom = Scene(Triple(5,2,3), "Lab Room", "A chemistry lab with abandoned experiments in petri dishes and beakers. There are some jerry cans in the corner.")

val elevator734 = Scene(Triple(7,3,4), "Elevator", "The bottom of an empty elevator shaft")
val hallway634 = Hallway(Triple(6,3,4))
val hallway633 = Hallway(Triple(6,3,3))
val hallway635 = Hallway(Triple(6,3,5))
val portalRoom = Scene(Triple(5,3,5), "Portal Chamber", "The frame of what looks to be a portal. It is inscribed with glowing runes.")
val portalControlRoom = Scene(Triple(5,3,3), "Portal Controls", "A set of levers and buttons that seems to control the portal.")


open class Scene(open val location: Triple<Int, Int, Int>, val name: String? = null, val description: String? = null) {
    var verticalConnection = 'n'
    var locked = 0
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

    // Makes the room locked. KeyID
    fun addToMapLocked(keyId: Int) {
        gameMap[location] = this
        locked = keyId
    }
}

class Hallway(override val location: Triple<Int, Int, Int>) : Scene(Triple(0,0,0), "Hallway", "A dimly lit corridor") {}

class Item(val name: String, val spawnLocations: Array<Scene>, val type: String, val id: Int) {
    fun spawn() {
        spawnLocations[spawnLocations.indices.random()].addItem(this) // Random location in list
    }
}


// Initialise items
val facilityKey = Item("Green Keycard", arrayOf(blastDoor), "Key", 1)
val weaponRoomKey = Item("Blue Keycard", arrayOf(messHall, serverRoom), "Key", 2)
val labKey = Item("Orange Keycard", arrayOf(storageRoom526, storageRoom622), "Key", 3)

val jerryCan = Item("Jerry Can", arrayOf(labRoom), "Generic", 4)


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
    weaponsRoom.addToMapLocked(2)
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
    portalRoom.addToMap()
    portalControlRoom.addToMapLocked(2)

    // Initialise Items
    facilityKey.spawn()
    weaponRoomKey.spawn()
    labKey.spawn()

    jerryCan.spawn()

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
        add(northButton)

        eastButton = JButton("E")
        eastButton.bounds = Rectangle(BUTTONS_X + 60, BUTTONS_Y + 60,50,50)
        eastButton.font = baseFont
        eastButton.addActionListener(this)     // Handle any clicks
        add(eastButton)

        southButton = JButton("S")
        southButton.bounds = Rectangle(BUTTONS_X, BUTTONS_Y + 120,50,50)
        southButton.font = baseFont
        southButton.addActionListener(this)     // Handle any clicks
        add(southButton)

        westButton = JButton("W")
        westButton.bounds = Rectangle(BUTTONS_X - 60, BUTTONS_Y + 60,50,50)
        westButton.font = baseFont
        westButton.addActionListener(this)     // Handle any clicks
        add(westButton)

        verticalButton = JButton("-")
        verticalButton.bounds = Rectangle(BUTTONS_X, BUTTONS_Y + 60,50,50)
        verticalButton.font = baseFont
        verticalButton.addActionListener(this)     // Handle any clicks
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
            add(button)
        }
    }




    /**
     * Update the UI controls based on the current state
     * of the application model
     */
    fun updateView() {
        titleLabel.text = app.currentLocation.name
        descriptionLabel.text = "<html>" + app.currentLocation.description + "</html>" // Wrap in html tags to enable line wrapping
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

        if (app.currentLocation.adjacentScene('n') != null) {
            northButton.text = "↑"
        } else {
            northButton.text = ""
        }
        if (app.currentLocation.adjacentScene('s') != null) {
            southButton.text = "↓"
        } else {
            southButton.text = ""
        }
        if (app.currentLocation.adjacentScene('e') != null) {
            eastButton.text = "→"
        } else {
            eastButton.text = ""
        }
        if (app.currentLocation.adjacentScene('w') != null) {
            westButton.text = "←"
        } else {
            westButton.text = ""
        }

        northButton.isEnabled = app.currentLocation.adjacentScene('n')?.locked == 0
        eastButton.isEnabled = app.currentLocation.adjacentScene('e')?.locked == 0
        southButton.isEnabled = app.currentLocation.adjacentScene('s')?.locked == 0
        westButton.isEnabled = app.currentLocation.adjacentScene('w')?.locked == 0

        when (app.currentLocation.verticalConnection) {
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
        println("Down: ${e?.keyCode}")

        // Check which key was pressed and act upon it
        when (e?.keyCode) {
            KeyEvent.VK_UP    -> print("Vdown")
            KeyEvent.VK_DOWN  -> print("Vup")
        }

        // Ensure view matched the updated app model data
        updateView()
    }

    override fun keyReleased(e: KeyEvent?) {

    }

}