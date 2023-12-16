package es.danirod.gdxjam27.actors.ui

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import es.danirod.gdxjam27.TheSignalGame

object MenuButton {

    fun newButton(game: TheSignalGame, text: CharSequence, callback: () -> Unit) = game.label(text)
        .apply {
            setScale(3f)
            setFontScale(3f)
        }.also { lbl ->
            lbl.addListener(object : InputListener() {
                override fun enter(event: InputEvent, x: Float, y: Float, pointer: Int, fromActor: Actor?) {
                    event.target.color = Color.CYAN
                }
                override fun exit(event: InputEvent, x: Float, y: Float, pointer: Int, toActor: Actor?) {
                    event.target.color = Color.WHITE
                }
                override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean = button == 0
                override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                    callback()
                }
            })
        }
}
