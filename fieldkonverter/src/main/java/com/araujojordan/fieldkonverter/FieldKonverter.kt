package com.araujojordan.fieldkonverter

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

/**
 * Designed and developed by Jordan Lira (@araujojordan)
 *
 * Copyright (C) 2020 Jordan Lira de Araujo Junior
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
open class FieldKonverter(private vararg val fields: EditText?,var callback: (EditText?, EditText?) -> String) {

    var fieldChangeCallback : (EditText,String) -> Unit = { _, _ ->  }
    var isRemoval = false
    var valueBefore: String = ""
    var cursorPosition: Int = 0

    private val textChangeListener = object : TextWatcher {
        override fun afterTextChanged(content: Editable?) {
            val fieldFromChange = fields.firstOrNull { it?.isFocused==true }
            fields.forEach { fieldToChange ->
                if (fieldFromChange != fieldToChange)
                    fieldToChange?.let {fieldHasTextToChange(fieldFromChange, it)}
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            isRemoval = after < 1
            valueBefore = s.toString()
            cursorPosition = start
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    init {
        fields.forEach { it?.addTextChangedListener(textChangeListener) }
    }




    private fun fieldHasTextToChange(
        fieldFromChange: EditText?,
        fieldToChange: EditText
    ) {
        fieldFromChange?.let {
            fieldToChange.removeTextChangedListener(textChangeListener)
            fieldFromChange.removeTextChangedListener(textChangeListener)
            fieldToChange.setText(callback(fieldFromChange, fieldToChange))
            fieldChangeCallback(fieldFromChange,fieldFromChange.text.toString())
            fieldChangeCallback(fieldToChange,fieldToChange.text.toString())
            fieldToChange.addTextChangedListener(textChangeListener)
            fieldFromChange.addTextChangedListener(textChangeListener)
        }
    }


}
