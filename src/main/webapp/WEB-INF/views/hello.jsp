<%@ include file="/WEB-INF/views/include.jsp" %>

<html>
  <head><title><fmt:message key="title"/></title></head>
  <body>
    <h1><fmt:message key="heading"/></h1>
    <p><fmt:message key="greeting"/> <c:out value="${model.now}"/></p>
    <h3>Card Information:</h3>
    <c:forEach items="${model.products}" var="prod">
      Card number: <i><c:out value="${prod.getCardNumber()}"/></i><br/>
      Card type: <i><c:out value="${prod.getCardType()}"/></i><br/>
      Buy limit diary: <i><c:out value="${prod.getBuyLimitDiary()}"/></i> Euros<br/>
      Buy limit monthly: <i><c:out value="${prod.getBuyLimitMonthly()}"/></i> Euros<br/>
      Cash limit diary: <i><c:out value="${prod.getCashLimitDiary()}"/></i> Euros<br/>
      Cash limit monthly: <i><c:out value="${prod.getCashLimitMonthly()}"/></i> Euros<br/>
    </c:forEach>
    <br>
    <a href="<c:url value="buyLimits.htm"/>">Change Buy Limits</a>
    <a href="<c:url value="cashLimits.htm"/>">Change Cash Limits</a>
    <br>
  </body>
</html>