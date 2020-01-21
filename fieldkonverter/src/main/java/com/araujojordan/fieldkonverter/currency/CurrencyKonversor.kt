package com.araujojordan.fieldkonverter.currency

import android.text.InputType
import android.text.method.DigitsKeyListener
import android.util.Log
import android.widget.EditText
import com.araujojordan.fieldkonverter.FieldKonverter

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
class CurrencyKonverter(
    vararg val fields: CurrencyField
) : FieldKonverter(
    *(ArrayList<EditText?>().apply {
        fields.forEach { add(it.editField) }
    }.toTypedArray()),
    callback = { _ , _ -> "" }
) {
    var decimalPlaces: Int = 2

    var localCallback = { from: EditText?, to: EditText? ->
        try {
            var input = from?.text.toString()
            val cursorPosition = from?.selectionStart?:0

            var indexOfFirstDot = input.indexOf('.')
            var indexOfLastDot = input.lastIndexOf('.')

            //remove duplicate dots
            while (indexOfFirstDot != indexOfLastDot) {
                input = input.reversed().replaceFirst(".", "").reversed()
                indexOfFirstDot = input.indexOf('.')
                indexOfLastDot = input.lastIndexOf('.')
            }

            //remove strange characters
            input = input.replace(("[^\\d.]").toRegex(), "")

            //limit number of decimal places
            if (indexOfFirstDot > -1 && indexOfFirstDot + (decimalPlaces + 1) <= input.length)
                input = input.substring(0, indexOfFirstDot + (decimalPlaces + 1))


            val fromField =  fields.firstOrNull { it.editField == from }
            val toField =  fields.firstOrNull { it.editField == to }

            //limit maximum amount
            var amount : Double = input.toDouble()
            fromField?.maximumAmount?.let {
                if (amount > it) amount = it
            }

            from?.setText(amount.toString())
            from?.setSelection(if (cursorPosition > amount.toString().length) amount.toString().length else cursorPosition)

            val exchangeRate = toField?.currencyAmount?:0.0

            "%.${decimalPlaces}f".format(amount * exchangeRate)
        } catch (err: Exception) {
            from?.text.toString()
        }
    }

    init {
        fields.forEach {
            it.editField?.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            it.editField?.keyListener = DigitsKeyListener.getInstance("0123456789.")
        }
        callback = localCallback
    }
}
