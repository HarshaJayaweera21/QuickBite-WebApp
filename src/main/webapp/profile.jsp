<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Profile</title>
    <link rel="icon" type="image/x-icon" href="external/letter-q.png">
    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=DM+Sans:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Patua+One&display=swap">
    <!-- Font Awesome for Icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile.css">
</head>
<body>
<%@ include file="header.jsp" %>

<!-- Toast Notification -->
<div id="toast" class="toast"></div>

<div class="container">
    <div class="main-content">
        <div class="profile-card">
            <div class="profile-header">
                <h2><i class="fas fa-user"></i> My Profile</h2>
            </div>
            <!-- Profile Details -->
            <div class="profile-details">
                <div>
                    <label>Name:</label>
                    <p>${sessionScope.user.name}</p>
                </div>
                <div>
                    <label>Email:</label>
                    <p>${sessionScope.user.email}</p>
                </div>
                <div>
                    <label>Phone:</label>
                    <p>${sessionScope.user.phoneNumber}</p>
                </div>
                <div>
                    <label>Address:</label>
                    <p>${sessionScope.user.address}</p>
                </div>
            </div>

            <!-- Action Buttons -->
            <div class="profile-buttons">
                <button id="editBtn" class="start-ordering">
                    <i class="fas fa-edit"></i> Edit Profile
                </button>
                <button id="deleteBtn" class="start-ordering delete-btn" data-item-name="your account" data-delete-url="${pageContext.request.contextPath}/user">
                    <i class="fas fa-trash"></i> Delete Account
                </button>
            </div>
        </div>
    </div>
</div>

<!-- Modal for Editing Profile -->
<div id="editModal" class="modal">
    <div class="modal-content">
        <h3>Edit Profile</h3>
        <form id="editForm" action="${pageContext.request.contextPath}/user" method="post">
            <input type="hidden" name="action" value="update">
            <div class="modal-field">
                <label for="name">Name:</label>
                <input type="text" id="name" name="name" value="${sessionScope.user.name}" required>
                <span id="nameError" class="error"></span>
            </div>
            <div class="modal-field">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="${sessionScope.user.email}" required>
                <span id="emailError" class="error"></span>
            </div>
            <div class="modal-field">
                <label for="phone">Phone:</label>
                <input type="tel" id="phone" name="phone" value="${sessionScope.user.phoneNumber}" pattern="[0-9]{10}" required placeholder="1234567890">
                <span id="phoneError" class="error"></span>
            </div>
            <div class="modal-field">
                <label for="address">Address:</label>
                <textarea id="address" name="address" required>${sessionScope.user.address}</textarea>
                <span id="addressError" class="error"></span>
            </div>
            <div class="modal-buttons">
                <button type="button" id="cancelBtn" class="cancel-btn">Cancel</button>
                <button type="submit" class="start-ordering">Save Changes</button>
            </div>
        </form>
    </div>
</div>

<!-- Modal for Delete Confirmation -->
<div id="deleteModal" class="modal">
    <div class="modal-content">
        <h3>Confirm Deletion</h3>
        <p id="deleteModalMessage"></p>
        <div class="modal-buttons">
            <button type="button" class="cancel-btn">Cancel</button>
            <button type="button" class="start-ordering delete-confirm-btn">Confirm</button>
        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>

