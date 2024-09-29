package com.example.baseprojectflamingo.ui.screen.frg.manage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baseprojectflamingo.base.ext.toMutableListRecordManage
import com.example.baseprojectflamingo.database.entities.RecordActualCost
import com.example.baseprojectflamingo.database.entities.RecordEstimateCost
import com.example.baseprojectflamingo.database.entities.RecordExpectedIncome
import com.example.baseprojectflamingo.database.entities.RecordRealIncome
import com.example.baseprojectflamingo.database.repository.ActualCostRepository
import com.example.baseprojectflamingo.database.repository.EstCostRepository
import com.example.baseprojectflamingo.database.repository.ExpectedIncomeRepository
import com.example.baseprojectflamingo.database.repository.RealIncomeRepository
import com.example.baseprojectflamingo.ui.domain.RecordManage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ManageViewModel(
    realIncomeRepo: RealIncomeRepository,
    actualCostRepo: ActualCostRepository,
    expectedIncomeRepo: ExpectedIncomeRepository,
    estCostRepo: EstCostRepository,
) : ViewModel() {

    val realIncome =
        realIncomeRepo.getAllRecordsRealIncome()
    val actualCost =
        actualCostRepo.getAllRecordActualCosts()
    val expectedIncome =
        expectedIncomeRepo.getAllRecordExpectedIncome()
    val estCost =
        estCostRepo.getAllRecordEstimateCosts()

    private val _indexFilterTab = MutableLiveData(0)
    private val _listRecordManage = MutableLiveData<MutableList<RecordManage>>()

    val indexFilterTab: LiveData<Int> = _indexFilterTab
    val listRecordManage: LiveData<MutableList<RecordManage>> = _listRecordManage

    init {
        _indexFilterTab.postValue(0)
    }

    fun replaceTab(index: Int) = _indexFilterTab.postValue(index)

    fun handleFilterRecordByDate(
        indexCategoryFilter: Int, startDate: Long, endDate: Long,
        listRealIncome: MutableList<RecordRealIncome>,
        listActualCost: MutableList<RecordActualCost>,
        listExpectedIncome: MutableList<RecordExpectedIncome>,
        listEstCost: MutableList<RecordEstimateCost>
    ) = viewModelScope.launch(Dispatchers.IO) {
        val listRecordManage = when (indexCategoryFilter) {
            0 -> listRealIncome.filter { it.time in startDate..endDate }.toMutableListRecordManage()

            1 -> listActualCost.filter { it.time in startDate..endDate }.toMutableListRecordManage()

            2 -> listExpectedIncome.filter { it.time in startDate..endDate }
                .toMutableListRecordManage()

            else -> listEstCost.filter { it.time in startDate..endDate }.toMutableListRecordManage()
        }

        _listRecordManage.postValue(listRecordManage)

        cancel()
    }

    fun getStartAndEndOfDay(dateString: String): Pair<Long, Long> {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        val calendar = Calendar.getInstance()

        return try {
            // Chuyển đổi chuỗi thành đối tượng Date
            val date = dateFormat.parse(dateString)
            if (date == null) {
                // Trả về giá trị mặc định nếu có lỗi
                val defaultCalendar = Calendar.getInstance()
                defaultCalendar.set(Calendar.HOUR_OF_DAY, 0)
                defaultCalendar.set(Calendar.MINUTE, 0)
                defaultCalendar.set(Calendar.SECOND, 0)
                defaultCalendar.set(Calendar.MILLISECOND, 0)
                val defaultStartOfDay = defaultCalendar.timeInMillis
                val defaultEndOfDay = defaultStartOfDay + 86399999 // 24h - 1 mili giây

                Pair(defaultStartOfDay, defaultEndOfDay)
            } else {
                calendar.time = date

                // Thiết lập thời gian cho ngày
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                val startOfDay = calendar.timeInMillis

                // Thiết lập thời gian cho ngày tiếp theo để lấy thời gian kết thúc
                calendar.add(Calendar.DAY_OF_MONTH, 1)
                val endOfDay = calendar.timeInMillis - 1 // Trừ đi 1 mili giây để lấy 23:59:59.999

                Pair(startOfDay, endOfDay)
            }
        } catch (e: Exception) {
            // Trả về giá trị mặc định nếu có lỗi
            val defaultCalendar = Calendar.getInstance()
            defaultCalendar.set(Calendar.HOUR_OF_DAY, 0)
            defaultCalendar.set(Calendar.MINUTE, 0)
            defaultCalendar.set(Calendar.SECOND, 0)
            defaultCalendar.set(Calendar.MILLISECOND, 0)
            val defaultStartOfDay = defaultCalendar.timeInMillis
            val defaultEndOfDay = defaultStartOfDay + 86399999 // 24h - 1 mili giây

            Pair(defaultStartOfDay, defaultEndOfDay)
        }
    }

}