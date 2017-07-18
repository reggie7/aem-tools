<%@ page session="false" import="
	com.day.cq.wcm.api.components.EditConfig,
	org.apache.commons.lang3.StringUtils" %>
<%@include file="/libs/foundation/global.jsp"%>
<cq:include script="/libs/foundation/components/parsys/new/new.jsp" />
<%
	String emptyText = currentStyle.get("cq:emptyText", "");
	if (StringUtils.isNotBlank(emptyText)) {
		EditConfig editConfig = editContext.getEditConfig();
		editConfig.setEmpty(true);
		editConfig.setEmptyText(emptyText);
	}
%>