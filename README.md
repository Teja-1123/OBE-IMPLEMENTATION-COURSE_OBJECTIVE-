# 🎓 COURSE OBJECTIVES MANAGEMENT SYSTEM

Efficiently manage and organize course objectives with a structured, user-friendly application. This project provides seamless CRUD (**C**reate, **R**ead, **U**pdate, **D**elete) operations, ensuring reliable interaction with the database through **Java** and **SQLite**.

---

## 🚀 FEATURES

- **CRUD Operations**:  
  Add, view, update, and delete course objectives with ease.  
- **Structured Database Schema**:  
  The database includes the following fields:
  - Course Objective Number
  - Course Objective Code
  - Course Objective ID
  - Course Objective Details
  - Course Code
- **Smooth JDBC Integration**:  
  Ensures consistent communication between the application and the database.  
- **User-Friendly Interface**:  
  Simplifies navigation and management of course objectives.

---

## 📂 FILES IN THE PROJECT

### Source Code
- `Main.java`: The main entry point of the application.
- `DatabaseConnection.java`: Handles the database connectivity.
- `CourseObjectiveCRUD.java`: Implements CRUD logic for the database.
- `UIManager.java`: Manages the graphical user interface and user actions.

### Documentation
- `README.md`: Provides an overview of the project.
- `UserGuide.pdf` *(optional)*: A guide for end-users on how to operate the application.

---

## 🛠️ STEPS TO CREATE THE PROJECT

### 1. CREATE THE PROJECT
- Open **NetBeans** and create a new Java project.
- **Uncheck** the option to create a class with the same name as the project.

### 2. CREATE THE PACKAGE
- In the `Source Packages` folder, create a new package named `courseobjective`.

### 3. ADD THE FILES
- Download the source code files from the **GitHub repository**.
- Add the files to the `courseobjective` package in your project.

### 4. SET UP THE DATABASE
- Create a database named `OBE_system` on your server.
- Create a table named `course_objectives` with the following columns:
  - `id` *(Primary Key)*
  - `course_code`
  - `course_objective`
  - `course_id`
  - `course_obj_no`

### 5. CONNECT THE DATABASE
- Configure the database connection in `DatabaseConnection.java`:
  - Set the correct **URL**, **username**, and **password** for your database.

### 6. RUN THE APPLICATION
- Open `Main.java` in **NetBeans**.
- Run the file to start the application.

