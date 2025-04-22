package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Order;
import model.User;
import service.OrderQueueService;
import service.OrderService;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@WebServlet("/queue")
public class OrderQueueServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(OrderQueueServlet.class.getName());
    private OrderQueueService orderQueueService;
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        this.orderQueueService = new OrderQueueService(getServletContext());
        this.orderService = new OrderService(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || !"Admin".equals(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // Set pending orders (queue)
        request.setAttribute("orderQueue", orderQueueService.getOrderQueue());

        // Set confirmed orders
        List<Order> confirmedOrders = orderService.getConfirmedOrders();
        request.setAttribute("confirmedOrders", confirmedOrders);

        request.getRequestDispatcher("/WEB-INF/views/order/adminOrderQueue.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("update".equals(action)) {
            try {
                if (orderQueueService.updateFrontOrder()) {
                    response.sendRedirect(request.getContextPath() + "/queue?status=updated");
                } else {
                    response.sendRedirect(request.getContextPath() + "/queue?error=Cannot update non-pending front order");
                }
            } catch (IOException e) {
                LOGGER.severe("Error updating order: " + e.getMessage());
                response.sendRedirect(request.getContextPath() + "/queue?error=Failed to update order");
            }
        } else if ("delete".equals(action)) {
            String orderId = request.getParameter("orderId");
            try {
                orderService.deleteOrder(orderId);
                response.sendRedirect(request.getContextPath() + "/queue?status=deleted");
            } catch (IOException e) {
                LOGGER.severe("Error deleting order: " + e.getMessage());
                response.sendRedirect(request.getContextPath() + "/queue?error=Failed to delete order");
            }
        }
    }
}