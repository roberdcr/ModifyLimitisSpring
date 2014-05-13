<%@ include file="/WEB-INF/views/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
  <title><fmt:message key="title"/></title>
  <style>
    .error { color: red; }
  </style>  
</head>
<body>
<h1><fmt:message key="priceincrease.heading"/></h1>
<form:form method="post" commandName="changeLimit">
  <table width="95%" bgcolor="f8f8ff" border="0" cellspacing="0" cellpadding="5">
    <tr>
      <td align="right" width="20%">Diary Limit:</td>
        <td width="20%">
          <form:input path="diaryLimit"/>
        </td>
        <td width="60%">
          <form:errors path="diaryLimit" cssClass="error"/>
        </td>
    </tr>
    <tr>
      <td align="right" width="20%">Monthly Limit:</td>
        <td width="20%">
          <form:input path="monthlyLimit"/>
        </td>
        <td width="60%">
          <form:errors path="monthlyLimit" cssClass="error"/>
        </td>
    </tr>
  </table>
  <br>
  <input type="submit" value="Save Changes">
</form:form>
<a href="<c:url value="changeLimits.htm"/>">Home</a>
</body>
</html>