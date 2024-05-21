package com.daydoodle.daydoodle.servlets;

import java.io.IOException;

import com.daydoodle.daydoodle.entities.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.logging.Logger;

@WebServlet(name = "Settings", value = "/Settings")
public class Settings extends HttpServlet {

    private static final Logger log = Logger.getLogger(Settings.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        log.info("\n Entered Settings.doGet \n");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        req.setAttribute("user", user);
        log.info("\n Exited Settings.doGet forwarding to settings.jsp \n");
        req.getRequestDispatcher("/WEB-INF/userPages/settings.jsp").forward(req, resp);
    }

}