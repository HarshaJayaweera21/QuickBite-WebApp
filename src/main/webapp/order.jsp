<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Order Confirmation</title>
  <link rel="icon" type="image/x-icon" href="external/letter-q.png">
  <!-- Google Fonts (Poppins, DM Sans, Patua One) -->
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css2?family=DM+Sans:wght@300;400;500;600;700&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Patua+One:wght@400&display=swap">
  <!-- Font Awesome for Icons -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
  <!-- Custom CSS -->
  <link rel="stylesheet" href="css/order.css">
  <link rel="stylesheet" href="css/styles.css">
</head>
<body>
<%@ include file="header.jsp" %>

<!-- Toast Notification Container -->
<div id="toast" class="toast"></div>

<div class="container">
  <div class="main-content fadeIn">
    <div class="header">
      <h2>Order Confirmation</h2>
    </div>
    <div class="order-details">
      <c:if test="${not empty cartItems}">
        <c:forEach var="cartItem" items="${cartItems}">
          <div class="order-item">
            <span>${cartItem.foodItem.name} x ${cartItem.quantity}</span>
            <span>${String.format("%.2f", cartItem.foodItem.price * cartItem.quantity)} LKR</span>
          </div>
        </c:forEach>
        <div class="order-total">
          <span>Subtotal:</span>
          <span>${subtotal} LKR</span>
        </div>
        <div class="order-total">
          <span>Discount (20%):</span>
          <span>${discount} LKR</span>
        </div>
        <div class="order-total">
          <span>Delivery Fee:</span>
          <span>${deliveryFee}</span>
        </div>
