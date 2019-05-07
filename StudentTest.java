import java.util.Scanner;

/**
 * StudentTest - simple CLI aplication used for testing the Students Class
 */

public class StudentTest {

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        try {
            // Testign instanciation of student class
            Students st1 = new Students();

            st1.setLength(5);

            //Test 1 - Adding Students in Order
            System.out.println("\n=====Adding students Test 1======");

            st1.addStudent("Student 1", 30);
            st1.addStudent("Student 2", 60);
            st1.addStudent("Student 3", 90);
            st1.addStudent("Student 4", 80);
            st1.addStudent("Student 5", 40);

            System.out.println(st1.displayAllStudents());

            //Test 2 - Adding Students in Random Order
            System.out.println("\n=====Adding students Test 2======");

            st1 = new Students();
            st1.setLength(5);

            st1.addStudent("Student 1", 80, 1);
            st1.addStudent("Student 2", 60, 2);
            st1.addStudent("Student 3", 30);
            st1.addStudent("Student 4", 50);
            st1.addStudent("Student 5", 100, 4);

            System.out.println(st1.displayAllStudents());

            //Test 3 - Adding students outside the array bounds
            System.out.println("\n=====Adding students Test 3======");

            st1.addStudent("Student 6", 5);
            st1.addStudent("Student 7", 15);
            st1.addStudent("Student 8", 25, 5);
            st1.addStudent("Student 9", 35, -1);

            System.out.println(st1.displayAllStudents()); // none of the above students will be added

            //Test 4 - Sorting
            System.out.println("\n=====Sorting Test======");
            System.out.println("Initial Order:");
            System.out.println(st1.displayAllStudents());

            System.out.println("Ordered by Grade:");
            st1.sortByGrades();
            System.out.println(st1.displayAllStudents()); // array will be sorted by grades
            System.out.println("Ordered by Name:");
            st1.sortByNames();
            System.out.println(st1.displayAllStudents()); // array will be sorted by names

            //Test 5 - Display average, lowest and highest class grade
            System.out.println("\n=====Display grades statistics Test======");

            System.out.println(st1.displayAllStudents());

            System.out.println("Ararage Grade : " + st1.averageGrade());
            System.out.println("Highest Grade : " + st1.highestGrade());
            System.out.println("Lowest Grade : " + st1.lowestGrade());

            //Test 6 - Search for Student’s name
            System.out.println("\n=====Search results Test======");

            System.out.println(st1.displayAllStudents());

            System.out.println("Searching for \"Student 3\" should be found");
            System.out.println(st1.find("Student 3")); // should return 2
            System.out.println("Searching for \"STUDENT 5\" should be found");
            System.out.println(st1.find("STUDENT 5")); // should return 4
            System.out.println("Searching for \"Student 9\" will not be found");
            System.out.println(st1.find("Student 9")); // should return -1

            System.out.println("\n=====Testing Exceptions======");

            Students st2 = new Students();

            System.out.println("\nAttempting to set the length outside the bounds");

            System.out.println("testing st2.setLength(-1)");
            try {
                st2.setLength(-1);
            } catch (Exception e) {
                System.err.println("Exception was thrown of type:\n\t" + e.toString());
            }

            System.out.println("testing st2.setLength(100)");
            try {
                st2.setLength(100);
            } catch (Exception e) {
                System.err.println("Exception was thrown of type:\n\t" + e.toString());
            }

            System.out.println("\nAttempting to add the student to not initialized array");

            System.out.println("testing st2.addStudent(\"Student 1\", 50)");
            try {
                st2.addStudent("Student 1", 50);
            } catch (Exception e) {
                System.err.println("Exception was thrown of type:\n\t" + e.toString());
            }

            System.out.println("testing st2.addStudent(\"Student 1\", 50, 0)");
            try {
                st2.addStudent("Student 1", 50, 0);
            } catch (Exception e) {
                System.err.println("Exception was thrown of type:\n\t" + e.toString());
            }

            System.out.println("\nAttempting to get a student data");

            System.out.println("testing System.out.println(st2.getGrade(0))");
            try {
                System.out.println(st2.getGrade(0));
            } catch (Exception e) {
                System.err.println("Exception was thrown of type:\n\t" + e.toString());
            }

            System.out.println("testing System.out.println(st2.getName(0))");
            try {
                System.out.println(st2.getName(0));
            } catch (Exception e) {
                System.err.println("Exception was thrown of type:\n\t" + e.toString());
            }

            System.out.println(
                    "\nTesting would other functions cause termination when the Students class was not initialized");

            System.out.println(st2.getLength());
            System.out.println(st2.find("Student 1"));
            st2.sortByGrades();
            st2.sortByNames();
            System.out.println(st2.displayAllStudents());

            /*
            Students stds = new Students();
            stds.createSampleSet(25); // will create random 25 Students
            
            //stds.setLength(5);
            
            stds.addStudent("John Doe", 100); // will return message that array is full
            stds.addStudent("Jane Doe", 100); // will return message that array is full
            
            System.out.println("\n");
            System.out.println(stds.displayAllStudents()); // will return students to the console
            
            System.out.println("Students: " + stds.getLength() + "\n\n"); // will return number of students to the console
            
            stds.addStudent("John Doe", 0.5, 24);
            stds.addStudent("Test Index Bounds", 80.50, 25); // attempt to add student outside the array length
            stds.addStudent("Jane Doe", 65.4, 0);
            stds.addStudent("Zoltan", 50);
            stds.addStudent("Test Person", 80, -5);
            stds.addStudent("Test Person", 200, 90);
            
            System.out.println("---------------FIND-------------");
            System.out.println(stds.displayStudent(stds.find("Jane Doe")));
            System.out.println(stds.displayStudent(stds.find("Zoltan")));
            
            System.out.println(stds.displayAllStudents());
            
            System.out.println(stds.displayStudent(10));
            System.out.println(stds.displayStudent(4));
            System.out.println("avg: " + stds.averageGrade());
            System.out.println("low: " + stds.lowestGrade());
            System.out.println("high: " + stds.highestGrade());
            
            stds.sortByGrades();
            
            System.out.println("low: " + stds.lowestGrade());
            System.out.println("high: " + stds.highestGrade());
            
            System.out.println("---------------FIND-------------");
            System.out.println(stds.displayStudent(stds.find("Jane Doe")));
            System.out.println(stds.displayStudent(stds.find("Zoltan")));
            
            System.out.println(stds.displayAllStudents());
            
            stds.sortByNames();
            
            System.out.println("---------------FIND-------------");
            System.out.println(stds.displayStudent(stds.find("Jane Doe")));
            System.out.println(stds.displayStudent(stds.find("Zoltan")));
            
            System.out.println(stds.displayAllStudents());
            
            System.out.println(stds.find("Zoltan")); // will return -1
            
            System.out.println("============= new test =============");
            
            //throw new InvalidNumberOfStudents();
            
            //throw new StudentsArrayNotInitialized();
            
            */

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sc.close();
        }

    }
}