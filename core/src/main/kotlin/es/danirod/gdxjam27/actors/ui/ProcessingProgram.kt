package es.danirod.gdxjam27.actors.ui

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import es.danirod.gdxjam27.State
import es.danirod.gdxjam27.challenges.Challenge
import es.danirod.gdxjam27.challenges.DecimalDivisionChallenge
import es.danirod.gdxjam27.challenges.DecimalSumChallenge

/** The actor that presents challenges. */
class ProcessingProgram(private val state: State, private val printer: CharacterPrinter, private val error: Sound, private val question: Sound) : Table() {

    /** The exception that will be thrown by the program when the queue is full. */
    inner class QueueOverflowException : Exception()

    /** The current challenge buffer. */
    private val queue = mutableListOf<Challenge>()

    private val prompt = Prompt(this, printer)

    /** Adds a new challenge to the queue. Throws an error if the buffer is full. */
    private fun queueChallenge(ch: Challenge) {
        if (state.buffer == 0) {
            // No more challenges can be pushed.
            throw QueueOverflowException()
        } else {
            queue.add(ch)
            state.buffer--
        }
    }

    /** Gets the current challenge that the user should be solving. */
    private fun currentChallenge() = queue.getOrNull(0)

    /** Finishes a challenge by removing it from the queue. */
    private fun evictChallenge() {
        currentChallenge()?.let { ch ->
            queue.remove(ch)
            state.buffer++
        }
    }

    /** How many seconds since now should pass before the next challenge is shown on screen. */
    private fun getDelayUntilNextChallenge() = if (state.time > 0 && state.time < 30) {
        // Challenges appear faster and faster every time between 3 and 30 seconds
        10f - 0.22f * state.time
    } else {
        // Otherwise use 3 seconds between challenges. This makes the first challenge appear quick (< 3),
        // and also once enough time has passed, we set a limit to how fast each challenge appears.
        3f
    }

    /** Actually adds a stage action that will trigger a new challenge after some amount of seconds. */
    fun scheduleNextChallenge() {
        stage.addAction(Actions.sequence(
            Actions.delay(getDelayUntilNextChallenge()),
            Actions.run {
                val next = newChallenge()
                queueChallenge(next)
                if (queue.size == 1) {
                    presentNextChallenge()
                }

                // Add the next challenge
                scheduleNextChallenge()
            }
        ))
    }

    /** Update the screen contents to present the current challenge, if present at all. */
    private fun presentNextChallenge() {
        clearChildren()
        stage.keyboardFocus = null
        currentChallenge()?.let { ch ->
            add(ch.render()).expandY().fillY().align(Align.center).row()
            add(prompt).expandX().fillX()
            question.play(0.7f)
            stage.keyboardFocus = prompt
        }
    }

    /** This callback is used when the program receives a value. */
    fun receiveInput(value: String) {
        prompt.clearInput()
        currentChallenge()?.let { ch ->
            if (ch.evaluate(value)) {
                evictChallenge()
                presentNextChallenge()
            } else {
                error.play(0.3f)
            }
        }
    }

    /** Challenge generator based on the game difficulty. */
    private fun newChallenge(): Challenge {
        val choices = listOf(
            DecimalDivisionChallenge(state.time > 30, printer),
            DecimalSumChallenge(state.time > 30, printer)
        )
        return choices.random()
    }

}
