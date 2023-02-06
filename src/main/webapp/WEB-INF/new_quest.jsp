<%@ include file="parts/header.jsp"%>

<form action="newQuest" method="post">
    <div class="elementToLeft">
        <p class="elementToLeft">Quest name</p>
        <label for="name">
            <input class="elementToLeft" type="text" name="name" id="name" required>
        </label>
        <p class="elementToLeft">Quest description</p>
        <label for="text" >
            <textarea class="elementToLeft" name="text" id="text" rows="14" cols="25"></textarea>
        </label>
        <label>
            <button type="submit" class="elementToLeft">Create</button>
        </label>
    </div>

</form>

<%@ include file="parts/footer.jsp"%>
