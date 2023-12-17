package es.danirod.gdxjam27.screen

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.Scaling
import es.danirod.gdxjam27.TheSignalGame
import es.danirod.gdxjam27.actors.ui.MenuButton

class MainMenuScreen(game: TheSignalGame) : BaseScreen(game) {

    private val forest = run {
        val texture = game.manager.get("forest.png", Texture::class.java)
        Image(texture)
    }

    private val logo = run {
        val texture = game.manager.get("thesignal.png", Texture::class.java)
        val region = TextureRegion(texture, 788, 152)
        Image(region).apply {
            setScaling(Scaling.fit)
        }
    }

    private val play = MenuButton.newButton(game, "PLAY") {
        val intro = IntroScreen(game)
        game.switchScreen(intro)
    }

    private val info = MenuButton.newButton(game, "INFO") {
        val info = InfoScreen(game)
        game.switchScreen(info)
    }

    override fun show() {
        super.show()

        forest.width = stage.viewport.worldWidth
        forest.height = stage.viewport.worldHeight
        forest.setScaling(Scaling.fillX)
        forest.align = Align.top
        stage.addActor(forest)

        val table = Table()
        table.add(logo).colspan(2).expand().padLeft(80f).padRight(80f).height(200f).row()
        table.add(play).expandX()
        table.add(info).expandX()
        table.width = stage.viewport.worldWidth
        table.y = (stage.viewport.worldHeight - table.height) / 2
        stage.addActor(table)
    }
}
