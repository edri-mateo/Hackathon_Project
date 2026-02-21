package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.io.IOException;

import org.example.MODEL.Calendar;
import org.example.MODEL.Event.Event;

// UI Class -----------------------------------------------------------------------------------------------------------
public class UI {
    private JPanel panel1;
    private JPanel topPanel;
    private JLabel titlePane;
    private JTextArea errorBox;
    private JPanel panelLabel;

    private JButton addEventButton;
    private JTextArea assesmentText;

    private JButton testButton;
    private JButton assignmentButton;
    private JButton examButton;
    private JButton quizButton;
    private JButton labButton;
    private JButton otherButton;

    private JTextField dayInput;
    private JTextField monthInput;
    private JTextField yearInput;
    private JTextField weightInput;
    private JTextField nameInput;
    private JTextField descriptionInput;
    private JLabel labelDate;
    private JLabel labelMonth;
    private JLabel labelYear;
    private JLabel labelWeight;
    private JLabel labelDescription;
    private JLabel labelName;
    private JButton enterButton;

    private JTextArea resultingFileTextArea;
    private JPanel rightPanel;
    private JPanel eventListPanel;
    private JButton generateButton;

    // event details
    private int eventType = -1;
    private String day, month, year, weight, name, description;

    // event display
    private Calendar calendar = new Calendar();

    // UI Constructor -------------------------------------------------------------------------------------------------
    public UI() {
        JFrame frame = new JFrame("UI");
        frame.setContentPane(this.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.pack();
        frame.setVisible(true);

        // set all event detail buttons and header to hidden at first
        assesmentText.setVisible(false);
        testButton.setVisible(false);
        quizButton.setVisible(false);
        labButton.setVisible(false);
        examButton.setVisible(false);
        assignmentButton.setVisible(false);
        otherButton.setVisible(false);
        dayInput.setVisible(false);
        monthInput.setVisible(false);
        yearInput.setVisible(false);
        weightInput.setVisible(false);
        nameInput.setVisible(false);
        descriptionInput.setVisible(false);
        labelDate.setVisible(false);
        labelMonth.setVisible(false);
        labelYear.setVisible(false);
        labelWeight.setVisible(false);
        labelDescription.setVisible(false);
        labelName.setVisible(false);
        enterButton.setVisible(false);

        // editing UI
        titlePane.setFont(new Font("Segoe UI", Font.BOLD, 40));
        titlePane.setForeground(new Color(40, 60, 90));

        // make the labels stack on top of each other
        eventListPanel.setLayout(new BoxLayout(eventListPanel, BoxLayout.Y_AXIS));

        // initialize event types
        selectEventTypes();

        // displays the event types
        addEventButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showEventTypes();
            }
        });

        // sends raw data to backend from processing
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setDetails();
                boolean validInput = sanitizeInput();

                if (validInput) {
                    listEvents();
                }
            }
        });

        // generates a file to be saved
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File outputFile = calendar.generateCSV();
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Specify a file to save");
                fileChooser.setSelectedFile(new File("new_calendar.csv"));

                int userSelection = fileChooser.showSaveDialog(panel1);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File destinationFile = fileChooser.getSelectedFile();
                    try {
                        Files.copy(outputFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        JOptionPane.showMessageDialog(null, "File saved successfully!");
                    } catch (IOException ioe) {
                        JOptionPane.showMessageDialog(null, "Error saving file: " + ioe.getMessage());
                    }
                }
            }
        });
    }

    // sets the event types to visible --------------------------------------------------------------------------------
    private void showEventTypes() {
        assesmentText.setVisible(true);
        testButton.setVisible(true);
        quizButton.setVisible(true);
        labButton.setVisible(true);
        examButton.setVisible(true);
        assignmentButton.setVisible(true);
        otherButton.setVisible(true);
        dayInput.setVisible(true);
        monthInput.setVisible(true);
        yearInput.setVisible(true);
        weightInput.setVisible(true);
        nameInput.setVisible(true);
        descriptionInput.setVisible(true);
        labelDate.setVisible(true);
        labelMonth.setVisible(true);
        labelYear.setVisible(true);
        labelWeight.setVisible(true);
        labelDescription.setVisible(true);
        labelName.setVisible(true);
        enterButton.setVisible(true);
    }

    // set type of event ----------------------------------------------------------------------------------------------
    // -1 = invalid event
    // 0 = test
    // 1 = assignment
    // 2 = exam
    // 3 = quiz
    // 4 = lab
    // 5 = other
    private void selectEventTypes() {
        testButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setEventType(0, testButton);
            }
        });
        assignmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setEventType(1, assignmentButton);
            }
        });
        examButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setEventType(2, examButton);
            }
        });
        quizButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setEventType(3, quizButton);
            }
        });
        labButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setEventType(4, labButton);
            }
        });
        otherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setEventType(5, otherButton);
            }
        });
    }

    // display event type
    private void setEventType(int type, JButton button) {
        eventType = type;

        // reset colour of every button
        testButton.setForeground(new Color(55, 75, 157));
        assignmentButton.setForeground(new Color(55, 75, 157));
        examButton.setForeground(new Color(55, 75, 157));
        quizButton.setForeground(new Color(55, 75, 157));
        labButton.setForeground(new Color(55, 75, 157));
        otherButton.setForeground(new Color(55, 75, 157));

        // change the current button colour
        button.setForeground(Color.RED);
    }

    // handle detail input --------------------------------------------------------------------------------------------
    private void setDetails() {
        day = dayInput.getText();
        month = monthInput.getText();
        year = yearInput.getText();
        weight = weightInput.getText();
        name = nameInput.getText();
        description = descriptionInput.getText();
    }

    // methods for input sanitation -----------------------------------------------------------------------------------
    private boolean sanitizeInput() {
        boolean validInput = true;
        if (day == null || month == null || year == null || weight == null || name == null || eventType == -1) {
            JOptionPane.showMessageDialog(null, "Empty Field Detected");
            validInput = false;
        }
        else if (day.length() == 0 || month.length() == 0 || year.length() == 0 || weight.length() == 0 || name.length() == 0) {
            JOptionPane.showMessageDialog(null, "Empty Field Detected");
            validInput = false;
        }
        else if (hasLetters(day) || hasLetters(month) || hasLetters(year) || hasLetters(weight)) {
            JOptionPane.showMessageDialog(null, "Day, Month, Year, and Weight must only be digits");
            validInput = false;
        }
        else if (Integer.parseInt(day) < 1 || Integer.parseInt(month) < 1 || Integer.parseInt(year) < 1) {
            JOptionPane.showMessageDialog(null, "Day, Month, and Year cannot be less than 1");
            validInput = false;
        }
        else if (Integer.parseInt(month) > 12) {
            JOptionPane.showMessageDialog(null, "Month cannot be greater than 12");
            validInput = false;
        }
        else if (Integer.parseInt(year) > 2200 || Integer.parseInt(year) < 1000) {
            JOptionPane.showMessageDialog(null, "Invalid Year");
            validInput = false;
        }
        else if (Integer.parseInt(day) > 31) {
            JOptionPane.showMessageDialog(null, "Day cannot be bigger than 31");
            validInput = false;
        }
        else if ((Integer.parseInt(month) == 2 ||
                Integer.parseInt(month) == 4 ||
                Integer.parseInt(month) == 6 ||
                Integer.parseInt(month) == 9 ||
                Integer.parseInt(month) == 11) &&
            Integer.parseInt(day) > 30) {
            JOptionPane.showMessageDialog(null, "Day cannot be bigger than 30");
            validInput = false;
        }
        else if (Integer.parseInt(month) == 2 &&
            Integer.parseInt(day) > 28) {
            JOptionPane.showMessageDialog(null, "Day cannot be bigger than 28");
            validInput = false;
        }
        else if (Integer.parseInt(weight) < 1 || Integer.parseInt(weight) > 100) {
            JOptionPane.showMessageDialog(null, "Weight must be within 1 - 100");
            validInput = false;
        }

        return validInput;
    }

    private boolean hasLetters(String s) {
        boolean returnFlag = false;
        for (int i = 0; i < s.length() && !returnFlag; i++) {
            if (!Character.isDigit(s.charAt(i))) {
                returnFlag = true;
            }
        }
        return returnFlag;
    }

    // list out events with delete button on right panel --------------------------------------------------------------
    private void listEvents() {
        JOptionPane.showMessageDialog(null, String.format("""
                            Your Assesment Type: %s
                            Date: %s %s %s
                            Weight: %s
                            Title: %s
                            Description: %s
                            """, eventType, day, month, year, weight, name, description));

        // send raw data to back end
        int[] dateArray  = {Integer.parseInt(day), Integer.parseInt(month), Integer.parseInt(year)};

        // create the currently submitted event object
        Event event = new Event(eventType, name, dateArray, Double.parseDouble(weight), description);

        // add to calendar
        Event eventLabel = calendar.addEvent(event);
        String eventText = eventLabel.getEventInfo();

        // add JTextArea to the rightPanel
        JTextArea newLabel = new JTextArea(eventText);
        newLabel.setEditable(false);  // Stop the user from typing in it
        newLabel.setOpaque(false);    // Make the background transparent to blend with the panel
        newLabel.setFocusable(false); // Prevent the blinking text cursor from appearing
        newLabel.setBorder(null);
        Dimension prefSize = newLabel.getPreferredSize();
        newLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, prefSize.height));

        eventListPanel.add(newLabel);

        // redraw panel
        eventListPanel.revalidate();
        eventListPanel.repaint();
    }










    // main method ----------------------------------------------------------------------------------------------------
    public static void main(String[] args) {
        new UI();
    }

    // TODO: input cleansing should be performed when the user clicks "submit" to a new event
    // TODO: day month year are strings, allow for int output
    // TODO: do not allow eventType == -1
    // TODO: ensure weight is between 0 - 100
}