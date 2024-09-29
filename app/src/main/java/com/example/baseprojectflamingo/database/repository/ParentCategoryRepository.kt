package com.example.baseprojectflamingo.database.repository

import com.example.baseprojectflamingo.database.dao.ParentCategoryDao
import com.example.baseprojectflamingo.database.entities.ParentCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ParentCategoryRepository(
    private val dao: ParentCategoryDao
) {

    suspend fun insertAllCategoryParent(categories: MutableList<ParentCategory>) =
        withContext(Dispatchers.IO) {
            dao.insertAllCategoryParent(categories)
        }

    fun getAllCategoryParents() =
        dao.getAllCategoryParents()

}