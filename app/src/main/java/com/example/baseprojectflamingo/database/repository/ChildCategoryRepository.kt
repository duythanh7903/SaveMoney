package com.example.baseprojectflamingo.database.repository

import com.example.baseprojectflamingo.database.dao.ChildCategoryDao
import com.example.baseprojectflamingo.database.entities.ChildCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChildCategoryRepository(
    private val childCateDao: ChildCategoryDao
) {

    suspend fun insertCategory(category: ChildCategory) = withContext(Dispatchers.IO) {
        childCateDao.insertChildCategory(category)
    }

    suspend fun updateChildCategory(category: ChildCategory) = withContext(Dispatchers.IO) {
        childCateDao.updateChildCategory(category)
    }

    fun getAllChildCategories() = childCateDao.getAllChildCategories()
}