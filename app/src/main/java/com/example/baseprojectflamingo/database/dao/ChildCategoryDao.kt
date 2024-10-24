package com.example.baseprojectflamingo.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.baseprojectflamingo.database.entities.ChildCategory

@Dao
interface ChildCategoryDao {

    @Insert
    fun insertChildCategory(category: ChildCategory)

    @Query("SELECT * FROM CHILD_CATEGORY ORDER BY createdAt DESC")
    fun getAllChildCategories(): LiveData<MutableList<ChildCategory>>

    @Update
    fun updateChildCategory(category: ChildCategory)

}