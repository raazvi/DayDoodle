package com.daydoodle.daydoodle.servlets.Calendar;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;
import com.daydoodle.daydoodle.common.CalendarDto;
import com.daydoodle.daydoodle.common.FriendshipDto;
import com.daydoodle.daydoodle.ejb.CalendarBean;
import com.daydoodle.daydoodle.ejb.FriendshipBean;
import com.daydoodle.daydoodle.entities.CalendarEvent;
import com.daydoodle.daydoodle.entities.User;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "Calendar", value = "/Calendar")
public class Calendar extends HttpServlet {

    private static final Logger log = Logger.getLogger(Calendar.class.getName());

    @Inject
    CalendarBean calendarBean;
    @Inject
    FriendshipBean friendshipBean;

    private static final List<String> COLORS = Arrays.asList(
            "#FF0000", "#0000FF", "#008000", "#800080", "#FFA500", "#A52A2A", "#000080",
            "#008080", "#FFC0CB", "#FFD700", "#8B0000", "#00008B", "#006400", "#4B0082",
            "#FF4500", "#2F4F4F", "#800000", "#4682B4", "#9ACD32", "#C71585", "#DAA520",
            "#5F9EA0", "#8B008B", "#32CD32", "#FF6347", "#6A5ACD", "#8A2BE2", "#20B2AA",
            "#FF1493", "#7B68EE", "#B22222", "#9400D3", "#FF00FF", "#000000", "#556B2F",
            "#1E90FF", "#D2691E", "#DC143C", "#8B4513", "#2E8B57", "#A0522D", "#6B8E23",
            "#B8860B", "#CD5C5C", "#9932CC", "#FF8C00", "#B03060", "#FFD700"
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

        // Finds all friends of the user
        List<User> allFriends=new ArrayList<>();
        for(FriendshipDto fr:allUserFriendships){
            allFriends.add(fr.getFriend());
        }

        // Finds all the friends of the user that are currently not in this calendar
        List<User> friendsNotInCalendar=new ArrayList<>();
        for(User u: allFriends){
            if(!calendar.getUsers().contains(u)){
                friendsNotInCalendar.add(u);
            }
        }

        friendsNotInCalendar.removeIf(u -> calendar.getUsers().contains(u));

        // Check if the calendar has been created by the user who is currently logged in
        boolean isCalendarOwnedByThisUser=false;
        if(calendar.getCreatedBy().equals(user.getUsername())){
            isCalendarOwnedByThisUser=true;
        }


        Map<String, String> userColors = new HashMap<>();
        int colorIndex = 0;
        for (User u : calendar.getUsers()) {
            userColors.put(u.getUsername(), COLORS.get(colorIndex % COLORS.size()));
            colorIndex++;
        }

        req.setAttribute("friendsNotInCalendar", friendsNotInCalendar);
        req.setAttribute("usersInCalendar", calendar.getUsers());
        req.setAttribute("ownedByThisUser", isCalendarOwnedByThisUser);
        req.setAttribute("userColors", userColors);

        req.getRequestDispatcher("/WEB-INF/userPages/calendar/calendar.jsp").forward(req,resp);
    }
}
