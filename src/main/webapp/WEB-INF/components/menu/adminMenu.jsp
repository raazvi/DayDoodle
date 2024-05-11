<header>
    <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
        <div class="container-fluid">
            <!-- Left side menu items -->
            <ul class="navbar-nav flex-fill">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/Feed"> Feed </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/Diary"> Diary </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/ViewCalendars"> Calendars </a>
                </li>
            </ul>
            <!-- Centered logo with link to home -->
            <a class="navbar-brand mx-auto" href="${pageContext.request.contextPath}">DayDoodle</a>
            <!-- Right side menu items -->
            <ul class="navbar-nav flex-fill justify-content-end">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/Profile"> Profile </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/Friends"> Friends </a>
                </li>
                <!-- Dropdown for Admin Settings -->
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle active" href="#" id="adminDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        Admin Settings
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="adminDropdown">
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/Users">Users</a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/Stats">Stats</a></li>
                    </ul>
                </li>
                <!-- Log Out -->
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/Logout"> Log Out </a>
                </li>
            </ul>
        </div>
    </nav>
</header>
