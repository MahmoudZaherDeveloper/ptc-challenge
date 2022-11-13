package com.ptc.challenge.utils

import com.ptc.challenge.data.remote.Exceptions

sealed class Resource<T>(val data: T? = null, val exception: Exceptions? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(exception: Exceptions, data: T? = null) : Resource<T>(data, exception)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}