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

@WebServlet(name = "DeleteActivity", value = "/DeleteActivity")
public class DeleteActivity extends HttpServlet {

    private static final Logger log = Logger.getLogger(DeleteActivity.class.getName());

    @Inject
    ActivityBean activityBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Retrieve activityId parameter from request URL
        String activityId = req.getParameter("activityId");

        if (activityId != null) {
            try {
                Long id = Long.parseLong(activityId);

                activityBean.deleteActivity(id);

                resp.sendRedirect(req.getContextPath() + "/ManageActivities");
            } catch (NumberFormatException e) {
                log.warning("Invalid activityId format: " + activityId);
            }
        } else {
            log.warning("No activityId parameter found in the request URL.");

        }
    }

}