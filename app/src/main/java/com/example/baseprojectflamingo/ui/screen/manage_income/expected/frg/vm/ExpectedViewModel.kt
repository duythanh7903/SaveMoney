package com.example.baseprojectflamingo.ui.screen.manage_income.expected.frg.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baseprojectflamingo.database.entities.ChildCategory
import com.example.baseprojectflamingo.database.entities.ParentCategory
import com.example.baseprojectflamingo.database.entities.RecordExpectedIncome
import com.example.baseprojectflamingo.database.repository.ChildCategoryRepository
import com.example.baseprojectflamingo.database.repository.ExpectedIncomeRepository
import com.example.baseprojectflamingo.database.repository.ParentCategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ExpectedViewModel(
    private val parentCateRepo: ParentCategoryRepository,
    private val childCatRepo: ChildCategoryRepository,
    private val expectedIncomeRepo: ExpectedIncomeRepository
): ViewModel() {

    val listParentCat =
        parentCateRepo.getAllCategoryParents()
    val listChildCat =
        childCatRepo.getAllChildCategories()
    val listRecordRevenue =
        expectedIncomeRepo.getAllRecordExpectedIncome()

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

    fun saveRecordExpectedIncome(record: RecordExpectedIncome) =
        viewModelScope.launch(Dispatchers.IO) {
            expectedIncomeRepo.saveRecordExpectedIncome(record)
            cancel()
        }

}