package com.example.baseprojectflamingo.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.baseprojectflamingo.database.entities.ParentCategory

@Dao
interface ParentCategoryDao {
    @Insert
    fun insertAllCategoryParent(categories: MutableList<ParentCategory>)

    @Query("SELECT * FROM PARENT_CATEGORY")
    fun getAllCategoryParents(): LiveData<MutableList<ParentCategory>>
}