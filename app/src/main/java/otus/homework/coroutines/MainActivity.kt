package otus.homework.coroutines

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.cancel

class MainActivity : AppCompatActivity() {

    lateinit var catsPresenter: CatsPresenter

    private val diContainer = DiContainer()
    private val presenterScope = PresenterScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

        catsPresenter = CatsPresenter(diContainer.service, diContainer.apiPict, presenterScope, this)
        view.presenter = catsPresenter
        catsPresenter.attachView(view)
    }

    override fun onResume() {
        super.onResume()
        catsPresenter.onInitComplete()
    }

    override fun onDestroy() {
        catsPresenter.detachView()
        presenterScope.cancel()
        super.onDestroy()
    }
}