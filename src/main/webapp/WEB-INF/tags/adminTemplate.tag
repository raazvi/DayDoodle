<%@tag description="base page template" pageEncoding="UTF-8"%>
<%@attribute name="pageTitle"%>

<!DOCTYPE html>
<html>
<head>
    <title>${pageTitle}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/resources/favicon.ico">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-icons/1.8.1/font/bootstrap-icons.min.css">
    <!-- Custom style files -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/style/main.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/style/admin.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

</head>
<body>
<jsp:include page="/WEB-INF/components/menu/menu.jsp"/>
<main class="container-fluid mt-5">
    <jsp:doBody/>

    <jsp:include page="/WEB-INF/components/footer.jsp"/>
    <script src="${pageContext.request.contextPath}/WEB-INF/components/scripts/validateForm.js"></script>
</main>

</body>
</html>
