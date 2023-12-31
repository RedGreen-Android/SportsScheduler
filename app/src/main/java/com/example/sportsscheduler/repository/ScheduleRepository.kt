package com.example.sportsscheduler.repository

import com.example.sportsscheduler.network.ScheduleAPI
import javax.inject.Inject

class ScheduleRepository @Inject constructor(private val api: ScheduleAPI) {

    suspend fun getSchedule() = api.getWeather()
}