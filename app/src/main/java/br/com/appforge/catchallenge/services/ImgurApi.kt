package br.com.appforge.catchallenge.services

import br.com.appforge.catchallenge.models.CatImagesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ImgurApi {

    @GET("gallery/search/")
    suspend fun getImages(
        @Query("q")
        q: String
    ): Response<CatImagesResponse>
}