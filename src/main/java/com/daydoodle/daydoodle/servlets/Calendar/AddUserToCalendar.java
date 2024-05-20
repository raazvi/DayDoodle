package com.daydoodle.daydoodle.servlets.Calendar;

import java.io.IOException;
import java.util.logging.Logger;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.daydoodle.daydoodle.ejb.CalendarBean;
import com.daydoodle.daydoodle.ejb.UserBean;

@WebServlet(name = "AddUserToCalendar", value = "/AddUserToCalendar")
public class AddUserToCalendar extends HttpServlet {

    private static final Logger log = Logger.getLogger(AddUserToCalendar.class.getName());

    @Inject
    CalendarBean calendarBean;

    @Inject
    UserBean userBean;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String calendarIdStr = request.getParameter("calendarId");
        String friendUsername = request.getParameter("friendUsername");

        Long calendarId = Long.parseLong(calendarIdStr);
        try {
            calendarBean.addUserToCalendar(friendUsername, calendarId);
            response.sendRedirect(request.getContextPath() + "/Calendar?calendarId=" + calendarId);
        } catch (Exception e) {
            log.severe("Error adding user to calendar: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error adding user to calendar.");
        }
    }
}
