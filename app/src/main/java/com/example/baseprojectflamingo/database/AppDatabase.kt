package com.example.baseprojectflamingo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.baseprojectflamingo.database.dao.ActivitiesDao
import com.example.baseprojectflamingo.database.dao.ChildCategoryDao
import com.example.baseprojectflamingo.database.dao.NotificationDao
import com.example.baseprojectflamingo.database.dao.ParentCategoryDao
import com.example.baseprojectflamingo.database.dao.RecordActualCostDao
import com.example.baseprojectflamingo.database.dao.RecordEstimateCostDao
import com.example.baseprojectflamingo.database.dao.RecordExpectedIncomeDao
import com.example.baseprojectflamingo.database.dao.RecordRealIncomeDao
import com.example.baseprojectflamingo.database.dao.UserAccountDao
import com.example.baseprojectflamingo.database.entities.Activities
import com.example.baseprojectflamingo.database.entities.ChildCategory
import com.example.baseprojectflamingo.database.entities.NotificationEntity
import com.example.baseprojectflamingo.database.entities.ParentCategory
import com.example.baseprojectflamingo.database.entities.RecordActualCost
import com.example.baseprojectflamingo.database.entities.RecordEstimateCost
import com.example.baseprojectflamingo.database.entities.RecordExpectedIncome
import com.example.baseprojectflamingo.database.entities.RecordRealIncome
import com.example.baseprojectflamingo.database.entities.UserAccount

@Database(
    entities = [
        UserAccount::class, Activities::class,
        ParentCategory::class, ChildCategory::class,
        RecordRealIncome::class, RecordExpectedIncome::class,
        RecordActualCost::class, RecordEstimateCost::class,
        NotificationEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userAccountDao(): UserAccountDao
    abstract fun activitiesDao(): ActivitiesDao
    abstract fun parentCategoryDao(): ParentCategoryDao
    abstract fun childCategoryDao(): ChildCategoryDao
    abstract fun recordRealIncomeDao(): RecordRealIncomeDao
    abstract fun recordExpectedIncomeDao(): RecordExpectedIncomeDao
    abstract fun recordActualCostDao(): RecordActualCostDao
    abstract fun recordEstimateCostDao(): RecordEstimateCostDao
    abstract fun notificationDao(): NotificationDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            synchronized(this) {
                if (instance != null) instance!!
                else {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "SaveMoney.db"
                    )
                        .allowMainThreadQueries().build()
                    instance!!
                }
            }
    }
}