package CourseObjective;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.List;
import java.util.Map;

public class CourseObjectiveUI extends JFrame {

    private JTextField courseObjNoField;
    private JTextField courseObjCodeField;
    private JTextArea courseObjDetailsArea;
    private JTextField courseCodeField;

    private JButton addButton, updateButton, deleteButton, retrieveButton;
    private JTable objectivesTable;
    private DefaultTableModel tableModel;

    private CourseObjectiveCRUD crud;

    public CourseObjectiveUI() {
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) {
            JOptionPane.showMessageDialog(this, "Database connection failed!", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        crud = new CourseObjectiveCRUD(connection);

        setTitle("📚 Course Objective Manager");
        setSize(850, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 248, 250));

        // ==== Top Panel (form + buttons) ====
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(new Color(245, 248, 250));

        // ==== Form Panel ====
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new TitledBorder(new LineBorder(new Color(200, 200, 255)), "Course Objective Details"));
        formPanel.setBackground(new Color(240, 245, 255));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Column 1 - Course Obj No
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Course Obj No:"), gbc);

        gbc.gridx = 1;
        courseObjNoField = createTextField();
        formPanel.add(courseObjNoField, gbc);

        // Column 2 - Course Obj Code
        gbc.gridx = 2;
        formPanel.add(new JLabel("Course Obj Code:"), gbc);

        gbc.gridx = 3;
        courseObjCodeField = createTextField();
        formPanel.add(courseObjCodeField, gbc);

        // Row 2 - Course Obj Details
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Course Obj Details:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        courseObjDetailsArea = createTextArea();
        JScrollPane detailsScrollPane = new JScrollPane(courseObjDetailsArea);
        formPanel.add(detailsScrollPane, gbc);
        gbc.gridwidth = 1;

        // Row 3 - Course Code
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Course Code:"), gbc);

        gbc.gridx = 1;
        courseCodeField = createTextField();
        formPanel.add(courseCodeField, gbc);

        topPanel.add(formPanel);

        // ==== Buttons Panel ====
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(245, 248, 250));

        addButton = createButton("Add", new Color(173, 216, 230));
        updateButton = createButton("Update", new Color(173, 216, 230));
        deleteButton = createButton("Delete", new Color(173, 216, 230));
        retrieveButton = createButton("Retrieve", new Color(173, 216, 230));

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(retrieveButton);

        topPanel.add(buttonPanel);

        // Add combined panel to top
        add(topPanel, BorderLayout.NORTH);

        // ==== Table Section ====
        tableModel = new DefaultTableModel(new String[]{"ID", "Course Code", "Course Obj Code", "Course Obj No", "Course Obj Details"}, 0);
        objectivesTable = new JTable(tableModel);
        objectivesTable.setFillsViewportHeight(true);
        objectivesTable.setRowHeight(24);
        objectivesTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        objectivesTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        objectivesTable.getTableHeader().setBackground(new Color(220, 230, 255));
        objectivesTable.setShowVerticalLines(false);
        JScrollPane scrollPane = new JScrollPane(objectivesTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("📋 Display Objectives"));
        add(scrollPane, BorderLayout.CENTER);

        // ==== Button Actions ====
        addButton.addActionListener(e -> addObjective());
        updateButton.addActionListener(e -> updateObjective());
        deleteButton.addActionListener(e -> deleteObjective());
        retrieveButton.addActionListener(e -> retrieveObjectives());

        displayAllObjectives();
//        objectivesTable = new JTable(tableModel);

        // Add ListSelectionListener
        objectivesTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = objectivesTable.getSelectedRow();
                if (selectedRow != -1) {
                    // Ensure there are enough columns before accessing
                    int columnCount = tableModel.getColumnCount();
                    if (columnCount > 3) {
                        courseObjNoField.setText((String) tableModel.getValueAt(selectedRow, 3));  // Course Obj No
                        courseObjCodeField.setText((String) tableModel.getValueAt(selectedRow, 2));  // Course Obj Code
                        courseObjDetailsArea.setText((String) tableModel.getValueAt(selectedRow, 4));  // Course Obj Details
                        if (columnCount > 1) {
                            courseCodeField.setText((String) tableModel.getValueAt(selectedRow, 1));  // Course Code
                        }
                    }
                }
            }
        });
    }

    private JTextField createTextField() {
        JTextField field = new JTextField(20);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(180, 180, 255), 1, true),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        return field;
    }

    private JTextArea createTextArea() {
        JTextArea area = new JTextArea(4, 40);
        area.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(180, 180, 255), 1, true),
                BorderFactory.createEmptyBorder(8, 8, 5, 8)
        ));
        return area;
    }

    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(bgColor);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBorder(new LineBorder(Color.GRAY, 1, true));
        button.setPreferredSize(new Dimension(100, 35));
        return button;
    }

    private void addObjective() {
        String courseObjNo = courseObjNoField.getText().trim();
        String courseObjCode = courseObjCodeField.getText().trim();
        String courseObjDetails = courseObjDetailsArea.getText().trim();
        String courseCode = courseCodeField.getText().trim();

        if (courseObjNo.isEmpty() || courseObjCode.isEmpty() || courseObjDetails.isEmpty() || courseCode.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Call the addCourseObjective method and check the result
        boolean success = crud.addCourseObjective(courseObjNo, courseObjCode, courseObjDetails, courseCode);
        if (success) {
            JOptionPane.showMessageDialog(this, "✅ Objective added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            displayAllObjectives();  // Update the list of objectives
            // Clear the input fields after adding
            courseObjNoField.setText("");
            courseObjCodeField.setText("");
            courseObjDetailsArea.setText("");
            courseCodeField.setText("");
        } else {
            // If the insertion failed, check if it's due to duplicate
            JOptionPane.showMessageDialog(this, "Duplicate found! The course objective already exists.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
    private void updateObjective() {
        int selectedRow = objectivesTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an objective to update.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String courseObjNo = courseObjNoField.getText().trim();
        String courseObjCode = courseObjCodeField.getText().trim();
        String newDetails = courseObjDetailsArea.getText().trim();
        String courseCode = courseCodeField.getText().trim();

        if (courseObjNo.isEmpty() || courseObjCode.isEmpty() || newDetails.isEmpty() || courseCode.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Ask user whether to append or replace
        String[] options = {"Append", "Replace", "Cancel"};
        int choice = JOptionPane.showOptionDialog(this,
                "Do you want to append to or replace the existing Course Objective?",
                "Update Mode",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        if (choice == 2 || choice == JOptionPane.CLOSED_OPTION) {
            return; // User canceled
        }

        String finalDetails = newDetails;

        if (choice == 0) { // Append
            // Get existing details from the table
            String existingDetails = (String) tableModel.getValueAt(selectedRow, 3);

            // Split existing into points
            String[] existingPoints = existingDetails.split("\\n");
            int pointNumber = existingPoints.length + 1;

            // Prepare numbered final details
            StringBuilder updatedDetails = new StringBuilder();
            for (int i = 0; i < existingPoints.length; i++) {
                updatedDetails.append((i + 1)).append(". ").append(existingPoints[i].trim()).append("\n");
            }
            updatedDetails.append(pointNumber).append(". ").append(newDetails.trim());

            finalDetails = updatedDetails.toString();
        }

        boolean success = crud.updateCourseObjective(courseObjNo, courseObjCode, finalDetails, courseCode);
        if (success) {
            JOptionPane.showMessageDialog(this, "✅ Objective updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            displayAllObjectives();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update objective.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteObjective() {
    // Check if a row is selected
    int selectedRow = objectivesTable.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Please select an objective to delete.", "Error", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Retrieve course_obj_no from the selected row (assuming it's in column 0)
    Object objNo = tableModel.getValueAt(selectedRow, 0);
    String courseObjNo = objNo != null ? objNo.toString().trim() : "";

    // Confirm deletion
    int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete this objective?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION
    );

    if (confirm == JOptionPane.YES_OPTION) {
        // Call the delete method with only courseObjNo
        boolean success = crud.deleteCourseObjective(courseObjNo);

        if (success) {
            JOptionPane.showMessageDialog(this, "✅ Objective deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            displayAllObjectives(); // Refresh the table
        } else {
            JOptionPane.showMessageDialog(this, "Failed to delete objective.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}


    private void retrieveObjectives() {
        String courseCode = courseCodeField.getText().trim();
        if (courseCode.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a course code to search.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<Map<String, Object>> courseObjectives = crud.getCourseObjectivesByCode(courseCode);
        tableModel.setRowCount(0); // Clear existing rows

        int rowCount = 1;
        for (Map<String, Object> course : courseObjectives) {
            tableModel.addRow(new Object[]{
                rowCount++,
                course.get("course_code"),
                course.get("course_obj_code"),
                course.get("course_obj_no"),
                course.get("course_obj_details")
            });
        }

        if (courseObjectives.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No objectives found for the given course code.", "No Results", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void displayAllObjectives() {
        List<Map<String, Object>> courseObjectives = crud.getAllCourseObjectives();
        tableModel.setRowCount(0);

        int rowCount = 1;
        for (Map<String, Object> course : courseObjectives) {
            tableModel.addRow(new Object[]{
                rowCount++,
                course.get("course_code"),
                course.get("course_obj_code"),
                course.get("course_obj_no"),
                course.get("course_obj_details")
            });
        }
    }
}
