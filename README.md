# SmartCampus System (GNC College Final Assessment)

**Author:** Himanshu Sharma  
**Repository:** `gnc-java-final-assessment-smartcampus`

## 📖 Problem Statement
The objective of this project is to build a Smart Campus Management System to manage student records, course catalogs, and a robust enrollment process. The system ensures safe data entry, asynchronous processing to maintain performance, and persistent data storage.

## 🚀 Features Implemented
1. **Object-Oriented Programming:** Structured `Student` and `Course` entities.
2. **Collections Framework:** Heavy utilization of `HashMap` and `ArrayList` to map students to multiple courses efficiently.
3. **Custom Exception Handling:** Implemented `HsInvalidDataException` to catch negative fees, duplicate IDs, and invalid email formats safely.
4. **Multithreading:** Built an `HsEnrollmentProcessor` thread. When a student enrolls, the thread simulates a background network request asynchronously, keeping the main menu responsive.
5. **File Handling (Bonus):** Uses `ObjectOutputStream` and `ObjectInputStream` to save all state data locally to a `.dat` file, ensuring no data is lost upon exit.
6. **UNIQUE FEATURE (Himanshu's Scholarship Logic):** A custom business logic layer inside the "View Enrollments" module that detects if a student is enrolled in multiple courses and automatically applies a 10% scholarship discount to their total fees.

## 💻 Output Screenshots
*(Note: Replace this section with actual screenshots of your console once you run the code)*
* Screenshot 1: Adding a student and course.
* Screenshot 2: Catching a custom exception (e.g., negative fee).
* Screenshot 3: The Async Thread printing "success" while the main menu is active.
* Screenshot 4: Applying the 10% Multi-Course Scholarship.

## ⚙️ How to Run
1. Ensure Java (JDK 8 or higher) is installed.
2. Compile the code: `javac HimanshuSmartCampusMain.java`
3. Run the application: `java HimanshuSmartCampusMain`
4. Follow the interactive console menu.
