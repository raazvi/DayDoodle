package com.daydoodle.daydoodle.servlets;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.logging.Logger;

@WebServlet(name = "Logout", value = "/Logout")
public class Logout extends HttpServlet {

    private static final Logger log = Logger.getLogger(Logout.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("\n Entered Logout.doGet, logging out..."+ req.getRemoteUser() +" \n");

        req.logout();
        req.getSession().invalidate();

        log.info("\n Exited Logout.doGet, redirecting to Login servlet\n");
        resp.sendRedirect(req.getContextPath()+"/Login");
    }
}