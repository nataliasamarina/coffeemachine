package machine

enum class Action(val s: String) {
    BUY("buy"),
    FILL("fill"),
    TAKE("take"),
    REMAINING("remaining"),
    BACK("back"),
    EXIT("exit");

    companion object {
        private val map = values().associateBy { it.s }
        fun from( value: String): Action? = map[value]
    }
}