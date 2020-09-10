package com.araujojordan.fieldkonverter.currency

import android.text.InputType
import android.text.method.DigitsKeyListener
import android.util.Log
import android.widget.EditText
import com.araujojordan.fieldkonverter.FieldKonverter
import java.util.*
import kotlin.collections.ArrayList

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
class CurrencyKonverter(
    vararg val fields: CurrencyField,
    val afterChange: ((EditText?, EditText?) -> Unit)? = null
) : FieldKonverter(
    *(ArrayList<EditText?>().apply {
        fields.forEach { add(it.editField) }
    }.toTypedArray()),
    callback = null
) {
    var decimalPlaces: Int = 2

    var localCallback = { from: EditText?, to: EditText? ->
        try {
            var input = from?.text.toString()
            val cursorPosition = from?.selectionStart ?: 0

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

            //fix dot removal
            if (isRemoval && !input.contains(".") && valueBefore.contains(".")) {
                from?.setText(valueBefore)
                from?.setSelection(cursorPosition)
                to?.text.toString()
            } else {

                //limit number of decimal places
                if (indexOfFirstDot > -1 && indexOfFirstDot + (decimalPlaces + 1) <= input.length)
                    input = input.substring(0, indexOfFirstDot + (decimalPlaces + 1))


                val fromField = fields.firstOrNull { it.editField == from }
                val toField = fields.firstOrNull { it.editField == to }

                //limit maximum amount
                var amount: Double = input.toDouble()
                fromField?.maximumAmount?.let {
                    if (amount > it) amount = it
                }

                from?.setText(String.format(Locale.US, "%.${decimalPlaces}f", amount))
                from?.setSelection(if (cursorPosition > from.text.toString().length) from.text.toString().length else cursorPosition)

                val exchangeRate = toField?.currencyAmount ?: 0.0

                String.format(Locale.US, "%.${decimalPlaces}f", (amount * exchangeRate))
            }
        } catch (err: Exception) {
            Log.e("CurrencyKonverter", err.message.toString())
            from?.text.toString()
        }
    }

    override fun fieldHasTextToChange(
        fieldFromChange: EditText?,
        fieldToChange: EditText
    ) {
        fieldFromChange?.let {
            fieldToChange.removeTextChangedListener(textChangeListener)
            fieldFromChange.removeTextChangedListener(textChangeListener)
            callback?.let { fieldToChange.setText(it(fieldFromChange, fieldToChange)) }
            afterChange?.invoke(fieldFromChange, fieldToChange)
            fieldChangeCallback(fieldFromChange,fieldFromChange.text.toString())
            fieldChangeCallback(fieldToChange,fieldToChange.text.toString())
            fieldToChange.addTextChangedListener(textChangeListener)
            fieldFromChange.addTextChangedListener(textChangeListener)
        }
    }

    init {
        fields.forEach {
            it.editField?.inputType =
                InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            it.editField?.keyListener = DigitsKeyListener.getInstance("0123456789.")
        }
        callback = localCallback
    }
}
