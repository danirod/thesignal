package es.danirod.gdxjam27.actors.ui

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup
import com.badlogic.gdx.scenes.scene2d.ui.Image

class CharacterPrinter(texture: Texture) {

    private val region = TextureRegion(texture, 6 * 40, 4 * 64)

    private val regions = region.split(40, 64)

    /** Gets some symbols. */
    fun plus() = Image(regions[0][5])
    fun equals() = Image(regions[1][5])
    fun div() = Image(regions[2][2])
    fun percentage() = Image(regions[2][0])
    fun underscore() = Image(regions[2][1])

    /** Gets a single digit as an image actor. */
    private fun get(number: Int) = Image(regions[number / 5][number % 5])

    /** Gets a complete number as a group of image actors. */
    fun number(value: Int): Group {
        val g = HorizontalGroup()

        for (digit in value.toString()) {
            val image = get(digit - '0')
            g.addActor(image)
        }

        g.layout()
        g.validate()

        return g
    }

}
