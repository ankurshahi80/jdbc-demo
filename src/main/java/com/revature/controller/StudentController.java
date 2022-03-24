package com.revature.controller;

import com.revature.dao.StudentDao;
import com.revature.exception.StudentNotFoundException;
import com.revature.model.Student;
import com.revature.service.StudentService;
import io.javalin.Javalin;
import io.javalin.http.Handler;

import java.util.List;

public class StudentController implements Controller{

    private StudentService studentService;

    public StudentController() {
        this.studentService = new StudentService();
    }
// This lambda will implicitly have "throws Exceptions" based on the functional interface
    private Handler getAllStudents = (ctx) ->{
       List<Student> students = studentService.getAllStudents();
       ctx.json(students);
    };

    private Handler getStudentById = (ctx)->{
        String id = ctx.pathParam("id");
        Student student = studentService.getStudentById(id);
        ctx.json(student);
    };

    private Handler addStudent = (ctx)->{
        Student studentToAdd = ctx.bodyAsClass(Student.class);
        Student newStudent = studentService.addStudent(studentToAdd);
        ctx.status(201); // 201 CREATED
        ctx.json(newStudent);
    };

    private Handler updateStudent = (ctx) -> {
        Student studentBody = ctx.bodyAsClass(Student.class);
        String sId = ctx.pathParam("id");

        Student updatedStudent = studentService.updateStudent(sId, studentBody);
        ctx.status(200);
        ctx.json(updatedStudent);
    };

    @Override
    public void mapEndPoints(Javalin app) {
        app.get("/students",getAllStudents);
        app.get("/students/{id}",getStudentById);
        app.post("/students",addStudent);
        app.put("/students/{id}",updateStudent);
    }
}
