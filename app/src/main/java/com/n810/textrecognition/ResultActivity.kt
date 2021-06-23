package com.n810.textrecognition

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.n810.TextRecognition.R
import kotlinx.android.synthetic.main.activity_main.*


class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        capture_button.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }

        load_button.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }

        convert_button.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }
}