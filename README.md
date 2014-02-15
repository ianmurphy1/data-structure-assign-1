Electronic Calculator (Using Stacks) Due Date: 28th Feb. 2014
====



The task
---

The goal of this assignment is to build an electronic calculator. The calculator should allow mathematical expressions to be entered and displayed. When the user presses the ‘=’ button the calculator displays the calculated result. You will be given three classes that implements a simple calculator, and your job is to expand it to cater for more complex calculations.


##The project


Please find Attached project calculator. Open the project and have a look. You will see three classes: Calculator, UserInterface and CalcEngine. The calculator works for simple calculations where the display area only shows numbers.

###Calculator


This is the main class. It creates a calculation engine (CalcEngine) and a user interface and starts off the application. That's really all it does. You do not have to modify this class.
###UserInterface
This class provides the user interface of the calculator: the window on screen, the buttons, etc. Modify this class so that the calculator has 5 rows, each of which has 4 buttons:

>|  |  |  |   |
|:-------------:|:-------------:|:-------------:|:-------------:|
| C | ( | ) | / |
| 7 | 8 | 9 | * |
| 4 | 5 | 6 | + |
| 1 | 2 | 3 | - |
| 0 |   | . | = |

###CalcEngine
This is where all the real work is done. The calculation engine is responsible for implementing the logic of the calculator. It gets informed of button pushes and does the calculations.
You will need to utilise a Stack of some type. This will allow you to store the data in a postfix format suitable for the correction interpretation of the formula. You can implement the stack yourselves or can they use an existing implementation (i.e. Java Collections API). Extra marks for doing both versions.
###The goal 
The goal is to produce a fully working calculator with addition, subtraction, multiplication and division functionality. The calculator has ‘(‘ and ‘)’ symbols to allow for more complex calculations. Here is an example session:
keys pressed/displayed calculated value 12+8= 20
12+8*3= 36
(12+8)*3= 60
(12+8)*3-4*(2+3)= 40
Extra Marks
Extra marks for implementing two implementations of the Stack.
###Submission 
*This is an individual assignment.* 

**All the work done as part of this assignment must be done by you personally.**