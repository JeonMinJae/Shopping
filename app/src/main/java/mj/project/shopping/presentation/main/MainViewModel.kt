package mj.project.shopping.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import mj.project.shopping.presentation.BaseViewModel

internal class MainViewModel: BaseViewModel() {

    private var _mainStateLiveData = MutableLiveData<MainState>()
    val mainStateLiveData: LiveData<MainState> = _mainStateLiveData

    override fun fetchData(): Job = Job()

    fun refreshOrderList() = viewModelScope.launch {
        _mainStateLiveData.postValue(MainState.RefreshOrderList)
        // postValue : MainThread가 아닌 IO 스케쥴러를 활용하는 경우 postValue를 활용한다.
    }
}