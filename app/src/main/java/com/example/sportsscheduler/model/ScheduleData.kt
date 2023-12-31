package com.example.sportsscheduler.model

data class ScheduleData(
    val DefaultGameId: String,
    val Filters: List<Filter>,
    val GameSection: List<GameSection>,
    val Team: Team,
    val YinzNode: YinzNode,
)