package com.daydoodle.daydoodle.servlets.Calendar;

import java.io.IOException;
import com.daydoodle.daydoodle.common.CalendarDto;
import com.daydoodle.daydoodle.ejb.CalendarBean;
import com.daydoodle.daydoodle.entities.User;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@WebServlet(name = "ViewCalendars", value = "/ViewCalendars")
public class ViewCalendars extends HttpServlet {

    private static final Logger log = Logger.getLogger(ViewCalendars.class.getName());
    @Inject
    CalendarBean calendarBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("\n Entered ViewCalendar.doGet ");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        List<CalendarDto> allCalendars = calendarBean.findAllCalendars();
        List<CalendarDto> yourCalendars = new ArrayList<>();
        List<CalendarDto> notYourCalendars = new ArrayList<>();

        for (CalendarDto calendar : allCalendars) {
            if (calendar.getCreatedBy().equals(user.getUsername())) {
                yourCalendars.add(calendar);
            } else {
                for (User calendarUser : calendar.getUsers()) {
                    if (calendarUser.equals(user)) {
                        notYourCalendars.add(calendar);
                        break;  // No need to check further if the user is already found
                    }
                }
            }
        }

        req.setAttribute("yourCalendars", yourCalendars);
        req.setAttribute("notYourCalendars", notYourCalendars);

        log.info("\n Exited ViewCalendar.doGet ");
        req.getRequestDispatcher("/WEB-INF/userPages/calendar/viewCalendars.jsp").forward(req, resp);
    }
}
