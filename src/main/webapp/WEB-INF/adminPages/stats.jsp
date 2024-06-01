<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Stats</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
<t:adminTemplate pageTitle="Admin Stats">
    <div class="container mt-4">
        <h2>Stats Page</h2>

        <div class="row">
            <div class="col-md-12 center-text">
                <h4>Total Users</h4>
                <p>${totalUsers}</p>
            </div>
        </div>

        <div class="chart-container">
            <h4 class="chart-heading">Age Distribution</h4>
            <canvas id="ageGroupsChart"></canvas>
            <table class="table table-striped mt-3">
                <thead>
                <tr>
                    <th>Age Group</th>
                    <th>Count</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="entry" items="${ageGroups}">
                    <tr>
                        <td>${entry.key}</td>
                        <td>${entry.value}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="chart-container">
            <h4 class="chart-heading">Account Creation Trends</h4>
            <canvas id="accountCreationTrendsChart"></canvas>
            <table class="table table-striped mt-3">
                <thead>
                <tr>
                    <th>Month</th>
                    <th>Account Creations</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="entry" items="${accountCreationTrends}">
                    <tr>
                        <td>${entry.key}</td>
                        <td>${entry.value}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</t:adminTemplate>

<script>
    // Age Groups Chart
    var ageGroupsCtx = document.getElementById('ageGroupsChart').getContext('2d');
    var ageGroupsChart = new Chart(ageGroupsCtx, {
        type: 'pie',
        data: {
            labels: [<c:forEach var="entry" items="${ageGroups}">"${entry.key}", </c:forEach>],
            datasets: [{
                label: 'Age Groups',
                data: [<c:forEach var="entry" items="${ageGroups}">${entry.value}, </c:forEach>],
                backgroundColor: [
                    'rgba(255, 99, 132, 0.2)',
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(75, 192, 192, 0.2)',
                    'rgba(153, 102, 255, 0.2)',
                    'rgba(255, 159, 64, 0.2)'
                ],
                borderColor: [
                    'rgba(255, 99, 132, 1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(75, 192, 192, 1)',
                    'rgba(153, 102, 255, 1)',
                    'rgba(255, 159, 64, 1)'
                ],
                borderWidth: 1
            }]
        },
        options: {
            responsive: true
        }
    });

    // Account Creation Trends Chart
    var accountCreationTrendsCtx = document.getElementById('accountCreationTrendsChart').getContext('2d');
    var accountCreationTrendsChart = new Chart(accountCreationTrendsCtx, {
        type: 'bar',
        data: {
            labels: [<c:forEach var="entry" items="${accountCreationTrends}">"${entry.key}", </c:forEach>],
            datasets: [{
                label: 'Account Creations',
                data: [<c:forEach var="entry" items="${accountCreationTrends}">${entry.value}, </c:forEach>],
                backgroundColor: 'rgba(54, 162, 235, 0.2)',
                borderColor: 'rgba(54, 162, 235, 1)',
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
</script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
