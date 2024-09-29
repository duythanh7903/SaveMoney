package com.example.baseprojectflamingo.database.repository

import com.example.baseprojectflamingo.database.dao.ActivitiesDao
import com.example.baseprojectflamingo.database.entities.Activities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ActivitiesRepository(
    private val dao: ActivitiesDao
) {

    suspend fun insertActivity(activity: Activities) =
        withContext(Dispatchers.IO) {
            dao.insertActivity(activity)
        }

    fun getAllActivities() = dao.getAllActivities()

}