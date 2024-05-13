package com.daydoodle.daydoodle.servlets.Calendar;

import java.io.IOException;

import com.daydoodle.daydoodle.ejb.CalendarBean;
import com.daydoodle.daydoodle.ejb.UserBean;
import com.daydoodle.daydoodle.entities.User;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.logging.Logger;

@WebServlet(name = "AddCalendar", value = "/AddCalendar")
public class AddCalendar extends HttpServlet {

    private static final Logger log = Logger.getLogger(AddCalendar.class.getName());
    @Inject
    UserBean userBean;
    @Inject
    CalendarBean calendarBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        log.info("\n** Entered AddCalendar.doGet **\n");

        log.info("\n** Exited AddCalendar.doGet **\n");
        req.getRequestDispatcher("/WEB-INF/userPages/calendar/addCalendar.jsp").forward(req,resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("\n** Entered AddCalendar.doPost **\n");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        String title=req.getParameter("title");
        String description=req.getParameter("description");

        calendarBean.createCalendar(user.getUsername(), title,description);

        log.info("\n** Exited AddCalendar.doPost **\n");
        resp.sendRedirect(req.getContextPath()+"/ViewCalendars");
    }


}