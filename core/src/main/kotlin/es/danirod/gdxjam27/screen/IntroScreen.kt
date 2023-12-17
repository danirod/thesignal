package es.danirod.gdxjam27.screen

import es.danirod.gdxjam27.State
import es.danirod.gdxjam27.TheSignalGame
import es.danirod.gdxjam27.actors.game.Dialog

class IntroScreen(game: TheSignalGame): BaseScreen(game) {

    val dialogs = listOf(
        "I like working in the night shift. Nothing ever happens during night shift.",
        "The observatory is empty and I am alone with my thoughts.",
        "And I get to work only with the noise of the radios, the beeps of the telescopes and the signals of the pulsars.",
        "I like this job.",
    )

    override fun show() {
        super.show()
        nextLine(0)
    }

    private fun nextLine(line: Int) {
        stage.clear()

        if (line == dialogs.size) {
            val nextScreen = GameScreen(game, State(State.Difficulty.Normal))
            game.switchScreen(nextScreen)
            return
        }

        val dialogLine = Dialog(game, dialogs[line], false) {
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
}
