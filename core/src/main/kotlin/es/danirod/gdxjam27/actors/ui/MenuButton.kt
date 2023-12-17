package es.danirod.gdxjam27.actors.ui

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import es.danirod.gdxjam27.TheSignalGame

object MenuButton {

    fun newButton(game: TheSignalGame, text: String, callback: () -> Unit): Actor {
        val style = TextButtonStyle()
        style.font = game.manager.get("fixedsys.fnt", BitmapFont::class.java)
        style.fontColor = Color.WHITE
        style.overFontColor = Color.CYAN

        val button = TextButton(text, style)
        button.isTransform = true
        button.setScale(0.66f)
        button.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                callback()
            }

        })

        return button
    }
}
