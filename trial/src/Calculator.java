import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame implements ActionListener {
    JTextArea display;
    String currentOperator;
    StringBuilder inputEntries;
    double result;
    boolean isOperatorPressed;
    boolean isDecimalAdded;

    public Calculator() {
        setTitle("My Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 500);
        setLayout(new BorderLayout());

        // Create display
        display = new JTextArea(3, 20);
        display.setFont(new Font("Roboto", Font.BOLD, 20));
        display.setEditable(false);
        display.setLineWrap(true);
        display.setWrapStyleWord(true);
        display.setBackground(Color.gray);
        display.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(display, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.NORTH);

        // Create button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 0, 0));
        String[] buttons = {
                "7", "8", "9", "<<",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                ".", "0", "+", "/",
                "C", "±", " ","="
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Roboto", Font.PLAIN, 18));
            button.addActionListener(this);
            buttonPanel.add(button);

        }

        add(buttonPanel, BorderLayout.CENTER);

        setVisible(true);
        isOperatorPressed = false;
        isDecimalAdded = false;
        inputEntries = new StringBuilder();
        currentOperator = "";
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        // Handle numeric input
        double operand2;
        double operand1;
        if (command.matches("\\d")) {
            if (isOperatorPressed) {
                display.append("\n");
                isOperatorPressed = false;
                isDecimalAdded = false; // Reset decimal flag for new number
            }
            inputEntries.append(command);
            display.setText(inputEntries.toString());
        }
        // Handle decimal input
        else if (command.equals(".")) {
            if (!isDecimalAdded) {
                if (inputEntries.isEmpty() || isOperatorPressed) {
                    inputEntries.append("0");
                }
                inputEntries.append(".");
                isDecimalAdded = true;
                display.setText(inputEntries.toString());
            }
        } else if (command.equals("±")) {
            if (!inputEntries.isEmpty()) {
                if (inputEntries.charAt(0) == '-') {
                    inputEntries.deleteCharAt(0); // Remove negative sign
                } else {
                    inputEntries.insert(0, '-'); // Add negative sign
                }
                display.setText(inputEntries.toString());
            }
        }
        // Handle operator input
        else if (command.matches("[+\\-*/]")) {
            operand1 = Double.parseDouble(inputEntries.toString());
            currentOperator = command;
            inputEntries.append(" ").append(command).append(" ");
            display.setText(inputEntries.toString());
            isOperatorPressed = true;
            isDecimalAdded = false;
        }
        // Handle "=" for calculations
        else if (command.equals("=")) {
            String[] parts = inputEntries.toString().split(" ");
            if (parts.length == 3) {
                operand1 = Double.parseDouble(parts[0]);
                currentOperator = parts[1];
                operand2 = Double.parseDouble(parts[2]);

                switch (currentOperator) {
                    case "+" -> result = operand1 + operand2;
                    case "-" -> result = operand1 - operand2;
                    case "*" -> result = operand1 * operand2;
                    case "/" -> result = operand1 / operand2;
                }
                display.append("\n= " + result);
                inputEntries.setLength(0);
                inputEntries.append(result);
            }
        }
        // Handle "C" to clear
        else if (command.equals("C")) {
            display.setText("");
            inputEntries.setLength(0);
            operand1 = operand2 = result = 0;
            currentOperator = "";
            isDecimalAdded = false;
        } else if (command.equals("<<")) {
            if (!inputEntries.isEmpty()) {
                char removedChar = inputEntries.charAt(inputEntries.length() - 1);
                inputEntries.deleteCharAt(inputEntries.length() - 1);
                if (removedChar == '.') {
                    isDecimalAdded = false; // Reset decimal flag if dot is removed
                }
                display.setText(inputEntries.toString());
            }
        }
    }
            public static void main (String[]args){
                SwingUtilities.invokeLater(Calculator::new);
            }
        }
