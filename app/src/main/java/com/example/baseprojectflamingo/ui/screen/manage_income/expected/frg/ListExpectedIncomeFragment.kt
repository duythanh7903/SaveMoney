package com.example.baseprojectflamingo.ui.screen.manage_income.expected.frg

import androidx.lifecycle.ViewModelProvider
import com.example.baseprojectflamingo.R
import com.example.baseprojectflamingo.base.BaseFragment
import com.example.baseprojectflamingo.database.AppDatabase
import com.example.baseprojectflamingo.database.entities.RecordExpectedIncome
import com.example.baseprojectflamingo.database.repository.ChildCategoryRepository
import com.example.baseprojectflamingo.database.repository.ExpectedIncomeRepository
import com.example.baseprojectflamingo.database.repository.ParentCategoryRepository
import com.example.baseprojectflamingo.databinding.FragmentListExpectedIncomeBinding
import com.example.baseprojectflamingo.ui.screen.manage_income.expected.frg.vm.ExpectedViewModel
import com.example.baseprojectflamingo.ui.screen.manage_income.expected.frg.vm.ViewModelExpectedFactory

class ListExpectedIncomeFragment :
    BaseFragment<FragmentListExpectedIncomeBinding>(R.layout.fragment_list_expected_income) {

    private lateinit var viewModel: ExpectedViewModel
    private lateinit var expectedIncomeAdapter: ExpectedIncomeAdapter

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
        val expectedIncomeDao = appDatabase.recordExpectedIncomeDao()
        val parentCateRepo = ParentCategoryRepository(parentCateDao)
        val childCateRepo = ChildCategoryRepository(childCateDao)
        val expectedIncomeRepo = ExpectedIncomeRepository(expectedIncomeDao)
        val viewModelFactory =
            ViewModelExpectedFactory(parentCateRepo, childCateRepo, expectedIncomeRepo)
        viewModel = ViewModelProvider(this, viewModelFactory)[ExpectedViewModel::class.java]
    }

    private fun initAdapter() = binding.rcvItem.apply {
        expectedIncomeAdapter = ExpectedIncomeAdapter(requireContext())
        adapter = expectedIncomeAdapter
    }

    private fun observeData() = viewModel.apply {
        listRecordRevenue.observe(viewLifecycleOwner) { listRecord ->
            if (listRecord.isEmpty()) {
                val listTemp = mutableListOf(
                    RecordExpectedIncome(
                        0L,
                        requireActivity().getString(R.string.record_sample_1),
                        revenue = 123456,
                    )
                )
                expectedIncomeAdapter.submitData(listTemp)
            } else expectedIncomeAdapter.submitData(listRecord)
        }
    }
}