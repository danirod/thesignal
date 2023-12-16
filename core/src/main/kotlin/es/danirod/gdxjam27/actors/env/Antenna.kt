package es.danirod.gdxjam27.actors.env

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.ui.Image

class Antenna(texture: Texture) : Image(texture) {

    private val renderer = ShapeRenderer()

    /** Control the ray. */
    var rayEnabled = false
    val rayFocus = Vector2()

    private var deployPercentage: Float = 0f

    /** Used mainly to compute the color of the ray. */
    private var ticks = 0f

    override fun act(delta: Float) {
        super.act(delta)
        ticks += delta

        if (rayEnabled && deployPercentage < 1) {
            // Ray is not fully deployed.
            deployPercentage += MathUtils.sin(MathUtils.PI / 2 * delta)
            if (deployPercentage > 1)
                deployPercentage = 1f
        } else if (!rayEnabled && deployPercentage > 0) {
            // Ray is still deployed
            deployPercentage -= MathUtils.sin(MathUtils.PI / 2 * delta)
            if (deployPercentage < 0)
                deployPercentage = 0f
        }
    }

    private fun colorFormula(offset: Float) = 0.01f * (MathUtils.sin(ticks + offset * 3f) * 20f + 70f)
    private val colorFrom = Color()
    private val colorTo = Color()

    private fun updateRayColors(alpha: Float) {
        val fromBlueTone = colorFormula(-0.5f)
        val toBlueTone = colorFormula(0.5f)
        colorFrom.set(fromBlueTone, 1f, 1f, alpha)
        colorTo.set(toBlueTone, 1f, 1f, alpha)
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        super.draw(batch, parentAlpha)

        batch.end()
        renderer.projectionMatrix = batch.projectionMatrix
        renderer.transformMatrix = batch.transformMatrix
        drawRay(parentAlpha)
        batch.begin()
    }

    private fun drawRay(parentAlpha: Float) {
        updateRayColors(parentAlpha)
        renderer.begin(ShapeRenderer.ShapeType.Line)
        val start = Vector2(345f, 360f).also { p -> localToParentCoordinates(p) }
        val target = rayFocus.cpy().sub(start).scl(deployPercentage).add(start)
        for (y in -1..1) {
            for (x in -1..1) {
                val s = start.cpy().add(x.toFloat(), y.toFloat())
                val e = target.cpy().add(x.toFloat(), y.toFloat())
                renderer.line(s.x,s.y, e.x, e.y, colorFrom, colorTo)
            }
        }
        renderer.end()
    }
}
