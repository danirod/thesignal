package es.danirod.gdxjam27.challenges

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup
import es.danirod.gdxjam27.actors.ui.CharacterPrinter

class DecimalDivisionChallenge(val hard: Boolean, private val printer: CharacterPrinter): Challenge() {

    private val type = when (MathUtils.random(3)) {
        0 -> 2
        1 -> 4
        else -> 5
    }

    private val divisor = run {
        var result: Int
        val (min, max) = if (hard)
            Pair(10, 199)
        else
            Pair(10, 99)
        do result = MathUtils.random(min, max) while (result % type != 0)
        result
    }

    override fun render(): Group = HorizontalGroup().apply {
        addActor(printer.number(divisor))
        addActor(printer.div())
        addActor(printer.number(type))
        layout()
        validate()
        setSize(prefWidth, prefHeight)
    }

    override fun evaluate(input: String) = input.toInt() == divisor / type
}
