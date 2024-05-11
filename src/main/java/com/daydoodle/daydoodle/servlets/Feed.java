package com.daydoodle.daydoodle.servlets;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.logging.Logger;

@WebServlet(name = "Feed", value = "/Feed")
public class Feed extends HttpServlet {

    private static final Logger log = Logger.getLogger(Feed.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("\n Entered Feed.doGet \n");

        log.info("\n Exited Feed.doGet, redirecting to feed.jsp \n");
        req.getRequestDispatcher("/WEB-INF/userPages/feed.jsp").forward(req, resp);
    }
}