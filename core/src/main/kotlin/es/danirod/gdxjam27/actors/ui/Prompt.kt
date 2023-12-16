package es.danirod.gdxjam27.actors.ui

import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup

class Prompt(private val program: ProcessingProgram, private val printer: CharacterPrinter) : HorizontalGroup() {

    private var input: String = ""

    private inner class PromptInputListener : InputListener() {
        override fun keyDown(event: InputEvent?, keycode: Int) = when {
            keycode >= Input.Keys.NUM_0 && keycode <= Input.Keys.NUM_9 -> {
                val realValue = keycode - Input.Keys.NUM_0
                if (input.length < 7)
                    input += realValue.toString()
                repaintPrompt()
                true
            }
            keycode >= Input.Keys.NUMPAD_0 && keycode <= Input.Keys.NUMPAD_9 -> {
                val realValue = keycode - Input.Keys.NUMPAD_0
                if (input.length < 7)
                    input += realValue.toString()
                repaintPrompt()
                true
            }
            keycode == Input.Keys.BACKSPACE -> {
                input = input.dropLast(1)
                repaintPrompt()
                true
            }
            keycode == Input.Keys.ENTER || keycode == Input.Keys.NUMPAD_ENTER ->
                if (input.isNotBlank()) {
                    program.receiveInput(input)
                    repaintPrompt()
                    true
                } else {
                    false
                }
            else -> false
        }
    }

    init {
        addListener(PromptInputListener())
        repaintPrompt()
    }

    private fun repaintPrompt() {
        clearChildren()
        addActor(printer.percentage())
        if (!input.isBlank())
            addActor(printer.number(input.toInt()))
        addActor(printer.underscore())
    }

    fun clearInput() {
        this.input = ""
        repaintPrompt()
    }
}
