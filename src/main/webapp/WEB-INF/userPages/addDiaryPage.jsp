<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:pageTemplate pageTitle="Add a page to your diary">
    <div class="new-entry-section">
        <h3 class="welcome-title">Write a new page...</h3>
        <form class="new-entry-form" action="${pageContext.request.contextPath}/AddDiaryPage" method="post">
            <label for="date">Date:</label>
            <input type="date" id="date" name="date"><br>

            <label for="title">Title:</label>
            <input type="text" id="title" name="title" required><br>

            <label for="moodCategory">How did you feel:</label>
            <div class="mood-select">
                <select id="moodCategory" name="moodCategory" onchange="populateMoodValues(this)" required>
                    <option value="" selected disabled>Select a mood...</option>
                    <option value="Happy">Happy</option>
                    <option value="Sad">Sad</option>
                    <option value="Disgusted">Disgusted</option>
                    <option value="Angry">Angry</option>
                    <option value="Fearful">Fearful</option>
                    <option value="Bad">Bad</option>
                    <option value="Surprised">Surprised</option>
                </select>
                <div id="moodBubble" class="mood-bubble"></div>
            </div><br>

            <label for="moodValue">Be more specific:</label>
            <select id="moodValue" name="moodValue" required>
                <option value="" selected disabled>Your mood in a more specific way...</option>
            </select><br>

            <label for="moreDetails">Fill in with more details about your day:</label><br>
            <textarea id="moreDetails" name="moreDetails" rows="4" cols="50" required></textarea><br>

            <input type="submit" value="Submit">
        </form>
    </div>
</t:pageTemplate>

<script>
    function populateMoodValues(select) {
        var moodValueSelect = document.getElementById("moodValue");
        var moodBubble = document.getElementById("moodBubble");
        moodValueSelect.innerHTML = "";
        moodBubble.style.display = 'none';

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
            moodBubble.textContent = moodCategory;
            moodBubble.style.display = 'inline-block';
        }
    }
</script>
