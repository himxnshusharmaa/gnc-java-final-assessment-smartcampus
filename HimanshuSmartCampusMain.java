import java.io.*;
import java.util.*;

// 1. Custom Exception for handling invalid inputs
class HsInvalidDataException extends Exception {
    public HsInvalidDataException(String message) {
        super(message);
    }
}

// 2. Student Class (Implements Serializable for File Handling)
class HimanshuStudent implements Serializable {
    int hsStudentId;
    String sharmaName;
    String hsEmail;

    public HimanshuStudent(int id, String name, String email) {
        this.hsStudentId = id;
        this.sharmaName = name;
        this.hsEmail = email;
    }

    @Override
    public String toString() {
        return "Student ID: " + hsStudentId + " | Name: " + sharmaName + " | Email: " + hsEmail;
    }
}

// 3. Course Class (Implements Serializable)
class HimanshuCourse implements Serializable {
    int hsCourseId;
    String hsCourseName;
    double sharmaFee;

    public HimanshuCourse(int id, String name, double fee) {
        this.hsCourseId = id;
        this.hsCourseName = name;
        this.sharmaFee = fee;
    }

    @Override
    public String toString() {
        return "Course ID: " + hsCourseId + " | Course: " + hsCourseName + " | Fee: ₹" + sharmaFee;
    }
}

// 4. Multithreading: Async Enrollment Processor
class HsEnrollmentProcessor extends Thread {
    private String studentName;
    private String courseName;

    public HsEnrollmentProcessor(String studentName, String courseName) {
        this.studentName = studentName;
        this.courseName = courseName;
    }

    @Override
    public void run() {
        try {
            System.out.println("   [Thread] Sending data to server for " + studentName + "...");
            Thread.sleep(2000); // Simulating processing delay
            System.out.println("   [Thread-Success] " + studentName + " successfully registered for " + courseName + "!");
        } catch (InterruptedException e) {
            System.out.println("   [Thread-Error] Enrollment interrupted.");
        }
    }
}

// 5. Main Application Class
public class HimanshuSmartCampusMain {
    // Collections
    private static HashMap<Integer, HimanshuStudent> hsStudentsMap = new HashMap<>();
    private static HashMap<Integer, HimanshuCourse> hsCoursesMap = new HashMap<>();
    private static HashMap<Integer, ArrayList<HimanshuCourse>> sharmaEnrollmentMap = new HashMap<>();
    
    // File path for Bonus File I/O
    private static final String FILE_NAME = "HimanshuCampusData.dat";

    public static void main(String[] args) {
        Scanner hsScanner = new Scanner(System.in);
        loadDataFromFile(); // Load previously saved data on startup

        while (true) {
            System.out.println("\n=============================================");
            System.out.println("   HIMANSHU SHARMA'S SMART CAMPUS SYSTEM     ");
            System.out.println("=============================================");
            System.out.println("1. Add Student");
            System.out.println("2. Add Course");
            System.out.println("3. Enroll Student (Triggers Async Thread)");
            System.out.println("4. View All Students");
            System.out.println("5. View All Enrollments & Unique Feature");
            System.out.println("6. Save Data & Exit");
            System.out.print("Enter your choice: ");
            
            try {
                int hsChoice = Integer.parseInt(hsScanner.nextLine());

                switch (hsChoice) {
                    case 1:
                        addStudent(hsScanner);
                        break;
                    case 2:
                        addCourse(hsScanner);
                        break;
                    case 3:
                        enrollStudent(hsScanner);
                        break;
                    case 4:
                        viewStudents();
                        break;
                    case 5:
                        viewEnrollments();
                        break;
                    case 6:
                        saveDataToFile();
                        System.out.println("Data saved successfully. Exiting Himanshu's Campus System. Goodbye!");
                        hsScanner.close();
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice! Please select between 1-6.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number for the menu.");
            } catch (HsInvalidDataException e) {
                System.out.println("Validation Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected Error: " + e.getMessage());
            }
        }
    }

    private static void addStudent(Scanner sc) throws HsInvalidDataException {
        System.out.print("Enter Student ID: ");
        int id = Integer.parseInt(sc.nextLine());
        if (hsStudentsMap.containsKey(id)) throw new HsInvalidDataException("Student ID already exists!");

        System.out.print("Enter Student Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Student Email: ");
        String email = sc.nextLine();
        
        if (!email.contains("@")) throw new HsInvalidDataException("Invalid Email format!");

        hsStudentsMap.put(id, new HimanshuStudent(id, name, email));
        sharmaEnrollmentMap.put(id, new ArrayList<>()); // Initialize empty enrollment list
        System.out.println("Student added successfully!");
    }

    private static void addCourse(Scanner sc) throws HsInvalidDataException {
        System.out.print("Enter Course ID: ");
        int id = Integer.parseInt(sc.nextLine());
        if (hsCoursesMap.containsKey(id)) throw new HsInvalidDataException("Course ID already exists!");

        System.out.print("Enter Course Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Course Fee: ");
        double fee = Double.parseDouble(sc.nextLine());
        
        if (fee < 0) throw new HsInvalidDataException("Course fee cannot be negative!");

        hsCoursesMap.put(id, new HimanshuCourse(id, name, fee));
        System.out.println("Course added successfully!");
    }

    private static void enrollStudent(Scanner sc) throws HsInvalidDataException {
        System.out.print("Enter Student ID to enroll: ");
        int sId = Integer.parseInt(sc.nextLine());
        if (!hsStudentsMap.containsKey(sId)) throw new HsInvalidDataException("Student not found!");

        System.out.print("Enter Course ID to enroll in: ");
        int cId = Integer.parseInt(sc.nextLine());
        if (!hsCoursesMap.containsKey(cId)) throw new HsInvalidDataException("Course not found!");

        HimanshuStudent student = hsStudentsMap.get(sId);
        HimanshuCourse course = hsCoursesMap.get(cId);

        // Add to collections
        sharmaEnrollmentMap.get(sId).add(course);
        System.out.println("Enrollment request queued...");

        // Trigger Async Processing (Multithreading)
        HsEnrollmentProcessor thread = new HsEnrollmentProcessor(student.sharmaName, course.hsCourseName);
        thread.start();
    }

    private static void viewStudents() {
        if (hsStudentsMap.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        System.out.println("\n--- Registered Students ---");
        for (HimanshuStudent s : hsStudentsMap.values()) {
            System.out.println(s);
        }
    }

    private static void viewEnrollments() {
        if (sharmaEnrollmentMap.isEmpty()) {
            System.out.println("No enrollments found.");
            return;
        }
        System.out.println("\n--- Enrollment Details & Scholarship Check ---");
        for (Map.Entry<Integer, ArrayList<HimanshuCourse>> entry : sharmaEnrollmentMap.entrySet()) {
            HimanshuStudent student = hsStudentsMap.get(entry.getKey());
            ArrayList<HimanshuCourse> courses = entry.getValue();
            
            if (courses.isEmpty()) continue;

            System.out.println("Student: " + student.sharmaName);
            double totalFee = 0;
            for (HimanshuCourse c : courses) {
                System.out.println("  -> " + c.hsCourseName + " (₹" + c.sharmaFee + ")");
                totalFee += c.sharmaFee;
            }
            
            // UNIQUE FEATURE: Himanshu's Scholarship Logic
            System.out.println("  Total Fee: ₹" + totalFee);
            if (courses.size() > 1) {
                double discount = totalFee * 0.10;
                System.out.println("  * Eligible for Himanshu's Multi-Course Scholarship (10% Off)! *");
                System.out.println("  * Final Amount Payable: ₹" + (totalFee - discount) + " *");
            }
            System.out.println();
        }
    }

    // Bonus: File Handling Write
    private static void saveDataToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(hsStudentsMap);
            oos.writeObject(hsCoursesMap);
            oos.writeObject(sharmaEnrollmentMap);
        } catch (IOException e) {
            System.out.println("File save error: " + e.getMessage());
        }
    }

    // Bonus: File Handling Read
    @SuppressWarnings("unchecked")
    private static void loadDataFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            hsStudentsMap = (HashMap<Integer, HimanshuStudent>) ois.readObject();
            hsCoursesMap = (HashMap<Integer, HimanshuCourse>) ois.readObject();
            sharmaEnrollmentMap = (HashMap<Integer, ArrayList<HimanshuCourse>>) ois.readObject();
            System.out.println("Previous campus data loaded successfully!");
        } catch (Exception e) {
            System.out.println("Could not load previous data. Starting fresh.");
        }
    }
}