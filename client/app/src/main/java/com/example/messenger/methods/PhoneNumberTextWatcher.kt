package com.example.messenger.methods

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import androidx.core.content.PackageManagerCompat.LOG_TAG
import com.google.android.material.textfield.TextInputEditText


class PhoneNumberTextWatcher(
    private val textInputEditTextCode: TextInputEditText,
    private val textInputEditTextNumber: TextInputEditText
) : TextWatcher,KeyEvent.Callback{

    private var flag: Boolean = false
    override fun beforeTextChanged(text: CharSequence, p1: Int, p2: Int, p3: Int) {
        if(text.isEmpty())
        {
            flag = true
        }
    }

    override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {

    }

    override fun afterTextChanged(text: Editable) {
        if(text.isEmpty() && flag)
        {
            textInputEditTextCode.requestFocus()
        }
    }

    override fun onKeyDown(p0: Int, p1: KeyEvent?): Boolean {
        return true
    }

    override fun onKeyLongPress(p0: Int, p1: KeyEvent?): Boolean {
        return true
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {

        return true
    }

    override fun onKeyMultiple(p0: Int, p1: Int, p2: KeyEvent?): Boolean {
        return true
    }


}