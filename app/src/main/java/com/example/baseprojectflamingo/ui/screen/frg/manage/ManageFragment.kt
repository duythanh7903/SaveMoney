package com.example.baseprojectflamingo.ui.screen.frg.manage

import android.graphics.Color
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.baseprojectflamingo.R
import com.example.baseprojectflamingo.base.BaseFragment
import com.example.baseprojectflamingo.base.ext.click
import com.example.baseprojectflamingo.base.ext.hide
import com.example.baseprojectflamingo.base.ext.invisible
import com.example.baseprojectflamingo.base.ext.show
import com.example.baseprojectflamingo.database.AppDatabase
import com.example.baseprojectflamingo.database.entities.RecordActualCost
import com.example.baseprojectflamingo.database.entities.RecordEstimateCost
import com.example.baseprojectflamingo.database.entities.RecordExpectedIncome
import com.example.baseprojectflamingo.database.entities.RecordRealIncome
import com.example.baseprojectflamingo.database.repository.ActualCostRepository
import com.example.baseprojectflamingo.database.repository.EstCostRepository
import com.example.baseprojectflamingo.database.repository.ExpectedIncomeRepository
import com.example.baseprojectflamingo.database.repository.RealIncomeRepository
import com.example.baseprojectflamingo.databinding.FragmentManageBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ManageFragment : BaseFragment<FragmentManageBinding>(R.layout.fragment_manage) {

    private lateinit var listTabFilter: MutableList<TextView>
    private lateinit var viewModel: ManageViewModel
    private lateinit var recordManageAdapter: RecordManageAdapter

    private var listCacheRecordRealIncome: MutableList<RecordRealIncome> = mutableListOf()
    private var listCacheRecordActualCost: MutableList<RecordActualCost> = mutableListOf()
    private var listCacheRecordExpectedIncome: MutableList<RecordExpectedIncome> = mutableListOf()
    private var listCacheRecordEstimateCost: MutableList<RecordEstimateCost> = mutableListOf()

    private var startDateFilter: Long = System.currentTimeMillis()
    private var endDateFilter: Long = System.currentTimeMillis()

    override fun initView() {
        initViewModel()
        initRcvAdapter()
        initListTabFilter()
        initStartDateAndEndDate()
        observeData()
        clickViews()
    }

    override fun initData() = Unit

    private fun initListTabFilter() {
        listTabFilter = mutableListOf(
            binding.tabFilter1,
            binding.tabFilter2,
            binding.tabFilter3,
            binding.tabFilter4,
        )
    }

    private fun activeTabFilter(index: Int) {
        listTabFilter.forEach {
            it.isActivated = false
            it.setTextColor((Color.parseColor("#FF1B2251")))
        }
        listTabFilter[index].isActivated = true
        listTabFilter[index].setTextColor(Color.parseColor("#FFFFFF"))
    }

    private fun initViewModel() {
        val appDatabase = AppDatabase.getInstance(requireActivity())
        val realIncomeDao = appDatabase.recordRealIncomeDao()
        val actualCostDao = appDatabase.recordActualCostDao()
        val expectedIncomeDao = appDatabase.recordExpectedIncomeDao()
        val estimateCostDao = appDatabase.recordEstimateCostDao()
        val realIncomeRepo = RealIncomeRepository(realIncomeDao)
        val actualCostRepo = ActualCostRepository(actualCostDao)
        val expectedIncomeRepo = ExpectedIncomeRepository(expectedIncomeDao)
        val estimateCostRepo = EstCostRepository(estimateCostDao)
        val viewModelFactory = ManageViewModelFactory(
            realIncomeRepo,
            actualCostRepo,
            expectedIncomeRepo,
            estimateCostRepo
        )
        viewModel = ViewModelProvider(this, viewModelFactory)[ManageViewModel::class.java]
    }

    private fun observeData() = viewModel.apply {
        indexFilterTab.observe(viewLifecycleOwner) { index ->
            activeTabFilter(index)
            handleFilter(
                index, startDateFilter, endDateFilter,
                listCacheRecordRealIncome,
                listCacheRecordActualCost,
                listCacheRecordExpectedIncome,
                listCacheRecordEstimateCost
            )
        }

        realIncome.observe(viewLifecycleOwner) { listReal ->
            listCacheRecordRealIncome = listReal
        }

        actualCost.observe(viewLifecycleOwner) { listActual ->
            listCacheRecordActualCost = listActual
        }

        expectedIncome.observe(viewLifecycleOwner) { listExpected ->
            listCacheRecordExpectedIncome = listExpected
        }

        estCost.observe(viewLifecycleOwner) { listEst ->
            listCacheRecordEstimateCost = listEst
        }

        listRecordManage.observe(viewLifecycleOwner) { listRecord ->
            if (listRecord.isEmpty()) {
                binding.rcv.invisible()
                binding.animNotFound.show()
            } else {
                binding.rcv.show()
                binding.animNotFound.hide()
                recordManageAdapter.submitData(listRecord)
            }
        }
    }

    private fun clickViews() = binding.apply {
        tabFilter1.click {
            if (viewModel.indexFilterTab.value!! != 0) {
                viewModel.replaceTab(0)
            }
        }

        tabFilter2.click {
            if (viewModel.indexFilterTab.value!! != 1) {
                viewModel.replaceTab(1)
            }
        }

        tabFilter3.click {
            if (viewModel.indexFilterTab.value!! != 2) {
                viewModel.replaceTab(2)
            }
        }

        tabFilter4.click {
            if (viewModel.indexFilterTab.value!! != 3) {
                viewModel.replaceTab(3)
            }
        }

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = "$dayOfMonth/${month + 1}/$year"
            val getStartDateAndEndDateFilter = viewModel.getStartAndEndOfDay(selectedDate)
            startDateFilter = getStartDateAndEndDateFilter.first
            endDateFilter = getStartDateAndEndDateFilter.second
            handleFilter(
                viewModel.indexFilterTab.value ?: 0, startDateFilter, endDateFilter,
                listCacheRecordRealIncome,
                listCacheRecordActualCost,
                listCacheRecordExpectedIncome,
                listCacheRecordEstimateCost
            )
        }
    }

    private fun initRcvAdapter() = binding.rcv.apply {
        recordManageAdapter = RecordManageAdapter(requireActivity())

        adapter = recordManageAdapter
    }

    private fun handleFilter(
        indexTabFilter: Int,
        startDate: Long,
        endDate: Long,
        listCacheRecordRealIncome: MutableList<RecordRealIncome>,
        listCacheRecordActualCost: MutableList<RecordActualCost>,
        listCacheRecordExpectedIncome: MutableList<RecordExpectedIncome>,
        listCacheRecordEstimateCost: MutableList<RecordEstimateCost>
    ) {
        viewModel.handleFilterRecordByDate(
            indexTabFilter, startDate, endDate,
            listCacheRecordRealIncome,
            listCacheRecordActualCost,
            listCacheRecordExpectedIncome,
            listCacheRecordEstimateCost
        )
    }

    private fun initStartDateAndEndDate() {
        val currentTime = System.currentTimeMillis()
        val currentTimeTypeStr =
            convertLongToDateString(currentTime)
        val getStartDateAndEndDate = viewModel.getStartAndEndOfDay(currentTimeTypeStr)
        startDateFilter = getStartDateAndEndDate.first
        endDateFilter = getStartDateAndEndDate.second
    }

    private fun convertLongToDateString(timestamp: Long): String {
        val date = Date(timestamp)
        val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return format.format(date)
    }

    override fun onResume() {
        super.onResume()

        handleFilter(
            viewModel.indexFilterTab.value ?: 0,
            startDateFilter, endDateFilter,
            listCacheRecordRealIncome,
            listCacheRecordActualCost,
            listCacheRecordExpectedIncome,
            listCacheRecordEstimateCost
        )
    }
}