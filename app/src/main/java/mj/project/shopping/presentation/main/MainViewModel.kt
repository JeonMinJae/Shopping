package mj.project.shopping.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import mj.project.shopping.presentation.BaseViewModel

//코루틴 사용하려고 gradle에 viewmodel 해줘야해
internal class MainViewModel: BaseViewModel() {

    override fun fetchData(): Job = Job()

    private var _mainStateLiveData = MutableLiveData<MainState>()
    val mainStateLiveData: LiveData<MainState> = _mainStateLiveData

    fun refreshOrderList() = viewModelScope.launch {
        _mainStateLiveData.postValue(MainState.RefreshOrderList)
    }

}