package com.adityadavin.nbsmoviedb.utils

import android.content.Context
import android.widget.Toast

sealed class Event {
    object NoEvent : Event()
    class ShowToast(private val message: String) : Event() {
        fun show(context: Context) = Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}