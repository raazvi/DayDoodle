<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:pageTemplate pageTitle="Edit Profile">
    <div class="container text-center">
        <div class="row">
            <h3>Since this is the first time you logged into your account, tell us more about yourself!</h3>
        </div>

        <form method="POST" action="${pageContext.request.contextPath}/UpdateUserDetails" enctype="multipart/form-data">
            <div class="row">
                <!-- Profile Picture Field -->
                <div class="col-md-6 mb-3">
                    <label for="profilePicture">Profile Picture</label>
                    <input type="file" class="form-control" id="profilePicture" name="profilePicture">
                </div>
                <!-- End of Profile Picture Field -->
                <div class="col-md-6 mb-3">
                    <label for="firstname">*First Name</label>
                    <input type="text" class="form-control" id="firstname" name="firstname" placeholder="Enter your first name" value="" required>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6 mb-3">
                    <label for="lastname">*Last Name</label>
                    <input type="text" class="form-control" id="lastname" name="lastname" placeholder="Enter your last name" value="" required>
                </div>
                <div class="col-md-6 mb-3">
                    <label for="nickname">Nickname</label>
                    <input type="text" class="form-control" id="nickname" name="nickname" placeholder="Enter your nickname" value="">
                </div>
            </div>
            <div class="row">
                <div class="col-md-6 mb-3">
                    <label for="location">Location</label>
                    <input type="text" class="form-control" id="location" name="location" placeholder="Enter your location" value="">
                </div>
                <div class="col-md-6 mb-3">
                    <label for="birthdate">*Birthday: </label>
                    <input type="date" class="form-control" id="birthdate" name="birthdate" placeholder="Enter your birth date" value="" required>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6 mb-3">
                    <p>The zodiac sign is filled automatically based on your birthday!</p>
                    <label for="zodiacSign">Zodiac Sign</label>
                    <input type="text" class="form-control" id="zodiacSign" name="zodiacSign" placeholder="Zodiac Sign" disabled>
                </div>
                <div class="col-md-6 mb-3">
                    <label for="pronouns">Pronouns</label>
                    <input type="text" class="form-control" id="pronouns" name="pronouns" placeholder="Enter your pronouns" value="">
                </div>
            </div>
            <div class="row">
                <div class="col-md-6 mb-3">
                    <label for="description">About yourself</label>
                    <input type="text" class="form-control" id="description" name="description" placeholder="Tell us about yourself" value="">
                </div>
            </div>
            <!-- Hidden Field for Username -->
            <input type="hidden" name="username" value="${sessionScope.user.username}">
            <input type="hidden" id="imageFormat" name="imageFormat">
            <!-- Submit Button -->
            <input type="submit" value="Done" class="btn btn-primary submit-btn">
        </form>

        <p>Even though not all fields are mandatory, you have to at least let us know your full name and your birthday (those fields are marked with a star :D )</p>
        <h2>P.S.: You'll be able to edit these details later by clicking on your profile, so don't stress out!</h2>
    </div>
</t:pageTemplate>

<script>
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

    document.getElementById('birthdate').addEventListener('change', setZodiacSign);
</script>
