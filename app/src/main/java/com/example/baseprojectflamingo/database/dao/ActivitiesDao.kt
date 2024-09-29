package com.example.baseprojectflamingo.database.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.baseprojectflamingo.database.entities.Activities

@Dao
interface ActivitiesDao {
    @Insert
    fun insertActivity(activity: Activities)

    @Query("SELECT * FROM ACTIVITIES ORDER BY createdAt DESC")
    fun getAllActivities(): LiveData<MutableList<Activities>>
}