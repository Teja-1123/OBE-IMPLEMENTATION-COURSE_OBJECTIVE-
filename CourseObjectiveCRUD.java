package CourseObjective;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseObjectiveCRUD {

    private Connection con;

    public CourseObjectiveCRUD(Connection con) {
        this.con = con;
    }

    public boolean addCourseObjective(String courseObjNo, String courseObjCode, String courseObjDetails, String courseCode) {
        // First, check if the course objective already exists
        String checkSql = "SELECT COUNT(*) FROM course_objectives WHERE course_obj_no = ? AND course_obj_code = ? AND course_code = ?";
        try (PreparedStatement checkStmt = con.prepareStatement(checkSql)) {
            checkStmt.setString(1, courseObjNo);
            checkStmt.setString(2, courseObjCode);
            checkStmt.setString(3, courseCode);

            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                // Duplicate entry found, do not insert
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        // If no duplicate is found, proceed with the insert
        String sql = "INSERT INTO course_objectives (course_obj_no, course_obj_code, course_obj_details, course_code) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, courseObjNo);
            stmt.setString(2, courseObjCode);
            stmt.setString(3, courseObjDetails);
            stmt.setString(4, courseCode);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateCourseObjective(String courseObjNo, String courseObjCode, String courseObjDetails, String courseCode) {
        String sql = "UPDATE course_objectives SET course_obj_code = ?, course_obj_details = ?, course_code = ? WHERE course_obj_no = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, courseObjCode);
            stmt.setString(2, courseObjDetails);
            stmt.setString(3, courseCode);
            stmt.setString(4, courseObjNo);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteCourseObjective(String courseObjNo) {
        String sql = "DELETE FROM course_objectives WHERE course_obj_no = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, courseObjNo);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Map<String, Object>> getCourseObjectivesByCode(String courseCode) {
        List<Map<String, Object>> objectivesList = new ArrayList<>();
        String sql = "SELECT * FROM course_objectives WHERE course_code = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, courseCode);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> course = new HashMap<>();
                course.put("course_obj_no", rs.getString("course_obj_no"));
                course.put("course_obj_code", rs.getString("course_obj_code"));
                course.put("course_obj_details", rs.getString("course_obj_details"));
                course.put("course_code", rs.getString("course_code"));
                objectivesList.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return objectivesList;
    }

    public List<Map<String, Object>> getAllCourseObjectives() {
        List<Map<String, Object>> objectivesList = new ArrayList<>();
        String sql = "SELECT * FROM course_objectives";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> course = new HashMap<>();
                course.put("course_obj_no", rs.getString("course_obj_no"));
                course.put("course_obj_code", rs.getString("course_obj_code"));
                course.put("course_obj_details", rs.getString("course_obj_details"));
                course.put("course_code", rs.getString("course_code"));
                objectivesList.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return objectivesList;
    }
}
