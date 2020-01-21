package com.araujojordan.fieldkonverter

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

/**
 * Designed and developed by Jordan Lira (@AraujoJordan)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
open class FieldKonverter(private vararg val fields: EditText?,var callback: (EditText?, EditText?) -> String) {

    var fieldChangeCallback : (EditText,String) -> Unit = { _, _ ->  }

    private val textChangeListener = object : TextWatcher {
        override fun afterTextChanged(content: Editable?) {
            val fieldFromChange = fields.firstOrNull { it?.isFocused==true }
            fields.forEach { fieldToChange ->
                if (fieldFromChange != fieldToChange)
                    fieldToChange?.let {fieldHasTextToChange(fieldFromChange, it)}
            }
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
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
