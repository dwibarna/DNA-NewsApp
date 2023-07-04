package com.sobarna.dnanewsapp.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.sobarna.dnanewsapp.domain.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(useCase: NewsUseCase) : ViewModel() {

    val getAllHistory = useCase.getListHistory().asLiveData()
}