package controller;

import entity.EntityPublication;
import entity.EntityRole;
import entity.EntityUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.Service;
import services.ServiceExcept;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

public class Controller extends javax.servlet.http.HttpServlet {
    private final Service service = new Service();
    private final Logger logger = LogManager.getLogger();

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws IOException {
        String[] str = request.getRequestURI().split("/");
        switch (str[str.length-1]) {
            case "auth.html":{
                auth(request, response);
                break;
            }
            case "registration.html":{
                register(request, response);
                break;
            }
            case "save-publ.html":{
                savePubl(request,response);
                break;
            }
            case "payment-submit.html":{
                paymentSubmit(request,response);
                break;
            }
            default:{
                response.sendError(404);
                break;
            }
        }
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String[] str = request.getRequestURI().split("/");
        switch (str[str.length-1]){
            case "auth.html":{
                request.getRequestDispatcher("/WEB-INF/jsp/auth.jsp").forward(request,response);
                break;
            }
            case "registration.html":{
                request.getRequestDispatcher("/WEB-INF/jsp/registration.jsp").forward(request, response);
                break;
            }
            case "list.html":{
                list(request, response);
                break;
            }
            case "leave.html":{
                leave(request, response);
                break;
            }
            case "lang.html":{
                lang(request, response);
                break;
            }
            case "add-publ.html": {
                request.getRequestDispatcher("/WEB-INF/jsp/add-publ.jsp").forward(request, response);
                break;
            }
            case "delete-publ.html": {
                deletePubl(request, response);
                break;
            }
            case "details.html": {
                details(request,response);
                break;
            }
            case "order.html": {
                order(request,response);
                break;
            }
            case "payment.html": {
                payment(request,response);
                break;
            }
            default:{
                response.sendError(404);
                break;
            }
        }
    }

    private void auth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        EntityUser entityUser = new EntityUser();
        entityUser.setUsername(username);
        entityUser.setPassword(password);
        boolean signed;
        try {
            signed = service.auth(entityUser);
        } catch (ServiceExcept e) {
            logger.error(e.getMessage());
            response.sendError(500);
            return;
        }
        if (signed) {
            HttpSession session = request.getSession();
            session.setAttribute("user", entityUser);
            response.sendRedirect(request.getContextPath() + "/dev/list.html");
        } else {
            response.sendRedirect(request.getContextPath() + "/dev/auth.html");
        }
    }

    private void register(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        EntityRole role = EntityRole.valueOf(request.getParameter("role"));
        EntityUser entityUser = new EntityUser();
        entityUser.setUsername(username);
        entityUser.setPassword(password);
        entityUser.setEmail(email);
        entityUser.setRole(role);
        boolean signed;
        try {
            signed = service.register(entityUser);
        } catch (ServiceExcept e) {
            logger.error(e.getMessage());
            response.sendError(500);
            return;
        }
        if (signed) {
            HttpSession session = request.getSession();
            session.setAttribute("user", entityUser);
            response.sendRedirect(request.getContextPath() + "/dev/list.html");
        } else {
            response.sendRedirect(request.getContextPath() + "/dev/auth.html");
        }
    }

    private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<EntityPublication> publications = service.getPublications();
            request.setAttribute("publications", publications);
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
        } catch (ServiceExcept e) {
            logger.error(e.getMessage());
            response.sendError(500);
        }
    }

    private void leave(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        response.sendRedirect(request.getContextPath() + "/");
    }

    private void lang(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String lang = request.getParameter("value");
        HttpSession session = request.getSession();
        session.setAttribute("lang", lang);
        response.getWriter().println("Language changed to "+ lang +".");
    }

    private void savePubl(HttpServletRequest request, HttpServletResponse response) throws IOException {
        EntityPublication publication = new EntityPublication();
        publication.setName(request.getParameter("publication-name"));
        publication.setCost(Integer.parseInt(request.getParameter("publication-cost")));
        publication.setDescription(request.getParameter("publication-description"));
        try {
            service.savePubl(publication);
        } catch (ServiceExcept e) {
            logger.error(e.getMessage());
            response.sendError(500);
        }
        response.sendRedirect(request.getContextPath() + "/dev/list.html");
    }

    private void deletePubl(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int publicationId = Integer.parseInt(request.getParameter("id"));
        try {
            service.deletePubl(publicationId);
        } catch (ServiceExcept e) {
            logger.error(e.getMessage());
            response.sendError(500);
        }
        response.sendRedirect(request.getContextPath() + "/dev/list.html");
    }

    private void details(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int publicationId = Integer.parseInt(request.getParameter("id"));
        EntityPublication publication;
        try {
            publication = service.getPublication(publicationId);
            request.setAttribute("publication", publication);
            request.getRequestDispatcher("/WEB-INF/jsp/details.jsp").forward(request, response);
        } catch (ServiceExcept e) {
            logger.error(e.getMessage());
            response.sendError(500);
        }
    }

    private void order(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int publicationId = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession();
        EntityUser user = (EntityUser) session.getAttribute("user");
        try {
            service.order(publicationId, user);
        } catch (ServiceExcept e) {
            logger.error(e.getMessage());
            response.sendError(500);
        }
        response.sendRedirect(request.getContextPath() + "/dev/list.html");
    }

    private void payment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        EntityUser user = (EntityUser) session.getAttribute("user");
        int userId = user.getId();
        try {
            List<EntityPublication> list = service.payment(userId);
            session.setAttribute("publication_list", list);
            request.getRequestDispatcher("/WEB-INF/jsp/payment.jsp").forward(request, response);
        } catch (ServiceExcept | ServletException e) {
            logger.error(e.getMessage());
            response.sendError(500);
        }
    }

    private void paymentSubmit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        EntityUser user = (EntityUser) session.getAttribute("user");
        int userId = user.getId();
        try {
            List<EntityPublication> list = service.payment(userId);
            service.paymentSubmit(list, userId);
        } catch (ServiceExcept e) {
            logger.error(e.getMessage());
            response.sendError(500);
        }
        response.sendRedirect(request.getContextPath() + "/dev/list.html");
    }
}
