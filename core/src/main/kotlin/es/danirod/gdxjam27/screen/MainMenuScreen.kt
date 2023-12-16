package es.danirod.gdxjam27.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.Scaling
import es.danirod.gdxjam27.TheSignalGame
import es.danirod.gdxjam27.actors.ui.MenuButton

class MainMenuScreen(private val game: TheSignalGame) : ScreenAdapter() {

    private val forest = run {
        val texture = game.manager.get("forest.png", Texture::class.java)
        Image(texture)
    }

    private val logo = run {
        val texture = game.manager.get("thesignal.png", Texture::class.java)
        val region = TextureRegion(texture, 788, 152)
        Image(region)
    }

    private val play = MenuButton.newButton(game, "PLAY") {
        val intro = IntroScreen(game)
        game.switchScreen(intro)
    }

    private val info = MenuButton.newButton(game, "INFO") {
        val info = InfoScreen(game)
        game.switchScreen(info)
    }

    private val stage = Stage()

    override fun show() {
        forest.width = stage.viewport.worldWidth
        forest.height = stage.viewport.worldHeight
        forest.setScaling(Scaling.fillX)
        forest.align = Align.top

        stage.addActor(forest)
        logo.setPosition((stage.viewport.worldWidth - logo.width) / 2, stage.viewport.worldHeight - logo.height - 50)
        stage.addActor(logo)

        play.setPosition(150f, 120f)
        info.setPosition(550f, 120f)
        stage.addActor(play)
        stage.addActor(info)
        Gdx.input.inputProcessor = stage
    }

    override fun render(delta: Float) {
        super.render(delta)
        stage.act(delta)
        stage.draw()
    }

}
