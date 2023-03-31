package machine

class Reader {
    companion object {
        val readInt: (String) -> Int? = { it.toIntOrNull() }
        val readAction: (String) -> Action? = { Action.from(it) }
        val readDrink: (String) -> Drink? = { Drink.fromNum(it.toIntOrNull()) }
    }
}