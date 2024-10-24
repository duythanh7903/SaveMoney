package com.example.baseprojectflamingo.ui.screen.manage_cost.actual.fragment.vm

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baseprojectflamingo.R
import com.example.baseprojectflamingo.base.ext.convertJsonToObject
import com.example.baseprojectflamingo.base.ext.formatNumberWithDots
import com.example.baseprojectflamingo.commons.AppConst.CHANEL_ID_NOTI
import com.example.baseprojectflamingo.commons.AppConst.NOTI_ID
import com.example.baseprojectflamingo.commons.PreferencesUtils
import com.example.baseprojectflamingo.database.entities.Activities
import com.example.baseprojectflamingo.database.entities.ChildCategory
import com.example.baseprojectflamingo.database.entities.NotificationEntity
import com.example.baseprojectflamingo.database.entities.ParentCategory
import com.example.baseprojectflamingo.database.entities.RecordActualCost
import com.example.baseprojectflamingo.database.entities.UserAccount
import com.example.baseprojectflamingo.database.repository.ActivitiesRepository
import com.example.baseprojectflamingo.database.repository.ActualCostRepository
import com.example.baseprojectflamingo.database.repository.ChildCategoryRepository
import com.example.baseprojectflamingo.database.repository.NotificationRepository
import com.example.baseprojectflamingo.database.repository.ParentCategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ActualViewModel(
    private val parentCateRepo: ParentCategoryRepository,
    private val childCatRepo: ChildCategoryRepository,
    private val actualCostRepo: ActualCostRepository,
    private val activitiesRepo: ActivitiesRepository,
    private val notiRepo: NotificationRepository
) : ViewModel() {

    val listParentCat =
        parentCateRepo.getAllCategoryParents()
    val listChildCat =
        childCatRepo.getAllChildCategories()
    val listRecordCost =
        actualCostRepo.getAllRecordActualCosts()

    fun insertAllParentCat(list: MutableList<ParentCategory>) =
        viewModelScope.launch(Dispatchers.IO) {
            parentCateRepo.insertAllCategoryParent(list)
            cancel()
        }

    fun updateChildCate(category: ChildCategory) = viewModelScope.launch(Dispatchers.IO) {
        childCatRepo.updateChildCategory(category)
        cancel()
    }

    fun insertChildCate(catName: String, parentCate: ParentCategory) =
        viewModelScope.launch(Dispatchers.IO) {
            val category = ChildCategory(
                0L, catName, "", parentCate
            )
            childCatRepo.insertCategory(category)
            cancel()
        }

    fun saveRecordActualCost(context: Context, record: RecordActualCost) =
        viewModelScope.launch(Dispatchers.IO) {
            actualCostRepo.saveRecordActualCost(record)
            PreferencesUtils.accountBalance -= record.cost
            saveActivitiesInsertRecordActualCost(record)
            saveNoti(context, record)
            sendNotification(context, record)
            cancel()
        }

    private suspend fun saveActivitiesInsertRecordActualCost(record: RecordActualCost) {
        val activities = Activities(
            eventName = record.noteTitle,
            createdAt = record.time,
            balanceFluctuations = record.cost,
            isBalanceVolatilityIncreasing = false,
            balanceAmount = PreferencesUtils.accountBalance
        )
        activitiesRepo.insertActivity(activities)
    }

    private fun sendNotification(context: Context, record: RecordActualCost) {
        val notificationId = NOTI_ID
        val channelId = CHANEL_ID_NOTI

        // Tạo thông báo
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_noti)
            .setContentTitle(context.getString(R.string.balance_fluctuations))
            .setContentText(
                "${
                    context.getString(
                        R.string.amount_params,
                        context.convertJsonToObject(
                            PreferencesUtils.jsonAccount,
                            UserAccount::class.java
                        ).userName
                    )
                }\n${
                    context.getString(
                        R.string.transaction_amount_params_2,
                        record.cost
                    )
                }\n${
                    context.getString(
                        R.string.balance_params,
                        context.formatNumberWithDots(PreferencesUtils.accountBalance)
                    )
                }"
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        // Gửi thông báo
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, notificationBuilder.build())
    }

    private suspend fun saveNoti(context: Context, record: RecordActualCost) {
        val noti = NotificationEntity(
            id = 0L,
            title = context.getString(R.string.balance_fluctuations),
            content = "${
                context.getString(
                    R.string.amount_params,
                    context.convertJsonToObject(
                        PreferencesUtils.jsonAccount,
                        UserAccount::class.java
                    ).userName
                )
            }\n${
                context.getString(
                    R.string.transaction_amount_params_2,
                    record.cost
                )
            }\n${
                context.getString(
                    R.string.balance_params,
                    context.formatNumberWithDots(PreferencesUtils.accountBalance)
                )
            }"
        )
        notiRepo.saveNotification(noti)
    }
}