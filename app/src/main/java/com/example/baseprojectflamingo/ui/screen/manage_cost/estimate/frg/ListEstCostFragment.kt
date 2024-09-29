package com.example.baseprojectflamingo.ui.screen.manage_cost.estimate.frg

import androidx.lifecycle.ViewModelProvider
import com.example.baseprojectflamingo.R
import com.example.baseprojectflamingo.base.BaseFragment
import com.example.baseprojectflamingo.database.AppDatabase
import com.example.baseprojectflamingo.database.entities.RecordEstimateCost
import com.example.baseprojectflamingo.database.repository.ChildCategoryRepository
import com.example.baseprojectflamingo.database.repository.EstCostRepository
import com.example.baseprojectflamingo.database.repository.ParentCategoryRepository
import com.example.baseprojectflamingo.databinding.FragmentListEstCostBinding
import com.example.baseprojectflamingo.ui.screen.manage_cost.estimate.frg.vm.EstViewModel
import com.example.baseprojectflamingo.ui.screen.manage_cost.estimate.frg.vm.ViewModelEstFactory

class ListEstCostFragment :
    BaseFragment<FragmentListEstCostBinding>(R.layout.fragment_list_est_cost) {

    private lateinit var viewModel: EstViewModel
    private lateinit var estCostAdapter: EstCostAdapter

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
        val estCostDao = appDatabase.recordEstimateCostDao()
        val parentCateRepo = ParentCategoryRepository(parentCateDao)
        val childCateRepo = ChildCategoryRepository(childCateDao)
        val estCostRepo = EstCostRepository(estCostDao)
        val viewModelFactory =
            ViewModelEstFactory(parentCateRepo, childCateRepo, estCostRepo)
        viewModel = ViewModelProvider(this, viewModelFactory)[EstViewModel::class.java]
    }

    private fun initAdapter() = binding.rcvItem.apply {
        estCostAdapter = EstCostAdapter(requireContext())
        adapter = estCostAdapter
    }

    private fun observeData() = viewModel.apply {
        listCost.observe(viewLifecycleOwner) { listRecord ->
            if (listRecord.isEmpty()) {
                val listTemp = mutableListOf(
                    RecordEstimateCost(
                        0L, requireActivity().getString(R.string.record_sample_1), cost = 123456,
                    )
                )
                estCostAdapter.submitData(listTemp)
            } else estCostAdapter.submitData(listRecord)
        }
    }
}