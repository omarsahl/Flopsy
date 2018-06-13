package com.os.flopsy

import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var bunn: Bunn

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bunn = Bunn(animationView)

        setupBunnListeners()
    }

    private fun setupBunnListeners() {
        emailInputEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                bunn.setPreTrackingState()
            }
        }

        passwordInputEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                if (passwordInputLayout.isPasswordToggleChecked())
                    bunn.setPeekState()
                else
                    bunn.setShyState()
            }
        }

        passwordInputLayout.setPasswordToggleListener(object : OnPasswordToggleListener {
            override fun onPasswordToggleChanged(isChecked: Boolean) {
                if (!passwordInputEditText.hasFocus()) return
                if (isChecked) {
                    bunn.setShyState()
                } else {
                    bunn.setPeekState()
                }
            }
        })

        emailInputEditText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Timber.d("emailInputWidth=${emailInputEditText.width}, textWidth=${getTextWidth(emailInputEditText)}")
                bunn.setEyesPosition(getTextWidth(emailInputEditText) / emailInputEditText.width)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                bunn.startTracking()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun getTextWidth(editText: TextInputEditText): Float {
        return editText.paint.measureText(editText.text.toString())
    }
}
