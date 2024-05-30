package com.daydoodle.daydoodle.servlets.Calendar;

import java.io.IOException;

import com.daydoodle.daydoodle.common.ActivityDto;
import com.daydoodle.daydoodle.common.CalendarDto;
import com.daydoodle.daydoodle.common.CustomActivityDto;
import com.daydoodle.daydoodle.ejb.ActivityBean;
import com.daydoodle.daydoodle.ejb.CalendarBean;
import com.daydoodle.daydoodle.ejb.CalendarEventBean;
import com.daydoodle.daydoodle.ejb.CustomActivityBean;
import com.daydoodle.daydoodle.entities.User;
import com.daydoodle.daydoodle.exceptions.InvalidDateRangeException;
import com.daydoodle.daydoodle.exceptions.InvalidDateRangeException;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.logging.Logger;

@WebServlet(name = "AddEvent", value = "/AddEvent")
public class AddEvent extends HttpServlet {

    private static final Logger log = Logger.getLogger(AddEvent.class.getName());

    @Inject
    CalendarBean calendarBean;
    @Inject
    ActivityBean activityBean;
    @Inject
    CustomActivityBean customActivityBean;
    @Inject
    CalendarEventBean calendarEventBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("\n** Entered AddEvent.doGet, redirecting user to the addEvent.jsp page... **\n");

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        String calendarIdString = req.getParameter("calendarId");
        Long cardId=Long.parseLong(calendarIdString);
        CalendarDto calendar = calendarBean.findCalendarById(cardId,calendarBean.findAllCalendars());

        List<ActivityDto> activityList = activityBean.findAllActivities();
        List<CustomActivityDto> userActivityList = customActivityBean.findAllCustomActivitiesByUsername(user.getUsername(),customActivityBean.findAllCustomActivities());

        req.setAttribute("calendar",calendar);
        req.setAttribute("activities",activityList);
        req.setAttribute("customActivities", userActivityList);
        req.setAttribute("username",user.getUsername());
        log.info("\n** Exited AddEvent.doGet. **\n");
        req.getRequestDispatcher("/WEB-INF/userPages/calendar/addEvent.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("\n Entered AddEvent.doPost, adding the new event... \n");

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        String username=user.getUsername();
        String title=req.getParameter("title");
        String startDateString= req.getParameter("fromDate");
        String endDateString=req.getParameter("toDate");
        String location=req.getParameter("location");
        String description=req.getParameter("description");
        String tempActivity=req.getParameter("activity");
        Long calendarId=Long.parseLong(req.getParameter("calendarId"));
        Long activityId = null, customActivityId = null;
        char activityType;
        LocalDateTime startDate=null;
        LocalDateTime endDate=null;
        log.info("\n Variables: "+title+" "+startDateString+" "+endDateString+" "+location+" "+description+" "+tempActivity+" "+username+" "+calendarId+" \n");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        try {
            log.info("\n Parsing dates from String to LocalDateTime... \n");
            startDate = LocalDateTime.parse(startDateString, formatter);
            endDate = LocalDateTime.parse(endDateString, formatter);
            log.info("\n Successful!!!! \n");

            if (endDate.isBefore(startDate)) {
                throw new InvalidDateRangeException("End date cannot be earlier than start date.");
            }

        } catch (DateTimeParseException e) {
            log.severe("Error parsing the date: " + e.getMessage());
            req.setAttribute("error", "Invalid date format. Please use the correct format.");
            req.getRequestDispatcher("/WEB-INF/userPages/calendar/addEvent.jsp").forward(req, resp);
            return;
        } catch (InvalidDateRangeException e) {
            log.severe(e.getMessage());
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/userPages/calendar/addEvent.jsp").forward(req, resp);
            return;
        }

        activityType=tempActivity.charAt(0);

        log.info("\n Detecting activity type... \n");
        if(activityType=='a'){
            String activityIdString = tempActivity.substring(1);
            activityId=Long.parseLong(activityIdString);
        } else {
            String userActivityIdString = tempActivity.substring(1);
            customActivityId = Long.parseLong(userActivityIdString);
        }
        log.info("\n Successful!!!! **\n");
        log.info("\n Activty id: "+activityId+" Custom activity id: "+ customActivityId +" Activity type: "+activityType);

        if(activityId!=null){
            log.info("\n Creating a new event with a predefined activity!");
            calendarEventBean.addEventWithActivity(title,startDate,endDate,description,location,username,activityId,calendarId);
        }else {
            log.info("\n Creating a new event with a custom activity!");
            calendarEventBean.addEventWithCustomActivity(title,startDate,endDate,description,location,username, customActivityId,calendarId);
        }

        log.info("\n Exited AddEvent.doPost. **\n");
        resp.sendRedirect(req.getContextPath() + "/Calendar?calendarId=" + calendarId);
    }
}
