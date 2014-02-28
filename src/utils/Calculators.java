package utils;

import java.util.regex.Pattern;

/**
 * @author Ian Murphy - 20057028
 *         Date: 27/02/14
 */
public class Calculators {






    // Array of operators, more to be added
    public static final String[] OPERATORS = { "+", "-", "*", "/"};

    /**
     * This method takes in a stack of strings and calculates the correct result
     * based on RPN.
     *
     * @param stack The Stack to be evaluated
     * @return The final result
     */
    public static Double calculate(MyStack<String> stack) {
        MyStack<String> inStack = stack;
        MyStack<String> opStack = new MyStack<String>();
        MyStack<Double> outStack = new MyStack<Double>();

        while (true) {
            if (inStack.isEmpty()) break;
            while (isOp(inStack.peek())) {
                opStack.push(inStack.pop());
            }

            String test = inStack.pop();
            if (isNumber(test) && inStack.isEmpty()) {
                outStack.push(eval(opStack.pop(), Double.parseDouble(test), outStack.pop()));
                break;
            } else if (isNumber(test) && isOp(inStack.peek())) {
                outStack.push(Double.parseDouble(test));
                continue;  //Jump back to start of loop
            }
            inStack.push(test);
            outStack.push(eval(opStack.pop(), Double.parseDouble(inStack.pop()), Double.parseDouble(inStack.pop())));
        }
        while (!opStack.isEmpty()) {
            // Order of operands has flipped once reaching here
            double op1, op2;
            op2 = outStack.pop();
            op1 = outStack.pop();
            outStack.push(eval(opStack.pop(), op1, op2));
        }
        return outStack.pop();
    }

    /**
     * Method to check whether a string is a number or not
     * @param s String to be checked
     * @return true it's a number, false otherwise
     */
     //private static boolean isNumber(String s) { return s.matches("((-|\\\\+)?[0-9]+(\\\\.[0-9]+)?)+"); }
     private static boolean isNumber(String s) {
        if (Pattern.matches(fpRegex, s)) return true;
        return false;
    }

    /**
     * Method that checks whether a string token passed into it is an operator
     * or not.
     *
     * @param operator The operator to check
     * @return true if it is an operator, false otherwise
     */
    private static boolean isOp(String operator) {
        for (String str: OPERATORS) if (str.equals(operator)) return true;
        return false;
    }

    /**
     * Method that takes in an operator and 2 values from the stack and carries out the
     * operation and returns its result.
     *
     * @param operator The operation to be carried out
     * @param val1 The first value from the top of the stack
     * @param val2 The second value from the stack,
     *             this goes to the left of the operator in infix
     * @return The result from the operation
     */
    private static Double eval(String operator, Double val1, Double val2) {
        if (operator.equals("+")) return val2 + val1;
        else if (operator.equals("-")) return val2 - val1;
        else if (operator.equals("*")) return val2 * val1;
        else if (operator.equals("/")) return val2 / val1;
        else throw new IllegalArgumentException("Not a Valid Operator");
    }

    /**
     * Regular expressions to check wheter a number is valid or not.
     * These have been taken from the @see {@link java.lang.Double#valueOf(String s)} method
     * where these are specified.
     */
    final static String Digits     = "(\\p{Digit}+)";
    final static String HexDigits  = "(\\p{XDigit}+)";
    // an exponent is 'e' or 'E' followed by an optionally
    // signed decimal integer.
    final static String Exp        = "[eE][+-]?"+Digits;
    final static String fpRegex    =
            ( " [\\x00-\\x20]*" +  // Optional leading "whitespace"
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