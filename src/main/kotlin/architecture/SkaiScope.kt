package architecture

import org.jetbrains.kotlinx.multik.ndarray.data.D1Array
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array

typealias ObjectsArray = D2Array<Double>
typealias TargetsArray = D1Array<Double>

sealed interface SkaiScope {
    val context: SkaiContext
}

internal object RootSkaiScope: SkaiScope{
    override val context: SkaiContext = EmptyNeuroContext()
}

class BuilderSkaiScope(override val context: SkaiContext): SkaiScope