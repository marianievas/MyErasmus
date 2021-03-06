package com.myerasmus.data.model

/**
 * Data Class *ResultSignUp*
 *
 * expresses the result of a sign-up execution.
 * @author Ferran
 * @property success: means that the sign-up was successful
 * @property error: means that there was an error signing up. contains the exception obtained.
 */
data class ResultSignUp(
    val success: Boolean? = null, // bool innecessari però esque anira checkejant des de la Activity si és null.
    val error: Exception? = null
)
