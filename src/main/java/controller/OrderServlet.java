package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cart;
import model.FoodItem;
import model.Order;
import model.User;
import service.FoodItemService;
import service.OrderService;
import service.OrderQueueService;
import service.CartService;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet({"/order", "/myorders"})
public class OrderServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(OrderServlet.class.getName());
    private OrderService orderService;
    private FoodItemService foodItemService;
    private CartService cartService;
    private OrderQueueService orderQueueService;

    @Override
    public void init() throws ServletException {
        this.orderService = new OrderService(getServletContext());
        this.foodItemService = new FoodItemService(getServletContext());
        this.cartService = new CartService(getServletContext());
        this.orderQueueService = new OrderQueueService(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

