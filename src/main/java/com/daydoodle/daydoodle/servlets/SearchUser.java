import com.daydoodle.daydoodle.ejb.UserBean;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "SearchUser", value = "/SearchUser")
public class SearchUser extends HttpServlet {

    @Inject
    UserBean userBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String keyword = req.getParameter("keyword");

        // Assuming userBean.findUsersByKeyword() returns a list of usernames matching the keyword
        List<String> suggestions = userBean.findUsersByKeyword(keyword);

        // Convert suggestions to JSON format
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        for (String username : suggestions) {
            jsonArrayBuilder.add(username);
        }
        JsonArray jsonArray = jsonArrayBuilder.build();

        // Send JSON response
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.print(jsonArray.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
