<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:pageTemplate pageTitle="Edit Profile">
    <div class="container text-center">
        <div class="row">
            <h3>Edit Profile</h3>
        </div>

        <form method="POST" action="${pageContext.request.contextPath}/EditProfile" enctype="multipart/form-data">
            <div class="row">
                <!-- Profile Picture Field -->
                <div class="col-md-6 mb-3">
                    <label for="profilePicture">Profile Picture</label><br>
                    <img src="data:image/jpeg;base64,${user.profilePicture}" alt="Profile Picture" width="150" height="150"><br>
                    <input type="file" id="profilePicture" name="profilePicture">
                </div>
                <!-- End of Profile Picture Field -->
                <div class="col-md-6 mb-3">
                    <label for="firstname">First Name</label>
                    <input type="text" class="form-control" id="firstname" name="firstname" placeholder="First Name" value="${user.firstName}">
                </div>
            </div>
            <div class="row">
                <div class="col-md-6 mb-3">
                    <label for="lastname">Last Name</label>
                    <input type="text" class="form-control" id="lastname" name="lastname" placeholder="Last Name" value="${user.lastName}">
                </div>
                <div class="col-md-6 mb-3">
                    <label for="nickname">Nickname</label>
                    <input type="text" class="form-control" id="nickname" name="nickname" placeholder="Nickname" value="${user.nickname}">
                </div>
            </div>
            <div class="row">
                <div class="col-md-6 mb-3">
                    <label for="location">Location</label>
                    <input type="text" class="form-control" id="location" name="location" placeholder="Location" value="${user.location}">
                </div>
                <div class="col-md-6 mb-3">
                    <label for="birthdate">Birthday:</label>
                    <input type="date" class="form-control" id="birthdate" name="birthdate" value="${user.birthDate}">
                </div>
            </div>
            <div class="row">
                <div class="col-md-6 mb-3">
                    <p> The zodiac sign is filled automatically based on your birthday!</p>
                    <label for="zodiacSign">Zodiac Sign</label>
                    <select class="form-control" id="zodiacSign" name="zodiacSign" disabled>
                        <c:forEach var="sign" items="${['Aries', 'Taurus', 'Gemini', 'Cancer', 'Leo', 'Virgo', 'Libra', 'Scorpio', 'Sagittarius', 'Capricorn', 'Aquarius', 'Pisces']}">
                            <option value="${sign}" ${user.zodiacSign eq sign ? 'selected' : ''}>${sign}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-6 mb-3">
                    <label for="pronouns">Pronouns</label>
                    <input type="text" class="form-control" id="pronouns" name="pronouns" placeholder="Pronouns" value="${user.pronouns}">
                </div>
            </div>
            <div class="row">
                <div class="col-md-6 mb-3">
                    <label for="description">About Yourself</label>
                    <textarea class="form-control" id="description" name="description" placeholder="Tell us about yourself">${user.description}</textarea>
                </div>
            </div>
            <!-- Hidden Field for Username -->
            <input type="hidden" name="username" value="${user.username}">
            <input type="hidden" id="imageFormat" name="imageFormat">

            <!-- Submit Button -->
            <button type="submit" class="btn btn-primary">Submit Changes</button>
        </form>
    </div>
</t:pageTemplate>

<script>
    // Function to extract image format and set the hidden input value
    function setImageFormat() {
        // Get the file input element
        var input = document.getElementById('profilePicture');
        // Check if a file is selected
        if (input.files && input.files[0]) {
            // Get the file name
            var fileName = input.files[0].name;
            // Extract the file extension to determine the format
            var imageFormat = fileName.split('.').pop().toLowerCase();
            // Set the value of the hidden input
            document.getElementById('imageFormat').value = imageFormat;
        }
    }

    // Function to calculate and set the zodiac sign based on the birth date
    function setZodiacSign() {
        var birthdateInput = document.getElementById('birthdate');
        var zodiacSignInput = document.getElementById('zodiacSign');

        if (birthdateInput.value) {
            var birthdate = new Date(birthdateInput.value);
            var month = birthdate.getMonth() + 1;
            var day = birthdate.getDate();

            var zodiacSigns = ["Capricorn", "Aquarius", "Pisces", "Aries", "Taurus", "Gemini", "Cancer", "Leo", "Virgo", "Libra", "Scorpio", "Sagittarius"];
            var endDates = [20, 19, 20, 20, 21, 21, 22, 23, 23, 23, 22, 21];

            var zodiacIndex = month - (day < endDates[month - 1] ? 1 : 0);
            if (zodiacIndex < 0) {
                zodiacIndex = 11;
            }

            zodiacSignInput.value = zodiacSigns[zodiacIndex];
        }
    }

    // Add event listeners
    document.getElementById('profilePicture').addEventListener('change', setImageFormat);
    document.getElementById('birthdate').addEventListener('change', setZodiacSign);

    // Initial call to set the zodiac sign if the birth date is already filled
    setZodiacSign();
</script>
