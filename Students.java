import java.text.NumberFormat;

/**
 * Students
 * Author:      Pawel Wodyk
 * Student ID:  B00122935
 * Written on:  10/03/19
 * Description:
 *      Student class holding all the logic for initializing students arrays and correstponding methods
 *      Also holding custom exceptions thrown by the Student class
 * 
 * Global Variables:
 *      int length - used for initializing the arrays, ensures both studentsNames and studentsGrades are the same length
 *      int index - global index variable, used as a quard for addStudent(String name, double grade) method
 *      String[] studentsNames - array holding students' names
 *      double[] studentsGrades - array holding students' grades
 *      boolean isInitialized - variable to verify that both arrays has been initialized correctlly
 * 
 * Constructors:
 *      Students() -  default constructor creates the student object and initialize the global variables to empty instances
 * 
 * Methods:
 *      ==Getters and seters==
 *      int getLength() - returns number of students
 *      String getName(int atIndex) - returns student's name from the specified index location of a student
 *      double getGrade(int atIndex) - returns student's grade from the specified index location of a student
 *      void setLength(int length) - sets the length of arrays and initialize the the students' arrays
 * 
 *      ==Worker Methods==
 *      void addStudent(String name, double grade) adds student to first available position of the array
 *      void addStudent(String name, double grade, int atIndex) - adds student at the specified location
 *      void createSampleSet(int length) - creates randomized set of students for debuging and testing
 *      String displayStudent(int atIndex) - returnes pre-formated String with data describing student at the given index
 *      String displayAllStudents() - iterates through the data set and returns all students in preformated string
 *      double averageGrade() - itterates through studentsGrades array and returnes avarage grade
 *      double lowestGrade() - itterates through studentsGrades array and returnes lowest grade
 *      double highestGrade() - itterates through studentsGrades array and returnes highest grade
 *      void sortByGrades() - sorts students arrays based on the grades in ascending order
 *      void sortByNames() - sorts students arrays based on the name in ascending order
 *      int find(String key) - finds String key in the names and returns index position or -1 if student is not found
 * 
 */

public class Students {

    private int length;
    private String[] studentsNames;
    private double[] studentsGrades;
    private boolean isInitialized;

    // constructor initialize the object with empty global variables
    public Students() {
        this.isInitialized = false;
        this.length = 0;
        this.studentsNames = null;
        this.studentsGrades = null;
    }

    //returns number of students
    public int getLength() {
        return length;
    }

    // sets the length of the arrays and initialize the object, throws exception when the number of students is ouside the spectified bounds
    public void setLength(int length) throws InvalidNumberOfStudents {
        if (length > 1 && length <= 25) { // checks is the number of students correct
            this.length = length; //sets length variable
            studentsNames = new String[length]; //initialize array with names
            studentsGrades = new double[length]; // initialize array with grades
            this.isInitialized = true; // indicates that the array was set correctlly
        } else {
            this.isInitialized = false; // indicates that the array is not set
            throw new InvalidNumberOfStudents(); // throws the exception
        }
    }

    //returns student name at index position
    public String getName(int atIndex) throws StudentsArrayNotInitialized {
        if (isInitialized) {
            return studentsNames[atIndex];
        } else {
            throw new StudentsArrayNotInitialized();
        }
    }

    //returns grade at index positon
    public double getGrade(int atIndex) throws StudentsArrayNotInitialized {
        if (isInitialized) {
            return studentsGrades[atIndex];
        } else {
            throw new StudentsArrayNotInitialized();
        }
    }

    //adds single user to the first empty position of array
    public void addStudent(String name, double grade) throws StudentsArrayNotInitialized {
        //checks is the array initialized correctlly
        if (isInitialized) {
            int index = 0;
            //sets the index to the first unset position
            for (int i = 0; i < this.length; i++) {
                if (studentsNames[i] == null) {
                    index = i;
                    break;
                } else {
                    index = i + 1;
                }
            }
            //ensures the array is not full, prevents IndexOutOfBoundsException
            if (index < this.length) {
                this.studentsNames[index] = name; // sets the student name in the array
                this.studentsGrades[index] = grade; // sets the student grade in the array
                System.out.println("added student " + this.studentsNames[index] + ", with grade "
                        + this.studentsGrades[index] + " at index position [" + index + "*]"); // informs the user the student was added
                index++; // increment the global varaible index
            } else {
                System.err.println("Array Full cannot add another Student"); // inform user that the array is full
            }
        } else {
            throw new StudentsArrayNotInitialized(); // throws an error when user try to add the studnt to the array that has not been initialized
        }
    }

    // adds or modifies the student data at the specific index location
    public void addStudent(String name, double grade, int atIndex) throws StudentsArrayNotInitialized {
        //checks is the array initialized correctlly
        if (isInitialized) {
            //ensures that the index provided is not outside the array bounds, prevents IndexOutOfBoundsException
            if (atIndex >= this.length || atIndex < 0) {
                System.err.println("index entered is outside the array bounds, Student was not added");
            } else {
                this.studentsNames[atIndex] = name;
                this.studentsGrades[atIndex] = grade;
                System.out.println("added student " + this.studentsNames[atIndex] + ", with grade "
                        + this.studentsGrades[atIndex] + " at index position [" + atIndex + "]");
            }
        } else {
            throw new StudentsArrayNotInitialized(); // throws an error when user try to add the student to the array that has not been initialized
        }
    }

    //creates random set of students and for ease of use initialize the objects
    public void createSampleSet(int length) {
        //creats local Random object
        java.util.Random rng = new java.util.Random();
        //creates random set of names
        String[] randomNames = { "Jack", "James", "Daniel", "Conor", "Sean", "Adam", "Noah", "Michael", "Charlie",
                "Luke", "Thomas", "Oisin", "Alex", "Cian", "Harry", "Patrick", "Emily", "Emma", "Ava", "Sophie",
                "Amelia", "Ella", "Lucy", "Grace", "Chloe", "Mia", "Lily", "Hannah", "Aoife", "Anna", "Olivia",
                "Sarah" };
        //handles the errors thrown by setLength and addStudent
        try {
            setLength(length); // calls setLength to initialize the global varaibles
            // iterates through students
            for (int i = 0; i < this.length; i++) {
                //adds random values to arrays
                addStudent(randomNames[rng.nextInt(randomNames.length)], //picks random name from the @ranomNames array
                        rng.nextInt(101)); // creates random grade (integer used for simplicity)
            }
        } catch (InvalidNumberOfStudents invalidNumEx) {
            System.err.println(invalidNumEx.getMessage());
        } catch (StudentsArrayNotInitialized notInitEx) {
            System.err.println(notInitEx.getMessage());
        }

    }

    //returns single student in the formated string
    public String displayStudent(int atIndex) {
        //checks the queried student index 
        if (atIndex < this.length && atIndex >= 0) {
            return (atIndex + 1) + ": " + this.studentsNames[atIndex] + ":\t" + this.studentsGrades[atIndex];
        } else {
            return "No such student"; // displays no such student when the index specified is outside the array bounds
        }

    }

    //returns all students each on the seperate line in the single String
    public String displayAllStudents() {
        String output = "Students:\tGrades:\n"; // sets the first line of the returned string
        //iterates through the arrays
        for (int i = 0; i < getLength(); i++) {
            output += (i + 1) + ": " + this.studentsNames[i] + ":\t" + this.studentsGrades[i] + "\n"; // adds each students' details in a seperate line
        }
        return output; // returns the whole string
    }

    //calculates the avarage grade
    public double averageGrade() {
        double output = 0; // initialize the @output variable
        //iterates through all students grades
        for (double grade : studentsGrades) {
            output += grade; // adds each students grade to the @output
        }
        return output / this.length; // returns total grades divided by number of students
    }

    //finds lowest grade
    public double lowestGrade() {
        double lowest = this.studentsGrades[0]; //initialize the @lowest variable and sets it to the first value in case the array is sorted by lowest grades first
        //iterates through all students grades
        for (int i = 1; i < this.length; i++) {
            //sets the grade as lowest if the grade is less then the next grade in the array
            if (lowest > this.studentsGrades[i]) {
                lowest = this.studentsGrades[i];
            }
        }
        return lowest; // returns the lowest grade value
    }

    // finds highest grade
    public double highestGrade() {
        double highest = this.studentsGrades[this.length - 1]; //initialize the @highest and sets it to the last value in the array in case it is sorted by lowest grades first to preserve the assigning the value multiple times
        //iterates through all students grades
        for (int i = this.length - 2; i > 0; i--) {
            //sets the grade as highest if the grade is larger then the previous grade in the array
            if (highest < this.studentsGrades[i]) {
                highest = this.studentsGrades[i];
            }
        }
        return highest;
    }

    //sorts the array based on grades in ascending order using booble sort
    public void sortByGrades() {
        double tempGrade; // sets @tempGrade that will hold the grade during swap
        String tempName; // sets @tempName that will hold the name during swap
        boolean sorted; // sets the boolean @sorted - used as a guard
        for (int i = 0; i < this.length - 1; i++) {
            sorted = true; // sets the initial value of sorted
            for (int j = 0; j < (this.length - i - 1); j++) { // the (length -i -1) ensures that after each itteration
                                                              // the code do not check the last value which would be
                                                              // already sorted after previous itteration
                if (this.studentsGrades[j] > this.studentsGrades[j + 1]) {
                    sorted = false; // changes the value of @sorted if the unsorted data is detected

                    //swaps the grade with the next grade if its higher then the next grade
                    tempGrade = this.studentsGrades[j];
                    this.studentsGrades[j] = this.studentsGrades[j + 1];
                    this.studentsGrades[j + 1] = tempGrade;

                    //ensures the student name is swaped as well to preserve the data cohesion
                    tempName = this.studentsNames[j];
                    this.studentsNames[j] = this.studentsNames[j + 1];
                    this.studentsNames[j + 1] = tempName;
                }
            }
            // this code make sure that the code will not itterate if the array is already sorted
            if (sorted) {
                break;
            }
        }
    }

    //sorts the array based on names in alphabetical order using booble sort
    public void sortByNames() {
        String tempName; // sets @tempName that will hold the name during swap
        double tempGrade; // sets @tempGrade that will hold the grade during swap
        boolean sorted; // sets the boolean @sorted - used as a guard

        for (int i = 0; i < this.length - 1; i++) {
            sorted = true; // sets the initial value of sorted for each itteration
            for (int j = 0; j < (this.length - i - 1); j++) {
                //checks is the next name value higher in alphabetical order
                if (this.studentsNames[j + 1].compareTo(this.studentsNames[j]) < 0) {
                    sorted = false; // changes @sorted if the unsorted data is detected

                    //swaps the name in array with the next value
                    tempName = this.studentsNames[j];
                    this.studentsNames[j] = this.studentsNames[j + 1];
                    this.studentsNames[j + 1] = tempName;

                    //ensures the student grade is swaped as well to preserve the data cohesion
                    tempGrade = this.studentsGrades[j];
                    this.studentsGrades[j] = this.studentsGrades[j + 1];
                    this.studentsGrades[j + 1] = tempGrade;
                }
            }
            // this code make sure that the loop is exited if array is already sorted
            if (sorted) {
                break;
            }
        }
    }

    //searches for a String @key in the studentsNames array
    //note: uses linear search since the order of the data is not guarateed to be sorted at the time of search
    public int find(String key) {
        //itterate through the array
        for (int i = 0; i < this.length; i++) {
            //compares the @key with student name
            if (key.equalsIgnoreCase(this.studentsNames[i])) {
                //System.out.println("student found...");
                return i; // return student index
            }
        }
        return -1; // rerurn -1 if the student is not found
    }

}

// =========CUSTOM EXCEPTIONS===========

class InvalidNumberOfStudents extends Exception {
    InvalidNumberOfStudents() {
        super("Invalid number of records selected. It must be between 2 and 25.");
    }
}

class StudentsArrayNotInitialized extends Exception {
    StudentsArrayNotInitialized() {
        super("Student class has not been initialized!");
    }
}

// unused code for binary search
//note: this code was not used since I did not want to force the data to be sorted which could not be what user want
// AND in case of using the temporary array with System.arraycopy function would yeald wrong index results
// HENCE I deciaded to use linear search which will not affect the performance since the data set is very small (up to 25 students)

// public int find(String name) {

// String[] tempArr = new String[this.length];
// String tempName;
// boolean sorted;

// System.arraycopy(this.studentsNames, 0, tempArr, 0, this.length);

// for (int i = 0; i < tempArr.length - 1; i++) {
// sorted = true;
// for (int j = 0; j < (tempArr.length - i - 1); j++) {
// if (tempArr[j + 1].compareTo(tempArr[j]) < 0) {
// sorted = false;

// tempName = tempArr[j];
// tempArr[j] = tempArr[j + 1];
// tempArr[j + 1] = tempName;
// }
// }
// if (sorted) {
// break;
// }
// }

// int iMin = 0;
// int iMax = this.length;
// int pointer;
// int iteration = 0;

// while (iMin <= iMax) {
// pointer = (iMax+iMin)/2;
// System.out.println(++iteration +" ["+ iMin +"; "+ pointer +"; "+ iMax +"]" );
// //for debuging purpose

// if (name.equalsIgnoreCase(tempArr[pointer])){
// return pointer;
// }else if (name.compareTo(tempArr[pointer])>0) { // if the value is higher
// then a pointer:
// iMin= pointer; // -> set min value to pointer value
// } else { // if the value is lower:
// iMax = pointer; // -> set max value to the pointer
// }
// if (iteration > this.length) break; //for debuging purpose prevent infinite
// loop
// }
// return -1;
// }