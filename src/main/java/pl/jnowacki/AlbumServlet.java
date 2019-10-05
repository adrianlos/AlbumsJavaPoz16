package pl.jnowacki;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(value = "/")
public class AlbumServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("albums") == null) {
            req.getSession().setAttribute("albums", new ArrayList<Album>());
        }

        getServletContext().getRequestDispatcher("/albums.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String author = req.getParameter("author");

        try {
            Integer year = Integer.valueOf(req.getParameter("year"));
        } catch (NumberFormatException e) {

        }

        Album album = new Album(title, author, year);

        if (album.isValid()){
            List<Album> albums = (List<Album>)req.getSession().getAttribute("albums");
            albums.add(album);
        } else {
            req.setAttribute("error", true);
        }
        getServletContext().getRequestDispatcher("/albums.jsp").forward(req, resp);
    }
}
