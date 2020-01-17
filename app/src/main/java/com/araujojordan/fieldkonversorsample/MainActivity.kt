package com.araujojordan.fieldkonversorsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.araujojordan.fieldkonversor.CurrencyKonversor
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Simple example
        editText.tag = 0.5
        editText2.tag = 2.0
        CurrencyKonversor(editText, editText2).apply {
            maximumAmount = 25000.00
        }
    }
}
