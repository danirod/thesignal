package es.danirod.gdxjam27.actors.env

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import es.danirod.gdxjam27.screen.GameScreen

class Window(manager: AssetManager, private val screen: GameScreen) : Group() {

    private val background = manager.get("forest.png", Texture::class.java)
    private val antenna = manager.get("antenna.png", Texture::class.java)
    private val alien = manager.get("alien.png", Texture::class.java)

    private val explosion = manager.get("explosion.ogg", Sound::class.java)
    private val distantExplosion = manager.get("distant_explosion.ogg", Sound::class.java)
    private val nuclear = manager.get("nuclear.ogg", Sound::class.java)

    private val fire = manager.get("explosion.png", Texture::class.java)

    private val antennas = listOf(
        Antenna(antenna).apply {
            setPosition(-100f, -50f)
            setScale(0.7f)
            color.a = 0.6f
        },
        Antenna(antenna).apply {
            setPosition(80f, 50f)
            setScale(0.6f)
            color.a = 0.5f
        },
        Antenna(antenna).apply {
            setPosition(750f, -50f)
            setScale(-0.7f, 0.7f)
            color.a = 0.6f
        },
        Antenna(antenna).apply {
            setPosition(620f, 50f)
            setScale(-0.6f, 0.6f)
            color.a = 0.5f
        },
    )

    private val backgroundActor = Image(background)

    private val focalPoint = Vector2(0.5f, 01f)

    private var ticks = 0f

    init {
        setSize(backgroundActor.width, backgroundActor.height)
        addActor(backgroundActor)
        antennas.forEach(this::addActor)
    }

    override fun act(delta: Float) {
        super.act(delta)

        ticks += delta
        focalPoint.x = MathUtils.sin(ticks * 0.5f) * 0.25f + 0.25f
        updateFocus()
    }

    var rayEnabled: Boolean = false
        set(value) {
            field = value
            antennas.forEach { a -> a.rayEnabled = value }
        }

    private fun updateFocus() {
        val focalCoordinates = focalPoint.cpy().scl(width, height)
        antennas.forEach {a -> a.rayFocus.set(focalCoordinates) }
    }

    fun destroyAnntenas() {
        explosion.play(0.6f)

        antennas.forEach { a ->
            val zigzag = Actions.repeat(25, Actions.sequence(
                Actions.moveBy(-30f, -5f, 0.015f),
                Actions.moveBy(60f, -10f, 0.03f),
                Actions.moveBy(-30f, -5f, 0.015f)
            ))

            val explode = Actions.run {
                a.drawable = TextureRegionDrawable(fire)
                a.y = 0f
                distantExplosion.play(MathUtils.random(0.8f, 1.2f), MathUtils.random(0.8f, 1.2f), 0f)
                a.addAction(Actions.parallel(
                    Actions.moveBy(0f, 150f, 1f),
                    Actions.fadeOut(1f),
                ))
            }

            a.addAction(Actions.sequence(zigzag, explode, Actions.delay(1f)))
        }

        antennas[0].addAction(Actions.after(Actions.run { initEndOfTheWorld() }))
    }

    fun initEndOfTheWorld() {
        nuclear.play(0.35f)

        val image = Image(alien)
        image.setPosition(width / 4, height)
        image.rotation = 45f
        image.align = Align.center

        image.addAction(Actions.sequence(
            Actions.parallel(Actions.moveBy(0f, -height / 2, 3f, Interpolation.sineOut), Actions.rotateBy(-25f, 3f)),
            Actions.rotateBy(-25f, 3f),
            Actions.parallel(
                Actions.sequence(
                    Actions.delay(6f),
                    Actions.run { screen.fadeOutAndGameOver() },
                ),
                Actions.scaleBy(6f, 6f, 12f, Interpolation.sineIn),
                Actions.moveBy(-800f, -300f, 12f),
            )
        ))

        addActor(image)
    }

}
