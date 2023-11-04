package App

import architecture.*

object NeuroApp {

    private val neuroNetModel = getNeuro()

    fun start(){
        welcomeMessage()
        startNeuro()
    }

    private fun welcomeMessage() {
        println("""
            Привет, добро пожаловать в Определитель пропущенных дедлайнов
            Для начала работы введите сколько элементов будет в тренировочной выборке
            
            Ваш ввод:
        """.trimIndent())
    }

    private fun startNeuro(){
        val trainCount = readln().toInt()
        println("""
            Теперь для каждого объекта через пробел запишите значения признаков
            значения признаков объекта для тренировки через пробел:
            - Насколько студент занят на работе? (1 - полный рабочий день, 0 - не работает)
            - Как часто студент пропускает дедлайны? (1 - всегда, 0 - никогда)
            - Какая средняя оценка студента? (1 - 10, 0 - 0)
            - На сколько предмет профильный? (1 - основополагающий, 0 - доп. дисциплина)
            
            Ввод:
        """.trimIndent()
        )
        val datas = mutableListOf<List<Double>>()
        val targets = mutableListOf<Double>()
        repeat(trainCount){
            datas.add(readln().split(' ').map { it.toDouble() })
            println("""
                Введите ожидаемый прогноз(Численно, где 1 - дедлайн будет пропущен, а 0 - всё будет сдано вовремя):
            """.trimIndent()
            )
            targets.add(readln().toDouble())
        }

        neuroNetModel.train(
            generateObjectsArray(*datas.toTypedArray()),
            generateTargetsArray(*targets.toDoubleArray()),
            epochs = 200
        )
        println("""
            Супер, модель прошла тренировку. 
            Теперь введите признаки для того, чтобы получить прогноз:
        """.trimIndent()
        )
        cyclePredictProgram(false)
    }

    private fun cyclePredictProgram(stopWorking: Boolean){
        if (stopWorking) {
            println("""
            
            Завершение работы
            """.trimIndent()
            )
            return
        }

        println("""
            Введите признаки для того, чтобы получить прогноз:   
        """.trimIndent()
        )

        val predictData = readln().split(' ').map { it.toDouble() }
        neuroNetModel.predict(generateObjectsArray(predictData))

        println("""
            Завершить работу? (Да/Нет)    
        """.trimIndent()
        )

        val answer = readln()

        return cyclePredictProgram(answer.contains("Да".lowercase()))
    }

    private fun getNeuro() = runSkai {
        val firstWeights = generateWeights(
            listOf(0.3, 0.7, -1.3, -1.7),
            listOf(0.5, 0.5, 0.3, 0.7),
            listOf(0.4, 0.6, 0.25, 0.75),
        )
        val secondWeights = generateWeights(
            listOf(0.25, 0.35, 0.4)
        )
        buildSkaiModel {
            fullConnectedLayer(weights = firstWeights)
            fullConnectedLayer(weights = secondWeights)
            setupClassificationOutput(mapOf(
                { outputValue: Double -> outputValue < 0.5 } to "Сдаст в срок",
                { outputValue: Double -> outputValue == 0.5 } to "Наука тут бессильна",
                { outputValue: Double -> outputValue > 0.5 } to "Не сдаст вовремя",
            )
            )
        }
    }
}