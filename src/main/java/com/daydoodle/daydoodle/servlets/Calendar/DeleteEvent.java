package com.daydoodle.daydoodle.servlets.Calendar;

import java.io.IOException;

import com.daydoodle.daydoodle.ejb.CalendarBean;
import com.daydoodle.daydoodle.ejb.CalendarEventBean;
import com.daydoodle.daydoodle.entities.User;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.logging.Logger;

@WebServlet(name = "DeleteEvent", value = "/DeleteEvent")
public class DeleteEvent extends HttpServlet {

    private static final Logger log = Logger.getLogger(DeleteEvent.class.getName());
    @Inject
    CalendarBean calendarBean;
    @Inject
    CalendarEventBean calendarEventBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String eventId = req.getParameter("eventId");
        String calendarId = req.getParameter("calendarId");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        log.info("\n Entered DeleteEvent.doGet with "+ eventId + " and calendarId " + calendarId+" \n");
        if (eventId != null && !eventId.isEmpty() && calendarId != null && !calendarId.isEmpty()) {
            try {
                Long eventIdLong =Long.parseLong(eventId);
                Long calendarIdLong =Long.parseLong(calendarId);

                calendarBean.deleteEventFromCalendar(calendarIdLong,eventIdLong);
                calendarEventBean.deleteEvent(user.getUsername(),eventIdLong);

                resp.sendRedirect(req.getContextPath() + "/Calendar?calendarId=" + calendarId);
            } catch (NumberFormatException e) {

                log.warning("Invalid eventId or calendarId format: " + e.getMessage());
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid eventId or calendarId format");
            }
        } else {

            log.warning("eventId or calendarId parameter is missing");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "eventId or calendarId parameter is missing");
        }
    }

}