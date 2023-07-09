package otus.homework.coroutines.lesson

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.async
import kotlin.system.measureTimeMillis


fun main() {
    runBlocking {// блокирующая рутовая корутина (главный поток её ждёт)
        work1()
        work2(this) // передать CoroutineScope для работы с async
    }
}


suspend fun work1() {
    val scope = CoroutineScope(Dispatchers.IO) // Job()
    val job = scope.launch {// независимая рутовая корутина (родитель её не ждёт)
        delay(1000)
        println("Hello World!")
    }
    job.join() // runBlocking будет ждать завершения job
}


suspend fun work2(scope: CoroutineScope) {
    val time = measureTimeMillis {
        val one = scope.async(start = CoroutineStart.LAZY) { doOne() } // запуск в ленивом режиме
        one.start() // для запуска при каких-то условиях
        val two = scope.async { doTwo() }
        println("The answer is ${one.await() + two.await()}") // await() - дождаться результата
        // ленивые корутины без start() будут выполняться последовательно в соответствии с await()
    }
    println("Completed in ${time}ms")
}

suspend fun doOne(): Int {
    delay(500)
    return 10
}

suspend fun doTwo(): Int {
    delay(500)
    return 5
}