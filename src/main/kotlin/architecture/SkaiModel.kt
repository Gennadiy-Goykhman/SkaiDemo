package architecture

import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.operations.toList

interface SkaiModel {
    fun train(objects: ObjectsArray, targets: TargetsArray, epochs: Int = 10)
    fun predict(objects: ObjectsArray)
}

internal open class MainSkaiModel(
    private val skaiScope: BuilderSkaiScope,
    private val modelActions: BuilderSkaiScope.() -> Unit
): SkaiModel, BackPropogation<BuilderSkaiScope> by BackPropogationExecutor() {
    override fun train(objects: ObjectsArray, targets: TargetsArray, epochs: Int) =
        repeat(epochs){
            skaiScope.goForward(objects, targets, modelActions)
        }

    override fun predict(objects: ObjectsArray) {
        repeat(objects.shape[0]){
            skaiScope.context.currentData.set(objects[it].toList())
            skaiScope.modelActions()
        }
        result()
    }

    private fun result(){
        println("""
            Результат предсказания нейронной сети:
            ${skaiScope.context.currentResults.joinToString(" ")}
        """.trimIndent())
    }
}