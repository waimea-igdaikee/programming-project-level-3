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
import jdk.jfr.Description
import java.awt.*
import java.awt.event.*
import javax.swing.*


/**
 * Launch the application
 */
fun main() {
    FlatDarkLaf.setup()     // Flat, dark look-and-feel
    val app = App()         // Create the app model
    MainWindow(app)         // Create and show the UI, using the app model
}


/**
 * The application class (model)
 * This is the place where any application data should be
 * stored, plus any application logic functions
 */
class App() {
    // Constants defining any key values
    val MAX_CLICKS = 10

    // Data fields
    var clicks = 0

    // Application logic functions
    fun updateClickCount() {
        clicks++
        if (clicks > MAX_CLICKS) clicks = MAX_CLICKS
    }
}

class Scene(val name: String, val description: String, val colour: Color, ) {

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
    }


    /**
     * Update the UI controls based on the current state
     * of the application model
     */
    fun updateView() {
        clicksLabel.text = "Button Press"
    }

    /**
     * Handle any UI events (e.g. button clicks)
     * Usually this involves updating the application model
     * then refreshing the UI view
     */
    override fun actionPerformed(e: ActionEvent?) {
        when (e?.source) {
            northButton -> {
                app.updateClickCount()
                updateView()
            }
        }
    }

}

