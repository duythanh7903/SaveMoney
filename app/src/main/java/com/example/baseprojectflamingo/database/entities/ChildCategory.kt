package com.example.baseprojectflamingo.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CHILD_CATEGORY")
data class ChildCategory(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var categoryName: String = "",
    var iconCategoryChild: String = "",
    var categoryParent: ParentCategory = ParentCategory(),
    var createdAt: Long = System.currentTimeMillis()
) {
}