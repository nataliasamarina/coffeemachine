package machine

import kotlin.system.exitProcess

interface ICoffeeMachine: IResource, IStateHandler {
    fun start()
    fun parseCommand(cmd: String)
    val view: CReporter
}

class CoffeeMachine: ICoffeeMachine {
    private var state: CMState = CMState.SelectActionState(this)
    private var resInsideMachine = Resources(550, 400, 540, 120, 9)
    private val prn: (Any?) -> Unit = {
        println(it)
    }
    override val view = CReporter(prn)

    override fun start() {
        state.onStart()
    }

    override fun initResources(res: Resources) { resInsideMachine = res }
    override fun onStateChanged(newState: CMState) {
        state = newState
        state.onStart()
    }

    override fun parseCommand(cmd: String) { state.handleCommand(cmd) }

    fun sellADrink(drink: Drink) {
        try {
            resInsideMachine -= drink.res
        } catch (e: Exception) {
            view.reportNotEnoughResources()
            return
        }
        view.reportEnoughResources()
    }

    override fun addWater(value: Int) = resInsideMachine.add(Resource.WATER, value)
    override fun addMilk(value: Int) = resInsideMachine.add(Resource.MILK, value)
    override fun addBeans(value: Int) = resInsideMachine.add(Resource.BEANS, value)
    override fun addCups(value: Int) = resInsideMachine.add(Resource.CUPS, value)
    override fun takeMoney() = view.reportTakeMoney(resInsideMachine.takeMoney())
    override fun shareResources() = view.reportResourcesOfMachine(resInsideMachine)

    fun switchOff() { exitProcess(0) }
}
