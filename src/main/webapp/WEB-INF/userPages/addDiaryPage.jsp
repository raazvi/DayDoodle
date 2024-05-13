<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:pageTemplate pageTitle="Add a page to your diary">
    <h1>Welcome to my app!</h1>
    <form action="${pageContext.request.contextPath}/AddDiaryPage" method="post">
        <label for="title">Title:</label>
        <input type="text" id="title" name="title" required><br><br>

        <label for="moodCategory">How did you feel:</label>
        <select id="moodCategory" name="moodCategory" onchange="populateMoodValues(this)">
            <option value="" selected disabled>Select a Mood Category</option>
            <option value="Happy">Happy</option>
            <option value="Sad">Sad</option>
            <option value="Disgusted">Disgusted</option>
            <option value="Angry">Angry</option>
            <option value="Fearful">Fearful</option>
            <option value="Bad">Bad</option>
            <option value="Surprised">Surprised</option>
        </select><br><br>

        <label for="moodValue">Be more specific:</label>
        <select id="moodValue" name="moodValue" required>
            <option value="" selected disabled>Select a Mood Value</option>
        </select><br><br>

        <label for="moreDetails">Fill in with more details about your day:</label><br>
        <textarea id="moreDetails" name="moreDetails" rows="4" cols="50"></textarea><br><br>

        <input type="submit" value="Submit">
    </form>
</t:pageTemplate>

<script>
    function populateMoodValues(select) {
        var moodValueSelect = document.getElementById("moodValue");
        moodValueSelect.innerHTML = "";

        var moodCategory = select.value;
        var moodValues = {
            "Happy": ["Playful", "Content", "Interested", "Proud", "Accepted", "Powerful", "Peaceful", "Trusting", "Optimistic"],
            "Sad": ["Lonely", "Vulnerable", "Despair", "Guilty", "Depressed", "Hurt"],
            "Disgusted": ["Awful", "Disappointed", "Disapproving", "Repelled"],
            "Angry": ["Let Down", "Humiliated", "Bitter", "Mad", "Aggressive", "Frustrated", "Distant", "Critical"],
            "Fearful": ["Scared", "Anxious", "Insecure", "Weak", "Rejected", "Threatened"],
            "Bad": ["Bored", "Busy", "Stressed", "Tired"],
            "Surprised": ["Startled", "Confused", "Amazed", "Excited"]
        };

        if (moodCategory in moodValues) {
            moodValues[moodCategory].forEach(function(value) {
                var option = document.createElement("option");
                option.value = value;
                option.text = value;
                moodValueSelect.add(option);
            });
        }
    }
</script>
