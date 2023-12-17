package es.danirod.gdxjam27.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import es.danirod.gdxjam27.State
import es.danirod.gdxjam27.TheSignalGame
import es.danirod.gdxjam27.actors.env.Window
import es.danirod.gdxjam27.actors.game.Dialog
import es.danirod.gdxjam27.actors.ui.*

class GameScreen(game: TheSignalGame, private val state: State) : BaseScreen(game) {

    /** Whatever you see outside of the window, including the aliens. */
    private val outsideWindow = Window(game.manager, this).apply {
        setScale(0.3f)
        setPosition(675f, 190f)
    }

    /** The main fullscreen image which also acts as a cover for the window and as a background for the PC. */
    private val mainConsoleImage = run {
        val consoleTexture = game.manager.get("console.png", Texture::class.java)
        val consoleRegion = TextureRegion(consoleTexture, 0, 0, 854, 480)
        Image(consoleRegion).apply {
            setBounds(0f, 0f, 854f, 480f)
        }
    }

    /** Printer used to render characters. */
    private val printer = run {
        val numbers = game.manager.get("numbers.png", Texture::class.java)
        CharacterPrinter(numbers)
    }

    /** The computer screen where the data is presented. */
    private val computerScreen = ComputerScreen(game, state).also { actor ->
        actor.setPosition(75f, 170f)
        actor.setSize(350f, 220f)
    }

    private val gameStartScript = listOf(
        "Oh, snap! An invasion. I thought this would be a quiet night.",
        "The feed system is out of power.",
        "I'll have to manually run the algorithm to keep those rays on.",
        "I think I remember this from my training program when I joined the company.",
        "I have to keep finishing the formulas I see in the screen.",
        "(Use the NUMBER KEYS to type the result of the operation.)",
        "(Use the BACKSPACE key if you have to delete something.)",
        "(Use the ENTER key to submit your answer to the system.)",
        "(The buffer will hold the pending operations as they come in.)",
        "(Do not let the operations fill the buffer or the system will crash.)",
        "(Good luck.)",
    )

    /** Will not update the state timer unless this is true. */
    private var isActuallyPlaying = false

    /** Configure the main UI. */
    private fun setUpStage() {
        stage.addActor(outsideWindow)
        stage.addActor(mainConsoleImage)
        stage.addActor(computerScreen)
    }

    /** Starts the game. */
    private fun initGame() {
        val soundError = game.manager.get("error.ogg", Sound::class.java)
        val soundQuestion = game.manager.get("question.ogg", Sound::class.java)
        val app = ProcessingProgram(state, printer, soundError, soundQuestion)
        computerScreen.setApplication(app)
        app.scheduleNextChallenge()
    }

    override fun show() {
        super.show()
        setUpStage()
        initFadeIn()
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        if (isActuallyPlaying)
            state.addTime(delta)

        try {
            stage.act()
        } catch (e: ProcessingProgram.QueueOverflowException) {
            isActuallyPlaying = false
            val error = KernelPanicProgram(game.manager, this)
            computerScreen.setApplication(error)
            stage.keyboardFocus = null
            stage.root.addAction(
                Actions.sequence(
                    Actions.delay(0.25f),
                    Actions.run { stage.keyboardFocus = error }
                )
            )
            outsideWindow.rayEnabled = false
            stage.act()
        }

        stage.draw()
    }

    fun stateOfEmergency() {
        computerScreen.triggerBSOD()
        outsideWindow.destroyAnntenas()
    }

    private fun initFadeIn() {
        val blank = Image(game.manager.get("blank.png", Texture::class.java))
        blank.width = stage.viewport.worldWidth
        blank.height = stage.viewport.worldHeight
        blank.setPosition(0f, 0f)
        blank.addAction(
            Actions.sequence(
                Actions.alpha(0f, 2f),
                Actions.delay(3f),
                Actions.run(this::afterFadeIn)
            )
        )
        stage.addActor(blank)

        val ambient = game.manager.get("ambient.ogg", Sound::class.java)
        ambient.play(0.5f)
    }

    private fun afterFadeIn() {
        val program = WarningProgram(game.manager, this)
        computerScreen.setApplication(program)

        computerScreen.addAction(
            Actions.sequence(
                Actions.delay(3f),
                Actions.run { nextDialogLine(0) },
            ),
        )
    }

    private fun nextDialogLine(line: Int) {
        val dialog = stage.actors.find { ch -> ch is Dialog }
        dialog?.let { dialog.remove() }

        if (line == gameStartScript.size) {
            // End of dialog, start of game
            outsideWindow.rayEnabled = true
            isActuallyPlaying = true
            initGame()
        } else {
            val dialogLine = Dialog(game, gameStartScript[line], true) {
                nextDialogLine(line + 1)
            }
            dialogLine.setSize(700f, 150f)
            dialogLine.x = (stage.viewport.worldWidth - dialogLine.width ) / 2f
            dialogLine.y = (stage.viewport.worldHeight - dialogLine.height ) / 2f
            stage.addActor(dialogLine)
            stage.keyboardFocus = dialogLine
        }
    }

    fun fadeOutAndGameOver() {
        val blank = Image(game.manager.get("blank.png", Texture::class.java))
        blank.width = stage.viewport.worldWidth
        blank.height = stage.viewport.worldHeight
        blank.setPosition(0f, 0f)
        blank.isVisible = false
        blank.addAction(
            Actions.sequence(
                Actions.alpha(0f),
                Actions.show(),
                Actions.alpha(1f, 4f),
                Actions.delay(1f),
                Actions.run {
                    val gameOver = GameOverScreen(game, state)
                    game.switchScreen(gameOver)
                }
            )
        )
        stage.addActor(blank)
    }

}
