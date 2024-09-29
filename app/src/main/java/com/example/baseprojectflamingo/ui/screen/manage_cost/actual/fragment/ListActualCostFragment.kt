package com.example.baseprojectflamingo.ui.screen.manage_cost.actual.fragment

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.example.baseprojectflamingo.R
import com.example.baseprojectflamingo.base.BaseFragment
import com.example.baseprojectflamingo.database.AppDatabase
import com.example.baseprojectflamingo.database.entities.RecordActualCost
import com.example.baseprojectflamingo.database.repository.ActivitiesRepository
import com.example.baseprojectflamingo.database.repository.ActualCostRepository
import com.example.baseprojectflamingo.database.repository.ChildCategoryRepository
import com.example.baseprojectflamingo.database.repository.NotificationRepository
import com.example.baseprojectflamingo.database.repository.ParentCategoryRepository
import com.example.baseprojectflamingo.databinding.FragmentListActualCostBinding
import com.example.baseprojectflamingo.ui.domain.MonthlyMoney
import com.example.baseprojectflamingo.ui.screen.manage_cost.actual.fragment.vm.ActualViewModel
import com.example.baseprojectflamingo.ui.screen.manage_cost.actual.fragment.vm.ViewModelActualFactory
import java.time.Instant
import java.time.YearMonth
import java.time.ZoneId

class ListActualCostFragment :
    BaseFragment<FragmentListActualCostBinding>(R.layout.fragment_list_actual_cost) {

    private lateinit var viewModel: ActualViewModel
    private lateinit var actualCostAdapter: ActualCostAdapter

    override fun initView() {
        initViewModel()
        initAdapter()
        observeData()
    }

    override fun initData() = Unit

    private fun initViewModel() {
        val appDatabase = AppDatabase.getInstance(requireActivity())
        val parentCateDao = appDatabase.parentCategoryDao()
        val childCateDao = appDatabase.childCategoryDao()
        val actualCostDao = appDatabase.recordActualCostDao()
        val activitiesDao = appDatabase.activitiesDao()
        val notiDao = appDatabase.notificationDao()
        val parentCateRepo = ParentCategoryRepository(parentCateDao)
        val childCateRepo = ChildCategoryRepository(childCateDao)
        val actualCostRepo = ActualCostRepository(actualCostDao)
        val activitiesRepo = ActivitiesRepository(activitiesDao)
        val notiRepo = NotificationRepository(notiDao)
        val viewModelFactory =
            ViewModelActualFactory(parentCateRepo, childCateRepo, actualCostRepo, activitiesRepo, notiRepo)
        viewModel = ViewModelProvider(this, viewModelFactory)[ActualViewModel::class.java]
    }

    private fun initAdapter() = binding.rcvItem.apply {
        actualCostAdapter = ActualCostAdapter(requireContext())
        adapter = actualCostAdapter
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun observeData() = viewModel.apply {
        listRecordCost.observe(viewLifecycleOwner) { listRecord ->
            if (listRecord.isEmpty()) {
                val listTemp = mutableListOf(
                    RecordActualCost(
                        0L, requireActivity().getString(R.string.record_sample_1), cost = 123456,
                    )
                )
                actualCostAdapter.submitData(listTemp)
            } else actualCostAdapter.submitData(listRecord)
            val s1 = processPoints(listRecord)
            val sum = s1.sumOf { it.totalCost }
            Log.d("duylt", sum.toString())
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun processPoints(objects: List<RecordActualCost>): List<MonthlyMoney> {
        // Lấy thời gian hiện tại
        val currentYearMonth = YearMonth.now()

        // Nhóm các object theo từng tháng và tổng hợp điểm
        return objects
            .asSequence() // Sử dụng sequence để xử lý tối ưu hơn với danh sách lớn
            .map { obj ->
                // Chuyển đổi timestamp từ milliseconds thành YearMonth
                val dateTime = Instant.ofEpochMilli(obj.time)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime()
                val yearMonth = YearMonth.of(dateTime.year, dateTime.monthValue)

                yearMonth to obj.cost;
            }
            .filter { (yearMonth, _) ->
                // Loại bỏ tháng hiện tại
                yearMonth.isBefore(currentYearMonth)
            }
            .groupBy { (yearMonth, _) ->
                // Nhóm theo YearMonth
                yearMonth
            }
            .map { (yearMonth, pointsList) ->
                // Tính tổng điểm cho mỗi tháng
                val totalPoints = pointsList.sumOf { it.second }
                MonthlyMoney(yearMonth.toString(), totalPoints)
            }
            .sortedByDescending { it.monthYear } // Sắp xếp theo tháng giảm dần
            .take(12) // Lấy 12 tháng gần nhất
    }
}