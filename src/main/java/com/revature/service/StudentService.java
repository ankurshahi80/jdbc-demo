package com.revature.service;

import com.revature.dao.StudentDao;
import com.revature.exception.StudentNotFoundException;
import com.revature.model.Student;

import java.sql.SQLException;
import java.util.List;

public class StudentService {

    private StudentDao studentDao;

    public StudentService() {
        this.studentDao = new StudentDao();
    }

    public List<Student> getAllStudents() throws SQLException {
        return this.studentDao.getAllStudents();
    }

    public Student getStudentById(String id) throws SQLException, StudentNotFoundException {

        try {
            int intId = Integer.parseInt(id);
            Student student = studentDao.getStudentById(intId);
//        If student is null, throw StudentNotFoundException
            if(student == null){
                throw new StudentNotFoundException("Student with id: " + id + " was not found. Please check the student id and try again.");
            }

            return student;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Please enter student id as integer only.");
        }




    }
}