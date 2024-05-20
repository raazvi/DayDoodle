package com.daydoodle.daydoodle.servlets.Calendar;

import java.io.IOException;

import com.daydoodle.daydoodle.common.CalendarDto;
import com.daydoodle.daydoodle.common.FriendshipDto;
import com.daydoodle.daydoodle.ejb.CalendarBean;
import com.daydoodle.daydoodle.ejb.FriendshipBean;
import com.daydoodle.daydoodle.entities.CalendarEvent;
import com.daydoodle.daydoodle.entities.Friendship;
import com.daydoodle.daydoodle.entities.User;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.*;
import java.util.logging.Logger;

@WebServlet(name = "Calendar", value = "/Calendar")
public class Calendar extends HttpServlet {

    private static final Logger log = Logger.getLogger(Calendar.class.getName());

    @Inject
    CalendarBean calendarBean;
    @Inject
    FriendshipBean friendshipBean;

    private static final List<String> COLORS = Arrays.asList(
            "#FF6633", "#FFB399", "#FF33FF", "#FFFF99", "#00B3E6", "#E6B333",
            "#3366E6", "#999966", "#99FF99", "#B34D4D", "#80B300", "#809900",
            "#E6B3B3", "#6680B3", "#66991A", "#FF99E6", "#CCFF1A", "#FF1A66",
            "#E6331A", "#33FFCC", "#66994D", "#B366CC", "#4D8000", "#B33300",
            "#CC80CC", "#66664D", "#991AFF", "#E666FF", "#4DB3FF", "#1AB399",
            "#E666B3", "#33991A", "#CC9999", "#B3B31A", "#00E680", "#4D8066",
            "#809980", "#E6FF80", "#1AFF33", "#999933", "#FF3380", "#CCCC00",
            "#66E64D", "#4D80CC", "#9900B3", "#E64D66", "#4DB380", "#FF4D4D",
            "#99E6E6", "#6666FF"
    );


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        List<CalendarDto> calendars = calendarBean.findAllCalendars();
        String calendarIdStr=req.getParameter("calendarId");
        Long calendarId=Long.parseLong(calendarIdStr);
        CalendarDto calendar=calendarBean.findCalendarById(calendarId,calendars);
        req.setAttribute("calendar", calendar);
        List<CalendarEvent> eventsInCalendar = calendar.getEvents();
        req.setAttribute("events",eventsInCalendar);
        List<FriendshipDto> allUserFriendships=friendshipBean.findFriendshipsByUser(user.getUsername(), friendshipBean.findAllFriendships());
        List<User> allFriends=new ArrayList<>();
        for(FriendshipDto fr:allUserFriendships){
            allFriends.add(fr.getFriend());
        }

        List<User> friendsNotInCalendar=new ArrayList<>();
        for(User u: allFriends){
            if(!calendar.getUsers().contains(u)){
                friendsNotInCalendar.add(u);
            }
        }

        req.setAttribute("friends", friendsNotInCalendar);

        List<User> usersInCalendar=calendar.getUsers();

        Map<String, String> userColors = generateUserColors(usersInCalendar);
        req.setAttribute("userColors", userColors);
        req.setAttribute("usersInCalendar", usersInCalendar);

        req.getRequestDispatcher("/WEB-INF/userPages/calendar/calendar.jsp").forward(req,resp);
    }

    private Map<String, String> generateUserColors(List<User> users) {
        Map<String, String> userColors = new HashMap<>();
        int colorIndex = 0;

        for (User user : users) {
            String color = COLORS.get(colorIndex % COLORS.size());
            userColors.put(user.getUsername(), color);
            colorIndex++;
        }

        return userColors;
    }

}