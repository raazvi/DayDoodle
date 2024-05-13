package com.daydoodle.daydoodle.servlets.Activities;

import java.io.IOException;

import com.daydoodle.daydoodle.ejb.ActivityBean;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.logging.Logger;

@WebServlet(name = "ManageActivities", value = "/ManageActivities")
public class ManageActivities extends HttpServlet {

    private static final Logger log = Logger.getLogger(ManageActivities.class.getName());

    @Inject
    ActivityBean activityBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("\n Entered ManageActivities.doGet \n");

        req.setAttribute("activities", activityBean.findAllActivities());

        log.info("\n Exited ManageActivities.doGet \n");
        req.getRequestDispatcher("/WEB-INF/adminPages/manageActivities.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("\n Entered ManageActivities.doPost \n");

        String name = req.getParameter("name");
        String description = req.getParameter("description");

        activityBean.addActivity(name,description);

        log.info("\n Exited ManageActivities.doPost \n");
        resp.sendRedirect(req.getContextPath() + "/ManageActivities");
    }

}