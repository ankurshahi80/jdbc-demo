package com.revature.service;

import com.revature.dao.StudentDao;
import com.revature.model.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    @Test
    public void testGetStudentById_positiveTest(){
        
    }
}
