package com.daydoodle.daydoodle.servlets.Calendar;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.logging.Logger;

@WebServlet(name = "AddUserToCalendar", value = "/AddUserToCalendar")
public class AddUserToCalendar extends HttpServlet {

    private static final Logger log = Logger.getLogger(AddUserToCalendar.class.getName());

}