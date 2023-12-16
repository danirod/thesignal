package es.danirod.gdxjam27.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport
import es.danirod.gdxjam27.State
import es.danirod.gdxjam27.TheSignalGame
import es.danirod.gdxjam27.actors.ui.MenuButton

class GameOverScreen(private val game: TheSignalGame, state: State): ScreenAdapter() {

    private val stage = Stage(ScreenViewport())

    private val gameOver = game.label("GAME OVER").apply {
        setFontScale(2f)
        setScale(2f)
    }

    private val goodBye = run {
        // For some reason toInt() fails on TeaVM, so here we are
        val secs = "${state.time}"
        val integerPart = secs.split("\\.")[0]
        game.label("You survived for $integerPart. Not bad.")
    }

    private val back = MenuButton.newButton(game, "BACK") {
        val mainMenu = MainMenuScreen(game)
        game.switchScreen(mainMenu)
    }

    override fun show() {
        gameOver.setPosition((stage.viewport.worldWidth - gameOver.width * 2f) / 2f, 380f)
        goodBye.setPosition((stage.viewport.worldWidth - goodBye.width) / 2f, (stage.viewport.worldHeight - goodBye.height) / 2)
        back.setPosition((stage.viewport.worldWidth - back.width * 3f) / 2f, 50f)

        stage.addActor(gameOver)
        stage.addActor(goodBye)
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
