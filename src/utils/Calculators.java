package utils;

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

        while (!inStack.isEmpty()) {
            while (isOp(inStack.peek())) {
                opStack.push(inStack.pop());
            }
            outStack.push(eval(opStack.pop(), Double.parseDouble(inStack.pop()), Double.parseDouble(inStack.pop())));
        }
        while (!opStack.isEmpty()) outStack.push(eval(opStack.pop(), outStack.pop(), outStack.pop()));
        return outStack.pop();
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


}
