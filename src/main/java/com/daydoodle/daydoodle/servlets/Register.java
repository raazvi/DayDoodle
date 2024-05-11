package com.daydoodle.daydoodle.servlets;

import java.io.IOException;

import com.daydoodle.daydoodle.ejb.UserBean;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.logging.Logger;

@WebServlet(name = "Register", value = "/Register")
public class Register extends HttpServlet {

    private static final Logger log = Logger.getLogger(Register.class.getName());

    @Inject
    UserBean userBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        log.info("\n Entered Register.doGet method \n");

        log.info("\n Exited Register.doGet method, redirecting to register.jsp \n");
        req.getRequestDispatcher("/WEB-INF/components/forms/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        log.info("\n Entered Register.doPost method \n");

        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String username = req.getParameter("username");

        List<String> existingUsernames = userBean.getExistingUsernames();

        if (existingUsernames.contains(username)) {
            req.setAttribute("email", email);
            req.setAttribute("password", password);
            req.setAttribute("username", username);
            req.setAttribute("errorMessage", "Username is taken. Please provide a new username!");
            log.info("\n Exited Register.doPost method ---> username already exists \n");
            req.getRequestDispatcher("/WEB-INF/components/forms/register.jsp").forward(req, resp);
        } else {
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hashedPasswordBytes = digest.digest(password.getBytes());
                String hashedPassword = Base64.getEncoder().encodeToString(hashedPasswordBytes);
                userBean.createUser(username, email, hashedPassword);
                log.info("\n Exited Register.doPost method, redirecting to login page \n");
                req.getRequestDispatcher("/WEB-INF/components/forms/login.jsp").forward(req, resp);
            } catch (NoSuchAlgorithmException e) {
                log.severe("Error hashing password: " + e.getMessage());
                req.setAttribute("email", email);
                req.setAttribute("password", password);
                req.setAttribute("username", username);
                req.setAttribute("errorMessage", "Error creating account. Please refresh the page and try again.");
                log.info("\n Exited Register.doPost method with an error on the hashing algorithm \n");
                req.getRequestDispatcher("/WEB-INF/components/forms/register.jsp").forward(req, resp);
            }
        }
    }
}