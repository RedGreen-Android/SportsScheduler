package com.example.sportsscheduler.network

import com.example.sportsscheduler.model.ScheduleData
import retrofit2.Response
import retrofit2.http.GET

interface ScheduleAPI {
    //url - http://files.yinzcam.com.s3.amazonaws.com/iOS/interviews/ScheduleExercise/schedule.json
    @GET("ScheduleExercise/schedule.json")
    suspend fun getWeather(): Response<ScheduleData>

}