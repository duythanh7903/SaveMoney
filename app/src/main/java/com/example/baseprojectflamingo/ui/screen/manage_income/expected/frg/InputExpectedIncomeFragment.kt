package com.example.baseprojectflamingo.ui.screen.manage_income.expected.frg

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.lifecycle.ViewModelProvider
import com.example.baseprojectflamingo.R
import com.example.baseprojectflamingo.base.BaseFragment
import com.example.baseprojectflamingo.base.ext.click
import com.example.baseprojectflamingo.base.ext.convertDateStringToLong
import com.example.baseprojectflamingo.base.ext.convertLongToDateString3
import com.example.baseprojectflamingo.base.ext.showToast
import com.example.baseprojectflamingo.commons.Commons.getAllParentCategory
import com.example.baseprojectflamingo.database.AppDatabase
import com.example.baseprojectflamingo.database.entities.ChildCategory
import com.example.baseprojectflamingo.database.entities.ParentCategory
import com.example.baseprojectflamingo.database.entities.ParentCategory.Companion.TYPE_INCOME
import com.example.baseprojectflamingo.database.entities.RecordExpectedIncome
import com.example.baseprojectflamingo.database.repository.ChildCategoryRepository
import com.example.baseprojectflamingo.database.repository.ExpectedIncomeRepository
import com.example.baseprojectflamingo.database.repository.ParentCategoryRepository
import com.example.baseprojectflamingo.databinding.FragmentInputExpectedIncomeBinding
import com.example.baseprojectflamingo.ui.screen.dialog.AddCategoryDialog
import com.example.baseprojectflamingo.ui.adapter.ChildCategoryAdapter
import com.example.baseprojectflamingo.ui.adapter.ParentCategoryAdapter
import com.example.baseprojectflamingo.ui.screen.dialog.UpdateCategoryDialog
import com.example.baseprojectflamingo.ui.screen.manage_income.expected.frg.vm.ExpectedViewModel
import com.example.baseprojectflamingo.ui.screen.manage_income.expected.frg.vm.ViewModelExpectedFactory
import java.util.Calendar

class InputExpectedIncomeFragment :
    BaseFragment<FragmentInputExpectedIncomeBinding>(R.layout.fragment_input_expected_income) {

    private lateinit var dialogAddCategory: AddCategoryDialog
    private lateinit var adapterParentCat: ParentCategoryAdapter
    private lateinit var adapterChildCat: ChildCategoryAdapter
    private lateinit var viewModel: ExpectedViewModel
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
        val expectedIncomeDao = appDatabase.recordExpectedIncomeDao()
        val parentCateRepo = ParentCategoryRepository(parentCateDao)
        val childCateRepo = ChildCategoryRepository(childCateDao)
        val expectedIncomeRepo = ExpectedIncomeRepository(expectedIncomeDao)
        val viewModelFactory =
            ViewModelExpectedFactory(parentCateRepo, childCateRepo, expectedIncomeRepo)
        viewModel = ViewModelProvider(this, viewModelFactory)[ExpectedViewModel::class.java]
    }

    private fun observe() = viewModel.apply {
        listParentCat.observe(viewLifecycleOwner) { listParent ->
            if (listParent.isEmpty() || listParent.none { it.typeCategory == TYPE_INCOME }) viewModel.insertAllParentCat(
                getAllParentCategory()
            )
            else adapterParentCat.submitData(listParent.filter { it.typeCategory == TYPE_INCOME })
            parentCatSelected = adapterParentCat.list[0]
        }

        listChildCat.observe(viewLifecycleOwner) { listChild ->
            if (listChild.isEmpty() || listChild.none { it.categoryParent.typeCategory == TYPE_INCOME }) {
                val listSampleData = mutableListOf(
                    ChildCategory(
                        0,
                        "Sample Data 1",
                        "",
                        ParentCategory(0, "Sample Data 1", R.drawable.icon_cate_bag)
                    ),
                )
                adapterChildCat.submitData(listSampleData)
            } else adapterChildCat.submitData(listChild.filter { it.categoryParent.typeCategory == TYPE_INCOME })
        }
    }

    private fun clickViews() = binding.apply {
        textAddMore.click { dialogAddCategory.show() }
        buttonCancel.click { activity?.finish() }
        buttonSaveRecord.click { onEventSaveRecord() }
        iconChoseTime.click { onEventChooseTime() }
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
            }, onEditItem = { c, i ->
                dialogUpdateCategory.apply {
                    onInputNull = { requireActivity().showToast(R.string.this_field_cannot_be_left_blank) }
                    onUpdateCategory = { title ->
                        c.categoryName = title
                        viewModel.updateChildCate(c)
                    }
                    show()
                }
            }
        )

        adapter = adapterChildCat
    }

    private fun initInputTime() {
        /*val currentTime = System.currentTimeMillis()
        val timeStr = activity?.convertLongToDateString3(currentTime) ?: "MM dd,yyyy"
        binding.inputTime.setText(timeStr)*/

        val currentTime = System.currentTimeMillis()

        // Tạo một đối tượng Calendar với thời gian hiện tại
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = currentTime

        // Cộng thêm 1 ngày
        calendar.add(Calendar.DAY_OF_MONTH, 1)

        // Lấy thời gian mới
        val newTime = calendar.timeInMillis

        // Chuyển đổi thành chuỗi ngày
        val timeStr = activity?.convertLongToDateString3(newTime) ?: "MM dd, yyyy"
        binding.inputTime.setText(timeStr)
    }

    private fun onEventSaveRecord() {
        val noteTitle = binding.inputNoteTitle.text.toString().trim()
        val revenueText = binding.inputRevenue.text.toString().trim()
        val time = activity?.convertDateStringToLong(binding.inputTime.text.toString().trim()) ?: System.currentTimeMillis()

        if (noteTitle.isEmpty() || revenueText.isEmpty() || childCatSelected == null) {
            activity?.showToast(R.string.this_field_cannot_be_left_blank)
        } else {
            viewModel.saveRecordExpectedIncome(
                RecordExpectedIncome(
                    noteTitle = noteTitle,
                    revenue = revenueText.toLong(),
                    time = time,
                    service = childCatSelected!!
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

    @SuppressLint("SetTextI18n")
    private fun onEventChooseTime() {
        val calendar = Calendar.getInstance()
        // Đặt ngày tối thiểu là ngày mai
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        val minDate = calendar.timeInMillis
        val datePickerDialog = DatePickerDialog(
            requireActivity(),
            { _, selectedYear, selectedMonth, selectedDay ->
                binding.inputTime.setText("$selectedDay/${selectedMonth + 1}/$selectedYear")
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        // Thiết lập ngày tối thiểu
        datePickerDialog.datePicker.minDate = minDate

        datePickerDialog.show()
    }
}