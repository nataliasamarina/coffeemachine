package machine

fun main() {
    val machine = CoffeeMachine()
    machine.start()
    while(true) {
        machine.parseCommand(readln())
    }
}
