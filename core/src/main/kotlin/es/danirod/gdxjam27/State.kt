package es.danirod.gdxjam27

class State(val difficulty: Difficulty) {

    var buffer: Int = 31

    private var elapsedTime: Float = 0f

    val time: Float
        get() = elapsedTime
    fun addTime(delta: Float) {
        elapsedTime += delta
    }

    enum class Difficulty { Normal, Hard }

}
