package com.ptc.challenge.presentation.splash_screen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.ptc.challenge.R
import com.ptc.challenge.data.remote.Exceptions
import com.ptc.challenge.presentation.MainActivity
import com.ptc.challenge.databinding.ActivitySplashScreenBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private val splashScreenViewModel: SplashScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listenToEvents()

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


    private fun listenToEvents() {

        lifecycleScope.launchWhenStarted {
            splashScreenViewModel.setupEvent.collect { event ->
                when (event) {
                    is SplashScreenViewModel.ConfigurationState.Error -> {
                        when (event.e) {
                            Exceptions.NoInternet -> {
                                Snackbar.make(
                                    binding.root,
                                    getString(R.string.error_something_went_wrong_internet_connection),
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }
                            Exceptions.NotFound -> {
                                Snackbar.make(
                                    binding.root,
                                    getString(R.string.error_something_went_wrong),
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }
                            Exceptions.ServerError -> {
                                Snackbar.make(
                                    binding.root,
                                    getString(R.string.error_something_went_wrong),
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }
                        }


                    }
                    is SplashScreenViewModel.ConfigurationState.Loading -> {

                    }
                    is SplashScreenViewModel.ConfigurationState.Success -> {

                        Intent(this@SplashScreen, MainActivity::class.java).also {
                            startActivity(it)
                            finish()
                        }

                    }
                    null -> Unit
                }
            }
        }
    }
}