Course Objectives Management System

This project is designed to efficiently manage course objectives using a structured approach. It provides CRUD (Create, Read, Update, Delete) operations for managing course-related data, ensuring seamless interaction with the database through Java and SQLite.

Features

Add, view, update, and delete course objectives.

Well-structured database schema with fields such as:

Course Objective Number

Course Objective Code


Course Objective ID

Course Objective Details

Course Code

Smooth integration of JDBC for reliable database interaction.

User-friendly interface for easy navigation and management of course objectives.

Files in the Project
Source Code Files
Main.java: The entry point of the application.

DatabaseConnection.java: Manages database connectivity.

CourseObjectiveCRUD.java: Contains CRUD operations for the course objectives table.

UIManager.java: Handles the graphical user interface and user interactions.

Steps to Create the Project
Create the Project:

Open NetBeans and create a new Java project.

Uncheck the option to create a class with the same name as the project.

Create the Package:

In the Source Packages folder, create a new package named courseobjective.

Add the Files:

Download the source code files from the GitHub repository.

Add the files to the courseobjective package in your project.

Set Up the Database:

Establish a database on your server with the name OBE_system.

Create a table named course_objectives with the following columns:

id (Primary Key)

course_code

course_objective

course_id

course_obj_no

Connect the Database:

Configure the database connection in the project using the JDBC driver.

Ensure the database connection details (URL, username, password) are correctly specified.

Run the Application:

Open the Main.java file in NetBeans.

Run the file to start the application.
Documentation
README.md: This file, providing an overview and details about the project.

UserGuide.pdf: A guide for end-users on how to use the application. (Add this if available)
