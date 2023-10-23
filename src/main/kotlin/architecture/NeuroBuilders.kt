package architecture

fun <T> runSkai(skaiActions: SkaiScope.() -> T) = RootSkaiScope.skaiActions()

fun SkaiScope.buildSkaiModel(modelSequence: BuilderSkaiScope.() -> Unit) : SkaiModel {
    return MainSkaiModel(BuilderSkaiScope(context), modelSequence)
}