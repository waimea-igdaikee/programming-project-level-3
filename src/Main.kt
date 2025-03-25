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
import com.formdev.flatlaf.json.Location
import jdk.jfr.Description
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


open class Scene(open val location: Triple<Int, Int, Int>, val name: String?, val description: String?) {
    fun addToMap() {
        gameMap[location] = this
    }
    fun adjacentScene(dir: Char): Scene? {
        return when (dir) {
            'n' -> gameMap[Triple(location.first, location.second, location.third - 1)]
            'e' -> gameMap[Triple(location.first + 1, location.second, location.third)]
            's' -> gameMap[Triple(location.first, location.second, location.third + 1)]
            'w' -> gameMap[Triple(location.first - 1, location.second, location.third)]
            else -> null
        }
    }
}

class Hallway(override val location: Triple<Int, Int, Int>) : Scene(Triple(0,0,0), "Hallway", "A dimly lit corridor") {}

//class Hallway : Scene("Hallway", Triple(1,1,1), "A dimly lit hallway") {
//} Need some better system for attributes

fun main() {
    FlatDarkLaf.setup()     // Flat, dark look-and-feel
    val app = App()         // Create the app model
    MainWindow(app)         // Create and show the UI, using the app model

    // Add scenes
    entrance.addToMap()
    hallway112.addToMap()
    blastDoor.addToMap()
    hallway213.addToMap()
    hallway313.addToMap()
    hallway413.addToMap()


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
    private lateinit var startButton: JButton

    /**
     * Configure the UI and display it
     */
    init {
        configureWindow()               // Configure the window
        addControls()                   // Build the UI

        setLocationRelativeTo(null)     // Centre the window
        isVisible = true                // Make it visible

        updateView()                    // Initialise the UI
    }

    /**
     * Configure the main window
     */
    private fun configureWindow() {
        title = "Kotlin Swing GUI Demo"
        contentPane.preferredSize = Dimension(1200, 700)
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        isResizable = false
        layout = null

        pack()
    }

    /**
     * Populate the UI with UI controls
     */
    private fun addControls() {
        val baseFont = Font(Font.SANS_SERIF, Font.PLAIN, 36)

        val BUTTONS_X = 100
        val BUTTONS_Y = 100

        clicksLabel = JLabel("CLICK INFO HERE")
        clicksLabel.horizontalAlignment = SwingConstants.CENTER
        clicksLabel.bounds = Rectangle(50, 50, 500, 100)
        clicksLabel.font = baseFont
        add(clicksLabel)

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

        startButton = JButton("Start")
        startButton.bounds = Rectangle(BUTTONS_X + 100, BUTTONS_Y + 50,50,50)
        startButton.font = baseFont
        startButton.addActionListener(this)     // Handle any clicks
        add(startButton)
    }



    /**
     * Update the UI controls based on the current state
     * of the application model
     */
    fun updateView() {

        clicksLabel.text = app.currentLocation?.name
        northButton.isEnabled = app.currentLocation?.adjacentScene('n') != null
        eastButton.isEnabled = app.currentLocation?.adjacentScene('e') != null
        southButton.isEnabled = app.currentLocation?.adjacentScene('s') != null
        westButton.isEnabled = app.currentLocation?.adjacentScene('w') != null
    }

    /**
     * Handle any UI events (e.g. button clicks)
     * Usually this involves updating the application model
     * then refreshing the UI view
     */
    override fun actionPerformed(e: ActionEvent?) {
        when (e?.source) {
            northButton -> {
                app.move('n')
                updateView()
            }
            eastButton -> {
                app.move('e')
                updateView()
            }
            southButton -> {
                app.move('s')
                updateView()
            }
            westButton -> {
                app.move('w')
                updateView()
            }
            startButton -> {
                updateView()
            }
        }
    }

}

