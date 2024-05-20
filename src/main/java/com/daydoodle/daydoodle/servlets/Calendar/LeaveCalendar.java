package com.daydoodle.daydoodle.servlets.Calendar;

import java.io.IOException;

import com.daydoodle.daydoodle.ejb.CalendarBean;
import com.daydoodle.daydoodle.entities.User;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.logging.Logger;

@WebServlet(name = "LeaveCalendar", value = "/LeaveCalendar")
public class LeaveCalendar extends HttpServlet {

    private static final Logger log = Logger.getLogger(LeaveCalendar.class.getName());

    @Inject
    CalendarBean calendarBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String calendarIdStr = req.getParameter("calendarId");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        Long calendarId = Long.parseLong(calendarIdStr);

        calendarBean.leaveCalendar(user.getUsername(),calendarId);
        resp.sendRedirect(req.getContextPath()+"/ViewCalendars");
    }
}