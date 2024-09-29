package com.example.baseprojectflamingo.ui.screen.dialog

import android.content.Context
import com.example.baseprojectflamingo.R
import com.example.baseprojectflamingo.base.BaseDialog
import com.example.baseprojectflamingo.base.ext.click
import com.example.baseprojectflamingo.databinding.DialogAddCategoryBinding
import com.example.baseprojectflamingo.ui.adapter.ParentCategoryAdapter

class AddCategoryDialog(
    private val context: Context,
    private val adapterCategoryParent: ParentCategoryAdapter,
    private val onInputNull: () -> Unit,
    private val onSaveCategory: (categoryName: String) -> Unit,
    private val onCancelSave: () -> Unit
) : BaseDialog<DialogAddCategoryBinding>(context) {

    override fun getLayoutDialog(): Int = R.layout.dialog_add_category

    override fun initViews() {
        initRcvCategoryParent()
        setCancelable(false)
    }

    override fun onClickViews() {
        binding.apply {
            iconClear.click {
                onCancelSave.invoke()
                dismiss()
            }

            buttonSave.click {
                val categoryName = inputCategoryName.text.toString().trim()
                if (categoryName.isEmpty()) onInputNull.invoke()
                else {
                    onSaveCategory.invoke(categoryName)
                    binding.inputCategoryName.text.clear()
                    dismiss()
                }
            }
        }
    }

    private fun initRcvCategoryParent() =
        binding.rcvCategoryParent.apply {
            adapter = adapterCategoryParent
        }
}