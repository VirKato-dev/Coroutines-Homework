package otus.homework.coroutines

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import otus.homework.coroutines.model.FactAndPict


class CatsViewModel : ViewModel() {
    private var catsService = DiContainer().service
    private var pictService = DiContainer().apiPict
    val factAndPict: MutableLiveData<FactAndPict> = MutableLiveData()

    fun getFactAndPict() {
        viewModelScope.launch {
            val data = withContext(Dispatchers.IO) {
                val fact = async { catsService.getCatFact() }
                val pict = async { pictService.getCatPicture() }
                FactAndPict(fact.await(), pict.await()[0].url)
            }
            factAndPict.value = data
        }
    }

    fun stop() {
        viewModelScope.cancel()
    }
}