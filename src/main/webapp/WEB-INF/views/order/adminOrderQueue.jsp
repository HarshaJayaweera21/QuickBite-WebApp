<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quick Bites - Admin Order Management</title>
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/external/letter-q.png">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=DM+Sans:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Patua+One:wght@400&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin-queue.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
</head>
<body>

<div class="header-container">
    <div class="header-inner">
        <!-- Top Row: Logo and User Actions -->
        <div class="top-row">
            <!-- Logo -->
            <div class="logo-container">
                <img alt="japanesefood8198" src="${pageContext.request.contextPath}/external/japanesefood8198-lw1q.svg">
                <a>QuickBite</a>
            </div>
            <!-- User Actions -->
            <div class="login-signup">
                <c:choose>
                    <c:when test="${not empty sessionScope.user}">
                        <%--                        <!-- User is logged in -->--%>
                        <%--                        <button class="profile-btn" onclick="window.location.href='${pageContext.request.contextPath}/user?action=profile'">--%>
                        <%--                            <i class="fas fa-user"></i> Profile--%>
                        <%--                        </button>--%>
                        <button class="logout-btn" onclick="window.location.href='${pageContext.request.contextPath}/user?action=logout'">
                            <i class="fas fa-sign-out-alt"></i> Logout
                        </button>
                    </c:when>
                    <c:otherwise>
                        <!-- User is not logged in -->
                        <button class="login-btn" onclick="window.location.href='${pageContext.request.contextPath}/login.jsp'">
                            <i class="fas fa-sign-in-alt"></i> Login
                        </button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <!-- Bottom Row: Navigation Links -->
        <div class="nav-container">
            <!-- Hamburger Menu Toggle (Visible on Mobile) -->
            <div class="hamburger" onclick="toggleMenu()">
                <div></div>
                <div></div>
                <div></div>
            </div>
            <!-- Navigation Menu -->
            <div class="nav-menu" id="nav-menu">
                <a href="${pageContext.request.contextPath}/admin/users" class="nav-link">Manage Users</a>
                <a href="${pageContext.request.contextPath}/admin/food-items" class="nav-link">Manage Food & Category</a>
                <a href="${pageContext.request.contextPath}/queue" class="nav-link">Manage Orders</a>
            </div>
        </div>
    </div>
</div>


<div id="toast" class="toast"></div>

<div class="container">
    <div class="main-content">
        <div class="header">
            <h2>Order Queue</h2>
        </div>

        <!-- Pending Orders Section -->
        <div class="queue-container">
            <h3>Pending Orders</h3>
            <c:if test="${orderQueue.isEmpty()}">
                <p>No pending orders in queue.</p>
            </c:if>
            <c:if test="${not orderQueue.isEmpty()}">
                <div class="queue-item">
                    <h4>Front Order</h4>
                    <p>Order ID: ${orderQueue.peekOrder().orderId}</p>
                    <p>User ID: ${orderQueue.peekOrder().userId}</p>
                    <p>Date: <c:out value="${orderQueue.peekOrder().orderDate != null ? orderQueue.peekOrder().orderDate : 'N/A'}" /></p>
                    <p>Total: ${String.format("%.2f", orderQueue.peekOrder().totalAmount)} LKR</p>
                    <p>Status: ${orderQueue.peekOrder().status}</p>
                    <form action="${pageContext.request.contextPath}/queue" method="post">
                        <input type="hidden" name="action" value="update">
                        <button type="submit" class="queue-btn">Confirm Order</button>
                    </form>
                </div>
            </c:if>
        </div>

        <!-- Confirmed Orders Section -->
        <div class="confirmed-container">
            <h3>Confirmed Orders</h3>
            <c:if test="${confirmedOrders.isEmpty()}">
                <p>No confirmed orders.</p>
            </c:if>
            <c:forEach var="order" items="${confirmedOrders}">
                <div class="confirmed-item">
                    <p>Order ID: ${order.orderId}</p>
                    <p>User ID: ${order.userId}</p>
                    <p>Date: <c:out value="${order.orderDate != null ? order.orderDate : 'N/A'}" /></p>
                    <p>Total: ${String.format("%.2f", order.totalAmount)} LKR</p>
                    <p>Status: ${order.status}</p>
                    <form action="${pageContext.request.contextPath}/queue" method="post">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="orderId" value="${order.orderId}">
                        <button type="submit" class="queue-btn remove-btn">Remove Order</button>
                    </form>
                </div>
            </c:forEach>
        </div>
    </div>
</div>

<script>
    window.onload = function() {
        const urlParams = new URLSearchParams(window.location.search);
        const toast = document.getElementById('toast');
        if (urlParams.get('status') === 'updated') {
            toast.textContent = "Order confirmed successfully!";
            toast.classList.add('show');
            setTimeout(() => toast.classList.remove('show'), 2000);
        } else if (urlParams.get('status') === 'deleted') {
            toast.textContent = "Order removed successfully!";
            toast.classList.add('show');
            setTimeout(() => toast.classList.remove('show'), 2000);
        } else if (urlParams.get('error')) {
            toast.textContent = urlParams.get('error');
            toast.classList.add('show');
            setTimeout(() => toast.classList.remove('show'), 2000);
        }
    };
</script>
</body>
</html>