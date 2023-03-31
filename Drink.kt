package machine

enum class Drink(val num: Int, val s: String, val res: Resources) {
    ESPRESSO(1, "espresso", Resources(-4, 250, 0, 16, 1)),
    LATTE(2, "latte", Resources(-7, 350, 75, 20, 1)),
    CAPPUCCINO(3, "cappuccino",Resources(-6, 200, 100, 12, 1));

    companion object {
        private val mapNum = Drink.values().associateBy { it.num }
        private val mapS = Drink.values().associateBy { it.s }
        fun fromNum( value: Int?) = mapNum[value]
        fun fromS(value: String) = mapS[value] //TODO
    }
}