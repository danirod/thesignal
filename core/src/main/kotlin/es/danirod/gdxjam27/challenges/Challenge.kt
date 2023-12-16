package es.danirod.gdxjam27.challenges

import com.badlogic.gdx.scenes.scene2d.Actor

abstract class Challenge {

    abstract fun render(): Actor

    abstract fun evaluate(input: String): Boolean

}
