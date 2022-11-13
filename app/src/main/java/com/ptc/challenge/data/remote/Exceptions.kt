package com.ptc.challenge.data.remote

import com.ptc.challenge.utils.Constants.Exceptions.NOT_FOUND
import com.ptc.challenge.utils.Constants.Exceptions.NO_INTERNET
import com.ptc.challenge.utils.Constants.Exceptions.SOMETHING_WENT_WRONG

sealed class Exceptions(val error: String) {
    object NoInternet : Exceptions(NO_INTERNET)
    object NotFound : Exceptions(NOT_FOUND)
    object ServerError : Exceptions(SOMETHING_WENT_WRONG)

}
