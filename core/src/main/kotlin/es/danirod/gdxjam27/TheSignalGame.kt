package es.danirod.gdxjam27

import com.badlogic.gdx.Game
import com.badlogic.gdx.Screen
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import es.danirod.gdxjam27.screen.MainMenuScreen

class TheSignalGame : Game() {

    lateinit var manager: AssetManager

    private val images = listOf(
        "alien.png",
        "antenna.png",
        "bar.png",
        "blank.png",
        "bsod.png",
        "barcolor.png",
        "buffer.png",
        "console.png",
        "dialog.png",
        "explosion.png",
        "forest.png",
        "lostsatellite1.png",
        "lostsatellite2.png",
        "numbers.png",
        "strength.png",
        "thesignal.png",
        "warning1.png",
        "warning2.png",
        "ui/knob.png",
        "ui/scroll.png",
    )

    private val sounds = listOf(
        "ambient.ogg",
        "distant_explosion.ogg",
        "error.ogg",
        "explosion.ogg",
        "gameover.ogg",
        "nuclear.ogg",
        "question.ogg",
    )

    private val fonts = listOf(
        "fixedsys.fnt",
    )

    override fun create() {
        manager = AssetManager()
        images.forEach { img -> manager.load(img, Texture::class.java) }
        sounds.forEach { snd -> manager.load(snd, Sound::class.java) }
        fonts.forEach { fnt -> manager.load(fnt, BitmapFont::class.java) }
        manager.finishLoading()
        startGame()
    }

    /** LabelStyle must be lazy so that it doesn't instantiate until the asset is loaded. */
    private val labelStyle: LabelStyle by lazy {
        LabelStyle(manager.get("fixedsys.fnt", BitmapFont::class.java), Color.WHITE)
    }

    /** Wrapper to generate a label. */
    fun label(text: CharSequence, scale: Float) = Label(text, labelStyle).apply {
        setFontScale(scale)
        setScale(scale)
    }

    fun switchScreen(newScreen: Screen) {
        val oldScreen = getScreen()
        setScreen(newScreen)
        oldScreen?.dispose()
    }

    private fun startGame() {
        val intro = MainMenuScreen(this)
        switchScreen(intro)
    }

    override fun dispose() {
        super.dispose()
        manager.dispose()
    }
}
