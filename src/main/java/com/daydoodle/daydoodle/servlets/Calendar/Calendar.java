package com.daydoodle.daydoodle.servlets.Calendar;

import java.io.IOException;

import com.daydoodle.daydoodle.common.CalendarDto;
import com.daydoodle.daydoodle.ejb.CalendarBean;
import com.daydoodle.daydoodle.entities.CalendarEvent;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.logging.Logger;

@WebServlet(name = "Calendar", value = "/Calendar")
public class Calendar extends HttpServlet {

    private static final Logger log = Logger.getLogger(Calendar.class.getName());

    @Inject
    CalendarBean calendarBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<CalendarDto> calendars = calendarBean.findAllCalendars();
        String calendarIdStr=req.getParameter("calendarId");
        Long calendarId=Long.parseLong(calendarIdStr);
        CalendarDto calendar=calendarBean.findCalendarById(calendarId,calendars);
        req.setAttribute("calendar", calendar);
        List<CalendarEvent> eventsInCalendar = calendar.getEvents();
        req.setAttribute("events",eventsInCalendar);

        req.getRequestDispatcher("/WEB-INF/userPages/calendar/calendar.jsp").forward(req,resp);
    }

}