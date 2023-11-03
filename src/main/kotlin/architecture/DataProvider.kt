package architecture

import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import java.util.*

interface DataProvider<T> {
    fun hasNext(): Boolean
    fun pop(): T
    fun drop()
    fun replaceStack(newStack: Stack<T>)
    fun get(): T
    fun set(value: T)
}

fun SkaiContext.currentValuesDataProvider() = object: DataProvider<List<Double>> {
    override fun get(): List<Double> = DataStore.getData(this@currentValuesDataProvider.hashCode())

    override fun set(value: List<Double>) =  DataStore.saveData(this@currentValuesDataProvider.hashCode(), value)

    override fun hasNext(): Boolean {
        TODO("Not yet implemented")
    }

    override fun pop(): List<Double> {
        TODO("Not yet implemented")
    }

    override fun drop() {
        TODO("Not yet implemented")
    }

    override fun replaceStack(newStack: Stack<List<Double>>) {
        TODO("Not yet implemented")
    }
}


fun SkaiContext.weightsDataProvider() = object: DataProvider<D2Array<Double>> {
    override fun get(): D2Array<Double> = DataStore.getWeights(this@weightsDataProvider.hashCode())

    override fun set(value: D2Array<Double>) = DataStore.saveWeights(this@weightsDataProvider.hashCode(), value)

    override fun hasNext(): Boolean {
        TODO("Not yet implemented")
    }

    override fun pop(): D2Array<Double> {
        TODO("Not yet implemented")
    }

    override fun drop() {
        TODO("Not yet implemented")
    }

    override fun replaceStack(newStack: Stack<D2Array<Double>>) {
        TODO("Not yet implemented")
    }
}



