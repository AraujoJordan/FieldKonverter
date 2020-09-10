package com.araujojordan.fieldkonvertersample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
//        FieldKonverter(editText,editText2) { from, _ ->
//            from?.text.toString().reversed()
//            //test
//
//        }

//        //Simple example
        CurrencyKonverter(
            CurrencyField(editText, 0.5),
            CurrencyField(editText2, 2.0),
            afterChange = { from, to ->
                from?.setText("1234")
            }
        ).apply {
            decimalPlaces = 2
        }
    }
}
