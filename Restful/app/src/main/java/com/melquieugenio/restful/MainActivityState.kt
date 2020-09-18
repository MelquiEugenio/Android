package com.melquieugenio.restful

sealed class MainActivityState {
    object OnLoading : MainActivityState()
    object OnLoaded : MainActivityState()
    data class OnError(val error: Throwable) : MainActivityState()
}
