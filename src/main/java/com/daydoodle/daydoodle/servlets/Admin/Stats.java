package com.daydoodle.daydoodle.servlets.Admin;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.daydoodle.daydoodle.ejb.UserBean;
import com.daydoodle.daydoodle.ejb.UserDetailsBean;
import com.daydoodle.daydoodle.common.UserDetailsDto;
import com.daydoodle.daydoodle.common.UserDto;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.logging.Logger;

@WebServlet(name = "Stats", value = "/Stats")
public class Stats extends HttpServlet {

    private static final Logger log = Logger.getLogger(Stats.class.getName());
    @Inject
    UserDetailsBean userDetailsBean;
    @Inject
    UserBean userBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UserDto> allUsers = userBean.findAllUsers();
        List<UserDetailsDto> allUserDetails = userDetailsBean.findAllUserDetails();

        int totalUsers = allUsers.size();
        Map<String, Long> ageGroups = getAgeGroups(allUserDetails);
        Map<Month, Long> accountCreationTrends = getAccountCreationTrends(allUsers);

        req.setAttribute("totalUsers", totalUsers);
        req.setAttribute("ageGroups", ageGroups);
        req.setAttribute("accountCreationTrends", accountCreationTrends);

        req.getRequestDispatcher("/WEB-INF/adminPages/stats.jsp").forward(req, resp);
    }

    private Map<String, Long> getAgeGroups(List<UserDetailsDto> userDetails) {
        return userDetails.stream()
                .filter(ud -> ud.getBirthDate() != null)
                .collect(Collectors.groupingBy(
                        ud -> {
                            int age = Period.between(ud.getBirthDate(), LocalDate.now()).getYears();
                            if (age < 18) return "Under 18";
                            else if (age <= 25) return "18-25";
                            else if (age <= 35) return "26-35";
                            else if (age <= 45) return "36-45";
                            else if (age <= 60) return "46-60";
                            else return "Over 60";
                        },
                        Collectors.counting()
                ));
    }

    private Map<Month, Long> getAccountCreationTrends(List<UserDto> users) {
        return users.stream()
                .filter(user -> user.getDateJoined() != null)
                .collect(Collectors.groupingBy(
                        user -> user.getDateJoined().getMonth(),
                        Collectors.counting()
                ));
    }
}
