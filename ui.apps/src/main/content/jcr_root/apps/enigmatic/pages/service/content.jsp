<%@page session="false" contentType="text/html" pageEncoding="utf-8"
%><%@include file="/apps/enigmatic/global.jsp" %>
<div>
	<h3><%= component.getTitle() %></h3>
	<%-- use HTL instead --%>
	<cq:include script="content.html" />
</div>
<div class="when-config-successful">
	<%@include file="/libs/cq/cloudserviceconfigs/components/childlist/childlist.jsp" %>
	<%= printChildren(xssAPI, currentPage, request) %>
</div>