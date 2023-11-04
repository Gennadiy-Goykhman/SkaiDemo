package architecture

import org.jetbrains.kotlinx.multik.api.d1array
import org.jetbrains.kotlinx.multik.api.d2array
import org.jetbrains.kotlinx.multik.api.linalg.dot
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.ndarray
import org.jetbrains.kotlinx.multik.ndarray.data.D1Array
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.operations.*
import java.util.*
import kotlin.random.Random

internal interface BackPropogation<T: SkaiScope> {
    fun T.goForward(objects: ObjectsArray, targets: TargetsArray, epochNumber: Int, actions: T.() -> Unit)

    fun T.goBackward(targets: TargetsArray)
}

class BackPropogationExecutor<T: SkaiScope>: BackPropogation<T> {

    private val randomizer = Random(Long.MAX_VALUE)

    override fun T.goForward(objects: ObjectsArray, targets: TargetsArray, epochNumber: Int, actions: T.() -> Unit) {
        println("""
            --------------------------------------
            Данные для $epochNumber эпохи:
            
        """.trimIndent()
        )
        val dataIndex = randomizer.nextInt(0, objects.shape[0])
        val trainData = objects[dataIndex].toList()
        val trainTarget = targets[dataIndex]

        context.currentData.set(trainData)
        actions()

        goBackward(generateTargetsArray(trainTarget))
        context.currentData.drop()
    }

    override fun T.goBackward(targets: TargetsArray) {
        targets.forEachIndexed { index, target ->
            val error = target - context.currentData.pop()[index]
            val newWeightsStack = countBackwardPropogation(
                delta = mk.ndarray(doubleArrayOf(error)),
                newWeightStack = Stack()
            )
            context.currentWeights.drop()
            newWeightsStack.forEach {
                context.currentWeights.set(it)
            }
        }
    }

    private tailrec fun T.countBackwardPropogation(delta: D1Array<Double>, newWeightStack: Stack<D2Array<Double>>): Stack<D2Array<Double>> {
        if (!context.currentWeights.hasNext() || !context.currentData.hasNext()) return newWeightStack
        val data = mk.ndarray(context.currentData.pop())
        val newWeights = mk.ndarray(context.currentWeights.pop()
            .toArray()
            .mapIndexed { index, doubleArray ->
                (mk.ndarray(doubleArray) - lambda() * delta[index] * data).toList()
            }
        )

        val newDelta =
            if (delta.size == 1)
                (mk.ndarray(newWeights.toDoubleArray()) * delta[0]) * localGradient(data)
            else
                mk.ndarray((mk.ndarray(listOf(delta.toList())) dot newWeights).toDoubleArray()) * localGradient(data)

        println("""
            |////////////////////////////////////////////////
            |Новые веса на итерации ${newWeightStack.size + 1}:
            |$newWeights
            |
            |Новые дельта на итерации ${newWeightStack.size + 1}
            |$newDelta
        """.trimMargin()
        )
        newWeightStack.push(newWeights)
        return countBackwardPropogation(newDelta, newWeightStack)
    }

    private fun lambda() = 0.2

    private fun localGradient(value: D1Array<Double>) = value.map { x -> 0.5 * (1 + x) * (1 - x) }

}