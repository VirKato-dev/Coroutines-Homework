package otus.homework.coroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatsPresenter(
    private val catsService: CatsService,
    private val presenterScope: PresenterScope
) {

    private var _catsView: ICatsView? = null

    fun onInitComplete() {
        presenterScope.launch { // ждём все подзадачи
            val data = withContext(Dispatchers.IO) { catsService.getCatFact() } // нужен другой поток
            _catsView?.populate(data)
        }
    }

    fun attachView(catsView: ICatsView) {
        _catsView = catsView
    }

    fun detachView() {
        _catsView = null
    }
}