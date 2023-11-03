package architecture

import org.jetbrains.kotlinx.multik.api.d1array
import org.jetbrains.kotlinx.multik.api.d2array
import org.jetbrains.kotlinx.multik.api.linalg.dot
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.ndarray.data.*
import org.jetbrains.kotlinx.multik.ndarray.operations.toList


fun SkaiScope.generateWeights(vararg weights: List<Double>): D2Array<Double> {
    val data = buildList { weights.forEach { addAll(it) } }
    return mk.d2array(weights.size, weights.maxBy { it.size }.size){data[it]}
}

fun generateObjectsArray(vararg objects: List<Double>): D2Array<Double> {
    val data = buildList { objects.forEach { addAll(it) } }
    return mk.d2array(objects.size, objects.maxBy { it.size }.size){data[it]}
}

fun generateTargetsArray(vararg targets: Double) = mk.d1array(targets.size){targets[it]}


fun BuilderSkaiScope.fullConnectedLayer(weights: D2Array<Double>) {
    val data = mk.d1array(context.currentData.get().size) { context.currentData.get()[it] }
    context.currentWeights.set(weights)
    context.currentData.set(mk.linalg.dot(weights,data).toList())
}

fun BuilderSkaiScope.setupClassificationOutput(outputMap: Map<(Double) -> Boolean, String>) {
    val data = context.currentData.get()
    context.currentResults = data.mapNotNull { resultValue ->
        outputMap[outputMap.keys.find { it(resultValue) }]
    }
}