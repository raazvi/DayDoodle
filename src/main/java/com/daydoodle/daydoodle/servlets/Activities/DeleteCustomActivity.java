package com.daydoodle.daydoodle.servlets.Activities;

import java.io.IOException;

import com.daydoodle.daydoodle.ejb.CustomActivityBean;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.logging.Logger;

@WebServlet(name = "DeleteCustomActivity", value = "/DeleteCustomActivity")
public class DeleteCustomActivity extends HttpServlet {

    private static final Logger log = Logger.getLogger(DeleteCustomActivity.class.getName());

    @Inject
    CustomActivityBean customActivityBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        log.info("\n Entered DeleteCustomActivity.doGet \n");
        String activityId = req.getParameter("activityId");
        if (activityId != null) {
            try {
                Long id = Long.parseLong(activityId);

                customActivityBean.deleteActivity(id);

                log.info("\n Deleted CustomActivity with id " + id + "\n");
                resp.sendRedirect(req.getContextPath() + "/Profile");
            } catch (NumberFormatException e) {
                log.warning("Invalid activityId format: " + activityId);
            }
        } else {
            log.warning("No activityId parameter found in the request URL.");

        }
    }


}