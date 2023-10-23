package architecture

import org.jetbrains.kotlinx.multik.ndarray.data.D2Array

interface SkaiContext

class EmptyNeuroContext : SkaiContext

var SkaiContext.currentData : List<Double>
    get() = DataStore.getData(hashCode())
    set(value) = DataStore.saveData(hashCode(), value)

var SkaiContext.currentResults: List<String>
    get() = DataStore.getResults(hashCode())
    set(value) = DataStore.saveResults(hashCode(), value)

var SkaiContext.currentWeights: D2Array<Double>
    get() = DataStore.getWeights(hashCode())
    set(value) = DataStore.saveWeights(hashCode(), value)
