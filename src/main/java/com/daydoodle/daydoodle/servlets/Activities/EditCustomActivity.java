package com.daydoodle.daydoodle.servlets.Activities;

import java.io.IOException;

import com.daydoodle.daydoodle.common.CustomActivityDto;
import com.daydoodle.daydoodle.ejb.CustomActivityBean;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.logging.Logger;

@WebServlet(name = "EditCustomActivity", value = "/EditCustomActivity")
public class EditCustomActivity extends HttpServlet {

    private static final Logger log = Logger.getLogger(EditCustomActivity.class.getName());
    @Inject
    CustomActivityBean customActivityBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("\n Entered EditCustomActivity.doGet \n");

        Long activityId=Long.parseLong(req.getParameter("activityId"));
        CustomActivityDto activity=customActivityBean.findCustomActivityById(activityId,customActivityBean.findAllCustomActivities());

        req.setAttribute("activity", activity);
        log.info("\n Exited EditCustomActivity.doGet \n");
        req.getRequestDispatcher("/WEB-INF/userPages/editCustomActivity.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("\n Entered EditCustomActivity.doPost \n");

        Long activityId= Long.parseLong(req.getParameter("activityId"));
        String activityName= req.getParameter("activityName");
        String activityDescription= req.getParameter("activityDescription");

        customActivityBean.editActivity(activityId,activityName,activityDescription);

        resp.sendRedirect(req.getContextPath()+"/Profile");
        log.info("\n Exited EditCustomActivity.doPost \n");
    }
}