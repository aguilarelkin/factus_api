package com.factus.app.ui.facture

import androidx.lifecycle.ViewModel
import com.factus.app.domain.repository.DataStoreRepository
import com.factus.app.domain.repository.FactureRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FactureViewModel @Inject constructor(
    private val factureRepository: FactureRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel()