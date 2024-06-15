package com.example.calculator

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import org.mozilla.javascript.Context
import org.mozilla.javascript.Scriptable

class CalculatorViewModel:ViewModel(){
    private val _equation = mutableStateOf("")
    private val _result = mutableStateOf("")
    val equation = _equation
    val result = _result
    val buttonList = listOf(
        "C", "A", ".", "/",
        "7", "8", "9", "*",
        "4", "5", "6", "+",
        "1", "2", "3", "-",
        "0", "="
    )

    fun click(symbol:String){
        when(symbol){
            "C"->{
                _equation.value = _equation.value.dropLast(1)
            }
            "A"->{
                _equation.value = ""
            }
            "="->{
                _result.value = calculateResult(_equation.value)
                _equation.value = ""
            }
            else->{
                _equation.value = _equation.value + symbol
            }
        }
    }
    private fun calculateResult(equation : String) : String{
        val context : Context = Context.enter()
        context.optimizationLevel = -1
        val scriptable : Scriptable = context.initStandardObjects()
        var finalResult = try {
            context.evaluateString(
                scriptable,
                equation,
                "Javascript",
                1,
                null
            ).toString()

        }catch (e:Exception){
            "Invalid Input"
        }
        if(finalResult.endsWith(".0")){
            finalResult = finalResult.replace(".0","")
        }
        if(finalResult.startsWith("org")){
            return "Error Occurred"
        }
        return finalResult
    }
}
