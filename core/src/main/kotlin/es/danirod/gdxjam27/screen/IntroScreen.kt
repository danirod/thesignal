package es.danirod.gdxjam27.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport
import es.danirod.gdxjam27.State
import es.danirod.gdxjam27.TheSignalGame
import es.danirod.gdxjam27.actors.game.Dialog

class IntroScreen(private val game: TheSignalGame): ScreenAdapter() {

    val stage = Stage(ScreenViewport())

    val dialogs = listOf(
        "I like working in the night shift. Nothing ever happens during night shift.",
        "The observatory is empty and I am alone with my thoughts.",
        "And I get to work only with the noise of the radios, the beeps of the telescopes and the signals of the pulsars.",
        "I like this job.",
    )

    override fun show() {
        nextLine(0)
        Gdx.input.inputProcessor = stage
    }

    private fun nextLine(line: Int) {
        stage.clear()

        if (line == dialogs.size) {
            val nextScreen = GameScreen(game, State(State.Difficulty.Normal))
            game.switchScreen(nextScreen)
            return
        }

        val dialogLine = Dialog(game, dialogs.get(line), false) {
            nextLine(line + 1)
        }
        dialogLine.setSize(700f, 150f)
        dialogLine.setPosition(
            (stage.viewport.worldWidth - dialogLine.width) / 2,
            (stage.viewport.worldHeight - dialogLine.height) / 2
        )
        stage.addActor(dialogLine)
        stage.keyboardFocus = dialogLine
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        stage.act(delta)
        stage.draw()
    }

}
