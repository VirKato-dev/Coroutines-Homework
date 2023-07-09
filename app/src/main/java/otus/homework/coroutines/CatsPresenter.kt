package otus.homework.coroutines

import android.content.Context
import android.widget.Toast
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException

class CatsPresenter(
    private val catsService: CatsService,
    private val presenterScope: PresenterScope,
    private val context: Context
) {

    private var _catsView: ICatsView? = null
    private val exceptionHandler =
        CoroutineExceptionHandler { _, throwable -> handleException(throwable) }

    fun onInitComplete() {
        presenterScope.launch(exceptionHandler) { // ждём все подзадачи
            try {
                val data = withContext(Dispatchers.IO) {
//                    throw SocketTimeoutException()
                    catsService.getCatFact()
                } // нужен другой поток
                _catsView?.populate(data)
            } catch (e: SocketTimeoutException) {
                // Обработка исключения SocketTimeoutException
                handleSocketTimeoutException()
            } catch (throwable: Throwable) {
                // Обработка других исключений
                handleOtherException(throwable)
            }
        }
    }

    private fun handleException(throwable: Throwable) {
        // Обработка общих исключений
    }

    private fun handleSocketTimeoutException() {
        // Обработка исключения SocketTimeoutException
        Toast.makeText(context, "Не удалось получить ответ от сервером", Toast.LENGTH_SHORT).show()
    }

    private fun handleOtherException(throwable: Throwable) {
        // Обработка других исключений
        Toast.makeText(context, throwable.message, Toast.LENGTH_LONG).show()
    }

    fun attachView(catsView: ICatsView) {
        _catsView = catsView
    }

    fun detachView() {
        _catsView = null
    }
}