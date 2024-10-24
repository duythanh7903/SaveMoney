package com.example.baseprojectflamingo.ui.screen.dialog

import android.content.Context
import com.example.baseprojectflamingo.R
import com.example.baseprojectflamingo.base.BaseDialog
import com.example.baseprojectflamingo.base.ext.click
import com.example.baseprojectflamingo.databinding.DialogChooseFilterTypeBinding

typealias TypeFilter = Int

class ChooseFilterTypeDialog(
    context: Context,
    var flagChooseSingleOrRange: Int = FLAG_RANGE,
    var onChangeTypeFilter: ((TypeFilter) -> Unit)? = null
) : BaseDialog<DialogChooseFilterTypeBinding>(context) {

    companion object {
        internal const val FLAG_RANGE = 0
        internal const val FLAG_SINGLE = 1
    }

    override fun getLayoutDialog(): Int = R.layout.dialog_choose_filter_type

    override fun initViews() {
        super.initViews()
        setCancelable(false)

        when (flagChooseSingleOrRange) {
            FLAG_RANGE -> {
                binding.iconTickRange.isActivated = true
                binding.iconTickSingle.isActivated = false
            }

            else -> {
                binding.iconTickRange.isActivated = false
                binding.iconTickSingle.isActivated = true
            }
        }
    }

    override fun onClickViews() {
        binding.apply {
            iconClear.click { dismiss() }

            btnRangeDate.click {
                onChangeTypeFilter?.invoke(FLAG_RANGE)
                dismiss()
            }

            btnSingleDate.click {
                onChangeTypeFilter?.invoke(FLAG_SINGLE)
                dismiss()
            }
        }
    }
}