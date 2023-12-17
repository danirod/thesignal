package es.danirod.gdxjam27.actors.ui

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Table
import es.danirod.gdxjam27.State
import es.danirod.gdxjam27.TheSignalGame

class ComputerScreen(game: TheSignalGame, private val state: State) : Table() {

    private val bufferLabel = game.label("BUFFER:", 0.25f)

    private val bufferBar = run {
        val bar = game.manager.get("bar.png", Texture::class.java)
        val palette = game.manager.get("barcolor.png", Texture::class.java)
        Bar(bar, palette, state.buffer)
    }

    private val desktop = Table()

    private val bsod = run {
        val texture = game.manager.get("bsod.png", Texture::class.java)
        val region = TextureRegion(texture, 720, 400)
        Image(region)
    }

    init {
        this.add(bufferLabel).padRight(10f)
        this.add(bufferBar).fill().expandX().padTop(1f).padBottom(1f)
        this.row()
        this.add(desktop).colspan(2).fill().expand().row()
    }

    fun setApplication(app: Actor) {
        desktop.clearChildren()
        desktop.add(app).fill().expand()
    }

    override fun act(delta: Float) {
        super.act(delta)

        // Update the bar if the strength level has recently changed
        if (lastValue != state.buffer) {
            bufferBar.value = state.buffer
            lastValue = state.buffer
        }
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)
    }

    fun triggerBSOD() {
        clear()
        add(bsod).fill().expand()
    }

    // Just so that I am not constantly updating the bar.
    private var lastValue = -1
}
