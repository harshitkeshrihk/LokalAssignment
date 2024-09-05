package com.example.lokalassignmentapp.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lokalassignmentapp.Utils.NetworkResult
import com.example.lokalassignmentapp.model.Result
import com.example.lokalassignmentapp.model.ResultJobs
import com.example.lokalassignmentapp.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo : Repository
) : ViewModel(){


    private var _data = MutableLiveData<NetworkResult<MutableList<Result>>>()
    var mdata: LiveData<NetworkResult<MutableList<Result>>> = _data


    private val _bookMarkedData = MutableLiveData<List<Result>?>()
    val mbookMarkedData: LiveData<List<Result>?> = _bookMarkedData

    private val _clickedJob = MutableLiveData<Result>(null)
    val clickedJob : LiveData<Result> = _clickedJob

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    //pagination
    private var currentPage = 1
    var isLastPage = false

    val TAG = "VIEWMODEL"

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        if(_data.value== null){
            fetchJobDetails(currentPage)
            fetchBookMarkedJob()
        }
    }



    fun fetchJobDetails(page : Int){

        viewModelScope.launch {
            try {
                _data.value = NetworkResult.Loading()
                val result = withContext(Dispatchers.IO){
                        repo.getJobs(page)

                }
                //pagination
                when(result){
                    is NetworkResult.Success ->{
                        _isLoading.value = false
                        if(page==1){
                            val firstPageResultJobs: MutableList<Result> = result.data?.results?.toMutableList() ?: mutableListOf()
                            _data.value = NetworkResult.Success(firstPageResultJobs)
                        }else{
                            val jobs = result.data?.results ?: mutableListOf()
                            _data.value = NetworkResult.Success(jobs.toMutableList())
                        }
                        isLastPage = result.data?.results?.isEmpty() ?: false
                    }
                    is NetworkResult.Loading ->{
                        _data.value = NetworkResult.Loading()
                        _isLoading.value = true
                    }
                    is NetworkResult.Error ->{
                        _data.value = NetworkResult.Error("Error fetching jobs")
                        _isLoading.value = false
                    }
                }
            }catch (e:Exception){
                _data.value =  NetworkResult.Error(e.message)
            }
        }
    }




    //pagination
    fun loadNextPage() {
        if(!isLastPage && isLoading.value == false) {
            currentPage++
            fetchJobDetails(currentPage)
        }
    }


    fun fetchBookMarkedJob(){
        viewModelScope.launch {
            try {
                _bookMarkedData.value = emptyList()
                val result = withContext(Dispatchers.IO){
                    viewModelScope.async {
                        repo.getAllBookmarkedJobs()
                    }
                }
                _bookMarkedData.value = result.await().data
            }catch (e:Exception){
                _bookMarkedData.value = emptyList()
            }
        }
    }

    fun insertJob(job: Result){
        viewModelScope.launch {
           val res = repo.insertJob(job)
            when(res){
                is NetworkResult.Loading ->{

                }
                is NetworkResult.Success ->{
                    repo.insertJob(job)
                    if(_bookMarkedData.value!=null && !_bookMarkedData.value!!.contains(job)) {
                        _bookMarkedData.value = _bookMarkedData.value?.plus(job)
                    }
                }
                is NetworkResult.Error ->{

                }
            }
        }
    }

    fun deleteJob(job: Result){
        viewModelScope.launch {
            val res = repo.insertJob(job)
            when(res){
                is NetworkResult.Loading ->{

                }
                is NetworkResult.Success ->{
                    repo.deleteJob(job)
                    if(_bookMarkedData.value!=null && _bookMarkedData.value!!.contains(job)) {
                        _bookMarkedData.value = _bookMarkedData.value?.minus(job)
                    }
                }
                is NetworkResult.Error ->{

                }
            }
        }
    }


    fun clickJob(job: Result){
        _clickedJob.postValue(job)
    }

//    override fun onCleared() {
//        super.onCleared()
//        viewModelJob.cancel() // Cancel all coroutines when ViewModel is cleared
//    }
}