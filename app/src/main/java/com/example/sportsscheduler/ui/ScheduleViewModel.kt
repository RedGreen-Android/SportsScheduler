package com.example.sportsscheduler.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportsscheduler.model.ScheduleData
import com.example.sportsscheduler.repository.ScheduleRepository
import com.example.sportsscheduler.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val repository: ScheduleRepository
) : ViewModel() {

    private val _sportsData: MutableLiveData<Resource<ScheduleData>> = MutableLiveData()
    val sportsData: LiveData<Resource<ScheduleData>> = _sportsData

    private fun getScheduleResponse(response: Response<ScheduleData>): Resource<ScheduleData> {
        return if (response.isSuccessful) {
            Resource.success(response.body())
        } else Resource.error(data = null, "Error: ${response.errorBody()}")
    }

    /**
     * Below is the way to do with Kotlin Coroutine FLOWS, it has same result, showing 2 ways to do it, where live data variable not needed
     */
//        fun getScheduleData() =
//        liveData {
//            emit(Resource.loading(data = null))
//            try {
//                emit(Resource.success(repository.getSchedule()))
//            } catch (exception : Exception) {
//                emit(Resource.error(data = null, message = exception.message?: "Error"))
//            }
//        }

    fun getScheduleData() {
        viewModelScope.launch {
            _sportsData.postValue(Resource.loading(null))
            try {
                val response = repository.getSchedule()
                Log.e("MyApp", "Success on schedule finish")
                _sportsData.postValue(getScheduleResponse(response))
            } catch (t: Throwable) {
                Log.e("MyApp", "Error getting user location: ${t.message}")
                when (t) {
                    is IOException -> _sportsData.postValue(
                        Resource.error(
                            data = null,
                            message = "Error: Network Failure"
                        )
                    )

                    else -> _sportsData.postValue(
                        Resource.error(
                            data = null,
                            message = t.message ?: "Error: "
                        )
                    )
                }
            }
        }
    }
}
