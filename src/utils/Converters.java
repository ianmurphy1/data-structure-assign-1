package utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Static class that be used to convert from one math notation
 * type to another notation.
 *
 * @author Ian Murphy - 20057028
 */
public class Converters {

    // Which side of the operator gets evaluated first
    private static int LEFTASSOC = 0;
    private static int RIGHTASSOC = 1; //Not used as not implementing square roots yet

    /**
     * Map to allow for easier addition of operators in future.
     * Maps an operator to a primitive array of ints, the first value is
     * a measure of its precedence. The second holds the asscociativity.
     */
    private static final Map<String, int[]> OPERATORS = new HashMap<String, int[]>();
    static {
        OPERATORS.put("+", new int[] { 0, LEFTASSOC });
        OPERATORS.put("-", new int[] { 0, LEFTASSOC });
        OPERATORS.put("*", new int[] { 5, LEFTASSOC });
        OPERATORS.put("/", new int[] { 5, LEFTASSOC });
    }

    /**
     * Check whether a token is an operator or not.
     *
     * @param token  The token being checked
     * @return true if the token is an operator
     */
    private static boolean isOperator(String token) {
        return OPERATORS.containsKey(token);
    }

    /**
     * Test associativity of an operator token.
     *
     * @param token The token being checked
     * @param type LEFTASSOC or RIGHTASSOC
     * @return True if the token type is the same as param type
     * @throws IllegalArgumentException
     */
    private static boolean isAssoc(String token, int type) throws IllegalArgumentException{
        if (!isOperator(token)) throw new IllegalArgumentException("Not a valid token" + token);
        if (OPERATORS.get(token)[1] == type) return true;
        return false;
    }

    /**
     * Method that compares the precedence of two operators
     *
     * @param token1 First operator being checked
     * @param token2 Second operator being checked
     * @return Neg if token1 has smaller precedence, positive if token1 has greater precedence
     * or 0 they are both equal
     * @throws IllegalArgumentException
     */
    private static int checkPrecedence(String token1, String token2) throws IllegalArgumentException {
        if (!isOperator(token1) || !isOperator(token2))
            throw new IllegalArgumentException("Invalid Tokens: " + token1 + " and " + token2);
        return OPERATORS.get(token1)[0] - OPERATORS.get(token2)[0];
    }

    /**
     * A method that converts an inputted string from infix notation to
     * postfix notation (RPN)
     * @param s The string to be converted
     * @return A String of the postfix equivalent of the inputted expression
     */
    public static String infixToPostfix(String s) {
        MyStack<String> outStack = new MyStack<String>();
        MyStack<String> opStack = new MyStack<String>();

        String[] tokens = s.split(" ");
        for (String token: tokens) {
            if (isOperator(token)) {
                // If token is an operator enter here
                while (!opStack.isEmpty() && isOperator(opStack.peek())) {
                    if (isAssoc(token, LEFTASSOC) && checkPrecedence(token, opStack.peek()) <= 0
                        || isAssoc(token, RIGHTASSOC) && checkPrecedence(token, opStack.peek()) < 0) {
                        outStack.push(opStack.pop());
                        continue;
                    }
                    break;
                }
                // Push new operator onto the stack
                opStack.push(token);
            } else if (token.equals("(")) opStack.push(token);
            else if (token.equals(")")) {
                while (!opStack.isEmpty() && !opStack.peek().equals("("))
                    outStack.push(opStack.pop());
                opStack.pop();
            } else outStack.push(token);
        }
        // Push whatever is left in opstack onto output stack
        while (!opStack.isEmpty()) {
            outStack.push(opStack.pop());
        }

        Iterator<String> it = outStack.iterator();
        StringBuilder sb = new StringBuilder();
        System.out.println(outStack.toString());
        for (String str : outStack) {
            sb.append(outStack.pop() + " ");
        }

        // Reverse the string before returning so that it prints the top of the Stack
        // last and it is in correct RPN format.
        return sb.reverse().toString();
    }

    public static String postfixToInfix(String s) {
        //TODO
        return null;
    }

    public static String infixToPrefix(String s) {
        //TODO
        return null;
    }

    public static String prefixToInfix(String s) {
        //TODO
        return null;
    }

    public static String postfixToPrefix(String s) {
        //TODO
        return null;
    }

    public static String prefixToPostfis(String s) {
        //TODO
        return null;
    }

    public static void main(String[] args) {
        String input = "( 1 + 2 ) * ( 3 / 4 )";
        String output = infixToPostfix(input);

        System.out.println(output);
    }


}
