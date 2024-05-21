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

@WebServlet(name = "Help", value = "/Help")
public class Help extends HttpServlet {

    private static final Logger log = Logger.getLogger(Help.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        log.info("\n Entered Help.doGet \n");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        req.setAttribute("user", user);
        log.info("\n Exited Help.doGet forwarding to help.jsp \n");
        req.getRequestDispatcher("/WEB-INF/userPages/help.jsp").forward(req, resp);
    }
}