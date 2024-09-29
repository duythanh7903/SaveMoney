package com.example.baseprojectflamingo.ui.screen.manage_cost.estimate.frg.vm

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baseprojectflamingo.R
import com.example.baseprojectflamingo.database.entities.ChildCategory
import com.example.baseprojectflamingo.database.entities.ParentCategory
import com.example.baseprojectflamingo.database.entities.RecordEstimateCost
import com.example.baseprojectflamingo.database.repository.ChildCategoryRepository
import com.example.baseprojectflamingo.database.repository.EstCostRepository
import com.example.baseprojectflamingo.database.repository.ParentCategoryRepository
import com.example.baseprojectflamingo.serivce.AlarmReceiver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import kotlin.coroutines.CoroutineContext

class EstViewModel(
    private val parentCateRepo: ParentCategoryRepository,
    private val childCatRepo: ChildCategoryRepository,
    private val estCostRepo: EstCostRepository
): ViewModel() {

    val listParentCat =
        parentCateRepo.getAllCategoryParents()
    val listChildCat =
        childCatRepo.getAllChildCategories()
    val listCost =
        estCostRepo.getAllRecordEstimateCosts()

    fun insertAllParentCat(list: MutableList<ParentCategory>) =
        viewModelScope.launch(Dispatchers.IO) {
            parentCateRepo.insertAllCategoryParent(list)
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

    fun saveRecordEstCost(context: Context, record: RecordEstimateCost) =
        viewModelScope.launch(Dispatchers.IO) {
            estCostRepo.insertRecordEstimateCost(record)
            withContext(Dispatchers.Main) {
                setAlarm(context, record)
            }
            cancel()
        }

    @SuppressLint("ScheduleExactAlarm")
    fun setAlarm(context: Context, record: RecordEstimateCost) {
        // Giả sử bạn muốn đặt lịch cho 10h sáng hôm nay
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 8)
            set(Calendar.MINUTE, 46)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            // Nếu thời gian đã qua, đặt cho ngày mai
            if (timeInMillis < System.currentTimeMillis()) {
                add(Calendar.DAY_OF_MONTH, 1)
            }
        }

        // Tạo Intent để gửi đến AlarmReceiver
        val intent = Intent(context, AlarmReceiver::class.java)
            .apply {
                putExtra("content_title", context.getString(R.string.remind))
                putExtra("content_text", context.getString(R.string.it_is_time_to_spend_money))
                putExtra("description_content_text", record.noteTitle)
            }
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        // Đặt lịch sử dụng AlarmManager
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }

}