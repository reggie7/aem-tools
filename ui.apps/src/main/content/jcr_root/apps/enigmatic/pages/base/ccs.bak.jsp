<%@page session="false" import="org.apache.sling.api.resource.Resource,
	org.apache.sling.api.resource.ValueMap,
	org.apache.sling.api.resource.ResourceUtil,
	com.day.cq.wcm.webservicesupport.*,
	java.util.Iterator" %><%
%><%@taglib prefix="cq" uri="http://www.day.com/taglibs/cq/1.0" %><%
%><cq:defineObjects/><%
	String[] services = pageProperties.getInherited("cq:cloudserviceconfigs", new String[]{});
	ConfigurationManager cfgMgr = slingRequest.getResourceResolver().adaptTo(ConfigurationManager.class);
	if (cfgMgr != null) {
		String username = null;
		Configuration cfg = cfgMgr.getConfiguration("mobirise-database", services);
		if (cfg != null) {
			username = cfg.get("username", null);
		}
%>
<h1><%= username %></h1>
<%
		Iterator<Service> i = cfgMgr.getServices();
		while (i.hasNext()) {
			%><p><%= i.next().getPath() %></p><%
		}
	}
%>