package es.danirod.gdxjam27.actors.game

import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Stack
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import es.danirod.gdxjam27.TheSignalGame

class Dialog(game: TheSignalGame, message: CharSequence, background: Boolean, private val callback: () -> Unit): Stack() {

    private val content = game.label("$message\n", 0.25f).apply {
        wrap = true
    }

    private val bubble = run {
        val texture = game.manager.get("dialog.png", Texture::class.java)
        val region = TextureRegion(texture, 700, 150)
        Image(region)
    }

    init {
        addListener(EnterInputListener())
        if (background) {
            add(bubble)
            val t = Table()
            t.add(content).fill().expand().pad(30f)
            add(t)
        } else {
            add(content)
        }

        val pressEnter = Table()
        val pressEnterText = game.label("PRESS ENTER TO CONTINUE", 0.15f)
        pressEnter.add(pressEnterText).align(Align.bottomRight).pad(0f, 0f, 5f, 10f).expand()
        add(pressEnter)
    }

    private inner class EnterInputListener : InputListener() {
        override fun keyDown(event: InputEvent?, keycode: Int) = when (keycode) {
            Input.Keys.ENTER -> true
            Input.Keys.SPACE -> true
            else -> false
        }

        override fun keyUp(event: InputEvent?, keycode: Int): Boolean {
            callback()
            return true
        }
    }



}
