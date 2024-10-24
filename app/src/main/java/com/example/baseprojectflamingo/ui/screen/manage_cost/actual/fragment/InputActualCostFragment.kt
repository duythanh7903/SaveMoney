package com.example.baseprojectflamingo.ui.screen.manage_cost.actual.fragment

import androidx.lifecycle.ViewModelProvider
import com.example.baseprojectflamingo.R
import com.example.baseprojectflamingo.base.BaseFragment
import com.example.baseprojectflamingo.base.ext.click
import com.example.baseprojectflamingo.base.ext.convertLongToDateString
import com.example.baseprojectflamingo.base.ext.showToast
import com.example.baseprojectflamingo.commons.Commons.getAllParentCategory
import com.example.baseprojectflamingo.database.AppDatabase
import com.example.baseprojectflamingo.database.entities.ChildCategory
import com.example.baseprojectflamingo.database.entities.ParentCategory
import com.example.baseprojectflamingo.database.entities.ParentCategory.Companion.TYPE_EXPENSE
import com.example.baseprojectflamingo.database.entities.RecordActualCost
import com.example.baseprojectflamingo.database.repository.ActivitiesRepository
import com.example.baseprojectflamingo.database.repository.ActualCostRepository
import com.example.baseprojectflamingo.database.repository.ChildCategoryRepository
import com.example.baseprojectflamingo.database.repository.NotificationRepository
import com.example.baseprojectflamingo.database.repository.ParentCategoryRepository
import com.example.baseprojectflamingo.databinding.FragmentInputActualCostBinding
import com.example.baseprojectflamingo.ui.adapter.ChildCategoryAdapter
import com.example.baseprojectflamingo.ui.adapter.ParentCategoryAdapter
import com.example.baseprojectflamingo.ui.screen.dialog.AddCategoryDialog
import com.example.baseprojectflamingo.ui.screen.dialog.UpdateCategoryDialog
import com.example.baseprojectflamingo.ui.screen.manage_cost.actual.fragment.vm.ActualViewModel
import com.example.baseprojectflamingo.ui.screen.manage_cost.actual.fragment.vm.ViewModelActualFactory

class InputActualCostFragment :
    BaseFragment<FragmentInputActualCostBinding>(R.layout.fragment_input_actual_cost) {

    private lateinit var dialogAddCategory: AddCategoryDialog
    private lateinit var adapterParentCat: ParentCategoryAdapter
    private lateinit var adapterChildCat: ChildCategoryAdapter
    private lateinit var viewModel: ActualViewModel
    private lateinit var dialogUpdateCategory: UpdateCategoryDialog

    private var parentCatSelected: ParentCategory? = null
    private var childCatSelected: ChildCategory? = null

    override fun initView() {
        initViewModel()
        initCategoryParentAdapter()
        initAddCategoryDialog()
        initChildCategory()
        observe()
        clickViews()
        initInputTime()
        initDialogUpdateCategory()
    }

    private fun initDialogUpdateCategory() {
        dialogUpdateCategory = UpdateCategoryDialog(requireActivity())
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

    private fun observe() = viewModel.apply {
        listParentCat.observe(viewLifecycleOwner) { listParent ->
            if (listParent.isEmpty() || listParent.none { it.typeCategory == TYPE_EXPENSE }) viewModel.insertAllParentCat(
                getAllParentCategory()
            )
            else adapterParentCat.submitData(listParent.filter { it.typeCategory == TYPE_EXPENSE })
            parentCatSelected = adapterParentCat.list[0]
        }

        listChildCat.observe(viewLifecycleOwner) { listChild ->
            if (listChild.isEmpty() || listChild.none { it.categoryParent.typeCategory == TYPE_EXPENSE }) {
                val listSampleData = mutableListOf(
                    ChildCategory(
                        0,
                        "Sample Data 1",
                        "",
                        ParentCategory(0, "Sample Data 1", R.drawable.icon_cate_bag)
                    ),
                )
                adapterChildCat.submitData(listSampleData)
            } else adapterChildCat.submitData(listChild.filter { it.categoryParent.typeCategory == TYPE_EXPENSE })
        }
    }

    private fun clickViews() = binding.apply {
        textAddMore.click { dialogAddCategory.show() }
        buttonCancel.click { activity?.finish() }
        buttonSaveRecord.click { onEventSaveRecord() }
    }

    private fun initCategoryParentAdapter() {
        adapterParentCat = ParentCategoryAdapter(
            contextParams = requireActivity(),
            onClickItem = { category, index ->
                val indexSelectedBefore = adapterParentCat.indexSelected
                adapterParentCat.indexSelected = index
                adapterParentCat.notifyItemChanged(indexSelectedBefore)

                parentCatSelected = category
            },
        )
    }

    private fun initAddCategoryDialog() {
        dialogAddCategory = AddCategoryDialog(
            context = requireActivity(),
            adapterCategoryParent = adapterParentCat,
            onInputNull = { activity?.showToast(R.string.this_field_cannot_be_left_blank) },
            onSaveCategory = { categoryName ->
                if (parentCatSelected == null) {
                    activity?.showToast(R.string.category_not_selected)
                } else {
                    // add to room
                    viewModel.insertChildCate(categoryName, parentCatSelected!!)

                    // refresh all
                    parentCatSelected = adapterParentCat.list[0]
                    val indexSelectedBefore = adapterParentCat.indexSelected
                    adapterParentCat.indexSelected = 0
                    adapterParentCat.notifyItemChanged(indexSelectedBefore)
                }
            }, onCancelSave = {
                parentCatSelected = adapterParentCat.list[0]
                val indexSelectedBefore = adapterParentCat.indexSelected
                adapterParentCat.indexSelected = 0
                adapterParentCat.notifyItemChanged(indexSelectedBefore)
            }
        )
    }

    private fun initChildCategory() = binding.rcvCategory.apply {
        adapterChildCat = ChildCategoryAdapter(
            contextParams = requireActivity(),
            onClickItem = { categoryChild, index ->
                if (categoryChild.id == 0L) {
                    activity?.showToast(R.string.this_is_sample_item)
                } else {
                    val indexSelectedBefore = adapterChildCat.indexSelected
                    adapterChildCat.indexSelected = index
                    adapterChildCat.notifyItemChanged(indexSelectedBefore)
                    childCatSelected = categoryChild
                }
            }, onEditItem = { categoryChild, index ->
                if (categoryChild.id == 0L) {
                    activity?.showToast(R.string.this_is_sample_item)
                } else {
                    dialogUpdateCategory.apply {
                        onInputNull =
                            { requireActivity().showToast(R.string.this_field_cannot_be_left_blank) }
                        onUpdateCategory = { title ->
                            categoryChild.categoryName = title
                            viewModel.updateChildCate(categoryChild)
                        }
                    }.show()
                }
            }
        )

        adapter = adapterChildCat
    }

    private fun initInputTime() {
        val currentTime = System.currentTimeMillis()
        val timeStr = activity?.convertLongToDateString(currentTime) ?: "MM dd,yyyy"
        binding.inputTime.setText(timeStr)
    }

    private fun onEventSaveRecord() {
        val noteTitle = binding.inputNoteTitle.text.toString().trim()
        val costText = binding.inputRevenue.text.toString().trim()
        val time = System.currentTimeMillis()

        if (noteTitle.isEmpty() || costText.isEmpty() || childCatSelected == null) {
            activity?.showToast(R.string.this_field_cannot_be_left_blank)
        } else {
            viewModel.saveRecordActualCost(
                requireActivity(),
                RecordActualCost(
                    noteTitle = noteTitle,
                    cost = costText.toLong(),
                    time = time,
                    categoryActualCost = childCatSelected!!
                )
            )

            // refresh
            binding.inputNoteTitle.text.clear()
            binding.inputRevenue.text.clear()
            childCatSelected = null
            val indexSelectedChildCatBefore = adapterChildCat.indexSelected
            adapterChildCat.indexSelected = -1
            adapterChildCat.notifyItemChanged(indexSelectedChildCatBefore)
            childCatSelected = null
        }
    }
}