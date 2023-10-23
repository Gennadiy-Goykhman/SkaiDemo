package architecture

import org.jetbrains.kotlinx.multik.ndarray.data.D2Array

internal object DataStore {
    private val mapOfData = mutableMapOf<Int,List<Double>>()
    private val mapOfWeights = mutableMapOf<Int, D2Array<Double>>()
    private val mapOfResults = mutableMapOf<Int, List<String>>()

    fun saveData(key: Int, data: List<Double>){
        mapOfData[key] = data
    }

    fun getData(key: Int): List<Double>{
        return mapOfData[key] ?: throw SkaiNotFoundException()
    }

    fun saveWeights(key: Int, data: D2Array<Double>){
        mapOfWeights[key] = data
    }

    fun getWeights(key: Int): D2Array<Double>{
        return mapOfWeights[key] ?: throw SkaiNotFoundException()
    }

    fun saveResults(key: Int, data: List<String>){
        mapOfResults[key] = data
    }

    fun getResults(key: Int): List<String>{
        return mapOfResults[key] ?: throw SkaiNotFoundException()
    }
}