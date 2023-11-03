package architecture

import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.operations.forEachIndexed
import org.jetbrains.kotlinx.multik.ndarray.operations.toList
import kotlin.random.Random

internal interface BackPropogation<T: SkaiScope> {
    fun T.goForward(objects: ObjectsArray, targets: TargetsArray, actions: T.() -> Unit)

    fun T.goBackward(targets: TargetsArray)
}

class BackPropogationExecutor<T: SkaiScope>: BackPropogation<T> {

    private val randomizer = Random(Long.MAX_VALUE)

    override fun T.goForward(objects: ObjectsArray, targets: TargetsArray, actions: T.() -> Unit) {
        val dataIndex = randomizer.nextInt(0, objects.shape[0])
        val trainData = objects[dataIndex].toList()
        val trainTarget = targets[dataIndex]

        context.currentData.set(trainData)
        actions()

        goBackward(generateTargetsArray(trainTarget))
    }

    override fun T.goBackward(targets: TargetsArray) {
        targets.forEachIndexed { index, target ->
            val error = target - context.currentData.get()[index]
        }
    }

    private fun T.countBackwardPropogation(){
        if (context.currentWeights.get().isEmpty()) return
        return
    }

}