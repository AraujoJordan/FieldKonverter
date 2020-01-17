package com.araujojordan.fieldkonversor

import android.text.InputType
import android.text.method.DigitsKeyListener
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
class CurrencyKonversor(
    vararg fields: EditText
) : FieldKonversor(
    *fields,
    callback = { from: EditText, to: EditText -> "" }
) {

    var decimalPlaces: Int = 2
//    var maximumAmount: Double? = null

    var localCallback = { from: EditText, to: EditText ->
        try {
            var input = from.text.toString()
            val cursorPosition = from.selectionStart

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


            var amount = input.toDouble()

            //limit maximum amount
//            maximumAmount?.let {
//                if (amount > it) {
//                    input = (amount - (it - amount)).toString()
//                    amount = input.toDouble()
//                }
//            }

            from.setText(input)
            from.setSelection(if (cursorPosition > input.length) input.length else cursorPosition)

            "%.${decimalPlaces}f".format(amount * (to.tag as Double))
        } catch (err: Exception) {
            from.text.toString()
        }
    }

    init {
        fields.forEach {
            it.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            it.keyListener = DigitsKeyListener.getInstance("0123456789.")
        }
        callback = localCallback
    }
}

