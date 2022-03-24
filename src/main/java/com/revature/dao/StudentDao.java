package com.revature.dao;

import com.revature.model.Student;
import com.revature.utility.ConnectionUtility;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDao {

    public Student addStudent(Student student) throws SQLException {
        try(Connection con = ConnectionUtility.getConnection()){
            String sql = "INSERT INTO students (first_name, last_name, age)" +
                    "VALUES(?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1,student.getFirstName());
            pstmt.setString(2, student.getLastName());
            pstmt.setInt(3,student.getAge());

            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();
            int generatedId = rs.getInt("id");

            Student newStudent = getStudentById(generatedId);

            return newStudent;
        }

    }

    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();

        try (Connection con = ConnectionUtility.getConnection()) {
            String sql = "SELECT * FROM students";
            PreparedStatement pstmt = con.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                int id = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                int age = rs.getInt("age");

                Student student = new Student(id, firstName, lastName, age);
                students.add(student);
            }

        }
        return students;
    }

    public Student getStudentById(int studentId) throws SQLException {
        try(Connection con = ConnectionUtility.getConnection()){
            String sql = "SELECT * FROM students WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, studentId);

            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                int id = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                int age = rs.getInt("age");

                Student student = new Student(id,firstName,lastName,age);
                return student;
            }
        }
        return null;
    }

    public Student updateStudent(Student student) throws SQLException {
        try(Connection con = ConnectionUtility.getConnection()){
            String sql = "UPDATE students " +
                    "SET first_name = ?, " +
                    "last_name = ?, " +
                    "age = ? " +
                    "WHERE id = ?";

            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1,student.getFirstName());
            pstmt.setString(2, student.getLastName());
            pstmt.setInt(3,student.getAge());
            pstmt.setInt(4,student.getId());

            pstmt.executeUpdate();
        }
        return getStudentById(student.getId());
    }

    public boolean deleteStudentById(int id) throws SQLException {

        try(Connection con = ConnectionUtility.getConnection()){
            String sql = "DELETE FROM students WHERE id =?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,id);

            int numberOfRecordsDeleted = pstmt.executeUpdate();

            if (numberOfRecordsDeleted == 1){
                return true;
            }
        }
        return false;
    }
}
