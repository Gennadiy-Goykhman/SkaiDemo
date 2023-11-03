package architecture

import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import java.util.Stack

internal object DataStore {
    private val mapOfData = delegateAsStack<List<Double>>()
    private val mapOfWeights = delegateAsStack<D2Array<Double>>()
    private val mapOfResults = mutableMapOf<Int, List<String>>()

    fun saveData(key: Int, data: List<Double>) {
        mapOfData[key] = data
    }

    fun getData(key: Int): List<Double> = mapOfData[key]

    fun saveWeights(key: Int, data: D2Array<Double>){
        mapOfWeights[key] = data
    }

    fun getWeights(key: Int): D2Array<Double> = mapOfWeights[key]

    fun saveResults(key: Int, data: List<String>){
        mapOfResults[key] = data
    }

    fun getResults(key: Int): List<String>{
        return mapOfResults[key] ?: throw SkaiNotFoundException()
    }
}

internal fun <T> delegateAsStack() = StackDelegate<T>()

internal class StackDelegate<T> {
    private val storeMap = mutableMapOf<Int,Stack<T>>()
    operator fun get(key: Int): T {
        val currentStack =  storeMap[key] ?: throw SkaiNotFoundException("Не были найдены данные для данного контекста")
        return if (currentStack.isEmpty()) throw SkaiNotFoundException("Набор данных пуст") else currentStack.peek()
    }

    operator fun set(key: Int, value: T){
        if (!storeMap.containsKey(key)) {
            storeMap[key] = Stack<T>()
        }
        storeMap[key]?.push(value)
    }

    operator fun set(key: Int, values: Stack<T>) {
        storeMap[key] = values
    }
}