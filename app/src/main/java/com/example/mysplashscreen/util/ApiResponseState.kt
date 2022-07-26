package com.example.mysplashscreen.util

/**
 * Different states for any network request.
 */
sealed class ApiResponseState {
    /**
     * Loading state.
     *
     */
    object Loading : ApiResponseState()

    /**
     * Success response state.
     *
     * @param response
     */
    data class Success(val response: Any) : ApiResponseState()

    /**
     * Error state.
     *
     * @param error
     */
    data class Error(val error: Throwable) : ApiResponseState()
}
