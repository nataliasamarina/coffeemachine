package machine

interface IReporter{
    fun reportResourcesOfMachine(res: Resources)
    fun reportTakeMoney(money: Int)
    fun reportEnoughResources()
    fun reportNotEnoughResources()
    fun askAction()
    fun askDrinkToBuy()
    fun askForCups()
    fun askForWater()
    fun askForMilk()
    fun askForBeans()
}

class CReporter(val prn: (Any?) -> Unit): IReporter {
    private fun prn(str: String, xxx: Int) = prn(str.replace(Strings.mock, xxx.toString()))
    override fun reportResourcesOfMachine(res: Resources) {
        prn(Strings.machineHas)
        prn(Strings.mlOfWater, res.water)
        prn(Strings.mlOfMilk, res.milk)
        prn(Strings.gOfCoffee, res.beans)
        prn(Strings.nCups, res.cups)
        prn(Strings.nMoney, res.money)
    }

    override fun reportTakeMoney(money: Int) = prn(Strings.takeMoney, money)
    override fun reportEnoughResources() = prn(Strings.msgEnough)
    override fun reportNotEnoughResources()  = prn(Strings.msgNotEnough)
    override fun askAction() = prn(Strings.writeAction.replace(Strings.mock,
        Action.values().joinToString(", ") { it.s }))
    override fun askDrinkToBuy() = prn(Strings.whatToBuy)
    override fun askForCups() = prn(Strings.howManyCups)
    override fun askForWater() = prn(Strings.howManyWater)
    override fun askForMilk() = prn(Strings.howManyMilk)
    override fun askForBeans() = prn(Strings.howManyCoffee)
}