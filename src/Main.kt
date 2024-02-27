import kotlinx.coroutines.*

suspend fun main2() {
    for(i in 0..5) {
        delay(500L)
        println(i)
    }
    println("Test message")
}

suspend fun main3() {
    coroutineScope {
         launch {
            heavyWork()
         }
        println("Test message")
    }
}
suspend fun heavyWork() {
    for(i in 0..5) {
        delay(1500L)
        println(i)
    }
}
suspend fun main4() {
    coroutineScope {
        val job: Job = launch { heavyWork() }
        println("Start")
        job.join() // ожидаем завершение работы корутины
        println("End")
    }
}

suspend fun main5() {
    coroutineScope {
        // корутина создана, но не запущена
        val job: Job = launch(start = CoroutineStart.LAZY) {
            delay(1000)
            println("Coroutine started!!!")
        }
        delay(3000)
        job.start() // корутина запущена
        delay(4000)
        job.cancel() // отменяем корутину
        job.join() // ожидаем завершения корутины
        job.cancelAndJoin()
        println("Smth else...")
    }
}

suspend fun main6() {
    coroutineScope {
        val deffered: Deferred<String> = async { getMessage() }
        println("message: ${deffered.await()}")
        println("Finished!")
    }
}
suspend fun getMessage() : String {
    delay(2000)
    return "Hello!!!"
}

suspend fun main7() {
    coroutineScope {
        val num1_def : Deferred<Int> = async { calc(100,200) }
        val num2_def = async { calc(10,20) }
        val num3_def = async { calc(1,2) }
        val num1 = num1_def.await()
        val num2 = num2_def.await()
        val num3 = num3_def.await()
        println("$num1 $num2 $num3")
    }
}
suspend fun calc(a: Int, b: Int) : Int {
    delay(2000)
    return a+b
}

suspend fun main() {
    coroutineScope {
        // корутина создана, но не запущена
        val sum = async(start = CoroutineStart.LAZY) { calc(1,2) }

        delay(1000)
        sum.start() // запуск
        println("sum: ${sum.await()}") // получение результата
    }
}





