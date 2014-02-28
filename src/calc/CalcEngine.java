package calc;

import utils.Calculators;
import utils.Converters;
import utils.MyStack;

import java.util.Stack;

/**
 * The main part of the calculator doing the calculations.
 *
 * @author  M. Kolling 
 * @version 0.1 (incomplete)
 */
public class CalcEngine
{
    char operator;
    String displayValue, operand1;

    /**
     * Create a CalcEngine instance. Initialise its state so that it is ready 
     * for use.
     */
    public CalcEngine()
    {
        displayValue = "0";
        operand1 = "0";
    }

    /**
     * Return the value that should currently be displayed on the calculator
     * display.
     */
    public String getDisplayValue() {
        System.out.println("Display val in engine: " + displayValue);
        return(displayValue);
    }

    /**
     * A number button was pressed. Do whatever you have to do to handle it.
     * The number value of the button is given as a parameter.
     */
    public void numberPressed(int number) {
        if (displayValue.charAt(0) == '0') displayValue = "";
        displayValue = displayValue + number;
    }

    /**
     * Point pressed, added it to the string responsible for the displaying of text.
     */
    public void pointPressed() {
        displayValue = displayValue + ".";
        //System.out.println(displayValue);
    }

    /**
     * The 'plus' button was pressed. 
     */
    public void plus()
    {
        operand1 = displayValue;
        displayValue = "0";
    }

    /**
     * The 'minus' button was pressed.
     */
    public void minus()
    {
        operand1 = displayValue;
        displayValue = "0";
    }

    public void multiply()
    {
        operand1 = displayValue;
        displayValue = "0";
    }

    public void divide()
    {
        operand1 = displayValue;
        displayValue = "0";
    }

    /**
     * The '=' button was pressed.
     */
    public void equals(String infix)
    {
        if (infix.isEmpty()) {
            displayValue = "0";
            return;
        }
        MyStack<String> rpn = Converters.infixToPostfix(infix);
        displayValue = Calculators.calculate(rpn) + "";
        if (Double.parseDouble(displayValue) == Double.NEGATIVE_INFINITY
            || Double.parseDouble(displayValue) == Double.POSITIVE_INFINITY) {
            displayValue = "MATH ERROR";
            throw new ArithmeticException();
        }
    }

    /**
     * The 'C' (clear) button was pressed.
     */
    public void clear()
    {
        displayValue = "0";
    }


    /**
     * Return the title of this calculation engine.
     */
    public String getTitle()
    {
        return("My Calculator");
    }

    /**
     * Return the author of this engine. This string is displayed as it is,
     * so it should say something like "Written by H. Simpson".
     */
    public String getAuthor()
    {
        return("Joe Daly");
    }

    /**
     * Return the version number of this engine. This string is displayed as 
     * it is, so it should say something like "Version 1.1".
     */
    public String getVersion()
    {
        return("Ver. 1.0");
    }

}