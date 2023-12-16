package es.danirod.gdxjam27.actors.ui

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Scaling

class Bar(bar: Texture, private val palette: Texture, initial: Int): Table() {

    private val pixmap = preparePixmap()

    var value: Int = 0
        set(value) {
            field = when {
                value < 0 -> 0
                value > 31 -> 31
                else -> value
            }
            for (visible in 0..field) {
                getChild(visible).color.a = 1f
            }
            for (invisible in (field+1)..31) {
                getChild(invisible).color.a = 0f
            }
        }

    init {
        for (i in 0..31) {
            val slot = Image(bar)
                .apply { color = getColorAtPixel(i) }
                .also { it.setScaling(Scaling.stretch) }
            add(slot).expand().fill()
        }
        value = initial
    }

    private fun preparePixmap() = palette.textureData.let { td ->
        if (!td.isPrepared) {
            td.prepare()
        }
        td.consumePixmap()
    }

    private fun getColorAtPixel(pos: Int) = Color(pixmap.getPixel(pos, 0))

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)
    }

}
