package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private String orderId;
    private String userId;
    private List<FoodItem> items;
    private double totalAmount;
    private String status;
    private Date orderDate;
    private String deliveryAddress;

    public Order(String orderId, String userId, List<FoodItem> items, double totalAmount, Date orderDate, String deliveryAddress, String status) {
        this.orderId = orderId;
        this.userId = userId;
        this.items = items;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
        this.deliveryAddress = deliveryAddress;
        this.status = status;
    }

    // Getters and Setters
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public List<FoodItem> getItems() { return items; }
    public void setItems(List<FoodItem> items) { this.items = items; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }
    public String getDeliveryAddress() { return deliveryAddress; }
    public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }

    // Methods from class diagram
    public void createOrder() {
        // Logic to initialize order, typically handled by servlet
    }

    public double calculateTotal() {
        double total = 0.0;
        if (items != null) {
            for (FoodItem item : items) {
                total += item.getPrice();
            }
        }
        return total;
    }

