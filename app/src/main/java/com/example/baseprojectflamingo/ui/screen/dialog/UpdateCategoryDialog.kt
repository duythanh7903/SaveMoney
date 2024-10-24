package com.example.baseprojectflamingo.ui.screen.dialog

import android.content.Context
import com.example.baseprojectflamingo.R
import com.example.baseprojectflamingo.base.BaseDialog
import com.example.baseprojectflamingo.base.ext.click
import com.example.baseprojectflamingo.databinding.DialogUpdateCategoryBinding

class UpdateCategoryDialog(
    private val context: Context,
    var onUpdateCategory: ((title: String) -> Unit)? = null,
    var onInputNull: (() -> Unit)? = null
) : BaseDialog<DialogUpdateCategoryBinding>(context) {

    override fun getLayoutDialog(): Int = R.layout.dialog_update_category

    override fun initViews() {
        super.initViews()
        setCancelable(false)
    }

    override fun onClickViews() {
        binding.buttonSave.click {
            val title = binding.inputCategoryName.text.toString().trim()
            if (title.isEmpty()) {
                onInputNull?.invoke()
            } else {
                onUpdateCategory?.invoke(title)
                dismiss()
            }
        }
    }
}