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
    var currentLocation: Scene? = entrance

    // Application logic functions
    fun move(dir: Char) {
        when (dir) {
            'n' -> currentLocation = currentLocation?.adjacentScene('n') // Nullable as buttons disabled
            'e' -> currentLocation = currentLocation?.adjacentScene('e')
            's' -> currentLocation = currentLocation?.adjacentScene('s')
            'w' -> currentLocation = currentLocation?.adjacentScene('w')
            'v' -> currentLocation = currentLocation?.adjacentScene('v')
        }
    }
}


val gameMap = mutableMapOf<Triple<Int, Int, Int>, Scene>()
val inventory = mutableListOf<Item>()

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
val elevator1 = Scene(Triple(3,1,5), "Elevator", "An untrustworthy elevator at the top")
val elevator2 = Scene(Triple(3,2,5), "Elevator", "An untrustworthy elevator at the bottom")
val messHall = Scene(Triple(4,1,4), "Mess Hall", "An untidy mess hall, with flickering lights")
val computerRoom = Scene(Triple(5,1,3), "Computer Room", "A computer room with seemingly outdated technology")
val serverRoom = Scene(Triple(5,1,2), "Server Room", "A server room, fans spinning and lights blinking")


open class Scene(open val location: Triple<Int, Int, Int>, val name: String? = null, val description: String? = null) {
    var verticalConnection = 'n'
    var unlocked = true
    val items = mutableListOf<Item>()
    fun enableVerticalConnection(direction: Char) {
        if (direction == 'u' || direction == 'd') {
            verticalConnection = direction
        }
    }

    fun lockRoom() {
        unlocked = false
    }

    fun addItem(obj: Item) {
        items.add(obj)
    }

    fun printItems(): String? {
        var itemString: String = ""
        for (item in items) {itemString += item.name + "\n"}
        return itemString
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

    fun unlockAdjacentRooms() {
        charArrayOf('n', 'e', 's', 'w').forEach { direction -> // Iterate through, unlocking each adjacent room
            if (adjacentScene(direction) != null) {
                adjacentScene(direction)!!.unlocked = true
            }
        }
    }

    fun addToMap() {
        gameMap[location] = this
    }
    fun addToMapLocked() {
        gameMap[location] = this
        lockRoom()
    }
}

class Hallway(override val location: Triple<Int, Int, Int>) : Scene(Triple(0,0,0), "Hallway", "A dimly lit corridor") {}

class Object(val name: String, val position: Scene) {

class Item(val name: String, val spawnLocations: Array<Scene>) {
    fun spawn() {
        spawnLocations[spawnLocations.indices.random()].addItem(this) // Random location in list
    }
}

// Initialise items
val weaponRoomKey = Item("Blue Keycard", arrayOf(messHall, serverRoom))


fun main() {
    FlatDarkLaf.setup()     // Flat, dark look-and-feel
    val app = App()         // Create the app model
    MainWindow(app)         // Create and show the UI, using the app model

    // Add scenes
    entrance.addToMap()
    hallway112.addToMap()
    blastDoor.addToMap()
    hallway213.addToMapLocked()
    hallway313.addToMap()
    hallway413.addToMap()
    hallway114.addToMapLocked()
    hallway115.addToMap()
    hallway215.addToMap()
    weaponsRoom.addToMapLocked()
    elevator1.addToMap()
    elevator1.enableVerticalConnection('d')
    messHall.addToMap()
    computerRoom.addToMap()
    serverRoom.addToMap()

    elevator2.addToMap()
    elevator2.enableVerticalConnection('u')

    // Initialise Items
    weaponRoomKey.spawn()

}

/**
 * Main UI window (view)
 * Defines the UI and responds to events
 * The app model should be passwd as an argument
 */
class MainWindow(val app: App) : JFrame(), ActionListener {

    // Fields to hold the UI elements
    private lateinit var clicksLabel: JLabel
    private lateinit var northButton: JButton
    private lateinit var eastButton: JButton
    private lateinit var southButton: JButton
    private lateinit var westButton: JButton
    private lateinit var verticalButton: JButton
    private lateinit var unlockButton: JButton
    private lateinit var descriptionLabel: JLabel
    private lateinit var itemListLabel: JLabel
    private lateinit var itemsLabel: JLabel
    private lateinit var inventoryLabel: JLabel
    private lateinit var inventoryButton1 : JButton
    private lateinit var inventoryButton2 : JButton
    private lateinit var inventoryButton3 : JButton
    private lateinit var inventoryButton4 : JButton
    private lateinit var inventoryButton5 : JButton
    private lateinit var inventoryButton6 : JButton
    private lateinit var inventoryButton7 : JButton
    private lateinit var inventoryButton8 : JButton

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

        val BUTTONS_X = 100
        val BUTTONS_Y = 100

        clicksLabel = JLabel("CLICK INFO HERE")
        clicksLabel.horizontalAlignment = SwingConstants.CENTER
        clicksLabel.bounds = Rectangle(50, 50, 500, 100)
        clicksLabel.font = baseFont
        add(clicksLabel)

        descriptionLabel = JLabel("DESCRIPTION HERE")
        descriptionLabel.horizontalAlignment = SwingConstants.CENTER
        descriptionLabel.bounds = Rectangle(100, 150, 500, 100)
        descriptionLabel.font = smallFont
        add(descriptionLabel)

        itemListLabel = JLabel("ITEM LIST")
        itemListLabel.horizontalAlignment = SwingConstants.CENTER
        itemListLabel.bounds = Rectangle(300, 150, 500, 300)
        itemListLabel.font = smallFont
        add(itemListLabel)

        northButton = JButton("N")
        northButton.bounds = Rectangle(BUTTONS_X, BUTTONS_Y,50,50)
        northButton.font = baseFont
        northButton.addActionListener(this)     // Handle any clicks
        add(northButton)

        eastButton = JButton("E")
        eastButton.bounds = Rectangle(BUTTONS_X + 50, BUTTONS_Y + 50,50,50)
        eastButton.font = baseFont
        eastButton.addActionListener(this)     // Handle any clicks
        add(eastButton)

        southButton = JButton("S")
        southButton.bounds = Rectangle(BUTTONS_X, BUTTONS_Y + 100,50,50)
        southButton.font = baseFont
        southButton.addActionListener(this)     // Handle any clicks
        add(southButton)

        westButton = JButton("W")
        westButton.bounds = Rectangle(BUTTONS_X - 50, BUTTONS_Y + 50,50,50)
        westButton.font = baseFont
        westButton.addActionListener(this)     // Handle any clicks
        add(westButton)

        verticalButton = JButton("-")
        verticalButton.bounds = Rectangle(BUTTONS_X, BUTTONS_Y + 50,50,50)
        verticalButton.font = baseFont
        verticalButton.addActionListener(this)     // Handle any clicks
        add(verticalButton)

        unlockButton = JButton("Unlock")
        unlockButton.bounds = Rectangle(BUTTONS_X - 50, BUTTONS_Y + 200,150,50)
        unlockButton.font = baseFont
        unlockButton.addActionListener(this)     // Handle any clicks
        add(unlockButton)

        itemsLabel = JLabel("Items")
        itemsLabel.horizontalAlignment = SwingConstants.CENTER
        itemsLabel.bounds = Rectangle(400, 20, 200, 100)
        itemsLabel.font = baseFont
        add(itemsLabel)

        inventoryLabel = JLabel("Inventory")
        inventoryLabel.horizontalAlignment = SwingConstants.CENTER
        inventoryLabel.bounds = Rectangle(700, 20, 200, 100)
        inventoryLabel.font = baseFont
        add(inventoryLabel)

        val INVENTORY_X = 500
        val INVENTORY_Y = 100
        val INVENTORY_HEIGHT = 30
        val INVENTORY_WIDTH = 150
        val INVENTORY_SPACING = 10

        inventoryButton1 = JButton("")
        inventoryButton1.bounds = Rectangle(INVENTORY_X, INVENTORY_Y, INVENTORY_WIDTH, INVENTORY_HEIGHT)
        inventoryButton1.font = smallFont
        inventoryButton1.addActionListener(this)
        add(inventoryButton1)

        inventoryButton2 = JButton("")
        inventoryButton2.bounds = Rectangle(INVENTORY_X, INVENTORY_Y + (INVENTORY_HEIGHT + INVENTORY_SPACING) * 1, INVENTORY_WIDTH, INVENTORY_HEIGHT)
        inventoryButton2.font = smallFont
        inventoryButton2.addActionListener(this)
        add(inventoryButton2)

        inventoryButton3 = JButton("")
        inventoryButton3.bounds = Rectangle(INVENTORY_X, INVENTORY_Y + (INVENTORY_HEIGHT + INVENTORY_SPACING) * 2, INVENTORY_WIDTH, INVENTORY_HEIGHT)
        inventoryButton3.font = smallFont
        inventoryButton3.addActionListener(this)
        add(inventoryButton3)

        inventoryButton4 = JButton("")
        inventoryButton4.bounds = Rectangle(INVENTORY_X, INVENTORY_Y + (INVENTORY_HEIGHT + INVENTORY_SPACING) * 3, INVENTORY_WIDTH, INVENTORY_HEIGHT)
        inventoryButton4.font = smallFont
        inventoryButton4.addActionListener(this)
        add(inventoryButton4)

        inventoryButton5 = JButton("")
        inventoryButton5.bounds = Rectangle(INVENTORY_X, INVENTORY_Y + (INVENTORY_HEIGHT + INVENTORY_SPACING) * 4, INVENTORY_WIDTH, INVENTORY_HEIGHT)
        inventoryButton5.font = smallFont
        inventoryButton5.addActionListener(this)
        add(inventoryButton5)
    }






    /**
     * Update the UI controls based on the current state
     * of the application model
     */
    fun updateView() {
        clicksLabel.text = app.currentLocation?.name
        descriptionLabel.text = app.currentLocation?.description
        itemListLabel.text = app.currentLocation?.printItems()
        println(app.currentLocation?.printItems())

        if (app.currentLocation?.adjacentScene('n') != null) {
            northButton.text = "↑"
        } else {
            northButton.text = ""
        }
        if (app.currentLocation?.adjacentScene('s') != null) {
            southButton.text = "↓"
        } else {
            southButton.text = ""
        }
        if (app.currentLocation?.adjacentScene('e') != null) {
            eastButton.text = "→"
        } else {
            eastButton.text = ""
        }
        if (app.currentLocation?.adjacentScene('w') != null) {
            westButton.text = "←"
        } else {
            westButton.text = ""
        }

        northButton.isEnabled = app.currentLocation?.adjacentScene('n')?.unlocked ?: false
        eastButton.isEnabled = app.currentLocation?.adjacentScene('e')?.unlocked ?: false
        southButton.isEnabled = app.currentLocation?.adjacentScene('s')?.unlocked ?: false
        westButton.isEnabled = app.currentLocation?.adjacentScene('w')?.unlocked ?: false

        when (app.currentLocation?.verticalConnection) {
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
            unlockButton -> app.currentLocation?.unlockAdjacentRooms()
            verticalButton -> app.move('v')
        }
        updateView()
    }

}

