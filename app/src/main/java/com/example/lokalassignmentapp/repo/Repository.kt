package com.example.lokalassignmentapp.repo


import com.example.lokalassignmentapp.Utils.NetworkResult
import com.example.lokalassignmentapp.api.NetworkService
import com.example.lokalassignmentapp.local.BookMarkDao
import com.example.lokalassignmentapp.model.Result
import com.example.lokalassignmentapp.model.ResultJobs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject


class Repository @Inject constructor(private val networkService: NetworkService,private val dao: BookMarkDao){



    suspend fun getJobs(page:Int): NetworkResult<ResultJobs> {
      try {
        val response = networkService.getJobs(page)
        if (response.isSuccessful && response.body() != null) {
            return NetworkResult.Success(response.body()!!)
        } else {
            return NetworkResult.Error("Network Error")
        }
    } catch (e: HttpException) {
        return NetworkResult.Error(e.message())
      }
    }

    suspend fun insertJob(job: Result): NetworkResult<Boolean>
    {
        try {
            dao.insertJob(job)
            return NetworkResult.Success<Boolean>(true)
        } catch (e: Exception) {
            return  NetworkResult.Error<Boolean>(e.message)
        }
    }

    suspend fun deleteJob(job: Result): NetworkResult<Boolean>
    {
        try {
            dao.deleteJob(job)
            return NetworkResult.Success<Boolean>(true)
        } catch (e: Exception) {
            return  NetworkResult.Error<Boolean>(e.message)
        }
    }


    suspend fun getAllBookmarkedJobs(): NetworkResult<List<Result>>
    {
        try {
            val list = dao.getAllBookmarks()
            return NetworkResult.Success<List<Result>>(list)
        } catch (e: Exception) {
            return  NetworkResult.Error<List<Result>>(e.message)
        }
    }

}