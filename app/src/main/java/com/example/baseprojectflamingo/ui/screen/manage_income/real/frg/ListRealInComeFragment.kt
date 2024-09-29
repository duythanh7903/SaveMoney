package com.example.baseprojectflamingo.ui.screen.manage_income.real.frg

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.baseprojectflamingo.R
import com.example.baseprojectflamingo.base.BaseFragment
import com.example.baseprojectflamingo.database.AppDatabase
import com.example.baseprojectflamingo.database.entities.ChildCategory
import com.example.baseprojectflamingo.database.entities.RecordRealIncome
import com.example.baseprojectflamingo.database.repository.ActivitiesRepository
import com.example.baseprojectflamingo.database.repository.ChildCategoryRepository
import com.example.baseprojectflamingo.database.repository.NotificationRepository
import com.example.baseprojectflamingo.database.repository.ParentCategoryRepository
import com.example.baseprojectflamingo.database.repository.RealIncomeRepository
import com.example.baseprojectflamingo.databinding.FragmentListRealIncomeBinding
import com.example.baseprojectflamingo.ui.screen.manage_income.real.frg.vm.RealViewModel
import com.example.baseprojectflamingo.ui.screen.manage_income.real.frg.vm.ViewModelRealFactory

class ListRealInComeFragment: BaseFragment<FragmentListRealIncomeBinding>(R.layout.fragment_list_real_income) {

    private lateinit var viewModel: RealViewModel
    private lateinit var realIncomeAdapter: RealIncomeAdapter

    override fun initData() = Unit

    override fun initView() {
        initViewModel()
        initAdapter()
        observeData()
    }

    private fun initViewModel() {
        val appDatabase = AppDatabase.getInstance(requireActivity())
        val parentCateDao = appDatabase.parentCategoryDao()
        val childCateDao = appDatabase.childCategoryDao()
        val realIncomeDao = appDatabase.recordRealIncomeDao()
        val activitiesDao = appDatabase.activitiesDao()
        val notificationsDao = appDatabase.notificationDao()
        val parentCateRepo = ParentCategoryRepository(parentCateDao)
        val childCateRepo = ChildCategoryRepository(childCateDao)
        val realIncomeRepo = RealIncomeRepository(realIncomeDao)
        val activitiesRepo = ActivitiesRepository(activitiesDao)
        val notificationsRepo = NotificationRepository(notificationsDao)
        val viewModelFactory = ViewModelRealFactory(parentCateRepo, childCateRepo, realIncomeRepo, activitiesRepo, notificationsRepo)
        viewModel = ViewModelProvider(this, viewModelFactory)[RealViewModel::class.java]
    }

    private fun initAdapter() = binding.rcvItem.apply {
        realIncomeAdapter = RealIncomeAdapter(requireContext())
        adapter = realIncomeAdapter
    }

    private fun observeData() = viewModel.apply {
        listRecordRevenue.observe(viewLifecycleOwner) { listRecord ->
            if (listRecord.isEmpty()) {
                val listTemp = mutableListOf(
                    RecordRealIncome(
                        0L, requireActivity().getString(R.string.record_sample_1), revenue = 123456,
                    )
                )
                realIncomeAdapter.submitData(listTemp)
            } else realIncomeAdapter.submitData(listRecord)
        }
    }
}