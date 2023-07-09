package otus.homework.coroutines

import android.util.Log
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.net.SocketTimeoutException

class CatsPresenter(
    private val catsService: CatsService
) {

    private var _catsView: ICatsView? = null

    suspend fun onInitComplete() {
        runBlocking {
            val scope = PresenterScope()
            val job = scope.async {
                try {
                    val response = catsService.getCatFact()
                    if (response.isSuccessful && response.body() != null) {
                        _catsView?.populate(response.body() as Fact)
                    } else {

                    }
                } catch (t: SocketTimeoutException) {
                    // Toast.makeText()
                    Log.e("init", "Не удалось получить ответ от сервером")
                } catch (t: Throwable) {
                    CrashMonitor.trackWarning()
                    t.message?.let { Log.e("init", it) }
                }
            }
            job.join()
        }
    }

    fun attachView(catsView: ICatsView) {
        _catsView = catsView
    }

    fun detachView() {
        _catsView = null
    }
}