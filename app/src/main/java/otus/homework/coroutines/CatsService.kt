package otus.homework.coroutines

import otus.homework.coroutines.model.Fact
import retrofit2.http.GET

interface CatsService {

    @GET("fact")
    suspend fun getCatFact(): Fact // получаем только один факт
}