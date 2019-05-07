import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * StudentsGrades
 * Author:      Pawel Wodyk
 * Student ID:  B00122935
 * Written on:  11/03/19
 * Description:
 * 	    StudentsGrades Program class, Displays GUI and handles interaction between Student Object and User.
 *      
 * Dependencies:
 *      Students.java
 * 
 * Methods:
 *      ==Constructors==
 *      StudentsGrades() - Constructor Initialize the Student Class and GUI
 * 
 *      ==Initialize Methods==
 * 	    initializeSlider() - creates Slider for the dialog Pane, that is used to select number of students
 *      inputStudents() - colect and set user input when seting the students;
 *      initializeSidePannel() - creates side panel with individual student information and options to edit student and find student
 *      initializeMainPannel() - creates main panel which displays all students as well as the max, min and avg grade values
 * 
 *      ==Event Methods==
 *      actionPerformed() - listens for the user interaction with buttons
 *      stateChanged() - Listens for the user interaction with slider
 * 
 *      ==Helper Methods==
 *      updateLabels() - update the max, min and avg Grade values when Grades are changed
 *      clearFields() - clear individual user fields
 *      showGoodByeMessage() - displays the good bye message
 *      
 *      == Main Method ==
 *      main() - runs the program and handles window close button event
 */

public class StudentsGrades extends JFrame implements ActionListener, ChangeListener {

    JPanel windowPnl, mainPnl, sidePnl;

    JButton sortByNameBtn, sortByGradeBtn, findBtn, editBtn, nextBtn;
    JLabel avgLbl, highLbl, lowLbl;
    JTextArea displayTxtA;
    JTextField nameTxtF, gradeTxtF, indexTxtF;
    JScrollPane diplayPane;
    TitledBorder title;
    Border blackline;
    JOptionPane startMessage;
    JSlider studentsSld;

    private NumberFormat nf;

    private Students st;
    private int studentsNumber = 10; // initialize the studentNumber and sets the fallback value

    public StudentsGrades() {
        //set the title of JFrame
        super("TU Dublin Students Grades Application");

        // st.createSampleSet(10); // used during design process to create Student object with random data

        nf = new DecimalFormat("0.0##");

        //sprecifies the instruction message and displays it to the user
        String welcomeMsg = "Welcome to TU Dublin Students Grades Application\n"
                + "This application is designed to alow Lecturers to analize the grades of their students\n\n"
                + "INSTRUCTIONS:\n"
                + "   1. On the next page you will be asked to select the number of students using slider provided\n"
                + "   2. Next you will be asked to Enter Name and Grade for each student\n"
                + "         NOTE: Pressing CANCEL will set the remaining data to default value\n"
                + "           however you will be able to modify it later in the program\n"
                + "   3. After entering all the students the main program window will be displayed.\n"
                + "       The options on the main window are as follows:\n"
                + "       - [NEXT]: will display next student using the index position in the index text field\n"
                + "       - [SAVE STUDENT]: will edit existing student using the data provided in the text fields\n"
                + "       - [FIND STUDENT]: will display popup allowing you to search for the student using their name\n"
                + "       - [SORT BY GRADE]: will sort the students by Grade in ascending order\n"
                + "       - [SORT BY NAME]: will sort the students by Name in alphabetical order\n";

        JOptionPane.showMessageDialog(null, welcomeMsg, "Welcome!", JOptionPane.INFORMATION_MESSAGE);

        // set the user input dialog for number of students

        startMessage = new JOptionPane();
        initializeSlider(); // calls helper class to initialize studentsSld Slider
        startMessage.setMessage(new Object[] { "Select a value: ", studentsSld });
        startMessage.setMessageType(JOptionPane.QUESTION_MESSAGE);
        startMessage.setOptionType(JOptionPane.DEFAULT_OPTION);

        JDialog dialog = startMessage.createDialog(null, "Enter Number of Students");
        dialog.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); // prevent user from closing this pane and circumvent the proces of creating Students object
        dialog.setVisible(true);
        int userInput = ((Integer) startMessage.getValue()).intValue(); // sets the button input to variable

        System.out.println("Number Of Students Entered: " + studentsSld.getValue() + " User Input: " + userInput);

        if (userInput == JOptionPane.OK_OPTION) {
            st = new Students(); // creates student object
            try {
                st.setLength(studentsNumber); // sets Length of the array
                inputStudents(); // display input panels allowing user to enter users to database
            } catch (NumberFormatException numForEx) {
                JOptionPane.showMessageDialog(windowPnl,
                        "Could not get the Value from Slider\n" + numForEx.getMessage(), "Slider Error!",
                        JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            } catch (InvalidNumberOfStudents invNumOfStudentsEx) {
                JOptionPane.showMessageDialog(windowPnl, invNumOfStudentsEx.getMessage(), "Error! ",
                        JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }

        } else {
            // functionaity Deprecated - Cancel button was removed from the popup box
            System.exit(0); //Exits on user entering the CANCEL button
        }

        // modify the JFrame window
        this.setLayout(new BorderLayout(10, 20)); //set Layout

        initializeSidePannel(); // creates side panel using helper method
        initializeMainPannel(); //creates main panel using helper method

        //sets Layout Manager for widow Panel
        windowPnl = new JPanel(new BorderLayout(10, 20));

        // set border for the window Panel 
        title = BorderFactory.createTitledBorder("Students Grades:");
        windowPnl.setBorder(title);

        // adds components to the window Panel
        windowPnl.add(sidePnl, BorderLayout.WEST);
        windowPnl.add(mainPnl, BorderLayout.CENTER);

        //adds window to th JFrame
        this.add(windowPnl, BorderLayout.CENTER);
        this.setResizable(false); // prevents resizing
        setSize(700, 400); //sets size
        setVisible(true);
    }

    // this method creates slider
    private void initializeSlider() {
        studentsSld = new JSlider();

        // stylize the slider
        studentsSld.setValue(10);
        studentsSld.setMinimum(2);
        studentsSld.setMaximum(25);
        studentsSld.setPaintTicks(true);
        studentsSld.setPaintLabels(true);
        studentsSld.setMajorTickSpacing(2);
        studentsSld.setMinorTickSpacing(1);

        //add change Listener to intercept user input
        studentsSld.addChangeListener(this);
    }

    // creates the prompts to the user to enter Students details
    private void inputStudents() {
        // sets temporary variables
        String name;
        double grade;

        //sets Texts Fields
        JTextField nField = new JTextField(10);
        JTextField gField = new JTextField(10);

        // creates custom dialog panel that will be displayed to the user
        JPanel dialogPnl = new JPanel(new GridLayout(2, 2, 10, 10));
        dialogPnl.add(new JLabel("Enter Name:"));
        dialogPnl.add(nField);
        dialogPnl.add(new JLabel("Enter Grade Between 0-100:"));
        dialogPnl.add(gField);

        // creates loop in order to display apropriate number of dialog panels, coresponding with the number of students
        for (int i = 0; i < st.getLength(); i++) {
            //interprets the button press in the dialog window
            int result = JOptionPane.showConfirmDialog(null, dialogPnl, "Please Enter Student Name and Surname",
                    JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                if (nField.getText().isEmpty()) {
                    name = "Not Set";
                } else {
                    name = nField.getText();
                }
                try {
                    grade = Double.parseDouble(gField.getText());
                    // informs user that the incorect number was entered in the Grade
                    if (grade > 100 || grade < 0) {
                        throw new NumberFormatException("Grade need to be set between 0 and 100");
                    }
                } catch (NumberFormatException numFormEx) {
                    //handles Error resulting from entering the incorect values
                    JOptionPane.showMessageDialog(null, numFormEx.toString() + "\nGrade will be set to default value",
                            "Wrong Input", JOptionPane.WARNING_MESSAGE);
                    grade = 0;
                }
                try {
                    st.addStudent(name, grade, i); //adds student to the student object
                } catch (StudentsArrayNotInitialized arrNotInit) {
                    // handles Error if the array has not been ititialize
                    JOptionPane.showMessageDialog(null, arrNotInit.getMessage(), "Error! ", JOptionPane.ERROR_MESSAGE);
                    System.exit(1); // exits the program since this is unrecoverable Error
                }

            } else if (result == JOptionPane.CANCEL_OPTION) {
                //if user press cancel the program will enter "not set" to name and 0 to grade for the remaining students
                try {
                    for (int j = i; j < st.getLength(); j++) {
                        st.addStudent("Not Set", 0, j); //adds student to the student object
                    }
                    //i = st.getLength();
                    break;
                } catch (StudentsArrayNotInitialized arrNotInit) {
                    // handles Error if the array has not been ititialize
                    JOptionPane.showMessageDialog(null, arrNotInit.getMessage(), "Error! ", JOptionPane.ERROR_MESSAGE);
                    System.exit(1); // exits the program since this is unrecoverable Error
                }
            } else {
                showGoodByeMessage();
                System.exit(0);
            }
            // clears Text Fields before the dialog window is displayed again
            nField.setText("");
            gField.setText("");
        }
    }

    //initialize the side panel
    private void initializeSidePannel() {
        sidePnl = new JPanel(new GridLayout(7, 1, 5, 10));

        indexTxtF = new JTextField("", 10);
        nameTxtF = new JTextField("", 10);
        gradeTxtF = new JTextField("", 10);

        // indexTxtF.setEditable(false);

        blackline = BorderFactory.createLineBorder(Color.BLACK);

        indexTxtF.setBorder(new CompoundBorder(indexTxtF.getBorder(), BorderFactory.createTitledBorder("Index")));
        nameTxtF.setBorder(new CompoundBorder(nameTxtF.getBorder(), BorderFactory.createTitledBorder("Student Name")));
        gradeTxtF.setBorder(
                new CompoundBorder(gradeTxtF.getBorder(), BorderFactory.createTitledBorder("Student Grade")));

        sidePnl.add(indexTxtF);
        sidePnl.add(nameTxtF);
        sidePnl.add(gradeTxtF);

        sidePnl.add(new JLabel());

        nextBtn = new JButton("Next");
        editBtn = new JButton("Save Student");
        findBtn = new JButton("Find Student By name");

        nextBtn.addActionListener(this);
        editBtn.addActionListener(this);
        findBtn.addActionListener(this);

        sidePnl.add(nextBtn);
        sidePnl.add(editBtn);
        sidePnl.add(findBtn);

    }

    //initialize the main panel
    private void initializeMainPannel() {
        mainPnl = new JPanel(new BorderLayout());

        JPanel topPnl = new JPanel(new FlowLayout());
        JPanel bottomPnl = new JPanel(new FlowLayout());

        // initilizing Top

        //initialize empty labels
        highLbl = new JLabel();
        lowLbl = new JLabel();
        avgLbl = new JLabel();

        //update labels with formated text
        updateLabels();

        topPnl.add(highLbl);
        topPnl.add(lowLbl);
        topPnl.add(avgLbl);

        // initilizing Center
        displayTxtA = new JTextArea();
        displayTxtA.setEditable(false);
        displayTxtA.setText(st.displayAllStudents());

        diplayPane = new JScrollPane(displayTxtA);

        // initilizing Bottom
        sortByGradeBtn = new JButton("Sort By Grade");
        sortByNameBtn = new JButton("Sort By Name");

        sortByGradeBtn.addActionListener(this);
        sortByNameBtn.addActionListener(this);

        bottomPnl.add(sortByGradeBtn);
        bottomPnl.add(sortByNameBtn);

        // adding each pannel to the mainPnl
        mainPnl.add(topPnl, BorderLayout.NORTH);
        mainPnl.add(diplayPane, BorderLayout.CENTER);
        mainPnl.add(bottomPnl, BorderLayout.SOUTH);

    }

    // handels events resulting from button presses
    public void actionPerformed(ActionEvent e) {
        //when the next button is pressed
        if (e.getSource() == nextBtn) {
            int currentPos; // sets temporary variable
            //sets the pointer to 0 when index is setin text field
            if (indexTxtF.getText().isEmpty()) {
                currentPos = 0;
            }
            // runs when tere is a data in index text field
            else {
                //attempts to set the valu from the field to currentPos variable
                try {
                    currentPos = Integer.parseInt(indexTxtF.getText());
                }
                // when data in the field is not a whole nuber sets position to 0 and informs user
                catch (NumberFormatException numForEx) {
                    currentPos = 0;
                    JOptionPane.showMessageDialog(windowPnl,
                            "Index need to be a whole number.\nResult set to the first entry", "Index input Error",
                            JOptionPane.WARNING_MESSAGE);
                }
            }

            // ensures the index is not outside the array bounds
            if (currentPos >= st.getLength()) {
                currentPos = 0;
            }

            try {
                gradeTxtF.setText(Double.toString(st.getGrade(currentPos)));
                nameTxtF.setText(st.getName(currentPos));
            }
            // handles Error if the array has not been ititialize
            catch (StudentsArrayNotInitialized arrNotInit) {
                // pulls data from array to the text fields
                JOptionPane.showMessageDialog(null, arrNotInit.getMessage(), "Error! ", JOptionPane.ERROR_MESSAGE);
                System.exit(1); // exits the program since this is unrecoverable Error
            }

            indexTxtF.setText(Integer.toString(++currentPos));

        }
        //handles then the edit button is pressed
        else if (e.getSource() == editBtn) {
            // instanciate temporary variables
            String name = "";
            double grade = -1;
            int index = -1;

            //ensures the text fields are not empty
            if (!nameTxtF.getText().isEmpty() && !gradeTxtF.getText().isEmpty() && !indexTxtF.getText().isEmpty()) {
                // sets the name variable
                name = nameTxtF.getText();

                //attempt to set the index value
                try {
                    int tempIndex = Integer.parseInt(indexTxtF.getText());
                    // checks is the index value within the array bounds
                    if (tempIndex > st.getLength() || tempIndex < 1) {
                        JOptionPane.showMessageDialog(windowPnl,
                                "The index need to be a between 1 and " + st.getLength(), "Wrong Input",
                                JOptionPane.WARNING_MESSAGE);
                    }
                    //sets the value for index when the data is correct
                    else {
                        index = tempIndex;
                    }
                }
                // handels the exception when index is not a whole number
                catch (NumberFormatException numForEx) {
                    JOptionPane.showMessageDialog(windowPnl, "The index need to be a whole number", "Wrong Input",
                            JOptionPane.WARNING_MESSAGE);
                }

                //attempt to set the data for grade
                try {
                    double tempGrade = Double.parseDouble(gradeTxtF.getText());
                    //checks is the grade in correct range
                    if (tempGrade > 100 || tempGrade < 0) {
                        JOptionPane.showMessageDialog(windowPnl, "The grade need to be a between 0 and 100",
                                "Wrong Input", JOptionPane.WARNING_MESSAGE);
                    }
                    //sets the value if everything is correct
                    else {
                        grade = tempGrade;
                    }

                }
                // catches the NumberFormatException when grade is not a number
                catch (NumberFormatException numForEx) {
                    JOptionPane.showMessageDialog(windowPnl, "The grade need to be a number", "Wrong Input",
                            JOptionPane.WARNING_MESSAGE);
                }

                //confirms that all the values were set correctlly
                if (!name.isEmpty() && grade != -1 && index != -1) {
                    try {
                        st.addStudent(name, grade, index - 1);
                        displayTxtA.setText(st.displayAllStudents());
                        updateLabels();
                        //throw new StudentsArrayNotInitialized(); // used to test the error
                    } catch (StudentsArrayNotInitialized arrNotInit) {
                        JOptionPane.showMessageDialog(windowPnl, arrNotInit.getMessage(), "Error! ",
                                JOptionPane.ERROR_MESSAGE);
                        System.exit(1);
                    }
                }

            }

            // if any field is left empty no action is taken and the user is informed
            else {
                JOptionPane.showMessageDialog(windowPnl, "You need to enter all 3 values to proceed", "Missing Input",
                        JOptionPane.WARNING_MESSAGE);
            }

        }

        // handles the find button press event
        else if (e.getSource() == findBtn) {
            //gets the data from the user
            String searchKey = (String) JOptionPane.showInputDialog(this, "Enter Name of the student you wish to Find",
                    "Student Search", JOptionPane.QUESTION_MESSAGE);
            // checks did user entered ok
            if (searchKey != null) {
                //confirms the user entered data
                if (!searchKey.isEmpty()) {
                    int result = st.find(searchKey); // calls the find method in Students object and passes the key as a parameter
                    //if the result is not found clears the fields and informs the user
                    if (result == -1) {
                        clearFields();
                        JOptionPane.showMessageDialog(windowPnl, "Could not find Student: " + searchKey,
                                "Search results", JOptionPane.INFORMATION_MESSAGE);
                    }
                    //when the result is found the text fields will be populated from the Students object and the user will be informed
                    else {
                        try {
                            nameTxtF.setText(st.getName(result));
                            gradeTxtF.setText(Double.toString(st.getGrade(result)));
                        }
                        // handles Error if the array has not been ititialize
                        catch (StudentsArrayNotInitialized arrNotInit) {
                            // pulls data from array to the text fields
                            JOptionPane.showMessageDialog(null, arrNotInit.getMessage(), "Error! ",
                                    JOptionPane.ERROR_MESSAGE);
                            System.exit(1); // exits the program since this is unrecoverable Error
                        }

                        indexTxtF.setText(Integer.toString(++result));
                        JOptionPane.showMessageDialog(windowPnl,
                                "Student: " + searchKey + " found at index " + (result), "Search results",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }

                //if the user do not enter data but presses ok the message will be displayed
                else {
                    JOptionPane.showMessageDialog(windowPnl, "You need to enter student name to search the records",
                            "Missing Input", JOptionPane.WARNING_MESSAGE);
                }
            }

            // if user pressed close or CANCEL button nothing will happend within GUI but message will be printed to the console
            else {
                System.err.println("Searching aborted");
            }
        }

        //sort buttons handled by calling corespponding methods and refresing the display text area
        if (e.getSource() == sortByNameBtn) {
            st.sortByNames();
            displayTxtA.setText(st.displayAllStudents());
            clearFields();
        } else if (e.getSource() == sortByGradeBtn) {
            st.sortByGrades();
            displayTxtA.setText(st.displayAllStudents());
            clearFields();
        }
    }

    // handels changes to the slider
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == studentsSld) {
            studentsNumber = studentsSld.getValue();
        }
    }

    // update the labels when the exisiting user has been modified
    private void updateLabels() {
        highLbl.setText("Highest class Grade: " + nf.format(st.highestGrade()));
        lowLbl.setText("Lowest class Grade: " + nf.format(st.lowestGrade()));
        avgLbl.setText("Avarage class Grade: " + nf.format(st.averageGrade()));
    }

    // clears fields
    private void clearFields() {
        gradeTxtF.setText("");
        nameTxtF.setText("");
        indexTxtF.setText("");
    }

    //shows goodbye message
    private void showGoodByeMessage() {
        JOptionPane.showMessageDialog(this,
                "Thank you for using: \n\n" + "   TU Dublin Students Grades Application\n\n"
                        + "We hope you enjoed the experience... Good Bye!",
                "Exiting Program", JOptionPane.PLAIN_MESSAGE);
    }

    public static void main(String args[]) {

        StudentsGrades app = new StudentsGrades();
        app.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                app.showGoodByeMessage();
                app.dispose();
                System.exit(0);
            }
        });

    }

}