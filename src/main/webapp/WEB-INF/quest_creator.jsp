<%@ include file="parts/header.jsp"%>

<div id="tree-container"></div>
<div id="rightDiv"></div>


<div class="newQuestId" id="${sessionScope.newQuestId == null ? -1 : sessionScope.newQuestId}"></div>
<div class="tt" id="${sessionScope.current_quest == null ? -1 : sessionScope.current_quest}"></div>

<script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
<script src="https://d3js.org/d3.v3.min.js"></script>
<script src="<c:url value="/js/DAG.js"/>"></script>
<%@ include file="parts/footer.jsp"%>
