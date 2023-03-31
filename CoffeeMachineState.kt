package machine

interface IStateHandler{
    fun onStateChanged(newState: CMState)
}

sealed class CMState (protected val context: CoffeeMachine ) {
    open fun onStart() {}
    open fun handleCommand(cmd: String?) {}

    class OffState(context: CoffeeMachine) : CMState(context) {
        override fun onStart() = context.switchOff()
        override fun handleCommand(cmd: String?) =
            context.onStateChanged(SelectActionState(context))
    }

    class SelectActionState(context: CoffeeMachine) : CMState(context) {
        override fun onStart() = context.view.askAction()
        override fun handleCommand(cmd: String?) = context.onStateChanged(
            when (cmd?.let { Reader.readAction(it) }) {
                Action.BUY -> BuyDrinkState(context)
                Action.FILL -> FillWaterState(context)
                Action.TAKE -> TakeMoneyState(context)
                Action.REMAINING -> RemainingResourcesState(context)
                Action.EXIT -> OffState(context)
                else -> this
            })
    }

    class BuyDrinkState(context: CoffeeMachine) : CMState(context) {
        override fun onStart() = context.view.askDrinkToBuy()
        override fun handleCommand(cmd: String?) = context.onStateChanged(
            when(val drink = cmd?.let { Reader.readDrink(it) }) {
                is Drink -> {
                    context.sellADrink(drink)
                    SelectActionState(context)
                }
                else -> when(cmd?.let { Reader.readAction(it) }) {
                    Action.BACK -> SelectActionState(context)
                    else -> this
                }
            })
    }

    class FillWaterState(context: CoffeeMachine) : CMState(context) {
        override fun onStart() = context.view.askForWater()
        override fun handleCommand(cmd: String?) = context.onStateChanged(
            when (val value = cmd?.let { Reader.readInt(it) }) {
                is Int -> {
                    context.addWater(value)
                    FillMilkState(context)
                }
                else -> this
            })
    }

    class FillMilkState(context: CoffeeMachine) : CMState(context) {
        override fun onStart() = context.view.askForMilk()
        override fun handleCommand(cmd: String?) = context.onStateChanged(
            when (val value = cmd?.let { Reader.readInt(it) }) {
                is Int -> {
                    context.addMilk(value)
                    FillBeansState(context)
                }
                else -> this
            })
    }

    class FillBeansState(context: CoffeeMachine) : CMState(context) {
        override fun onStart() = context.view.askForBeans()
        override fun handleCommand(cmd: String?) = context.onStateChanged(
            when (val value = cmd?.let { Reader.readInt(it) }) {
                is Int -> {
                    context.addBeans(value)
                    FillCupsState(context)
                }
                else -> this
            })
    }

    class FillCupsState(context: CoffeeMachine) : CMState(context) {
        override fun onStart() = context.view.askForCups()
        override fun handleCommand(cmd: String?) = context.onStateChanged(
            when (val value = cmd?.let { Reader.readInt(it) }) {
                is Int -> {
                    context.addCups(value)
                    SelectActionState(context)
                }
                else -> this
            })
    }

    class TakeMoneyState(context: CoffeeMachine) : CMState(context) {
        override fun onStart() {
            context.takeMoney()
            context.onStateChanged(SelectActionState(context))
        }

        override fun handleCommand(cmd: String?) {}
    }

    class RemainingResourcesState(context: CoffeeMachine) : CMState(context) {
        override fun onStart() {
            context.shareResources()
            context.onStateChanged(SelectActionState(context))
        }

        override fun handleCommand(cmd: String?) {}
    }
}