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
                    <label for="firstname">First Name</label>
                    <input type="text" class="form-control" id="firstname" name="firstname" placeholder="Enter your first name" value="">
                </div>
            </div>
            <div class="row">
                <div class="col-md-6 mb-3">
                    <label for="lastname">Last Name</label>
                    <input type="text" class="form-control" id="lastname" name="lastname" placeholder="Enter your last name" value="">
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
                    <label for="birthdate">Birthday: </label>
                    <input type="date" class="form-control" id="birthdate" name="birthdate" placeholder="Enter your birth date" value="">
                </div>
            </div>
            <div class="row">
                <div class="col-md-6 mb-3">
                    <label for="zodiacSign">Zodiac Sign</label>
                    <select class="form-control" id="zodiacSign" name="zodiacSign">
                        <option value="Aries">Aries</option>
                        <option value="Taurus">Taurus</option>
                        <option value="Gemini">Gemini</option>
                        <option value="Cancer">Cancer</option>
                        <option value="Leo">Leo</option>
                        <option value="Virgo">Virgo</option>
                        <option value="Libra">Libra</option>
                        <option value="Scorpio">Scorpio</option>
                        <option value="Sagittarius">Sagittarius</option>
                        <option value="Capricorn">Capricorn</option>
                        <option value="Aquarius">Aquarius</option>
                        <option value="Pisces">Pisces</option>
                    </select>
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
            <input type="submit" value="Done" class="btn btn-primary">
        </form>

        <h2> P.S.: You'll be able to edit these details later by clicking on your profile, so don't stress out!</h2>

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

    // Add an event listener to the file input to call setImageFormat function when a file is selected
    document.getElementById('profilePicture').addEventListener('change', setImageFormat);
</script>