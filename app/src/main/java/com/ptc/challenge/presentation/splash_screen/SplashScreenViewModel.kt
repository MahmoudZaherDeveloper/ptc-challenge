package com.ptc.challenge.presentation.splash_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ptc.challenge.data.remote.Exceptions
import com.ptc.challenge.domain.model.Currency
import com.ptc.challenge.domain.use_case.Configuration
import com.ptc.challenge.utils.Constants.Companion.SPLASHSCREEN_DELAY
import com.ptc.challenge.utils.DispatcherProvider
import com.ptc.challenge.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val configuration: Configuration,
    private val dispatchers: DispatcherProvider,
) : ViewModel() {


    private val _setupEvent = MutableStateFlow<ConfigurationState?>(null)
    val setupEvent = _setupEvent.asStateFlow()

    sealed class ConfigurationState {
        object Loading : ConfigurationState()
        data class Error(val e: Exceptions) : ConfigurationState()
        data class Success(val configuration: Currency) : ConfigurationState()

    }

    init {
        getConfigurations()
    }

    private fun getConfigurations() {
        viewModelScope.launch(dispatchers.io) {
            delay(SPLASHSCREEN_DELAY)

            configuration().collect {
                when (it) {
                    is Resource.Error -> {
                        _setupEvent.emit(
                            ConfigurationState.Error(
                                it.exception ?: Exceptions.ServerError
                            )
                        )
                    }
                    is Resource.Loading -> {
                        _setupEvent.emit(ConfigurationState.Loading)
                    }
                    is Resource.Success -> {

                        _setupEvent.emit(ConfigurationState.Success(it.data!!))
                    }
                }
            }
        }
    }

}