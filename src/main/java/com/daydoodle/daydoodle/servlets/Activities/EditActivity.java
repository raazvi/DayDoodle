package com.daydoodle.daydoodle.servlets.Activities;

import java.io.IOException;

import com.daydoodle.daydoodle.common.ActivityDto;
import com.daydoodle.daydoodle.ejb.ActivityBean;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.logging.Logger;

@WebServlet(name = "EditActivity", value = "/EditActivity")
public class EditActivity extends HttpServlet {

    private static final Logger log = Logger.getLogger(EditActivity.class.getName());

    @Inject
    ActivityBean activityBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        log.info("\n Entered EditActivity.doGet \n");
        Long activityId= Long.parseLong(req.getParameter("activityId"));

        ActivityDto activity=activityBean.findActivityById(activityId, activityBean.findAllActivities());

        req.setAttribute("activity", activity);
        log.info("\n Exited EditActivity.doGet \n");
        req.getRequestDispatcher("/WEB-INF/adminPages/editActivity.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("\n Entered EditActivity.doPost \n");

        Long activityId= Long.parseLong(req.getParameter("activityId"));
        String activityName= req.getParameter("activityName");
        String activityDescription= req.getParameter("activityDescription");

        activityBean.editActivity(activityId,activityName,activityDescription);

        resp.sendRedirect(req.getContextPath()+"/ManageActivities");
        log.info("\n Exited EditActivity.doPost \n");
    }
}