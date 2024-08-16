package com.example.calculadora

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import br.com.calculadora.myapplication.R

class MainActivity : AppCompatActivity() {

    private lateinit var display: EditText
    private var operand1: Double? = null
    private var operator: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.display)

        val buttons = listOf(
            R.id.button0, R.id.button1, R.id.button2, R.id.button3,
            R.id.button4, R.id.button5, R.id.button6, R.id.button7,
            R.id.button8, R.id.button9, R.id.buttonAdd, R.id.buttonSubtract,
            R.id.buttonMultiply, R.id.buttonDivide, R.id.buttonClear, R.id.buttonEqual
        )

        buttons.forEach { buttonId ->
            val button = findViewById<Button>(buttonId)
            button.setOnClickListener { onButtonClick(button.text.toString()) }
        }
    }

    private fun onButtonClick(value: String) {
        when {
            value in "0123456789" -> handleNumber(value)
            value in "+-*/" -> handleOperator(value)
            value == "=" -> calculateResult()
            value == "C" -> clear()
        }
    }

    private fun handleNumber(number: String) {
        if (display.text.isEmpty()) {
            display.setText(number)
        } else {
            display.append(number)
        }
    }

    private fun handleOperator(op: String) {
        operand1 = display.text.toString().toDoubleOrNull()
        operator = op
        display.text.clear()
    }

    private fun calculateResult() {
        val operand2 = display.text.toString().toDoubleOrNull()
        val op = operator

        if (operand1 != null && operand2 != null && op != null) {
            val result = when (op) {
                "+" -> add(operand1!!, operand2)
                "-" -> subtract(operand1!!, operand2)
                "*" -> multiply(operand1!!, operand2)
                "/" -> divide(operand1!!, operand2)
                else -> 0.0
            }
            display.setText(result.toString())
            operand1 = null
            operator = null
        } else {
            display.setText("Erro")
        }
    }

    private fun clear() {
        display.text.clear()
        operand1 = null
        operator = null
    }

    private fun add(a: Double, b: Double): Double = a + b
    private fun subtract(a: Double, b: Double): Double = a - b
    private fun multiply(a: Double, b: Double): Double = a * b
    private fun divide(a: Double, b: Double): Double {
        return if (b != 0.0) a / b else throw ArithmeticException("Divisão por zero")
    }
}
