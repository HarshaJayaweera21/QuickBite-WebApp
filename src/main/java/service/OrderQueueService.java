package service;

import model.Order;
//import model.OrderQueue;
import model.CustomOrderQueue;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import jakarta.servlet.ServletContext;

public class OrderQueueService {
    private static final Logger LOGGER = Logger.getLogger(OrderQueueService.class.getName());
    private final String ORDERS_FILE_PATH;
//    private OrderQueue orderQueue;
    private CustomOrderQueue orderQueue;
    private final ServletContext context;

    public OrderQueueService(ServletContext context) {
        this.ORDERS_FILE_PATH = context.getRealPath("/WEB-INF/orders.txt");
//        this.orderQueue = new OrderQueue();
        this.orderQueue = new CustomOrderQueue();
        this.context = context;
        loadQueue();
    }

    private void loadQueue() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ORDERS_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 7 && "Pending".equals(parts[4].trim())) {
                    Date orderDate;
                    try {
                        orderDate = new SimpleDateFormat("yyyy-MM-dd").parse(parts[5]);
                    } catch (java.text.ParseException e) {
                        LOGGER.warning("Failed to parse date for order: " + parts[0] + ", using current date");
                        orderDate = new Date();
                    }
                    Order order = new Order(
                            parts[0], // orderId
                            parts[1], // userId
                            new ArrayList<>(), // items
                            Double.parseDouble(parts[3]), // totalAmount
                            orderDate, // orderDate
                            parts[6], // deliveryAddress
                            parts[4] // status
                    );
                    orderQueue.addOrder(order);
                }
            }
        } catch (IOException e) {
            LOGGER.severe("Error loading queue: " + e.getMessage());
        }
    }

    private void saveQueue() throws IOException {
        List<Order> allOrders = new ArrayList<>();
        OrderService orderService = new OrderService(context);
        allOrders.addAll(orderService.getAllOrders());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ORDERS_FILE_PATH))) {
            for (Order order : allOrders) {
                writer.write(order.toCSV());
                writer.newLine();
            }
        }
    }

    public boolean addToQueue(Order order) throws IOException {
        if ("Pending".equals(order.getStatus())) {
            boolean added = orderQueue.addOrder(order);
            if (added) saveQueue();
            return added;
        }
        return false;
    }

    public boolean updateFrontOrder() throws IOException {
        Order frontOrder = orderQueue.peekOrder();
        if (frontOrder != null && "Pending".equals(frontOrder.getStatus())) {
            frontOrder.setStatus("Confirmed");
            OrderService orderService = new OrderService(context);
            orderService.updateOrderStatus(frontOrder.getOrderId(), "Confirmed");
            orderQueue.removeOrder(); // Remove from queue after confirming
            saveQueue();
            return true;
        }
        return false;
    }

    // public OrderQueue getOrderQueue() {
    //    return orderQueue;
    //}

    public CustomOrderQueue getOrderQueue() { // Updated return type
        return orderQueue;
    }
}