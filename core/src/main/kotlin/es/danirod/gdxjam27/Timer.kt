package es.danirod.gdxjam27

class Timer() {

    var time = 0f
        private set

    fun act(delta: Float) {
        time += delta
    }

    fun reset() {
        time = 0f
    }
}
