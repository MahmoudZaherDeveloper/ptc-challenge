package com.ptc.challenge.remote

val responseNotFound = """
    
    
                                                   Request was not matched
                                                   =======================

    -----------------------------------------------------------------------------------------------------------------------
    | Closest stub                                             | Request                                                  |
    -----------------------------------------------------------------------------------------------------------------------
                                                               |
    PRATICAL ADMISSION :: API :: SEARCH :: PHONE :: PAGE 2     |
                                                               |
    GET                                                        | GET
    [path] /search/phone/page/2/                               | /search/phone/page/3                                <<<<< URL does not match
                                                               |
                                                               |
    -----------------------------------------------------------------------------------------------------------------------

""".trimIndent()