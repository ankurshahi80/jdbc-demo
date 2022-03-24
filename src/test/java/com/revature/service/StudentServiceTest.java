package com.revature.service;

import com.revature.dao.StudentDao;
import com.revature.exception.StudentNotFoundException;
import com.revature.model.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StudentServiceTest {

    @Test
    public void testGetAllStudents() throws SQLException {
//        Arrange
        StudentDao mockObject = mock(StudentDao.class);
        List<Student> mockStudents = new ArrayList<>();
        mockStudents.add(new Student(1, "Bill", "Smith",20));
        mockStudents.add(new Student(2, "Jane", "Doe",25));
        mockStudents.add(new Student(3, "John", "Doe",15));

//        Whenever the code in the Service Layer calls the getAllStudents() method
//        for the dao layer, then return the list of students
//        we have specified above
        when(mockObject.getAllStudents()).thenReturn(mockStudents);

        StudentService studentService = new StudentService(mockObject);

//        Act
        List<Student> students = studentService.getAllStudents();

//        Assert
        Assertions.assertEquals(mockStudents, students);

//        System.out.println(students);
    }

//    Positive test is also known as the "Happy path". The user is utilizing this method correctly
    @Test
    public void test_getStudentById_positiveTest() throws SQLException, StudentNotFoundException {
//        Arrange
        StudentDao mockDao = mock(StudentDao.class);

//        mocking the return value for id 1
        Student expected = new Student(1, "John", "Doe", 41);
        when(mockDao.getStudentById(eq(1))).thenReturn(expected);

        StudentService studentService = new StudentService(mockDao);

//        Act
        Student actual = studentService.getStudentById("1");

//         Assert
        Assertions.assertEquals(expected, actual);
    }

//    Negative test
    @Test
    public void test_getStudentById_studentDoesNotExist() throws SQLException, StudentNotFoundException {
//        Arrange
        StudentDao mockDao = mock(StudentDao.class);

        StudentService studentService = new StudentService(mockDao);

//        Act + Assert
//        The test case will pass if we encounter StudentNotFound exception
        Assertions.assertThrows(StudentNotFoundException.class,()->{
            studentService.getStudentById("10");
        });
    }

//    Negative test
    @Test
    public void test_getStudentById_badRequest() throws SQLException, StudentNotFoundException {
//        Arrange
        StudentDao mockDao = mock(StudentDao.class);

        StudentService studentService = new StudentService(mockDao);

//        Act
        try {
            studentService.getStudentById("abc");
            fail(); // We only reach this line if no exception is caught
        } catch (IllegalArgumentException e) {
            String expectedMessage = "Student id must be a valid integer only.";
            String actualMessage = e.getMessage();
//        Assert
            Assertions.assertEquals(expectedMessage, actualMessage);
        }
    }

    @Test
    public void test_getStudentById_sqlExceptionFromDao() throws SQLException {
//        Arrange
        StudentDao mockDao = mock(StudentDao.class);

        when(mockDao.getStudentById(anyInt())).thenThrow(SQLException.class);

        StudentService studentService = new StudentService(mockDao);

//        Act + Assert
        Assertions.assertThrows(SQLException.class, () ->{
            studentService.getStudentById("5");
        });


    }
}
