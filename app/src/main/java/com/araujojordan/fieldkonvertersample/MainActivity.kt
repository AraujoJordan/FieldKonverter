package com.araujojordan.fieldkonvertersample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.araujojordan.fieldkonverter.FieldKonverter
import com.araujojordan.fieldkonverter.currency.CurrencyField
import com.araujojordan.fieldkonverter.currency.CurrencyKonverter
import kotlinx.android.synthetic.main.activity_main.*


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
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // link inverter
        FieldKonverter(editText,editText2) { from, to ->
            from?.text.toString().reversed()
        }

//        //Simple example
//        CurrencyKonverter(
//            CurrencyField(editText,0.5,25000.00),
//            CurrencyField(editText2,2.0,50000.00)
//        ).apply {
//            decimalPlaces = 3
//        }
    }
}