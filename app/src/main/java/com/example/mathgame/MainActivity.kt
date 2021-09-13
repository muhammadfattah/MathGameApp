package com.example.mathgame

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Range
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var resultTextView : TextView
    lateinit var answerTextView : TextView

    lateinit var topLeftButton : Button
    lateinit var topRightButton : Button
    lateinit var bottomLeftButton : Button
    lateinit var bottomRightButton : Button

    val questionRange : IntRange = (10..100)

    var answer = 0

    var remain = 0

    var isFirstButtonPressed: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        resultTextView = findViewById(R.id.resultTextView)
        answerTextView = findViewById(R.id.answerTextView)

        topLeftButton = findViewById(R.id.topLeftButton)
        topRightButton = findViewById(R.id.topRightButton)
        bottomLeftButton = findViewById(R.id.bottomLeftButton)
        bottomRightButton = findViewById(R.id.bottomRightButton)

        generateQuestion()
    }


    fun generateQuestion(){

        setButtonIsEnable(true)
        resultTextView.text = ""
        isFirstButtonPressed = false

        fun IntRange.random() = Random().nextInt((endInclusive) -
                start) + start

        answer = (questionRange).random()
        answerTextView.text = answer.toString()

        remain = 0

        val valueList = ArrayList<Int>()
        var i = 0

        while (i < 2){
            val option1 = (1..answer).random()
            val option2 = answer - option1

            valueList.add(option1)
            valueList.add(option2)

            i++
        }

        var currentValue = valueList.removeAt((0..valueList.size).random())
        topLeftButton.text = currentValue.toString()

        currentValue = valueList.removeAt((0..valueList.size).random())
        topRightButton.text = currentValue.toString()

        currentValue = valueList.removeAt((0..valueList.size).random())
        bottomLeftButton.text = currentValue.toString()

        currentValue = valueList.removeAt((0..valueList.size).random())
        bottomRightButton.text = currentValue.toString()
    }

    fun buttonPressed(v : View){

        var button : Button = v as Button
        v.isEnabled = false

        remain += Integer.parseInt(button.text.toString())

        if(isFirstButtonPressed){
            setButtonIsEnable(false)
            if (remain == answer){
                resultTextView.text = "Correct!"
            }
            else{
                resultTextView.text = "Incorrect!"
            }

            Handler().postDelayed({
                generateQuestion()
            },3000)
        }
        else{
            isFirstButtonPressed = true
        }
    }

    fun setButtonIsEnable(isEnabled : Boolean){
        topLeftButton.isEnabled = isEnabled
        topRightButton.isEnabled = isEnabled
        bottomLeftButton.isEnabled = isEnabled
        bottomRightButton.isEnabled = isEnabled
    }

}