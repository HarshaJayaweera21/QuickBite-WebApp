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

    private void listUsers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<User> users = userService.getAllUsers();
            request.setAttribute("users", users);
            request.getRequestDispatcher("/WEB-INF/views/user/adminUserManagement.jsp").forward(request, response);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error listing users: " + e.getMessage(), e);
            throw new ServletException("Unable to list users", e);
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userId = request.getParameter("id");
        try {
            User user = userService.getUserById(userId);
            if (user == null) {
                LOGGER.warning("User not found: " + userId);
                request.setAttribute("error", "User not found");
            } else {
                request.setAttribute("user", user);
            }
            request.getRequestDispatcher("/WEB-INF/views/user/adminUserManagement.jsp").forward(request, response);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving user: " + e.getMessage(), e);
            throw new ServletException("Error retrieving user", e);
        }
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String userID = request.getParameter("userID");
            String role = request.getParameter("role");
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String phoneNumber = request.getParameter("phoneNumber");
            String address = request.getParameter("address");

            User user;
            if ("Customer".equals(role)) {
                user = new Customer(userID, name, email, password, phoneNumber, address);
            } else if ("Admin".equals(role)) {
                user = new Admin(userID, name, email, password, phoneNumber, address);
            } else {
                throw new IllegalArgumentException("Invalid role specified.");
            }

            userService.registerUser(user);
            sendJsonResponse(response, true, null);
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.WARNING, "Validation error adding user: " + e.getMessage(), e);
            sendJsonResponse(response, false, e.getMessage());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to add user: " + e.getMessage(), e);
            sendJsonResponse(response, false, "Failed to add user due to server error.");
        }
    }

