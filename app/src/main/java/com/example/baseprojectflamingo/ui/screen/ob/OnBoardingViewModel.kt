package com.example.baseprojectflamingo.ui.screen.ob

import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baseprojectflamingo.R
import com.example.baseprojectflamingo.ui.domain.OnBoarding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OnBoardingViewModel : ViewModel() {

    private val _introList = MutableLiveData<MutableList<OnBoarding>>()

    val introList: LiveData<MutableList<OnBoarding>> = _introList

    fun fetchDataIntro() = viewModelScope.launch(Dispatchers.IO) {
        _introList.postValue(mutableListOf<OnBoarding>().apply {
            add(OnBoarding(0, R.drawable.img_ob_1, R.string.ob_t_1, R.string.ob_d_1))
            add(OnBoarding(1, R.drawable.img_ob_2, R.string.ob_t_2, R.string.ob_d_2))
            add(OnBoarding(2, R.drawable.img_ob_3, R.string.ob_t_3, R.string.ob_d_3))
        })
    }

    fun updateIndicatorView(indexSelected: Int, indicators: MutableList<ImageView>) =
        indicators.forEachIndexed { index, imageView ->
            imageView.setImageResource(if (index == indexSelected) R.drawable.ic_ob_selected else R.drawable.ic_ob_un_selected)
        }

}