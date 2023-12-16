package es.danirod.gdxjam27.challenges

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup
import es.danirod.gdxjam27.actors.ui.CharacterPrinter

class DecimalSumChallenge(val hard: Boolean, private val printer: CharacterPrinter) : Challenge() {


    private val a = if (hard)
        MathUtils.random(100, 500)
    else
        MathUtils.random(10, 50)

    private val b = run {
        var result: Int
        if (hard) {
            do result = MathUtils.random(100, 500) while (result < 100 || result > 999)
        } else {
            do result = MathUtils.random(10, 50) while (result < 10 || result > 99)
        }
        result
    }

    override fun render(): Group = HorizontalGroup().apply {
        addActor(printer.number(a))
        addActor(printer.plus())
        addActor(printer.number(b))
        layout()
        validate()
        setSize(prefWidth, prefHeight)
    }

    override fun evaluate(input: String) = input.toInt() == a + b
}
