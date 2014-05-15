<%@ include file="/WEB-INF/views/include.jsp"%>

<html>
<head>
<title><fmt:message key="title" /></title>
</head>
<body>
	<h1>
		<fmt:message key="heading" />
	</h1>
	<p>
		<fmt:message key="greeting" />
		<c:out value="${model.now}" />
	</p>
	<h3>Card Information:</h3>
	<table width="95%" bgcolor="f8f8ff" border="0" cellspacing="0"
		cellpadding="5">
		<tr>
			<td align="left" width="20%">Card number:</td>
			<td align="right" width="20%"><i><c:out
						value="${model.products.getCardNumber()}" /></i><br /></td>
		</tr>
		<tr>
			<td align="left" width="20%">Card type:</td>
			<td align="right" width="20%"><i><c:out
						value="${model.products.getCardType()}" /></i><br /></td>
		</tr>
		<tr>
			<td align="left" width="20%">Buy limit diary:</td>
			<td align="right" width="20%"><i><c:out
						value="${model.products.getBuyLimitDiary()}" /></i> Euros</td>
		</tr>
		<tr>
			<td align="left" width="20%">Buy limit monthly:</td>
			<td align="right" width="20%"><i><c:out
						value="${model.products.getBuyLimitMonthly()}" /></i> Euros</td>
		</tr>
		<tr>
			<td align="left" width="20%">Cash limit diary:</td>
			<td align="right" width="20%"><i><c:out
						value="${model.products.getCashLimitDiary()}" /></i> Euros</td>
		</tr>
		<tr>
			<td align="left" width="20%">Cash limit monthly:</td>
			<td align="right" width="20%"><i><c:out
						value="${model.products.getCashLimitMonthly()}" /></i> Euros</td>
		</tr>
	</table>
	<br>
	<button type="button"
		onClick="location.href='<c:url value="buyLimits.htm"/>'">Change
		Buy Limits</button> 
	<button type="button"
		onClick="location.href='<c:url value="cashLimits.htm"/>'">Change
		Cash Limits</button>
</body>
</html>