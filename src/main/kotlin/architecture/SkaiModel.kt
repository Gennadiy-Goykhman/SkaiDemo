package architecture

import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.operations.toList

interface SkaiModel {
    fun train(objects: ObjectsArray, targets: TargetsArray)
    fun predict(objects: ObjectsArray)
}

internal open class MainSkaiModel(private val skaiScope: BuilderSkaiScope, private val modelActions: BuilderSkaiScope.() -> Unit): SkaiModel{
    override fun train(objects: ObjectsArray, targets: TargetsArray) {
        repeat(objects.shape[0]){
            skaiScope.context.currentData  = objects[it].toList()
            skaiScope.modelActions()
        }
    }

    override fun predict(objects: ObjectsArray) {
        repeat(objects.shape[0]){
            skaiScope.context.currentData  = objects[it].toList()
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