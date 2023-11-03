package architecture

class SkaiNotFoundException(override val message: String = "Не были найдены данные для текущего контекста"): Throwable(message)