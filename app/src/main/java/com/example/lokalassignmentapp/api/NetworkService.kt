package com.example.lokalassignmentapp.api


import com.example.lokalassignmentapp.model.ResultJobs
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("common/jobs")
    suspend fun getJobs(
        @Query("page") page: Int?): Response<ResultJobs>

}