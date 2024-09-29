package com.example.baseprojectflamingo.database

import androidx.room.TypeConverter
import com.example.baseprojectflamingo.database.entities.ChildCategory
import com.example.baseprojectflamingo.database.entities.ParentCategory
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun fromParentCategory(categoryParent: ParentCategory): String {
        return Gson().toJson(categoryParent)
    }

    @TypeConverter
    fun toParentCategory(data: String): ParentCategory {
        return Gson().fromJson(data, ParentCategory::class.java)
    }

    @TypeConverter
    fun fromChildCategory(childCategory: ChildCategory): String {
        return Gson().toJson(childCategory)
    }

    @TypeConverter
    fun toChildCategory(data: String): ChildCategory {
        return Gson().fromJson(data, ChildCategory::class.java)
    }

}