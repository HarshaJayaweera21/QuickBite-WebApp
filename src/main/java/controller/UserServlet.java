package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import model.Customer;
import model.Admin;
import service.UserService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

@WebServlet(urlPatterns = {"/user", "/admin/users"})
public class UserServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(UserServlet.class.getName());
    private UserService userService;

    @Override
    public void init() throws ServletException {
        this.userService = new UserService(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String servletPath = request.getServletPath();
        String action = request.getParameter("action");

        if ("/admin/users".equals(servletPath)) {
            if ("edit".equals(action)) {
                showEditForm(request, response);
            } else if ("delete".equals(action)) {
                deleteUser(request, response);
            } else {
                listUsers(request, response);
            }
        } else {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            if (user == null) {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            } else if ("profile".equals(action)) {
                showProfile(request, response);
            } else if ("logout".equals(action)) {
                session.invalidate();
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            } else if ("viewHistory".equals(action)) {
                viewOrderHistory(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String servletPath = request.getServletPath();
        String action = request.getParameter("action");
        LOGGER.info("Received POST request to " + servletPath + " with action: " + action);

        if ("login".equals(action)) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            login(request, response);
        } else if ("register".equals(action)) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            LOGGER.info("Processing register action");
            register(request, response);
        } else if ("update".equals(action)) {
            updateProfile(request, response);
        } else if ("delete".equals(action)) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            deleteAccount(request, response);
        } else if ("/admin/users".equals(servletPath)) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            if ("add".equals(action)) {
                addUser(request, response);
            } else if ("update".equals(action)) {
                updateUser(request, response);
            }
        } else {
            LOGGER.warning("Invalid action received: " + action);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            sendJsonResponse(response, false, "Invalid action");
        }
    }

