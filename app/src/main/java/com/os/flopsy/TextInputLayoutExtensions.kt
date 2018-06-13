package com.os.flopsy

import android.annotation.SuppressLint
import android.support.design.widget.CheckableImageButton
import android.support.design.widget.TextInputLayout
import android.view.MotionEvent
import timber.log.Timber

/**
 * Created by Omar on 12-Jun-18 6:50 PM.
 */

@SuppressLint("ClickableViewAccessibility")

fun TextInputLayout.setPasswordToggleListener(listener: OnPasswordToggleListener) {
    val passwordToggle = findViewById<CheckableImageButton>(R.id.text_input_password_toggle)
    passwordToggle.setOnTouchListener { _, event ->
        Timber.d("Motion Event type = ${event.action}")
        if (event.action == MotionEvent.ACTION_UP) {
            listener.onPasswordToggleChanged(passwordToggle.isChecked)
        }
        false
    }
}

fun TextInputLayout.isPasswordToggleChecked(): Boolean {
    val passwordToggle = findViewById<CheckableImageButton>(R.id.text_input_password_toggle)
    return passwordToggle.isChecked
}

interface OnPasswordToggleListener {
    fun onPasswordToggleChanged(isChecked: Boolean)
}