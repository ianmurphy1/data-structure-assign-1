package calc;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Arc2D;
import java.text.DecimalFormat;
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
    private String str;
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
        str = "";
        showingAuthor = true;
        makeFrame();
        frame.setVisible(true);
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
        addButton(buttonPanel, "*");
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
            str += command;
            System.out.println(str);
        } else if (command.equals(".")) {
            calc.pointPressed();
            pointPressed += 1;
            if (pointPressed == 1) str += ".";

        }
        else if(command.equals("+")) {
            calc.plus();
            str += " " + "+" + " ";
            pointPressed = 0;
        }
        else if(command.equals("-")) {
            calc.minus();
            str += " " + "-" + " ";
            pointPressed = 0;
        }
        else if(command.equals("=")) {
            try {
                calc.equals(str);
            } catch (ArithmeticException e) {
                display.setText(calc.getDisplayValue());
            }
            str = "";
            pointPressed = 0;
        }
        else if(command.equals("C")) {
            calc.clear();
            str = "";
            pointPressed = 0;
            System.out.println("String Cleared: ");
        }
        else if(command.equals("*")) {
            calc.multiply();
            str += " " + "*" + " ";
            pointPressed = 0;
        }
        else if(command.equals("/")) {
            calc.divide();
            str += " " + "/" + " ";
            pointPressed = 0;
        }
        redisplay();
    }

    /**
     * Update the interface display to show the current value of the
     * calculator.
     */
    private void redisplay() {
        double d = Double.parseDouble(calc.getDisplayValue());
        System.out.println("Redisplay value: " + d);
        int i = (int) d;
        DecimalFormat whole = new DecimalFormat("#");
        if (Double.compare(d, i) == 0 && pointPressed == 0)
            display.setText("" + whole.format(d));
        else if (Double.compare(d, i) == 0 && pointPressed == 1) {
            System.out.println("Redisplay once point is pressed: " + calc.getDisplayValue());
            display.setText(calc.getDisplayValue());
        }
        else display.setText("" + calc.getDisplayValue());
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
}
