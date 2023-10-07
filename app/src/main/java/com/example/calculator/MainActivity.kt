package com.example.calculator


import android.animation.PropertyValuesHolder
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var inputTextView: TextView

    private var isNegative = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputTextView = findViewById(R.id.inputoutputtext)

        val clearButton: Button = findViewById(R.id.button_clear)
        clearButton.setOnClickListener {
            inputTextView.text = ""
            isNegative = false

            enableDotButton()
        }

        val plusminusButton: Button = findViewById(R.id.button_plusminus)
        plusminusButton.setOnClickListener {
            if (inputTextView.text.isNotEmpty()) {
                if (isNegative) {
                    inputTextView.text = inputTextView.text.toString().removePrefix("-")
                    isNegative = false
                } else {
                    inputTextView.text = "-${inputTextView.text}"
                    isNegative = true
                }
                disableDotButton()
            }
        }

        val percentButton:Button = findViewById(R.id.button_percent)
        percentButton.setOnClickListener {
            if (isLastCharOperator()) {
                replaceLastChar(percentButton.text[0])
            } else {
                inputTextView.append(percentButton.text)
            }
            disableDotButton()
        }
        val additionButton:Button = findViewById(R.id.button_addition)
        additionButton.setOnClickListener {
            if (isLastCharOperator()) {
                replaceLastChar(additionButton.text[0])
            } else {
                inputTextView.append(additionButton.text)
            }
            disableDotButton()
        }
        val equalButton:Button = findViewById(R.id.button_equals)
        equalButton.setOnClickListener {
            if (isLastCharOperator()) {
                replaceLastChar(equalButton.text[0])
            } else {
                inputTextView.append(equalButton.text)
            }
            disableDotButton()
        }
        val divisionButton:Button = findViewById(R.id.button_division)
        divisionButton.setOnClickListener {
            if (isLastCharOperator()) {
                replaceLastChar(divisionButton.text[0])
            } else {
                inputTextView.append(divisionButton.text)
            }
            disableDotButton()
        }
        val substractionButton:Button = findViewById(R.id.button_subtraction)
        substractionButton.setOnClickListener {
            if (isLastCharOperator()) {
                replaceLastChar(substractionButton.text[0])
            } else {
                inputTextView.append(substractionButton.text)
            }
            disableDotButton()
        }
        val multiplyButton:Button = findViewById(R.id.button_multiply)
        multiplyButton.setOnClickListener {
            if (isLastCharOperator()) {
                replaceLastChar(multiplyButton.text[0])
            } else {
                inputTextView.append(multiplyButton.text)
            }
            disableDotButton()
        }

        val dotButton:Button = findViewById(R.id.button_dot)
        dotButton.setOnClickListener {
            if (!isDotAlreadyPresent()) {
                inputTextView.append(dotButton.text)
                disableDotButton() // Disable dot button after appending a dot once.
            }
        }

        val buttonIds = arrayOf(
            R.id.button_0, R.id.button_1, R.id.button_2,
            R.id.button_3, R.id.button_4, R.id.button_5,
            R.id.button_6, R.id.button_7, R.id.button_8,
            R.id.button_9
        )

        for (id in buttonIds) {
            val button = findViewById<Button>(id)

            // Set OnClickListener to append the text of each button to the TextView when it is clicked.
            button.setOnClickListener {
                inputTextView.append(button.text)
                if (!isDotAlreadyPresent()) {
                    enableDotButton()
                }
            }

            applyScaleAnimation(button)
        }
    }
    private fun applyScaleAnimation(view: View) {
        val scaleXDown = PropertyValuesHolder.ofFloat(View.SCALE_X, 0.9f)
        val scaleYDown = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.9f)
        val animatorDown = ObjectAnimator.ofPropertyValuesHolder(view, scaleXDown, scaleYDown)
        animatorDown.duration = 200

        val scaleXUp = PropertyValuesHolder.ofFloat(View.SCALE_X, 1f)
        val scaleYUp = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f)
        val animatorUp = ObjectAnimator.ofPropertyValuesHolder(view, scaleXUp, scaleYUp)
        animatorUp.duration = 200

        view.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    animatorDown.start()
                }
                MotionEvent.ACTION_UP -> {
                    view.performClick()
                    animatorUp.start()
                }
            }
            true
        }
    }
    private fun isDotAlreadyPresent(): Boolean {
        val text = inputTextView.text.toString()
        val parts = text.split('+', '-', 'x', '/', '%')
        return parts.any { it.contains(".") }
    }


    private fun disableDotButton() {
        val dotButton: Button = findViewById(R.id.button_dot)
        dotButton.isEnabled = false

        // You can also change the appearance of the disabled state here if desired.
    }

    private fun enableDotButton(){
        val dotButton: Button = findViewById(R.id.button_dot)
        dotButton.isEnabled = true

        // You can also change the appearance of the enabled state here if desired.
    }
    private fun isLastCharOperator(): Boolean {
        val text = inputTextView.text.toString()
        return text.isNotEmpty() && !Character.isDigit(text.last())
    }
    private fun replaceLastChar(newChar: Char) {
        val text = inputTextView.text.toString()
        if (text.isNotEmpty()) {
            inputTextView.text = text.dropLast(1) + newChar
        }
    }


}
