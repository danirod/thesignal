package es.danirod.gdxjam27.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import es.danirod.gdxjam27.TheSignalGame
import es.danirod.gdxjam27.actors.ui.MenuButton

class InfoScreen(private val game: TheSignalGame) : ScreenAdapter() {

    private val stage = Stage()

    private val credits = Gdx.files.internal("info.txt").readString()

    private val area = run {
        val label = game.label(credits)
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

    override fun show() {
        area.setBounds(10f, 120f, 840f, 340f)
        back.setPosition(320f, 40f)
        stage.addActor(area)
        stage.addActor(back)
        Gdx.input.inputProcessor = stage
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        super.render(delta)
        stage.act(delta)
        stage.draw()
    }

}
