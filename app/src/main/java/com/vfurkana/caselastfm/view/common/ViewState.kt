package com.vfurkana.caselastfm.view.common

import java.lang.Exception

sealed class ViewState<out T> {
    class Initial(val initMessage: String? = null) : ViewState<Nothing>()
    object Progress : ViewState<Nothing>()
    object Refresh : ViewState<Nothing>()
    class Error(val exception: Exception?) : ViewState<Nothing>() { constructor(t: Throwable?) : this(exception = Exception(t)) }
    class Empty(val emptyMessage: String? = null) : ViewState<Nothing>()
    class Success<out T>(val data: T) : ViewState<T>()
}