{
<%@page contentType="application/json; charset=UTF-8"
%><%@page session="false"
	import="com.day.cq.wcm.api.components.EditConfig,
	org.apache.commons.lang3.StringUtils"
%><%@include file="/libs/foundation/global.jsp"
%><%
	String emptyText = currentStyle.get("new/cq:emptyText", "");
	if (StringUtils.isNotBlank(emptyText)) {
		%>"emptyText": "<%=emptyText%>"<%
	}
%>
}