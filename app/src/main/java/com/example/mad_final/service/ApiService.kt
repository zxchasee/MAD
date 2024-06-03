package com.example.mad_final   .service

import com.example.mad_final.models.Poster
import com.example.mad_final.models.WiutApiResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Query
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface ApiService{

//getALL is function that gets data from API
    @GET("records/all")
    suspend fun getAll(@Query("student_id")student_id: String = "00011414"):WiutApiResponse<List<Poster>?>

//getPosterById is function that gets record by Id from API
    @GET("records/{id}")
    suspend fun getPosterById(@Path("id") id: Int, @Query("student_id") studentId: String = "00011414"):WiutApiResponse<Poster?>

//createPoster is function that creates record
    @POST("records")
    suspend fun createPoster(@Body requestBody: Poster, @Query("student_id") studentId: String ="00011414"):WiutApiResponse<Any?>

//updatePoster is function that updates the record
    @PUT("records/{id}")
    suspend fun updatePoster(@Path("id") recordId: Int, @Body requestBody: Poster, @Query("student_id") studentId: String = "00011414"):WiutApiResponse<Any?>

//deleteAllPosters deletes all records from API
    @DELETE("records/all")
    suspend fun deleteAllPosters(@Query("student_id") studentId: String = "00011414"):WiutApiResponse<Any?>

//deletePoster deletes specific record from API
    @DELETE("records/{id}")
    suspend fun deletePoster(@Path("id") recordId: Int, @Query("student_id") studentId: String = "00011414"):WiutApiResponse<Any?>
}


val retrofit = Retrofit.Builder()
    .baseUrl("https://wiutmadcw.uz/api/v1/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val apiService = retrofit.create(ApiService::class.java)