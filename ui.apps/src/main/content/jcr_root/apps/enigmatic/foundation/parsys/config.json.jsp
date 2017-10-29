{
<%@page contentType="application/json; charset=UTF-8"
%><%@page session="false"
	import="org.apache.commons.lang3.StringUtils"
%><%@include file="/apps/enigmatic/global.jsp"
%><%
	String emptyText = currentStyle.get("new/cq:emptyText", "");
	if (StringUtils.isNotBlank(emptyText)) {
		%>"emptyText": "<%=emptyText%>"<%
	}
%>
}