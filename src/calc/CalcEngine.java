package calc;

import utils.Calculators;
import utils.Converters;
import utils.MyStack;

import java.text.DecimalFormat;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 * The main part of the calculator doing the calculations.
 *
 * @author  M. Kolling 
 * @version 0.1 (incomplete)
 */
public class CalcEngine
{

    String displayValue;
    boolean solved;

    /**
     * Create a CalcEngine instance. Initialise its state so that it is ready 
     * for use.
     */
    public CalcEngine()
    {
        displayValue = "0";
        solved = false;
    }

    /**
     * Return the value that should currently be displayed on the calculator
     * display.
     */
    public String getDisplayValue(int pointPressed) {
        if (isNumber(displayValue)) {
            double d = Double.parseDouble(displayValue);            
            int i = (int) d;
            DecimalFormat whole = new DecimalFormat("#");
            if (Double.compare(d, i) == 0 && pointPressed == 0)
                displayValue = whole.format(d);
        }
        return(displayValue);
    }

    /**
     * A number button was pressed. Do whatever you have to do to handle it.
     * The number value of the button is given as a parameter.
     */
    public void numberPressed(int number) {
        if (displayValue.charAt(0) == '0') displayValue = "";
        if (solved == true) {
            displayValue = "" + number;
            solved = false;
            return;
        }
        displayValue = displayValue + number;
    }

    /**
     * Point pressed, added it to the string responsible for the displaying of text.
     */
    public void pointPressed() {
        displayValue = displayValue + ".";
        if (solved == true) solved = false;       
    }

    /**
     * The 'plus' button was pressed. 
     */
    public void plus() {
        checkMultipleOps();
        displayValue += " + ";
        if (solved == true) solved = false;
    }
    
    /**
     * Check for multiple operators. 
     */
    private void checkMultipleOps() {
        if (displayValue.length() < 3) return;
        char c = displayValue.charAt(displayValue.length() - 2);
        System.out.println("Multiple op check before: " + displayValue + "|");
        if (c == '+' || c == '-' || c == '*' || c == '/') {
            displayValue = displayValue.substring(0, displayValue.length() - 3);            
        }
    }

    /**
     * The 'minus' button was pressed.
     */
    public void minus() {
        checkMultipleOps();
        displayValue += " - ";
        if (solved == true) solved = false;
    }

    /**
     * The multiply button was pressed
     */
    public void multiply(){
        checkMultipleOps();
        displayValue += " * ";
        if (solved == true) solved = false;
    }

    /**
     * The division button was pressed
     */
    public void divide() {
        checkMultipleOps();
        displayValue += " / ";
        if (solved == true) solved = false;
    }

    /**
     * The '=' button was pressed.
     */
    public void equals() {
        if (displayValue.isEmpty()) {
            displayValue = "0";
            return;
        } else if (!displayValue.contains(" ")) {
            return;
        }
        MyStack<String> rpn = Converters.infixToPostfix(displayValue);
        displayValue = Calculators.calculate(rpn) + "";
        if (Double.parseDouble(displayValue) == Double.NEGATIVE_INFINITY
            || Double.parseDouble(displayValue) == Double.POSITIVE_INFINITY) {
            displayValue = "MATH ERROR";
            throw new ArithmeticException();
        }
        solved = true;
    }

    public void rightParen() {
	    if (displayValue.charAt(0) == '0'); //do nothing
	    else displayValue += " )";
	}

	public void negate() {
	    if (displayValue.charAt(0) == '0') displayValue = "-";
	    else displayValue += "-";
	}

	public void leftParen() {
	    if (displayValue.charAt(0) == '0') displayValue = "( ";
	    else displayValue += "( ";
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
        return("Stack Calculator");
    }

    /**
     * Return the author of this engine. This string is displayed as it is,
     * so it should say something like "Written by H. Simpson".
     */
    public String getAuthor()
    {
        return("Ian Murphy");
    }

    /**
     * Return the version number of this engine. This string is displayed as 
     * it is, so it should say something like "Version 1.1".
     */
    public String getVersion()
    {
        return("Ver. 1.0");
    }

    private static boolean isNumber(String s) {
        if (Pattern.matches(fpRegex, s)) return true;
        return false;
    }

    /**
     * Regular expressions to check wheter a number is valid or not.
     * These have been taken from the @see {@link Double#valueOf(String s)} method
     * where these are specified.
     */
    final static String Digits     = "(\\p{Digit}+)";
    final static String HexDigits  = "(\\p{XDigit}+)";
    // an exponent is 'e' or 'E' followed by an optionally
    // signed decimal integer.
    final static String Exp        = "[eE][+-]?"+Digits;
    final static String fpRegex    =
            ( "[\\x00-\\x20]*" +  // Optional leading "whitespace"
                    "[+-]?(" + // Optional sign character
                    "NaN|" +           // "NaN" string
                    "Infinity|" +      // "Infinity" string

                    // A decimal floating-point string representing a finite positive
                    // number without a leading sign has at most five basic pieces:
                    // Digits . Digits ExponentPart FloatTypeSuffix
                    //
                    // Since this method allows integer-only strings as input
                    // in addition to strings of floating-point literals, the
                    // two sub-patterns below are simplifications of the grammar
                    // productions from the Java Language Specification, 2nd
                    // edition, section 3.10.2.

                    // Digits ._opt Digits_opt ExponentPart_opt FloatTypeSuffix_opt
                    "(((" +Digits+ "(\\.)?(" + Digits + "?)(" + Exp + ")?)|" +

                    // . Digits ExponentPart_opt FloatTypeSuffix_opt
                    "(\\.(" + Digits + ")(" + Exp + ")?)|" +

                    // Hexadecimal strings
                    "((" +
                    // 0[xX] HexDigits ._opt BinaryExponent FloatTypeSuffix_opt
                    "(0[xX]" + HexDigits + "(\\.)?)|" +

                    // 0[xX] HexDigits_opt . HexDigits BinaryExponent FloatTypeSuffix_opt
                    "(0[xX]" + HexDigits + "?(\\.)" + HexDigits + ")" +

                    ")[pP][+-]?" + Digits + "))" +
                    "[fFdD]?))" +
                    "[\\x00-\\x20]*");// Optional trailing "whitespace"
}