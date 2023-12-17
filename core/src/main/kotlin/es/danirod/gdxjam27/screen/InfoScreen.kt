package es.danirod.gdxjam27.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import es.danirod.gdxjam27.TheSignalGame
import es.danirod.gdxjam27.actors.ui.MenuButton

class InfoScreen(game: TheSignalGame) : BaseScreen(game) {

    private val credits = Gdx.files.internal("info.txt").readString()

    private val area = run {
        val label = game.label(credits, 0.2f)
        label.wrap = true

        val group = VerticalGroup()
        group.addActor(label)
        group.grow().pad(0f, 0f, 0f, 10f)

        val style = ScrollPaneStyle()
        style.vScrollKnob = TextureRegionDrawable(game.manager.get("ui/knob.png", Texture::class.java))
        style.vScroll = TextureRegionDrawable(game.manager.get("ui/scroll.png", Texture::class.java))

        val credits = ScrollPane(group, style)
        credits.setBounds(20f, 100f, stage.viewport.worldWidth - 40, stage.viewport.worldHeight - 120)
        credits.fadeScrollBars = false
        credits
    }

    private val back = MenuButton.newButton(game, "BACK") {
        val mainMenu = MainMenuScreen(game)
        game.switchScreen(mainMenu)
    }

    private val table = Table()

    override fun show() {
        super.show()
        table.add(area).expand().fill().padLeft(20f).padTop(20f).padRight(20f).row()
        table.add(back).expandX().padLeft(20f).padRight(20f).padBottom(20f).row()
        table.width = stage.viewport.worldWidth
        table.height = stage.viewport.worldHeight
        stage.addActor(table)
    }
}
