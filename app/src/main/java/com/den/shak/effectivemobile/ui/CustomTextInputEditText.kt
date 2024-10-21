package com.den.shak.effectivemobile.ui

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.EditText
import com.google.android.material.textfield.TextInputEditText

class CustomTextInputEditText(context: Context, attrs: AttributeSet) : TextInputEditText(context, attrs) {
    init {
        setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                hint = ""
            } else {
                if (text.isNullOrEmpty()) {
                    hint = "✱"
                }
            }
        }

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length == 1) {
                    focusNextField()
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun focusNextField() {
        val nextView = focusSearch(FOCUS_RIGHT)
        if (nextView != null && nextView is EditText) {
            nextView.requestFocus()
        }
    }
}
