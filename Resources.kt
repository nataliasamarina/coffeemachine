package machine

const val operationFailed = "Operation Failed"

enum class Resource{
    WATER,
    MILK,
    BEANS,
    CUPS
}

interface IResource{
    fun addWater(value: Int)
    fun addMilk(value: Int)
    fun addBeans(value: Int)
    fun addCups(value: Int)
    fun takeMoney()
    fun shareResources()
    fun initResources(res: Resources)
}

data class Resources(var money: Int, var water: Int, var milk: Int, var beans: Int, var cups: Int) {

    private fun isValid(): Boolean = ((water >= 0) && (milk >= 0) && (beans >= 0) && (cups >= 0))
    operator fun minus(dec: Resources): Resources {
        val res = Resources(money - dec.money, water - dec.water, milk - dec.milk,
            beans - dec.beans, cups - dec.cups)
        if (res.isValid()) return res
        else throw Exception(operationFailed)
    }

    operator fun plus(inc: Resources): Resources {
        return Resources(money + inc.money, water + inc.water, milk + inc.milk,
            beans + inc.beans, cups + inc.cups)
    }

    fun takeMoney(): Int  = money.also { money = 0 }
    fun add(res: Resource, value: Int) {
        when(res) {
            Resource.WATER -> water += value
            Resource.MILK -> milk += value
            Resource.BEANS -> beans += value
            Resource.CUPS -> cups += value
        }
    }
}