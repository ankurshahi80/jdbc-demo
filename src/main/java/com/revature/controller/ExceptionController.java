package com.revature.controller;

import com.revature.exception.StudentNotFoundException;
import io.javalin.Javalin;
import io.javalin.http.ExceptionHandler;

// Instead of using try-catch (directly from the Java language), we can extract
// that process away using Javalin's exception mapping functionality
// This exception controllers maps the exception at a central location.
public class ExceptionController implements Controller{

    private ExceptionHandler studentNotFound = (e, ctx)->{
      ctx.status(404);
      ctx.json(e.getMessage());
    };

    private ExceptionHandler badArgument = (e, ctx) -> {
      ctx.status(400);
      ctx.json(e.getMessage());
    };

    @Override
    public void mapEndPoints(Javalin app) {
        app.exception(StudentNotFoundException.class, studentNotFound);
        app.exception(IllegalArgumentException.class, badArgument);
    }
}
