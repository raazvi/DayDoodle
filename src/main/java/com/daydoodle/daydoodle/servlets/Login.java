package com.daydoodle.daydoodle.servlets;

import java.io.IOException;

import com.daydoodle.daydoodle.common.UserDto;
import com.daydoodle.daydoodle.ejb.UserBean;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.logging.Logger;

@WebServlet(name = "Login", value = "/Login")
public class Login extends HttpServlet {

    private static final Logger log = Logger.getLogger(Login.class.getName());

    @Inject
    UserBean userBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        log.info("\n Entered Login.doGet method \n");

        //Code for login

        log.info("\n Exited Login.doGet method \n");
        req.getRequestDispatcher("/WEB-INF/components/forms/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        log.info("\n Entered Login.doPost method \n");
        req.setAttribute("message", "Username or password incorrect");
        String username = req.getParameter("j_username");

        UserDto user=userBean.findUserByUsername(username,userBean.findAllUsers());

        if(user.isFirstLogin()){
            userBean.setFirstLoginFalse(username);
            resp.sendRedirect(req.getContextPath()+"/UpdateUserDetails");
        }else{
            resp.sendRedirect(req.getContextPath() + "/Feed");
        }
        log.info("\n Exited Login.doPost method, redirecting to Feed servlet \n");

    }
}