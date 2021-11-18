package com.borja.pruebanewyorktimes.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.borja.pruebanewyorktimes.domain.fold
import com.borja.pruebanewyorktimes.domain.usecase.GetNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.borja.pruebanewyorktimes.data.remote.dto.Result

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase
) : ViewModel() {

    private val _uiModel: MutableStateFlow<UiModel> =
        MutableStateFlow(UiModel.Loading)
    val uiModel: StateFlow<UiModel> = _uiModel

    fun getData(type: String, period: Int, mostPopularBy: String?) {
        viewModelScope.launch {
            _uiModel.value = UiModel.Loading
            getNewsUseCase.invoke(type, period, mostPopularBy).fold({
                _uiModel.value = UiModel.Failure
            }, {
                _uiModel.value = UiModel.Success(it.results)
            })
        }
    }


    sealed class UiModel {
        object Loading : UiModel()
        data class Success(val newsList: List<Result>): UiModel()
        object Failure : UiModel()
    }
}