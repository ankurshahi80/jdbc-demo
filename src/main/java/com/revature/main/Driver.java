package com.revature.main;

import com.revature.controller.Controller;
import com.revature.controller.StudentController;

import io.javalin.Javalin;

public class Driver {
    public static void main(String[] args) {
        Javalin app = Javalin.create();

        map(app, new StudentController());

        app.start();
    }

    public static void map(Javalin app, Controller... controllers){
        for(Controller c: controllers){
            c.mapEndPoints(app);
        }
    }
}
