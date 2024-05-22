package com.daydoodle.daydoodle.servlets.Diary;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import com.daydoodle.daydoodle.common.DiaryIdeaDto;
import com.daydoodle.daydoodle.common.DiaryPageDto;
import com.daydoodle.daydoodle.ejb.DiaryIdeaBean;
import com.daydoodle.daydoodle.ejb.DiaryPageBean;
import com.daydoodle.daydoodle.entities.User;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "Diary", urlPatterns = {"/Diary", "/DiaryIdea"})
public class Diary extends HttpServlet {

    private static final Logger log = Logger.getLogger(Diary.class.getName());

    @Inject
    DiaryPageBean diaryPageBean;
    @Inject
    DiaryIdeaBean diaryIdeaBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/DiaryIdea".equals(path)) {
            log.info("Fetching a random diary idea.");
            List<DiaryIdeaDto> diaryIdeas = diaryIdeaBean.findAllDiaryIdeas();
            if (!diaryIdeas.isEmpty()) {
                DiaryIdeaDto randomIdea = diaryIdeas.get((int) (Math.random() * diaryIdeas.size()));
                resp.getWriter().write(randomIdea.getPrompt());
            } else {
                resp.getWriter().write("No diary ideas available.");
            }
            resp.setContentType("text/plain");
            resp.setCharacterEncoding("UTF-8");
        } else {
            log.info("Entered Diary.doGet method.");
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute("user");

            List<DiaryPageDto> diaryPages = diaryPageBean.findPagesByUser(user.getUsername(), diaryPageBean.findAllDiaryPages());
            req.setAttribute("pages", diaryPages);

            List<DiaryIdeaDto> diaryIdeas = diaryIdeaBean.findAllDiaryIdeas();
            req.setAttribute("diaryIdeas", diaryIdeas);

            req.getRequestDispatcher("/WEB-INF/userPages/diary.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
