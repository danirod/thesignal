package es.danirod.gdxjam27.actors.ui

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Stack

class Alert(background: Texture, flashingText: Texture): Stack() {

    private val message = Image(background)

    private val title = Image(flashingText)

    private val flash = Actions.forever(
        Actions.sequence(
            Actions.delay(0.7f),
            Actions.hide(),
            Actions.delay(0.3f),
            Actions.show(),
        )
    )

    init {
        title.addAction(flash)
        add(message)
        add(title)
    }
}
