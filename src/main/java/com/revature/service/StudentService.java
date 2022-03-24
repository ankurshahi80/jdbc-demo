package com.revature.service;

import com.revature.dao.StudentDao;
import com.revature.exception.StudentNotFoundException;
import com.revature.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class StudentService {

    private static Logger logger = LoggerFactory.getLogger(StudentService.class);
    private StudentDao studentDao;

    public StudentService() {
        this.studentDao = new StudentDao();
    }

    public StudentService(StudentDao mockDao){
        this.studentDao = mockDao;
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
            throw new IllegalArgumentException("Student id must be a valid integer only.");
        }
    }

    public Student addStudent(Student studentToAdd) throws SQLException {

        validateStudentInformation(studentToAdd);

        Student newStudent = studentDao.addStudent(studentToAdd);
        return newStudent;
    }

    public Student updateStudent(String sId, Student studentBody) throws SQLException, StudentNotFoundException {
        try {
            int intId = Integer.parseInt(sId);

            if (studentDao.getStudentById(intId) == null) {
                throw new StudentNotFoundException("User is trying to edit a " +
                        "Student that does not exist. " +
                        "Student with id " + intId + " not found.");
            }

            validateStudentInformation(studentBody);

            studentBody.setId(intId);
            Student updatedStudent = studentDao.updateStudent(studentBody);
            return updatedStudent;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Id provided must be a valid integer.");
        }
    }

    public void validateStudentInformation(Student s){
        s.setFirstName(s.getFirstName().trim());
        s.setLastName(s.getLastName().trim());

        if(!s.getFirstName().matches("[a-zA-Z]+")){
            throw new IllegalArgumentException("First name must only have alphabetical characters. First name input was " + s.getFirstName() + ".");
        }

        if(!s.getLastName().matches("[a-zA-Z]+")){
            throw new IllegalArgumentException("Last name must only have alphabetical characters. Last name input was " + s.getLastName());
        }

        if(s.getAge() < 4){
            throw new IllegalArgumentException("Must be 4 years or over. Age provided was " + s.getAge());
        }
    }
}
