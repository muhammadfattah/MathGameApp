package com.example.mathgame

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Range
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var resultImageView : ImageView
    lateinit var answerTextView : TextView

    lateinit var textViewA: TextView
    lateinit var textViewB: TextView
    lateinit var textViewC: TextView
    lateinit var textViewD: TextView

    lateinit var imageButtonA: ImageButton
    lateinit var imageButtonB: ImageButton
    lateinit var imageButtonC: ImageButton
    lateinit var imageButtonD: ImageButton

    val questionRange : IntRange = (10..100)

    var answer = 0

    var remain = 0

    var isFirstButtonPressed: Boolean = false

    var optionA  = 0
    var optionB = 0
    var optionC = 0
    var optionD = 0

    var jawabanOptionA : Boolean = false
    var jawabanOptionB : Boolean = false
    var jawabanOptionC : Boolean = false
    var jawabanOptionD : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        resultImageView = findViewById(R.id.resultImageView)

        answerTextView = findViewById(R.id.answerTextView)

        textViewA = findViewById(R.id.textViewA)
        textViewB = findViewById(R.id.textViewB)
        textViewC = findViewById(R.id.textViewC)
        textViewD = findViewById(R.id.textViewD)

        imageButtonA = findViewById(R.id.imageButtonA)
        imageButtonB = findViewById(R.id.imageButtonB)
        imageButtonC = findViewById(R.id.imageButtonC)
        imageButtonD = findViewById(R.id.imageButtonD)

        generateQuestion()

        resultImageView.alpha = 0.0f
        resultImageView.scaleX = 0.75f
        resultImageView.scaleY = 0.75f

    }


    fun generateQuestion(){
        isFirstButtonPressed = false
        setButtonEnable()
        animasiFadeIn(answerTextView)
        animasiFadeIn(textViewA)
        animasiFadeIn(textViewB)
        animasiFadeIn(textViewC)
        animasiFadeIn(textViewD)

        fun IntRange.random() = Random().nextInt((endInclusive) -
                start) + start

        answer = (questionRange).random()
        answerTextView.text = answer.toString()

        remain = 0

        val valueList = ArrayList<Int>()

        val option1 = (1..answer).random()
        val option2 = answer - option1

        var incorrect1 = (1..answer).random()
        while((incorrect1 == option1) || (incorrect1 == option2)) {
            incorrect1 = (1..answer).random()
        }

        var incorrect2 = (1..answer).random()
        while((incorrect2 == option1) || (incorrect2 == option2) || ((incorrect1 + incorrect2) == answer)) {
            incorrect2 = (1..answer).random()
        }

        valueList.add(option1)
        valueList.add(option2)
        valueList.add(incorrect1)
        valueList.add(incorrect2)

        var currentValue = valueList.removeAt((0..valueList.size).random())
        textViewA.text = "A. " + currentValue.toString()
        optionA = currentValue
        if((currentValue == option1) || (currentValue == option2)){
            jawabanOptionA = true
        }
        else {
            jawabanOptionA = false
        }

        currentValue = valueList.removeAt((0..valueList.size).random())
        textViewB.text = "B. " + currentValue.toString()
        optionB = currentValue
        if((currentValue == option1) || (currentValue == option2)){
            jawabanOptionB = true
        }
        else {
            jawabanOptionB = false
        }

        currentValue = valueList.removeAt((0..valueList.size).random())
        textViewC.text = "C. " + currentValue.toString()
        optionC = currentValue
        if((currentValue == option1) || (currentValue == option2)){
            jawabanOptionC = true
        }
        else {
            jawabanOptionC = false
        }

        currentValue = valueList.removeAt((0..valueList.size).random())
        textViewD.text = "D. " + currentValue.toString()
        optionD = currentValue
        if((currentValue == option1) || (currentValue == option2)){
            jawabanOptionD = true
        }
        else {
            jawabanOptionD = false
        }
    }

    fun buttonPressed(v : View){

        when(v.id){
            R.id.imageButtonA -> {
                if( optionA == 0 ){
                    return
                }else{
                    imageButtonA.setImageResource(R.drawable.a_disable)
                    remain += optionA
                    optionA = 0
                }
            }
            R.id.imageButtonB -> {
                if( optionB == 0 ){
                    return
                }else {
                    imageButtonB.setImageResource(R.drawable.b_disable)
                    remain += optionB
                    optionB = 0
                }
            }
            R.id.imageButtonC -> {
                if( optionC == 0 ){
                    return
                }else {
                    imageButtonC.setImageResource(R.drawable.c_disable)
                    remain += optionC
                    optionC = 0
                }
            }
            R.id.imageButtonD -> {
                if( optionD == 0 ){
                    return
                }else {
                    imageButtonD.setImageResource(R.drawable.d_disable)
                    remain += optionD
                    optionD = 0
                }
            }
        }
        if(isFirstButtonPressed){
            if (remain == answer){
                resultImageView.setImageResource(R.drawable.correct)
            }
            else{
                resultImageView.setImageResource(R.drawable.incorrect)
            }
            animasiFadeIn(resultImageView)
            showTheAnswer()


            Handler().postDelayed({
                animasiFadeOut(resultImageView)
                animasiFadeOut(answerTextView)
                animasiFadeOut(textViewA)
                animasiFadeOut(textViewB)
                animasiFadeOut(textViewC)
                animasiFadeOut(textViewD)
                Handler().postDelayed({
                    generateQuestion()
                },500)
            },1000)
        }
        else{
            isFirstButtonPressed = true
        }
    }

    fun setButtonEnable(){
            imageButtonA.setImageResource((R.drawable.a_biasa))
            imageButtonB.setImageResource((R.drawable.b_biasa))
            imageButtonC.setImageResource((R.drawable.c_biasa))
            imageButtonD.setImageResource((R.drawable.d_biasa))
    }

    fun showTheAnswer(){
        if(jawabanOptionA){
            imageButtonA.setImageResource((R.drawable.a_correct))
        }else{
            imageButtonA.setImageResource((R.drawable.a_incorrect))

        }
        if(jawabanOptionB){
            imageButtonB.setImageResource((R.drawable.b_correct))
        }else{
            imageButtonB.setImageResource((R.drawable.b_incorrect))

        }
        if(jawabanOptionC){
            imageButtonC.setImageResource((R.drawable.c_correct))
        }else{
            imageButtonC.setImageResource((R.drawable.c_incorrect))

        }
        if(jawabanOptionD){
            imageButtonD.setImageResource((R.drawable.d_correct))
        }else{
            imageButtonD.setImageResource((R.drawable.d_incorrect))

        }
    }

    fun animasiFadeIn(v: View){
        val resultFadeIn = ObjectAnimator.ofFloat(v, "alpha", 1.0f).apply()
        {
            duration = 250
        }
        val resultZoomInX = ObjectAnimator.ofFloat(v, "scaleX", 1.0f).apply()
        {
            duration = 250
        }
        val resultZoomInY = ObjectAnimator.ofFloat(v, "scaleY", 1.0f).apply()
        {
            duration = 250
        }
        var animFadeIn = AnimatorSet().apply {
            play(resultFadeIn).with(resultZoomInX).with(resultZoomInY)
        }
        animFadeIn.start()
    }

    fun animasiFadeOut(v: View){
        val resultFadeOut = ObjectAnimator.ofFloat(v, "alpha", 0.0f).apply()
        {
            duration = 500
        }
        val resultZoomOutX = ObjectAnimator.ofFloat(v, "scaleX", 0.75f).apply()
        {
            duration = 500
        }
        val resultZoomOutY = ObjectAnimator.ofFloat(v, "scaleY", 0.75f).apply()
        {
            duration = 500
        }
        var animFadeOut = AnimatorSet().apply {
            play(resultFadeOut).with(resultZoomOutX).with(resultZoomOutY)
        }
        animFadeOut.start()
    }
}