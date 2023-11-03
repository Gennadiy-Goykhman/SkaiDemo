package architecture

interface SkaiContext

class EmptyNeuroContext : SkaiContext

val SkaiContext.currentData get() = currentValuesDataProvider()
val SkaiContext.currentWeights get() = weightsDataProvider()
var SkaiContext.currentResults: List<String>
    get() = DataStore.getResults(hashCode())
    set(value) = DataStore.saveResults(hashCode(), value)
