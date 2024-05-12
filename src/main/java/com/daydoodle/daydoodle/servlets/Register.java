package com.daydoodle.daydoodle.servlets;

import java.io.IOException;

import com.daydoodle.daydoodle.ejb.AuthenticationBean;
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
    @Inject
    AuthenticationBean authenticationBean;

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
        password=authenticationBean.encryptPassword(password);

        if(!existingUsernames.contains(username)) {
            userBean.createUser(username, email, password);
            log.info("\n Created user " + username + " successfully. Exiting Register.doPost method. \n");
            resp.sendRedirect(req.getContextPath() + "/Login");
        }else{
            log.info("\n User already exists. Exiting Register.doPost method. \n");
            req.setAttribute("username", username);
            req.setAttribute("email", email);
            req.getRequestDispatcher("/WEB-INF/components/forms/register.jsp").forward(req, resp);
        }
    }
}