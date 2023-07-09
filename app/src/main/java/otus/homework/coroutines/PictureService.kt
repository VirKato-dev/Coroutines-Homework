package otus.homework.coroutines

import otus.homework.coroutines.model.Pict
import retrofit2.http.GET

interface PictureService {

    @GET("v1/images/search")
    suspend fun getCatPicture(): Pict // получаем только один факт
}