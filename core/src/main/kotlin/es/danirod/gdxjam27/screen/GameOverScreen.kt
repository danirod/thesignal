package es.danirod.gdxjam27.screen

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import es.danirod.gdxjam27.State
import es.danirod.gdxjam27.TheSignalGame
import es.danirod.gdxjam27.actors.ui.MenuButton

class GameOverScreen(game: TheSignalGame, state: State): BaseScreen(game) {

    private val gameOver = game.label("GAME OVER", 0.5f)

    private val goodBye = game.label("You survived for ${state.time.toInt()} seconds. Not bad.", 0.25f)

    private val back = MenuButton.newButton(game, "BACK") {
        val mainMenu = MainMenuScreen(game)
        game.switchScreen(mainMenu)
    }

    override fun show() {
        super.show()

        val table = Table()
        table.width = stage.viewport.worldWidth
        table.height = stage.viewport.worldHeight
        stage.addActor(table)

        table.align(Align.center)
        table.add(gameOver).pad(15f).row()
        table.add(goodBye).expand().padLeft(15f).padRight(15f).row()
        table.add(back).pad(15f).row()
    }
}
