package calc;

import utils.Calculators;
import utils.Converters;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Arc2D;
import java.text.DecimalFormat;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.border.*;

/**
 * A graphical user interface for the calculator. No calculation is being
 * done here. This class is responsible just for putting up the display on
 * screen. It then refers to the "CalcEngine" to do all the real work.
 *
 * @author Michael Kolling
 * @version 31 July 2000
 */
public class UserInterface implements ActionListener
{
    private CalcEngine calc;
    private boolean showingAuthor;
    private boolean negated;
    private JFrame frame;
    private JTextField display;
    private JLabel status;
    private int pointPressed;

    /**
     * Create a user interface for a given calcEngine.
     */
    public UserInterface(CalcEngine engine)
    {
        pointPressed = 0;
        calc = engine;
        showingAuthor = true;
        makeFrame();
        frame.setVisible(true);
        negated = false;
    }

    /**
     * Make this interface visible again. (Has no effect if it is already
     * visible.)
     */
    public void setVisible(boolean visible)
    {
        frame.setVisible(visible);
    }

    /**
     * Make the frame for the user interface.
     */
    private void makeFrame()
    {
        frame = new JFrame(calc.getTitle());

        JPanel contentPane = (JPanel)frame.getContentPane();
        contentPane.setLayout(new BorderLayout(8, 8));
        contentPane.setBorder(new EmptyBorder( 10, 10, 10, 10));

        display = new JTextField("0");
        contentPane.add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 4));
        addButton(buttonPanel, "C");
        addButton(buttonPanel, "(");
        addButton(buttonPanel, ")");
        addButton(buttonPanel, "/");
        addButton(buttonPanel, "7");
        addButton(buttonPanel, "8");
        addButton(buttonPanel, "9");
        addButton(buttonPanel, "*");
        addButton(buttonPanel, "4");
        addButton(buttonPanel, "5");
        addButton(buttonPanel, "6");
        addButton(buttonPanel, "+");
        addButton(buttonPanel, "1");
        addButton(buttonPanel, "2");
        addButton(buttonPanel, "3");
        addButton(buttonPanel, "-");
        addButton(buttonPanel, "0");
        addButton(buttonPanel, " ");
        addButton(buttonPanel, ".");
        addButton(buttonPanel, "=");
        contentPane.add(buttonPanel, BorderLayout.CENTER);

        status = new JLabel(calc.getAuthor());
        contentPane.add(status, BorderLayout.SOUTH);

        frame.pack();
    }

    /**
     * Add a button to the button panel.
     */
    private void addButton(Container panel, String buttonText)
    {
        JButton button = new JButton(buttonText);
        button.addActionListener(this);
        panel.add(button);
    }

    /**
     * An interface action has been performed. Find out what it was and
     * handle it.
     */
    public void actionPerformed(ActionEvent event)
    {
        String command = event.getActionCommand();
        if(command.equals("0") ||
                command.equals("1") ||
                command.equals("2") ||
                command.equals("3") ||
                command.equals("4") ||
                command.equals("5") ||
                command.equals("6") ||
                command.equals("7") ||
                command.equals("8") ||
                command.equals("9"))
        {
            int number = Integer.parseInt(command);
            calc.numberPressed(number);
            negated = (negated == true) ? false : true;
        } else if (command.equals(".")) {
            calc.pointPressed();
            pointPressed += 1;
        }
        else if(command.equals("+")) {
            calc.plus();
            pointPressed = 0;
        }
        else if(command.equals("-")) {
            String str = calc.getDisplayValue(pointPressed);
            char a;
            char b = '+';
            a = str.charAt(str.length() - 1);
            if (str.length() > 1) {
                b = str.charAt(str.length() - 2);
                System.out.println(b);
            }

            /**
             * Check is the last operator exists, needs to check if a previous
             * operator exists before it can negate. Logical OR will be true for the
             * first part of the string if the display is 0.
             */

            if (str.charAt(0) == '0' || (a == ' ' && b == '-' || b == '(') ){
                System.out.println("In negate. A: " + a + " B: " + b);
                calc.negate();
                negated = true;
            } else if (negated == true) { // If a negation sign is awaiting a number do nothing
                ;
            } else {
                System.out.println("In minus. A: " + a + " B: " + b);
                pointPressed = 0;
                calc.minus();
            }
        }
        else if(command.equals("=")) {
            try {
                calc.equals();
            } catch (ArithmeticException e) {
                display.setText(calc.getDisplayValue(pointPressed));
            }
            pointPressed = 0;
        }
        else if(command.equals("C")) {
            calc.clear();
            pointPressed = 0;
            System.out.println("String Cleared: ");
        }
        else if(command.equals("*")) {
            calc.multiply();
            pointPressed = 0;
        }
        else if (command.equals("/")) {
            calc.divide();
            pointPressed = 0;
        } else if (command.equals("(")) {
            calc.leftParen();
        } else if (command.equals(")") ) {
            calc.rightParen();
        }
        redisplay();
    }

    /**
     * Update the interface display to show the current value of the
     * calculator.
     */
    private void redisplay() {
        display.setText(calc.getDisplayValue(pointPressed));
    }

    private static boolean isNumber(String s) {
        if (Pattern.matches(fpRegex, s)) return true;
        return false;
    }

    /**
     * Toggle the info display in the calculator's status area between the
     * author and version information.
     */
    private void showInfo()
    {
        if(showingAuthor)
            status.setText(calc.getVersion());
        else
            status.setText(calc.getAuthor());

        showingAuthor = !showingAuthor;
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
