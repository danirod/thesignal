package es.danirod.gdxjam27.actors.ui

import com.badlogic.gdx.Input
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import es.danirod.gdxjam27.screen.GameScreen

class WarningProgram(manager: AssetManager, private val screen: GameScreen) : Table() {

    private inner class PressEnterEventListener : InputListener() {
        override fun keyDown(event: InputEvent, keycode: Int) = keycode == Input.Keys.ENTER

        override fun keyUp(event: InputEvent, keycode: Int): Boolean {
            screen.stateOfEmergency()
            return true
        }
    }

    private val alert = manager.get("gameover.ogg", Sound::class.java)

    private val errorMessage = run {
        val messageCard = manager.get("warning1.png", Texture::class.java)
        val titleCard = manager.get("warning2.png", Texture::class.java)
        Alert(messageCard, titleCard)
    }

    init {
        addListener(PressEnterEventListener())
        add(errorMessage).pad(30f, 50f, 30f, 50f).align(Align.center).fill().expand()
        alert.play(0.75f)
    }
}
