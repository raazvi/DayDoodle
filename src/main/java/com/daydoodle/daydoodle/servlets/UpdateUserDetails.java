package com.daydoodle.daydoodle.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.logging.Logger;

@WebServlet(name = "UpdateUserDetails", value = "/UpdateUserDetails")
public class UpdateUserDetails extends HttpServlet {

    private static final Logger log = Logger.getLogger(UpdateUserDetails.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("\n Entered UpdateUserDetails.doGet \n");

        log.info("\n Exited UpdateUserDetails.doGet, redirecting to updateUserDetails.jsp \n");
        req.getRequestDispatcher("/WEB-INF/userPages/updateUserDetails.jsp").forward(req, resp);
    }
}